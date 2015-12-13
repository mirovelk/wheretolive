package xyz.wheretolive.rest;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.wheretolive.core.domain.Housing;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.core.domain.MapView;
import xyz.wheretolive.mongo.MapObjectRepository;

@Component
public class MapObjectResourceService {
    
    public static final double MAX_MAPVIEW_SIZE = 0.08;
    
    @Autowired
    MapObjectRepository repository;
    
    public <E extends MapObject> Collection<E> getIn(MapView view, Class<E> type) {
        if (view.getMaxDimension() >= MAX_MAPVIEW_SIZE) {
            return Collections.emptyList();
        }
        return repository.getIn(view, type);
    }

    public Collection<MapObject> getIn(MapView view) {
        return getIn(view, MapObject.class);
    }
    
    public HousingMetaData getHousingMetaDataIn(MapView view) {
        Collection<Housing> housings = getIn(view, Housing.class);
        HousingMetaData metaData = new HousingMetaData();
        for (Housing h : housings) {
            double area = h.getArea();
            if (metaData.getMinArea() > area) {
                metaData.setMinArea(area);
            }
            if (metaData.getMaxArea() < area) {
                metaData.setMaxArea(area);
            }
            double price = h.getPrice();
            if (metaData.getMinPrice() > price) {
                metaData.setMinPrice(price);
            }
            if (metaData.getMaxPrice() < price) {
                metaData.setMaxPrice(price);
            }
            double pricePerSquaredMeter = h.getPricePerSquaredMeter();
            if (metaData.getMinPricePerSquaredMeter() > pricePerSquaredMeter) {
                metaData.setMinPricePerSquaredMeter(pricePerSquaredMeter);
            }
            if (metaData.getMaxPricePerSquaredMeter() < pricePerSquaredMeter) {
                metaData.setMaxPricePerSquaredMeter(pricePerSquaredMeter);
            }
        }
        return metaData;
    }
}
