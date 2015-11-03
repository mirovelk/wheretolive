package xyz.wheretolive.core.domain;

public class Housing extends MapObject {
    
    private double price;
    
    private double area;
    
    public Housing() {
    }
    
    public Housing(double price, double area) {
        this.price = price;
        this.area = area;
    }

    public Housing(double price, double area, Coordinates location) {
        super(location);
        this.price = price;
        this.area = area;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
