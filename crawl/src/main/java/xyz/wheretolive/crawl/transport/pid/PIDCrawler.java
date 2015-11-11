package xyz.wheretolive.crawl.transport.pid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import xyz.wheretolive.core.domain.Coordinates;
import xyz.wheretolive.core.domain.Line;
import xyz.wheretolive.core.domain.LineType;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.core.domain.TrafficStop;
import xyz.wheretolive.crawl.HttpUtils;

@Component
public class PIDCrawler extends TrafficStopCrawler {

    private static final String PID = "PID";
    private static final String MAIN_PAGE_URL = "http://spojeni.dpp.cz/Map/MapView.aspx?a=OBJCHOICE&objID=txtMask&src=ObjectsForm";
    private static final String MAP_URL = "http://spojeni.dpp.cz/AJAXService.asmx/TimetableObjectsInRectExt";
    private static final String JSON = "{\"iLang\":\"CZECH\",\"sTTCombID\":\"PID\",\"dMinX\":\"{minX}\",\"dMaxX\":\"{maxX}\",\"dMinY\":\"{minY}\",\"dMaxY\":\"{maxY}\",\"iZoom\":\"16\",\"bShowStations\":\"true\"}";
    
    private static final double minX = 49.70;
    private static final double minY = 13.69;
    private static final double maxX = 50.36;
    private static final double maxY = 15.11;
    private static final double STEP = 0.09;
    
    @Autowired
    private PIDLinesExtractor pidLinesExtractor;
    
    @Override
    public Collection<MapObject> crawl() {
        String idosKey = getIdosKey();
        
        Set<TrafficStop> toReturn = new HashSet<>();
        
        for (double x = minX; x < maxX; x += STEP) {
            for (double y = minY; y < maxY; y += STEP) {
                String preparedJSON = JSON.replace("{minX}", Double.toString(x)).replace("{minY}", Double.toString(y))
                        .replace("{maxX}", Double.toString(x + STEP)).replace("{maxY}", Double.toString(y + STEP));

                PIDResult pidResult = getPidResult(idosKey, preparedJSON);
                toReturn.addAll(getTrafficStops(pidResult));
            }
        }
        

        return new HashSet<>(toReturn);
    }

    private List<TrafficStop> getTrafficStops(PIDResult pidResult) {
        if (pidResult.getD() == null) {
            return Collections.emptyList();
        }
        List<TrafficStop> toReturn = new ArrayList<>();
        for (PIDObject pidObject : pidResult.getD()) {
            List<Line> lines = null;
            if (pidObject.getInfo() != null) {
                lines = pidLinesExtractor.extractLines(pidObject.getInfo());
            } else if (pidObject.getIco().equals("20_train_m.gif")
                        || pidObject.getIco().equals("20_bus_m.gif")) {
                lines = new ArrayList<>();
                lines.add(new Line(null, LineType.TRAIN));
            }
            if (lines != null) {
                TrafficStop trafficStop = new TrafficStop(new Coordinates(pidObject.getX(), pidObject.getY()),
                        getName(), lines, pidObject.getName());
                toReturn.add(trafficStop);
            }
        }
        return toReturn;
    }

    private PIDResult getPidResult(final String idosKey, String preparedJSON) {
        Gson gson = new Gson();
        Map<String, String> headers = new HashMap<String, String>(){{put("idoskey", idosKey);}};
        String json = HttpUtils.postJson(MAP_URL, preparedJSON, headers);
        return gson.fromJson(json, PIDResult.class);
    }

    private String getIdosKey() {
        String html = HttpUtils.get(MAIN_PAGE_URL);
        Pattern r = Pattern.compile(".*sIDOSKey='([\\w-]*)';.*");
        Matcher m = r.matcher(html);
        if (m.find( )) {
            return m.group(1);
        }
        throw new IllegalStateException("Could not find sIDOSKey in HTML");
    }

    @Override
    @Scheduled(cron = "30 21 1 * * *")
    public void execute() {
        super.execute();
    }

    @Override
    protected String getName() {
        return PID;
    }
    
    public static void main(String args[]) {
        PIDCrawler pidCrawler = new PIDCrawler();
        pidCrawler.pidLinesExtractor = new PIDLinesExtractor();
        pidCrawler.crawl();
    }
    
}
