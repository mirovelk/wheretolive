package xyz.wheretolive.rest;

import java.util.ArrayList;
import java.util.List;

public class HousingMetaData {

    private double minPrice;
    
    private double maxPrice;
    
    private double minArea;

    private double maxArea;
    
    private double minPricePerSquaredMeter;

    private double maxPricePerSquaredMeter;
    
    private List<String> visitedHousingIds = new ArrayList<>();

    private List<String> favoriteHousingIds = new ArrayList<>();

    public double getMaxArea() {
        return maxArea;
    }

    public void setMaxArea(double maxArea) {
        this.maxArea = maxArea;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getMinArea() {
        return minArea;
    }

    public void setMinArea(double minArea) {
        this.minArea = minArea;
    }

    public double getMaxPricePerSquaredMeter() {
        return maxPricePerSquaredMeter;
    }

    public void setMaxPricePerSquaredMeter(double maxPricePerSquaredMeter) {
        this.maxPricePerSquaredMeter = maxPricePerSquaredMeter;
    }

    public double getMinPricePerSquaredMeter() {
        return minPricePerSquaredMeter;
    }

    public void setMinPricePerSquaredMeter(double minPricePerSquaredMeter) {
        this.minPricePerSquaredMeter = minPricePerSquaredMeter;
    }

    public List<String> getVisitedHousingIds() {
        return visitedHousingIds;
    }

    public void setVisitedHousingIds(List<String> visitedHousingIds) {
        this.visitedHousingIds = visitedHousingIds;
    }

    public List<String> getFavoriteHousingIds() {
        return favoriteHousingIds;
    }

    public void setFavoriteHousingIds(List<String> favoriteHousingIds) {
        this.favoriteHousingIds = favoriteHousingIds;
    }
}
