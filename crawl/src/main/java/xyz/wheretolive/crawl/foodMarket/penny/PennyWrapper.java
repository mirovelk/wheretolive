package xyz.wheretolive.crawl.foodMarket.penny;

import java.util.List;
import java.util.Map;

public class PennyWrapper {

    private Map<String, PennyResult> d;

    public Map<String, PennyResult> getD() {
        return d;
    }

    public void setD(Map<String, PennyResult> d) {
        this.d = d;
    }

    public static class PennyResult {
        private String group;
        private String features;
        private String name;
        private double longitude;
        private double latitude;
        private List<Object> jsonPayload;
        private String countrycode;
        private String zip;
        private String region;
        private String subregion;
        private String city;
        private String suburb;
        private String address1;
        private String address2;

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getSuburb() {
            return suburb;
        }

        public void setSuburb(String suburb) {
            this.suburb = suburb;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getSubregion() {
            return subregion;
        }

        public void setSubregion(String subregion) {
            this.subregion = subregion;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getZip() {
            return zip;
        }

        public void setZip(String zip) {
            this.zip = zip;
        }

        public String getCountrycode() {
            return countrycode;
        }

        public void setCountrycode(String countrycode) {
            this.countrycode = countrycode;
        }

        public List<Object> getJsonPayload() {
            return jsonPayload;
        }

        public void setJsonPayload(List<Object> jsonPayload) {
            this.jsonPayload = jsonPayload;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFeatures() {
            return features;
        }

        public void setFeatures(String features) {
            this.features = features;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }
    }
}
