package xyz.wheretolive.mongo;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import xyz.wheretolive.core.domain.Coordinates;
import xyz.wheretolive.core.domain.FoodMarket;
import xyz.wheretolive.core.domain.MapObject;
import xyz.wheretolive.core.domain.MapView;

@RunWith(SpringJUnit4ClassRunner.class)
public class MongoMapObjectRepositoryTest extends MongoTest {
    
    @Autowired
    MongoMapObjectRepository repository;
    
    @Before
    public void before() {
        super.before();
    }
    
    @Test
    public void test() {
        MapObject mapObject = new MapObject(new Coordinates(5.45, 6.55));
        repository.store(mapObject);
        FoodMarket foodMarket = new FoodMarket(new Coordinates(5.44, 6.54), "albert", null);
        repository.store(foodMarket);

        Collection<MapObject> mapObjects = repository.getIn(new MapView(new Coordinates(5.5, 6.6), new Coordinates(5.4, 6.5)));
        assertEquals(2, mapObjects.size());
    }
    
    @Test
    public void test2() {
        MapObject mapObject = new MapObject(new Coordinates(5.45, 6.55));
        repository.store(mapObject);
        FoodMarket foodMarket = new FoodMarket(new Coordinates(5.44, 6.54), "albert", null);
        repository.store(foodMarket);

        Collection<FoodMarket> mapObjects = repository.getIn(new MapView(new Coordinates(5.5, 6.6), new Coordinates(5.4, 6.5)), FoodMarket.class);
        assertEquals(1, mapObjects.size());
    }
}
