package xyz.wheretolive.core.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("persons")
public class Person {

    @Id
    private ObjectId id;

    private String facebookId;
    
    private FacebookLongTermToken longTermToken;
    
    private Map<String, List<Date>> visitedRealities = new HashMap<>();
    
    private Set<String> hiddenRealities = new HashSet<>();
    
    private Settings settings = new Settings();

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public Map<String, List<Date>> getVisitedRealities() {
        return visitedRealities;
    }

    public void setVisitedRealities(Map<String, List<Date>> visitedRealities) {
        this.visitedRealities = visitedRealities;
    }

    public FacebookLongTermToken getLongTermToken() {
        return longTermToken;
    }

    public void setLongTermToken(FacebookLongTermToken longTermToken) {
        this.longTermToken = longTermToken;
    }

    public ObjectId getId() {
        return id;
    }

    public Set<String> getHiddenRealities() {
        return hiddenRealities;
    }

    public void setHiddenRealities(Set<String> hiddenRealities) {
        this.hiddenRealities = hiddenRealities;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
