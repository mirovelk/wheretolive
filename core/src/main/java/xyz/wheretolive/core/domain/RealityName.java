package xyz.wheretolive.core.domain;

public enum RealityName {
    BEZ_REALITKY("BezRealitky"),
    M_M("M&M"),
    REAL1("Real1"),
    REALITY_MAT("RealityMat"),
    REMAX("REMAX"),
    SREALITY("SReality");

    private final String name;

    RealityName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
