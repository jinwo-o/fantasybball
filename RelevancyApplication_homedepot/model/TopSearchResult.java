package ca.homedepot.relevancy.model;

import java.util.List;

public class TopSearchResult {
    private List<ProductCategories> productcategories;
    private String redirectToPIP;
    private String renderSAPflags;
    private List<TopSearches> topsearches;

    public List<ProductCategories> getProductcategories() {
        return productcategories;
    }

    public void setProductcategories(List<ProductCategories> productcategories) {
        this.productcategories = productcategories;
    }

    public String getRedirectToPIP() {
        return redirectToPIP;
    }

    public void setRedirectToPIP(String redirectToPIP) {
        this.redirectToPIP = redirectToPIP;
    }

    public String getRenderSAPflags() {
        return renderSAPflags;
    }

    public void setRenderSAPflags(String renderSAPflags) {
        this.renderSAPflags = renderSAPflags;
    }

    public List<TopSearches> getTopsearches() {
        return topsearches;
    }

    public void setTopsearches(List<TopSearches> topsearches) {
        this.topsearches = topsearches;
    }
}
