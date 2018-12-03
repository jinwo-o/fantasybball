package ca.homedepot.relevancy.processors;

import ca.homedepot.relevancy.config.properties.ReportProperties;
import ca.homedepot.relevancy.model.SolrTopSearchResult;
import ca.homedepot.relevancy.model.TopSearchResult;
import ca.homedepot.relevancy.model.TopSearches;
import ca.homedepot.relevancy.model.RelevancyReportParameters;
import ca.homedepot.relevancy.services.hybris.HybrisService;
import ca.homedepot.relevancy.services.oauth.OAuthService;
import ca.homedepot.relevancy.services.solr.SolrService;
import ca.homedepot.relevancy.util.SearchTermUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Component
@Scope("prototype")
public class TopResultMatchProcessor {

    private static final int TOP_SEARCH_RETURN_RESULT = 3;
    private static final int PRODUCT_SEARCH_RETURN_RESULT = 1000;

    private static final String MATCH_RESULT = "match";
    private static final String NOT_MATCH_RESULT = "not_match";

    // Required dependencies.
    private final ReportProperties reportProperties;
    private final HybrisService hybrisService;
    private final SolrService solrService;
    private final OAuthService oauthService;

    // ExecutorService that will manage the match tasks.
    private final ExecutorService executor;

    // Map containing the sku and the match result.
    private Map<String, SolrTopSearchResult> matchResults;

    @Autowired
    public TopResultMatchProcessor(final ReportProperties reportProperties, final HybrisService hybrisService,
                                   final SolrService solrService, final OAuthService oauthService) {
        this.reportProperties = reportProperties;
        this.hybrisService = hybrisService;
        this.solrService = solrService;
        this.oauthService = oauthService;
        this.executor = Executors.newFixedThreadPool(reportProperties.getRelevancyProcessorThreadCount());
    }

    /**
     * Activate top result match tasks for the given parameters. This method will
     * distribute the search terms and start executing tasks to retrieve match
     * results.
     *
     * @param searchTerms
     * @param relevancyReportParameters
     */
    public void executeTopResultMatchTask(final List<String> searchTerms,
                                          final RelevancyReportParameters relevancyReportParameters,
                                          final Map<String, SolrTopSearchResult> matchResults) {

        this.matchResults = matchResults;

        // Distribute the list of search terms evenly into sub lists.
        final List<List<String>> partition = SearchTermUtil.partitionThread(reportProperties, searchTerms);
        // Each of the sublist will be passed to a match task.
        for (int threadIndex = 0; threadIndex < reportProperties.getRelevancyProcessorThreadCount(); threadIndex++) {
            final TopResultMatchTask matchTask = new TopResultMatchTask(partition.get(threadIndex),
                  relevancyReportParameters);
            executor.submit(matchTask);
        }
    }

    public void deactivateTopResultMatchTasks() {
        executor.shutdownNow();
    }

    public Map<String, SolrTopSearchResult> getMatchResults() {
        return this.matchResults;
    }

    private class TopResultMatchTask implements Runnable {

        private final Logger LOG = LoggerFactory.getLogger(TopResultMatchTask.class);

        private final List<String> searchTerms;
        private final RelevancyReportParameters relevancyReportParameters;

        private TopResultMatchTask(final List<String> searchTerms,
                                   final RelevancyReportParameters relevancyReportParameters) {
            this.searchTerms = searchTerms;
            this.relevancyReportParameters = relevancyReportParameters;
        }

        @Override
        public void run() {

            // Retrieve the access token once in order to make requests to hybris.
            final OAuth2AccessToken accessToken = oauthService.getAccessToken(relevancyReportParameters.getHybrisServer());

            for (final String searchQuery : searchTerms) {

                if (Thread.currentThread().isInterrupted()) {
                    break;
                }

                final SolrTopSearchResult solrTopSearchResult = new SolrTopSearchResult();

                try {

                    if (StringUtils.isNotEmpty(searchQuery)) {

                        final List<String> topSearchResults = searchTopResults(searchQuery, relevancyReportParameters,
                                accessToken, TOP_SEARCH_RETURN_RESULT);
                        final List<String> productSearchResults = searchResults(searchQuery, relevancyReportParameters,
                                accessToken, PRODUCT_SEARCH_RETURN_RESULT);

                        if (CollectionUtils.isNotEmpty(productSearchResults) && CollectionUtils.isNotEmpty(topSearchResults)) {
                            final int index = productSearchResults.size() < TOP_SEARCH_RETURN_RESULT ? productSearchResults.size() : TOP_SEARCH_RETURN_RESULT;
                            final List<String> topProductSearchResults = productSearchResults.subList(0, index);

                            for (int j = 0; j < topSearchResults.size(); ++j) {
                                if (!topSearchResults.get(j).equalsIgnoreCase(topProductSearchResults.get(j))) {
                                    solrTopSearchResult.setSearchResult(NOT_MATCH_RESULT);
                                    solrTopSearchResult.setSkuCode(topSearchResults.get(j));
                                    break;
                                }
                                solrTopSearchResult.setSearchResult(MATCH_RESULT);
                            }
                        }
                    }
                } catch (final Throwable t) {
                    LOG.error("Error calculating match result: " + searchQuery, t);
                } finally {
                    matchResults.put(searchQuery, solrTopSearchResult);
                }
            }
        }
    }

    private List<String> searchResults(final String searchQuery, final RelevancyReportParameters relevancyReportParameters,
                                       final OAuth2AccessToken accessToken, final int searchReturnResult) {
        // Retrieve the SolrQuery from hybris.
        final SolrQuery solrQuery = hybrisService.getSolrQuery(searchQuery, relevancyReportParameters,
                accessToken);
        solrQuery.set("rows", searchReturnResult);
        if (solrQuery != null) {

            // Query solr and retrieve a list of skus.
            final List<String> solrQueryResults = solrService.querySolrGetProductList(solrQuery,
                    relevancyReportParameters.getSolrServer());
            return solrQueryResults;
        }
        return null;
    }

    private List<String> searchTopResults(final String searchQuery, final RelevancyReportParameters relevancyReportParameters,
                                       final OAuth2AccessToken accessToken, final int searchReturnResult) {

        final TopSearchResult topSearchResult = hybrisService.getTopSearchResult(searchQuery, relevancyReportParameters,
                accessToken);

        if (topSearchResult != null) {
            final List<String> results = new ArrayList<>();
            final List<TopSearches> topsearches = topSearchResult.getTopsearches();
            if (CollectionUtils.isNotEmpty(topsearches)) {
                for (TopSearches search : topsearches) {
                    String link = search.getLink();
                    if (StringUtils.isNotEmpty(link)) {
                        final String partition = StringUtils.substringBefore(search.getLink(), ".html");
                        final String SkuCode = partition.substring(partition.lastIndexOf(".") + 1, partition.length());
                        results.add(SkuCode);
                    }
                }
                return results;
            }
        }
        return null;
    }

}
