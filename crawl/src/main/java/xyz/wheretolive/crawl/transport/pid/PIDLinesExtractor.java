package xyz.wheretolive.crawl.transport.pid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import xyz.wheretolive.core.domain.Line;
import xyz.wheretolive.core.domain.LineType;

@Component
public class PIDLinesExtractor {
    
    private static final String TRAM = "Tram";
    private static final String NIGHT_TRAM = "NTram";
    private static final String BUS = "Bus";
    private static final String NIGHT_BUS = "NBus";
    private static final String METRO = "Metro";
    private static final String SUBSTITUTE_V = "NáhrV";
    private static final String SUBSTITUTE_D = "NáhrD";
    private static final String SML = "Sml";
    private static final String SCHOOL_BUS = "ŠkBus";
    private static final String INV_BUS = "InvBus";
    private static final String REGIONAL_BUS = "RegBus";
    private static final String FERRY = "Loď";
    
    private static final String[] ALL = new String[]{TRAM, NIGHT_TRAM, BUS, NIGHT_BUS, METRO, SUBSTITUTE_V,
            SUBSTITUTE_D, SML, SCHOOL_BUS, INV_BUS, REGIONAL_BUS, FERRY};
    private static final String[] IMPORTANT = new String[]{TRAM, NIGHT_TRAM, BUS, NIGHT_BUS, METRO, REGIONAL_BUS, FERRY};
    private static final String[] IRRELEVANT = new String[]{SUBSTITUTE_V, SUBSTITUTE_D, SML, SCHOOL_BUS, INV_BUS};
    
    private static final String[] IRRELEVANT_LINES = new String[]{"odj.", "příj."};
    
    private static final String LINE_PATTERN = "[A|B|C]?P?(\\d+)?";

    public List<Line> extractLines(String info) {
        String[] splitted = prepare(info);

        Map<String, List<String>> transportTypes = mapLinesToTransportTypes(splitted);

        return getLines(transportTypes);
        
    }

    private List<Line> getLines(Map<String, List<String>> transportTypes) {
        List<Line> lines = new ArrayList<>();
        for (String transportType : transportTypes.keySet()) {
            if (startsWithAny(transportType, IRRELEVANT)) {
                continue;
            }
            LineType lineType = extractLineType(transportType);
            List<String> lineStrings = transportTypes.get(transportType);
            for (String lineString : lineStrings) {
                if (startsWithAny(lineString, IRRELEVANT_LINES)) {
                    continue;
                }
                if (!lineString.matches(LINE_PATTERN)) {
                    throw new IllegalStateException("Unrecognized line: " + lineString);
                }
                lines.add(new Line(lineString, lineType));
            }
        }
        return lines;
    }

    private Map<String, List<String>> mapLinesToTransportTypes(String[] splitted) {
        String actualBeingProcessed = null;
        Map<String, List<String>> transportTypes = new HashMap<>();
        for (String part : splitted) {
            if (startsWithAny(part, ALL)) {
                actualBeingProcessed = part;
                transportTypes.put(actualBeingProcessed, new ArrayList<>());
            } else {
                if (transportTypes.get(actualBeingProcessed) == null) {
                    throw new IllegalStateException("Transport type was not processed but found: " + part);
                }
                transportTypes.get(actualBeingProcessed).add(part);
            }
        } return transportTypes;
    }

    private String[] prepare(String info) {
        if (info == null) {
            String a = "a";
        }
        info = info.replaceAll(" \\*(\\d)", " $1");
        info = info.replaceAll(",", "");
        return info.split(" ");
    }

    private LineType extractLineType(String s) {
        switch (s) {
            case TRAM : return LineType.TRAM;
            case NIGHT_TRAM : return LineType.NIGHT_TRAM;
            case BUS : return LineType.BUS;
            case NIGHT_BUS : return LineType.NIGHT_BUS;
            case METRO : return LineType.METRO;
            case REGIONAL_BUS : return LineType.REGIONAL_BUS;
            case FERRY : return LineType.FERRY;
        }
        throw new IllegalArgumentException("Line type not recognized: " + s);
    }

    private boolean startsWithAny(String s, String[] strings) {
        for (String string : strings) {
            if (s.startsWith(string)) {
                return true;
            }
        }
        return false;
    }

}
