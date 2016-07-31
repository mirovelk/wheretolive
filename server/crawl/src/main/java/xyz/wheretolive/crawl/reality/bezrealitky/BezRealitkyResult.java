package xyz.wheretolive.crawl.reality.bezrealitky;

import java.util.List;

public class BezRealitkyResult {
    
    private String status;

    private List<BezRealitkySquares> squares;

    public List<BezRealitkySquares> getSquares() {
        return squares;
    }

    public void setSquares(List<BezRealitkySquares> squares) {
        this.squares = squares;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
