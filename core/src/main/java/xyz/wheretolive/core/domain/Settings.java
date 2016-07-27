package xyz.wheretolive.core.domain;

import java.util.EnumSet;

public class Settings {

    private String coloring = "time";

    private double priceMin = 10000;

    private double priceMax = 30000;

    private double sizeMin = 50;

    private double sizeMax = 150;

    private double ppm2Min = 0;
    
    private double ppm2Max = 500;

    private EnumSet<RealityName> realitiesShowed;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Settings)) {
            return false;
        }
        Settings other = (Settings) obj;
        return coloring.equals(other.coloring)
                && priceMin == other.priceMin
                && priceMax == other.priceMax
                && sizeMin == other.sizeMin
                && sizeMax == other.sizeMax
                && ppm2Min == other.ppm2Min
                && ppm2Max == other.ppm2Max;
    }

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
