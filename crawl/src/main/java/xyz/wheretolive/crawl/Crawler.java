package xyz.wheretolive.crawl;

import java.util.Collection;

import xyz.wheretolive.core.domain.MapObject;

public interface Crawler {

    String BEZ_REALITKY_CRON = "0 30 1,13 * * *";

    String SREALITY_CRON = "0 30 2,14 * * *";

    String REALITY_MAT_CRON = "0 30 3,15 * * *";

    String MM_CRON = "0 30 4,16 * * *";

    String REMAX_CRON = "0 30 5,17 * * *";

    String REAL1_CRON = "0 30 6,18 * * *";

    String ALBERT_CRON = "0 30 10 1 * *";

    String BILLA_CRON = "0 30 10 1 * *";

    String KAUFLAND_CRAWLER = "0 30 10 1 * *";

    String PENNY_CRON = "0 30 10 1 * *";

    String TESCO_CRON = "0 30 10 1 * *";

    Collection<MapObject> crawl();

    void execute();

    String getName();

}
