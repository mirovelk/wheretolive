package xyz.wheretolive.crawl.foodMarket.albert;

import com.google.gson.annotations.Expose;

/**
 * Created by anthonymottot on 29/10/2015.
 */
public class AlbertObject {

    /*
    {
        "title": "Praha Arkády Pankrác",
        "order": "8",
        "url": "/nase-prodejny/hypermarket-praha-arkady-pankrac",
        "latitude": "50.05113",
        "longitude": "14.43908",
        "type": "hypermarket",
        "message": " \r\n<div class=\"info-window-content\">\r\n\t<div class=\"as-h\">Praha Arkády Pankrác</div>\r\n\t<strong>Adresa:</strong><p>Na Pankráci 86<br />14000 Praha 4</p>\r\n\t<a href=\"/nase-prodejny/hypermarket-praha-arkady-pankrac\">Více informací</a>\r\n</div>"
    }
    */

    @Expose
    String title;

    @Expose
    Integer order;

    @Expose
    String url;

    @Expose
    Double latitude;

    @Expose
    Double longitude;

    @Expose
    String type;

    @Expose
    String message;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
