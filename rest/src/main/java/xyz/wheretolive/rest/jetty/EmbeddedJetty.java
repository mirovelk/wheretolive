package xyz.wheretolive.rest.jetty;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import xyz.wheretolive.core.SpringConfig;

public class EmbeddedJetty {

    private static Logger logger = LogManager.getLogger(EmbeddedJetty.class);
    
    public static void main(String[] args) {
        try {
            Server server = new Server(7070);


            WebAppContext webAppContext = new WebAppContext();
            webAppContext.setContextPath("/");
            webAppContext.setResourceBase(EmbeddedJetty.class.getResource("/webapp").getFile());
            webAppContext.setInitParameter("contextClass", AnnotationConfigWebApplicationContext.class.getName());
            webAppContext.setInitParameter("contextConfigLocation", SpringConfig.class.getName());
//            webAppContext.setInitParameter("javax.ws.rs.Application", JettyConfig.class.getName());
            webAppContext.addEventListener(new ContextLoaderListener());
            webAppContext.addEventListener(new RequestContextListener());

            
//            Connector serverConnector = new ServerConnector(server, new HttpConnectionFactory(new HttpConfiguration()));
//            server.addConnector(serverConnector);

            HandlerCollection handler = new HandlerCollection();
            handler.addHandler(webAppContext);
            
            server.setHandler(handler);
            
            server.start();
            server.join();
            
        } catch (Exception e) {
            logger.error("", e);
        }
    }
}
