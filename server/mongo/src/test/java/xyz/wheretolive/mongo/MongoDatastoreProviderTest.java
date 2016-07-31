package xyz.wheretolive.mongo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class MongoDatastoreProviderTest extends MongoTest {
    
    @Autowired
    MongoDatastoreProvider mongoDatastoreProvider;
    
    @Test
    public void test() {
        assert mongoDatastoreProvider != null;
    }
}
