package xyz.wheretolive.rest.springboot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan("xyz.wheretolive")
@EnableScheduling
public class Application implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {

    private static Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        context.start();
    }

    @Override
    public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
        int port = event.getEmbeddedServletContainer().getPort();
        logger.info("^^^^^^^^^^^^^^^^^^^^^^^ server listening on port " + port + "^^^^^^^^^^^^^^^^^^^^^^^");
    }
}