package xyz.wheretolive.core.domain;

public class Housing extends NameableMapObject {
    
    private double price;
    
    private double area;
    
    private double pricePerSquaredMeter;
    
    private Integer floor;
    
    private Boolean balcony;
    
    private Boolean terrace;
    
    private Boolean elevator;
    
    public Housing() {
    }
    
    public Housing(double price, double area) {
        this.price = price;
        this.area = area;
        updatePricePerSquaredMeter();
    }

    public Housing(double price, double area, String name, Coordinates location) {
        super(location, name);
        this.price = price;
        this.area = area;
        updatePricePerSquaredMeter();
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
        updatePricePerSquaredMeter();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        updatePricePerSquaredMeter();
    }
    
    private void updatePricePerSquaredMeter() {
        if (price != 0 && area != 0) {
            pricePerSquaredMeter = price / area;
        }
    }

    public double getPricePerSquaredMeter() {
        return pricePerSquaredMeter;
    }

    @Override
    public int hashCode() {
        int hash = (int) Math.round(price);
        hash = hash * 31 + (int) Math.round(area);
        return hash * 31 + super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Housing)) {
            return false;
        }
        Housing other = (Housing) obj;
        return super.equals(obj) && (price == other.price && area == other.area);
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Boolean getBalcony() {
        return balcony;
    }

    public void setBalcony(Boolean balcony) {
        this.balcony = balcony;
    }

    public Boolean getTerrace() {
        return terrace;
    }

    public void setTerrace(Boolean terrace) {
        this.terrace = terrace;
    }

    public Boolean getElevator() {
        return elevator;
    }

    public void setElevator(Boolean elevator) {
        this.elevator = elevator;
    }
}
