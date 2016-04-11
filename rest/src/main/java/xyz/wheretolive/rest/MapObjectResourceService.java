package xyz.wheretolive.rest;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.core.domain.MapView;
import xyz.wheretolive.mongo.MapObjectRepository;

@Component
public class MapObjectResourceService {
    
    public static final double MAX_MAPVIEW_SIZE = 0.08;
    
    @Autowired
    private MapObjectRepository repository;
    
    public <E extends MapObject> Collection<E> getIn(MapView view, Class<E> type) {
        if (view.getMaxDimension() >= MAX_MAPVIEW_SIZE) {
            return Collections.emptyList();
        }
        return repository.getIn(view, type);
    }

}
