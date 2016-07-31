package xyz.wheretolive.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.wheretolive.core.domain.CrawlerLog;
import xyz.wheretolive.mongo.MetadataRepository;

@Component
public class MetadataResourceService {

    @Autowired
    private MetadataRepository repository;

    public List<CrawlerLog> loadLogs() {
        return repository.load();
    }
}
