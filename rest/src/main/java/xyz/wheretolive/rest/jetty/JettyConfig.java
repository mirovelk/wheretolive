package xyz.wheretolive.rest.jetty;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("xyz.wheretolive")
public class JettyConfig extends ResourceConfig {

    public JettyConfig() {
        packages("xyz.wheretolive.rest");
    }
    
}
