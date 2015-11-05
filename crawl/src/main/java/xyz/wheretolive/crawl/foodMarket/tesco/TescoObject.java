package xyz.wheretolive.crawl.foodMarket.tesco;

public class TescoObject {

    private int id;
    private boolean active;
    private String code;
    private int type_id;
    private String name;
    private String address_street;
    private String address_number;
    private String address_city;
    private String address_zipcode;
    private String opening_time;
    private String phone;
    private double gps_lat;
    private double gps_long;
    private int nonstop;
    private int fs;
    private int gas;
    private int pharmacy;
    private int optics;
    private int phones;
    private int photo;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress_street() {
        return address_street;
    }

    public void setAddress_street(String address_street) {
        this.address_street = address_street;
    }

    public String getAddress_number() {
        return address_number;
    }

    public void setAddress_number(String address_number) {
        this.address_number = address_number;
    }

    public String getAddress_city() {
        return address_city;
    }

    public void setAddress_city(String address_city) {
        this.address_city = address_city;
    }

    public String getAddress_zipcode() {
        return address_zipcode;
    }

    public void setAddress_zipcode(String address_zipcode) {
        this.address_zipcode = address_zipcode;
    }

    public String getOpening_time() {
        return opening_time;
    }

    public void setOpening_time(String opening_time) {
        this.opening_time = opening_time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getGps_lat() {
        return gps_lat;
    }

    public void setGps_lat(Double gps_lat) {
        this.gps_lat = gps_lat;
    }

    public Double getGps_long() {
        return gps_long;
    }

    public void setGps_long(Double gps_long) {
        this.gps_long = gps_long;
    }

    public int getNonstop() {
        return nonstop;
    }

    public void setNonstop(int nonstop) {
        this.nonstop = nonstop;
    }

    public int getFs() {
        return fs;
    }

    public void setFs(int fs) {
        this.fs = fs;
    }

    public int getGas() {
        return gas;
    }

    public void setGas(int gas) {
        this.gas = gas;
    }

    public int getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(int pharmacy) {
        this.pharmacy = pharmacy;
    }

    public int getOptics() {
        return optics;
    }

    public void setOptics(int optics) {
        this.optics = optics;
    }

    public int getPhones() {
        return phones;
    }

    public void setPhones(int phones) {
        this.phones = phones;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
