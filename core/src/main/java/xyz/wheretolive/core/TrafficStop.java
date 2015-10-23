package xyz.wheretolive.core;

import java.util.List;

public class TrafficStop extends NameableMapObject {
    
    private final List<Line> lines;

    public TrafficStop(Coordinates location, String name, List<Line> lines) {
        super(location, name);
        this.lines = lines;
    }

    public List<Line> getLines() {
        return lines;
    }
}
