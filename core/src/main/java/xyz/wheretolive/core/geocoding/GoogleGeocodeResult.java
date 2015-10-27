package xyz.wheretolive.core.geocoding;

import java.util.Map;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("googleGeocodeResults")
public class GoogleGeocodeResult {

    @Id
    private ObjectId id;
    
    private String query;
    
    private Map<String, Object> result;
    
    public GoogleGeocodeResult() {
        
    }

    public GoogleGeocodeResult(String query, Map<String, Object> result) {
        this.query = query;
        this.result = result;
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public String getQuery() {
        return query;
    }
}
