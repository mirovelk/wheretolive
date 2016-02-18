package xyz.wheretolive.core.domain;

import java.util.Set;

public class Person {

    private String facebookAuthToken;
    
    private String facebookId;
    
    private Set<RealityVisit> visitedRealities;
    
    private FilterSettings filterSettings;

    public String getFacebookAuthToken() {
        return facebookAuthToken;
    }

    public void setFacebookAuthToken(String facebookAuthToken) {
        this.facebookAuthToken = facebookAuthToken;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public Set<RealityVisit> getVisitedRealities() {
        return visitedRealities;
    }

    public void setVisitedRealities(Set<RealityVisit> visitedRealities) {
        this.visitedRealities = visitedRealities;
    }

    public FilterSettings getFilterSettings() {
        return filterSettings;
    }

    public void setFilterSettings(FilterSettings filterSettings) {
        this.filterSettings = filterSettings;
    }
}
