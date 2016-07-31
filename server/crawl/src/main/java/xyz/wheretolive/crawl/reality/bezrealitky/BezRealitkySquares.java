package xyz.wheretolive.crawl.reality.bezrealitky;

import java.util.List;
import java.util.Map;

public class BezRealitkySquares {

    private Map<String, Integer> boundary;

    private List<BezRealitkyRecord> records;

    public Map<String, Integer> getBoundary() {
        return boundary;
    }

    public void setBoundary(Map<String, Integer> boundary) {
        this.boundary = boundary;
    }

    public List<BezRealitkyRecord> getRecords() {
        return records;
    }

    public void setRecords(List<BezRealitkyRecord> records) {
        this.records = records;
    }

}
