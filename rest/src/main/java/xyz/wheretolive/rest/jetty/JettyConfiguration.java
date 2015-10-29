package xyz.wheretolive.rest.jetty;

import org.springframework.context.annotation.Configuration;

@Configuration
public class JettyConfiguration {

//    @Bean(initMethod = "start", destroyMethod = "stop")
//    public Server jettyServer() throws IOException {
//
//		/* Create the server. */
//        Server server = new Server();
//
//		/* Create a basic connector. */
//        ServerConnector httpConnector = new ServerConnector(server);
//        httpConnector.setPort(7070);
//        server.addConnector(httpConnector);
//
//        ResourceHandler resourceHandler = new ResourceHandler();
//        resourceHandler.setDirectoriesListed(false);
//        resourceHandler.setResourceBase(EmbeddedJetty.class.getResource("/webapp").getFile());
//        resourceHandler.setWelcomeFiles(new String[]{ "index.html" });
//
//        ServletContextHandler servletHandler = new ServletContextHandler(server, "/ws");
//        servletHandler.addEventListener(new ContextLoaderListener());
//        servletHandler.setInitParameter("contextClass", AnnotationConfigWebApplicationContext.class.getName() );
////        servletHandler.setInitParameter("contextConfigLocation", SpringConfig.class.getName());
//
//        ServletHolder holder = servletHandler.addServlet(ServletContainer.class, "/*");
//        holder.setInitOrder(0);
//        holder.setInitParameter("javax.ws.rs.Application", JettyConfig.class.getName());
//
//
//        HandlerList handlerList = new HandlerList();
//        handlerList.setHandlers(new Handler[]{ servletHandler, resourceHandler });
//
//        server.setHandler(handlerList);
//
//        return server;
//    }
}
