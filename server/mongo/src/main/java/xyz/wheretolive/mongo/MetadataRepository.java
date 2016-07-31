package xyz.wheretolive.mongo;

import java.util.List;

import xyz.wheretolive.core.domain.CrawlerLog;

public interface MetadataRepository {

    List<CrawlerLog> load();
    
    void persist(CrawlerLog crawlerLog);
}
