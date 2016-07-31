package xyz.wheretolive.crawl.reality.sreality;

import java.util.Map;

public class SRealityEstate {

    private int attractive_offer;
    
    private Map<String, Object> _embedded;
    
    private String name;
    
    private String locality;
    
    private int region_tip;
    
    private int price;
    
    private Map<String, Object> price_czk;
    
    private boolean has_video;
    
    private Map<String, Object> _links;
    
    private boolean rus;
    
    private Map<String, Object> seo;
    
    private boolean isNew;
    
    private int paid_logo;
    
    private long hash_id;

    private Map<String, Object> gps;

    public Map<String, Object> getGps() {
        return gps;
    }

    public void setGps(Map<String, Object> gps) {
        this.gps = gps;
    }

    public int getAttractive_offer() {
        return attractive_offer;
    }

    public void setAttractive_offer(int attractive_offer) {
        this.attractive_offer = attractive_offer;
    }

    public Map<String, Object> get_embedded() {
        return _embedded;
    }

    public void set_embedded(Map<String, Object> _embedded) {
        this._embedded = _embedded;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public int getRegion_tip() {
        return region_tip;
    }

    public void setRegion_tip(int region_tip) {
        this.region_tip = region_tip;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Map<String, Object> getPrice_czk() {
        return price_czk;
    }

    public void setPrice_czk(Map<String, Object> price_czk) {
        this.price_czk = price_czk;
    }

    public boolean isHas_video() {
        return has_video;
    }

    public void setHas_video(boolean has_video) {
        this.has_video = has_video;
    }

    public Map<String, Object> get_links() {
        return _links;
    }

    public void set_links(Map<String, Object> _links) {
        this._links = _links;
    }

    public boolean isRus() {
        return rus;
    }

    public void setRus(boolean rus) {
        this.rus = rus;
    }

    public Map<String, Object> getSeo() {
        return seo;
    }

    public void setSeo(Map<String, Object> seo) {
        this.seo = seo;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public int getPaid_logo() {
        return paid_logo;
    }

    public void setPaid_logo(int paid_logo) {
        this.paid_logo = paid_logo;
    }

    public long getHash_id() {
        return hash_id;
    }

    public void setHash_id(long hash_id) {
        this.hash_id = hash_id;
    }

}
