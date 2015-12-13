package xyz.wheretolive.core.domain;

public class Housing extends NameableMapObject {
    
    private double price;
    
    private double area;
    
    private double pricePerSquaredMeter;
    
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
}
