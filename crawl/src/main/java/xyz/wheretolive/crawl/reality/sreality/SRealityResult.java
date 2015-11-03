package xyz.wheretolive.crawl.reality.sreality;

import java.util.Map;

public class SRealityResult {

    private String meta_description;
    
    private int result_size;
    
    private SRealityEmbeddedResult _embedded;
    
    private String title;
    
    private String locality;
    
    private Map<String, String> filter;
    
    private Map<String, Object> _links;
    
    private String locality_dativ;
    
    private boolean loggen_in;
    
    private int per_page;
    
    private String category_instrumental;
    
    private int page;

    public String getMeta_description() {
        return meta_description;
    }

    public void setMeta_description(String meta_description) {
        this.meta_description = meta_description;
    }

    public int getResult_size() {
        return result_size;
    }

    public void setResult_size(int result_size) {
        this.result_size = result_size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public Map<String, String> getFilter() {
        return filter;
    }

    public void setFilter(Map<String, String> filter) {
        this.filter = filter;
    }

    public Map<String, Object> get_links() {
        return _links;
    }

    public void set_links(Map<String, Object> _links) {
        this._links = _links;
    }

    public String getLocality_dativ() {
        return locality_dativ;
    }

    public void setLocality_dativ(String locality_dativ) {
        this.locality_dativ = locality_dativ;
    }

    public boolean isLoggen_in() {
        return loggen_in;
    }

    public void setLoggen_in(boolean loggen_in) {
        this.loggen_in = loggen_in;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public String getCategory_instrumental() {
        return category_instrumental;
    }

    public void setCategory_instrumental(String category_instrumental) {
        this.category_instrumental = category_instrumental;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public xyz.wheretolive.crawl.reality.sreality.SRealityEmbeddedResult get_embedded() {
        return _embedded;
    }

    public void set_embedded(xyz.wheretolive.crawl.reality.sreality.SRealityEmbeddedResult _embedded) {
        this._embedded = _embedded;
    }
}
