package xyz.wheretolive.crawl.reality.sreality;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import xyz.wheretolive.core.domain.Coordinates;
import xyz.wheretolive.core.domain.Housing;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.core.domain.Reality;
import xyz.wheretolive.crawl.HttpUtils;
import xyz.wheretolive.crawl.reality.RealityCrawler;

@Component
public abstract class SRealityCrawler extends RealityCrawler {
    
    private static final String SREALITY = "SReality";

    protected List<Reality> getRealities(List<SRealityEstate> estates, Housing.Type type, Housing.Transaction transaction) {
        List<Reality> result = new ArrayList<>();
        for (SRealityEstate estate : estates) {
            try {
                if (estate.getPrice() == 0) {
                    throw new IllegalStateException("Price not found in page with url " + estate.get_links().get("self"));
                }
                Reality reality = new Reality(estate.getHash_id() + "", estate.getPrice(),
                        extractArea(estate.getName()), getName(), new Coordinates((double) estate.getGps().get("lat"), (double) estate.getGps().get("lon")));
                reality.setType(type);
                reality.setTransaction(transaction);
                result.add(reality);
            } catch (Exception e) {
                logger.error("Error while parsing reality of " + getName() + " on URL: " + estate.get_links().get("self"), e);
                errorsCount++;
            }
        }
        return result;
    }

    protected List<SRealityEstate> getsRealityEstates(String url) {
        List<SRealityEstate> estates = new ArrayList<>();
        Gson gson = new Gson();
        int page = 0;
        String json;
        List<SRealityEstate> estatesOnPage;
        do {
            json = HttpUtils.get(url + page);
            SRealityResult sRealityResult = gson.fromJson(json, SRealityResult.class);
            SRealityEmbeddedResult embedded = sRealityResult.get_embedded();
            if (embedded == null) {
                break;
            }
            estatesOnPage = embedded.getEstates();
            estates.addAll(estatesOnPage);
            page++;
        } while (!estatesOnPage.isEmpty());
        return estates;
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
    @Scheduled(cron = SREALITY_CRON)
    public void execute() {
        super.execute();
    }

    @Override
    public String getName() {
        return SREALITY;
    }
    
    public static void main(String[] args) {
        SRealityCrawler crawler;
        Collection<MapObject> mapObjects;
        
        crawler = new SRealityFlatRentCrawler();
        mapObjects = crawler.crawl();
        assert mapObjects.size() > 0;

        crawler = new SRealityFlatSellCrawler();
        mapObjects = crawler.crawl();
        assert mapObjects.size() > 0;

        crawler = new SRealityHouseRentCrawler();
        mapObjects = crawler.crawl();
        assert mapObjects.size() > 0;

        crawler = new SRealityHouseSellCrawler();
        mapObjects = crawler.crawl();
        assert mapObjects.size() > 0;
    }
}
