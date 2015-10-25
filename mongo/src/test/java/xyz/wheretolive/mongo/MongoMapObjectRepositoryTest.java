package xyz.wheretolive.mongo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.wheretolive.core.domain.Coordinates;
import xyz.wheretolive.core.domain.FoodMarket;
import xyz.wheretolive.core.domain.MapObject;
import java.util.Collection;

import static org.junit.Assert.*;

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
        MapObject mapObject = new MapObject(new Coordinates(45.235, 12.365));
        repository.store(mapObject);
        FoodMarket foodMarket = new FoodMarket(new Coordinates(5.4, 6.54), "albert");
        repository.store(foodMarket);

        Collection<MapObject> mapObjects = repository.getIn(null);
        assertEquals(2, mapObjects.size());
    }
    
    @Test
    public void test2() {
        MapObject mapObject = new MapObject(new Coordinates(45.235, 12.365));
        repository.store(mapObject);
        FoodMarket foodMarket = new FoodMarket(new Coordinates(5.4, 6.54), "albert");
        repository.store(foodMarket);

        Collection<FoodMarket> mapObjects = repository.getIn(null, FoodMarket.class);
        assertEquals(1, mapObjects.size());
    }
}
