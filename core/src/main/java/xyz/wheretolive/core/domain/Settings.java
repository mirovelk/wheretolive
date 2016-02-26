package xyz.wheretolive.core.domain;

public class Settings {

    private String coloring;

    private double priceMin;

    private double priceMax;

    private double sizeMin;

    private double sizeMax;

    private double ppm2Min;
    
    private double ppm2Max;

    public double getPpm2Max() {
        return ppm2Max;
    }

    public void setPpm2Max(double ppm2Max) {
        this.ppm2Max = ppm2Max;
    }

    public String getColoring() {
        return coloring;
    }

    public void setColoring(String coloring) {
        this.coloring = coloring;
    }

    public double getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(double priceMin) {
        this.priceMin = priceMin;
    }

    public double getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(double priceMax) {
        this.priceMax = priceMax;
    }

    public double getSizeMin() {
        return sizeMin;
    }

    public void setSizeMin(double sizeMin) {
        this.sizeMin = sizeMin;
    }

    public double getSizeMax() {
        return sizeMax;
    }

    public void setSizeMax(double sizeMax) {
        this.sizeMax = sizeMax;
    }

    public double getPpm2Min() {
        return ppm2Min;
    }

    public void setPpm2Min(double ppm2Min) {
        this.ppm2Min = ppm2Min;
    }

}
