package xyz.wheretolive.rest.springboot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan("xyz.wheretolive")
@EnableScheduling
public class Application {

    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
    }
}