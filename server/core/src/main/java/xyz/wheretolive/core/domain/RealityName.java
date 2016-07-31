package xyz.wheretolive.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum RealityName {
    @JsonProperty("BezRealitky")
    BEZ_REALITKY("BezRealitky"),
    @JsonProperty("M&M")
    M_M("M&M"),
    @JsonProperty("Real1")
    REAL1("Real1"),
    @JsonProperty("RealityMat")
    REALITY_MAT("RealityMat"),
    @JsonProperty("REMAX")
    REMAX("REMAX"),
    @JsonProperty("SReality")
    SREALITY("SReality");

    private final String name;

    RealityName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
