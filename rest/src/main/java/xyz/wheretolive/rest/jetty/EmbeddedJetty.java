package xyz.wheretolive.rest.jetty;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

public class EmbeddedJetty {

    private static Logger logger = LogManager.getLogger(EmbeddedJetty.class);
    
    public static void main(String[] args) {
        try {
            Server server = new Server(7070);
            ServletContextHandler servletHandler = new ServletContextHandler(server, "/ws");
            
            ServletHolder holder = servletHandler.addServlet(ServletContainer.class, "/*");
            holder.setInitOrder(0);
            holder.setInitParameter("javax.ws.rs.Application", JettyConfig.class.getName());

            ResourceHandler resourceHandler = new ResourceHandler();
            resourceHandler.setDirectoriesListed(false);
            resourceHandler.setResourceBase(EmbeddedJetty.class.getResource("/webapp").getFile());
            resourceHandler.setWelcomeFiles(new String[]{ "index.html" });

            HandlerList handlerList = new HandlerList();
            handlerList.setHandlers(new Handler[] { servletHandler, resourceHandler });
            server.setHandler(handlerList);
            
            server.start();
        } catch (Exception e) {
            logger.error("", e);
        }
    }
}
