package xyz.wheretolive.crawl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.wheretolive.core.domain.Coordinates;
import xyz.wheretolive.core.domain.Line;
import xyz.wheretolive.core.domain.TrafficStop;
import xyz.wheretolive.mongo.MongoConnector;
import xyz.wheretolive.mongo.SpringConfig;

import java.util.Arrays;
import java.util.List;

import static xyz.wheretolive.core.domain.LineType.TRAM;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class IntegrationTest {

    @Autowired
    MongoConnector mongoConnector;
    
    @Test
    public void test() {
        assert mongoConnector != null;
    }
    
    @Test
    public void creatingCoreObjects() {
        List<Line> lines = Arrays.asList(new Line("12", TRAM), new Line("24", TRAM), new Line("1", TRAM));
        Coordinates location = new Coordinates(50.12315, 14.1253);
        TrafficStop stop = new TrafficStop(location, "Strossmayerovo namesti", lines);
    }
}
