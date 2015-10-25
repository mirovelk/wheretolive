package xyz.wheretolive.rest.jetty;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

public class EmbeddedJetty {

    private static Logger logger = LogManager.getLogger(EmbeddedJetty.class);
    
    public static void main(String[] args) {
        try {
            Server server = new Server(7070);
            ServletContextHandler handler = new ServletContextHandler(server, "");
            server.setHandler(handler);
            
            ServletHolder holder = handler.addServlet(ServletContainer.class, "/*");
            holder.setInitOrder(0);
            holder.setInitParameter("javax.ws.rs.Application", JettyConfig.class.getName());
            
            server.start();
        } catch (Exception e) {
            logger.error("", e);
        }
    }
}
