package ca.homedepot.relevancy.services.hybris;

import java.net.URI;

import ca.homedepot.relevancy.model.TopSearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;

import ca.homedepot.relevancy.model.SearchOption;
import ca.homedepot.relevancy.model.ServerEnvironment;
import ca.homedepot.relevancy.model.RelevancyReportParameters;
import ca.homedepot.relevancy.services.oauth.OAuthService;
import ca.homedepot.relevancy.services.server.ServerService;
import ca.homedepot.relevancy.services.web.WebService;

@Component
public class DefaultHybrisService implements HybrisService {

    private static final String QUERY_PARAM_NAME = "q";
    private static final String TOP_RESULTS_QUERY_PARAM_NAME = "query";
    private static final int TOP_SEARCH_RETURN_RESULT = 3;
    private static final int PRODUCT_SEARCH_RETURN_RESULT = 1000;

    private final WebService webService;
    private final OAuthService oauthService;
    private final ServerService serverService;

    @Autowired
    public DefaultHybrisService(final WebService webService, final OAuthService oauthService,
                                final ServerService serverService) {
        this.webService = webService;
        this.oauthService = oauthService;
        this.serverService = serverService;
    }

    @Override
    public SolrQuery getSolrQuery(final String query, final RelevancyReportParameters userReport, OAuth2AccessToken accessToken) {

        if (accessToken == null || accessToken.getTokenType() == null || accessToken.getValue() == null) {
            return null;
        }

        final ServerEnvironment hybrisServer = userReport.getHybrisServer();
        final String URL = serverService.getHybrisServer(hybrisServer) + "/homedepotcacommercewebservices/v2/homedepotca/solrQuery";
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL).queryParam(QUERY_PARAM_NAME, query)
                .queryParam(RelevancyReportParameters.NLP_PARAM_NAME, userReport.isNlpEnable())
                .queryParam(RelevancyReportParameters.BOOST_PARAM_NAME, userReport.isBoostBuryEnable())
                .queryParam(RelevancyReportParameters.ANALYTICS_PARAM_NAME, userReport.isAnalyticsEnable())
                .queryParam(RelevancyReportParameters.STORE_PARAM_NAME, userReport.getStoreNumber());

        final URI uri = builder.build().encode().toUri();

        String solrQueryJson;
        try {
            solrQueryJson = webService.getJson(uri, accessToken);
        } catch (final RestClientException ex1) {
            // Try refreshing access token first
            accessToken = oauthService.refreshAccessToken(hybrisServer);

            try {
                solrQueryJson = webService.getJson(uri, accessToken);
            } catch (final Exception ex2) {
                return null;
            }
        }

        final Gson gson = new Gson();
        final SolrQuery solrQuery = gson.fromJson(solrQueryJson, SolrQuery.class);

        if (userReport.getSearchOption() == SearchOption.TOP_SEARCH) {
            solrQuery.set("qt", "/autosuggestProductSearchPublishEn");
            solrQuery.set("rows", TOP_SEARCH_RETURN_RESULT);
        } else {
            solrQuery.set("rows", PRODUCT_SEARCH_RETURN_RESULT);
        }
        solrQuery.set("wt", "json");

        return solrQuery;
    }

    @Override
    public TopSearchResult getTopSearchResult(final String query, final RelevancyReportParameters userReport, OAuth2AccessToken accessToken) {

        if (accessToken == null || accessToken.getTokenType() == null || accessToken.getValue() == null) {
            return null;
        }

        final ServerEnvironment hybrisServer = userReport.getHybrisServer();
        final String URL = serverService.getHybrisServer(hybrisServer) + "/homedepotcacommercewebservices/v2/homedepotca/search/searchTopResults";
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam(RelevancyReportParameters.CATALOG_VERSION_PARAM_NAME, "Online")
                .queryParam(RelevancyReportParameters.LANG_PARAM_NAME, userReport.getLanguageOption())
                .queryParam(TOP_RESULTS_QUERY_PARAM_NAME, query)
                .queryParam(RelevancyReportParameters.LOCALIZED_POS_PARAM_NAME, userReport.getLocalizedPOS());

        final URI uri = builder.build().encode().toUri();

        String solrQueryJson;
        try {
            solrQueryJson = webService.getJson(uri);
        } catch (final Exception ex2) {
            return null;
        }

        final ObjectMapper objectMapper = new ObjectMapper();

        try {
            final TopSearchResult topSearchResult = objectMapper.readValue(solrQueryJson, TopSearchResult.class);
            return topSearchResult;
        } catch (Exception ex2) {
            return null;
        }
    }
}
