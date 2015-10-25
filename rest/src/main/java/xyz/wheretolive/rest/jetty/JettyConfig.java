package xyz.wheretolive.rest.jetty;

import org.glassfish.jersey.server.ResourceConfig;

public class JettyConfig extends ResourceConfig {

    public JettyConfig() {
        packages("xyz.wheretolive.rest");
    }
}
