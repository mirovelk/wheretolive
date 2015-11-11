package xyz.wheretolive.core.domain;

import java.util.List;

public class TrafficStop extends NameableMapObject {

    private List<Line> lines;
    
    private String trafficStopName;

    public TrafficStop(Coordinates location, String name, List<Line> lines, String trafficStopName) {
        super(location, name);
        this.lines = lines;
        this.trafficStopName = trafficStopName;
    }
    
    public TrafficStop(Coordinates location, String name) {
        super(location, name);
    }
    
    public TrafficStop() {}

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

}
