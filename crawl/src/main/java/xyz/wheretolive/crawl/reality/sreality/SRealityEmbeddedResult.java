package xyz.wheretolive.crawl.reality.sreality;

import java.util.List;
import java.util.Map;

public class SRealityEmbeddedResult {

    private List<SRealityEstate> estates;
    
    private Map<String, Object> is_saved;

    private Map<String, Object> not_precise_location_count;

    public Map<String, Object> getNot_precise_location_count() {
        return not_precise_location_count;
    }

    public void setNot_precise_location_count(Map<String, Object> not_precise_location_count) {
        this.not_precise_location_count = not_precise_location_count;
    }

    public Map<String, Object> getIs_saved() {
        return is_saved;
    }

    public void setIs_saved(Map<String, Object> is_saved) {
        this.is_saved = is_saved;
    }

    public List<SRealityEstate> getEstates() {
        return estates;
    }

    public void setEstates(List<SRealityEstate> estates) {
        this.estates = estates;
    }
}
