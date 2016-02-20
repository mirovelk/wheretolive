package xyz.wheretolive.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.wheretolive.core.domain.Housing;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.core.domain.MapView;
import xyz.wheretolive.core.domain.Person;
import xyz.wheretolive.core.domain.Reality;
import xyz.wheretolive.core.domain.RealityId;
import xyz.wheretolive.mongo.MapObjectRepository;
import xyz.wheretolive.mongo.PersonRepository;

@Component
public class MapObjectResourceService {
    
    public static final double MAX_MAPVIEW_SIZE = 0.08;
    
    @Autowired
    private MapObjectRepository repository;

    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private HttpSession httpSession;
    
    public <E extends MapObject> Collection<E> getIn(MapView view, Class<E> type) {
        if (view.getMaxDimension() >= MAX_MAPVIEW_SIZE) {
            return Collections.emptyList();
        }
        return repository.getIn(view, type);
    }

    public Collection<MapObject> getIn(MapView view) {
        return getIn(view, MapObject.class);
    }
    
    public Collection<Reality> getFilteredHousingsIn(MapView view) {
        if (view.getMaxDimension() >= MAX_MAPVIEW_SIZE) {
            return Collections.emptyList();
        }
        Set<String> hiddenRealities = Collections.emptySet();
        Person person = (Person) httpSession.getAttribute("person");
        if (person != null) {
            hiddenRealities = person.getHiddenRealities();
        }
        Collection<Reality> realties = repository.getIn(view, Reality.class);
        List<Reality> toReturn = filterHidden(hiddenRealities, realties);
        return toReturn;
    }

    private List<Reality> filterHidden(Set<String> hiddenRealities, Collection<Reality> realties) {
        List<Reality> toReturn = new ArrayList<>(realties.size());
        for (Reality r : realties) {
            RealityId realityIdObject = new RealityId(r.getName(), r.getRealityId());
            if (!hiddenRealities.contains(realityIdObject.toString())) {
                toReturn.add(r);
            }
        }
        return toReturn;
    }

    public HousingMetaData getHousingMetaDataIn(MapView view) {
        Collection<Reality> housings = getFilteredHousingsIn(view);
        HousingMetaData metaData = new HousingMetaData();
        Person person = (Person) httpSession.getAttribute("person");
        if (person != null) {
            metaData.getVisitedHousingIds().addAll(person.getVisitedRealities().keySet());
        }
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
    
    public void visitHousing(String realityId, String name) {
        Reality reality = repository.loadReality(realityId, name);
        if (reality == null) {
            return;
        }
        Person person = (Person) httpSession.getAttribute("person");
        if (person == null) {
            return;
        }
        RealityId realityIdObject = new RealityId(name, realityId);
        Map<String, List<Date>> visitedRealities = person.getVisitedRealities();
        if (visitedRealities.containsKey(realityIdObject.toString())) {
            List<Date> visits = visitedRealities.get(realityIdObject.toString());
            visits.add(new Date());
        } else {
            List<Date> visits = new ArrayList<>();
            visits.add(new Date());
            visitedRealities.put(realityIdObject.toString(), visits);
        }
        personRepository.updateVisitedRealities(person);
        httpSession.setAttribute("person", person);
    }

    public void hide(String realityId, String name) {
        Reality reality = repository.loadReality(realityId, name);
        if (reality == null) {
            return;
        }
        Person person = (Person) httpSession.getAttribute("person");
        if (person == null) {
            return;
        }
        RealityId realityIdObject = new RealityId(name, realityId);
        Set<String> hiddenRealities = person.getHiddenRealities();
        hiddenRealities.add(realityIdObject.toString());
        personRepository.updateHiddenRealities(person);
        httpSession.setAttribute("person", person);
    }
}
