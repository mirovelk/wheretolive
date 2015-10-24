package xyz.wheretolive.mongo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.wheretolive.core.Coordinates;
import xyz.wheretolive.core.FoodMarket;
import xyz.wheretolive.core.MapObject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class MongoMapObjectRepositoryTest {
    
    @Autowired
    MongoMapObjectRepository repository;
    
    @Test
    public void test() {
        MapObject mapObject = new MapObject(new Coordinates(45.235, 12.365));
        repository.store(mapObject);
        FoodMarket foodMarket = new FoodMarket(new Coordinates(5.4, 6.54), "albert");
        repository.store(foodMarket);
    }
}
