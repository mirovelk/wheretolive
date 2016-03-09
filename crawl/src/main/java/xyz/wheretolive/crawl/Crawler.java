package xyz.wheretolive.crawl;

import java.util.Collection;

import xyz.wheretolive.core.domain.MapObject;

public interface Crawler {

    static final String BEZ_REALITKY_CRON = "0 30 1,13 * * *";

    static final String SREALITY_CRON = "0 30 2,14 * * *";

    static final String REALITY_MAT_CRON = "0 30 3,15 * * *";

    static final String MM_CRON = "0 30 4,16 * * *";

    static final String REMAX_CRON = "0 30 5,17 * * *";

    Collection<MapObject> crawl();

    void execute();

    String getName();

}
