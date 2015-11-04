package xyz.wheretolive.crawl.foodMarket.tesco;

/**
 * Created by anthonymottot on 30/10/2015.
 */
public class TescoObject {

    /*
    {
        "id": 190,
            "active": true,
            "code": "14096",
            "type_id": 1,
            "name": "Expres Třinec Dukelská",
            "address_street": "Dukelská",
            "address_number": "1017",
            "address_city": "Třinec",
            "address_zipcode": "739 61",
            "opening_time": "po - pá: 6:00 - 20:00, so - ne: 6:00 - 19:00",
            "phone": "+420-725 908 447",
            "gps_lat": "49.6636",
            "gps_lon": "18.6806",
            "nonstop": 0,
            "fs": 0,
            "gas": 0,
            "pharmacy": 0,
            "optics": 0,
            "phones": 0,
            "photo": 0,
            "url": "/cs/obchody-tesco/expres-trinec-dukelska/"
    }
    */

    int id;
    boolean active;
    long code;
    int type_id;
    String name;
    String address_street;
    String address_number;
    String address_city;
    String address_zipcode;
    String opening_time;
    String phone;
    Double gps_lat;
    Double gps_long;
    int nonstop;
    int fs;
    int gas;
    int pharmacy;
    int optics;
    int phones;
    int photo;
    int url;

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

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
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

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }
}
