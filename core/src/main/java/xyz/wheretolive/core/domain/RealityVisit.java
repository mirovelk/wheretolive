package xyz.wheretolive.core.domain;

import java.util.Date;
import java.util.List;

public class RealityVisit {

    private String name;
    
    private String id;
    
    private List<Date> visitTimes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Date> getVisitTimes() {
        return visitTimes;
    }

    public void setVisitTimes(List<Date> visitTimes) {
        this.visitTimes = visitTimes;
    }
}
