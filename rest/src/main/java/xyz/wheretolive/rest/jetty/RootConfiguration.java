package xyz.wheretolive.rest.jetty;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import xyz.wheretolive.core.SpringConfig;

@Configuration
@Import({ SpringConfig.class, JettyConfiguration.class})
public class RootConfiguration {

}
