package xyz.wheretolive.core.domain;

public class Line {

    private String number;

    private LineType lineType;

    public Line() {
    }
    
    public Line(String number, LineType lineType) {
        this.number = number;
        this.lineType = lineType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LineType getLineType() {
        return lineType;
    }

    public void setLineType(LineType lineType) {
        this.lineType = lineType;
    }
}
