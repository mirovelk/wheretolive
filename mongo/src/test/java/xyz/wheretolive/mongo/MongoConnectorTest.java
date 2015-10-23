package xyz.wheretolive.mongo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class MongoConnectorTest {
    
    @Autowired
    MongoConnector mongoConnector;
    
    @Test
    public void test() {
        assert mongoConnector != null;
    }
}
