package xyz.wheretolive.rest.jetty;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class SpringJettyServer {

    private static Logger logger = LogManager.getLogger(SpringJettyServer.class);

    static boolean webApplicationContextInitialized = false;
    
    public static void main(String[] args) {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        
        applicationContext.addApplicationListener(new ApplicationListener<ContextRefreshedEvent>() {
            
            @Override
            public void onApplicationEvent(ContextRefreshedEvent applicationEvent) {
                ApplicationContext ctx = applicationEvent.getApplicationContext();
                if(ctx instanceof AnnotationConfigWebApplicationContext) {
                    webApplicationContextInitialized = true;
                }
            }
        });

        applicationContext.registerShutdownHook();
        applicationContext.register(RootConfiguration.class);
        applicationContext.refresh();

        if(!webApplicationContextInitialized) {
            logger.error("Web application context not initialized. Exiting.");
            System.exit(1);
        }

        logger.info("Running.");
    }

}
