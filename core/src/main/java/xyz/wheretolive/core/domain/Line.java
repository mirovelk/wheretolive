package xyz.wheretolive.core.domain;

public class Line {
    
    private final String number;
    
    private final LineType lineType;

    public Line(String number, LineType lineType) {
        this.number = number;
        this.lineType = lineType;
    }

    public String getNumber() {
        return number;
    }

    public LineType getLineType() {
        return lineType;
    }
}
