package xyz.wheretolive.crawl.reality.bezrealitky;

import java.math.BigDecimal;

public class BezRealitkyRecord {
    
    private String id;
    private double lat;
    private double lng;
    private boolean highlight;
    private String title;
    private boolean ideveloper;
    private BigDecimal price;
    private double surface;
    private long time_order;
    private String image;
    private String address;
    private String iw;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public boolean isHighlight() {
        return highlight;
    }

    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isIdeveloper() {
        return ideveloper;
    }

    public void setIdeveloper(boolean ideveloper) {
        this.ideveloper = ideveloper;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public double getSurface() {
        return surface;
    }

    public void setSurface(double surface) {
        this.surface = surface;
    }

    public long getTime_order() {
        return time_order;
    }

    public void setTime_order(long time_order) {
        this.time_order = time_order;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIw() {
        return iw;
    }

    public void setIw(String iw) {
        this.iw = iw;
    }

    private String url;

}
