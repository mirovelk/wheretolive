package xyz.wheretolive.core.domain;

public class FilterSettings {

    public enum ColoringScheme {
        SIZE,
        PRICE,
        PRICE_PER_SQUARED_METER,
        TIME
    }

    private int minPrice;

    private int maxPrice;

    private int minSize;

    private int maxSize;

    private int minPricePerSquaredMeter;

    private int maxPricePerSquaredMeter;

    private ColoringScheme coloringScheme;

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getMinSize() {
        return minSize;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getMinPricePerSquaredMeter() {
        return minPricePerSquaredMeter;
    }

    public void setMinPricePerSquaredMeter(int minPricePerSquaredMeter) {
        this.minPricePerSquaredMeter = minPricePerSquaredMeter;
    }

    public int getMaxPricePerSquaredMeter() {
        return maxPricePerSquaredMeter;
    }

    public void setMaxPricePerSquaredMeter(int maxPricePerSquaredMeter) {
        this.maxPricePerSquaredMeter = maxPricePerSquaredMeter;
    }

    public ColoringScheme getColoringScheme() {
        return coloringScheme;
    }

    public void setColoringScheme(ColoringScheme coloringScheme) {
        this.coloringScheme = coloringScheme;
    }

}
