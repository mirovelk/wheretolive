package xyz.wheretolive.crawl.reality.sreality;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import xyz.wheretolive.core.domain.Coordinates;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.core.domain.Reality;
import xyz.wheretolive.crawl.HttpUtils;
import xyz.wheretolive.crawl.reality.RealityCrawler;

@Component
public class SRealityCrawler extends RealityCrawler {
    
    private static final String RENT_FLATS = "http://www.sreality.cz/api/cs/v1/estates?category_main_cb=1&category_type_cb=2&page=";
    
    private static final String SREALITY = "SReality";

    @Override
    public Collection<MapObject> crawl() {
        Gson gson = new Gson();
        List<SRealityEstate> estates = new ArrayList<>();
        int i = 0;
        String json = HttpUtils.getJson(RENT_FLATS + i);
        do {
            SRealityResult sRealityResult = gson.fromJson(json, SRealityResult.class);
            estates.addAll(sRealityResult.get_embedded().getEstates());
            json = HttpUtils.getJson(RENT_FLATS + i);
            i++;
        } while (!json.contains("Not found."));
        
        List<Reality> result = new ArrayList<>();
        for (SRealityEstate estate : estates) {
            Reality reality = new Reality(estate.getHash_id()+ "", estate.getPrice(),
                    extractArea(estate.getName()), SREALITY, new Coordinates((double)estate.getGps().get("lat"), (double)estate.getGps().get("lon")));
            result.add(reality);
        }
        return new HashSet<>(result);
    }

    private int extractArea(String name) {
        name = name.replace("\u00a0", " ");
        Pattern p = Pattern.compile(".*\\s([0-9]+)\\s.*");
        Matcher m = p.matcher(name);

        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }
        throw new IllegalArgumentException("can not find price in: " + name);
    }

    @Override
    @Scheduled(cron = "30 14 * * * *")
    public void execute() {
        super.execute();
    }

    @Override
    protected String getName() {
        return SREALITY;
    }
}
