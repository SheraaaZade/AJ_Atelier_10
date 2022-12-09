package main;

import api.CdResource;
import jakarta.inject.Singleton;
import jakarta.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import services.Catalog;
import services.CatalogImpl;

import java.net.URI;

public class ApiServer {
    private static HttpServer server;
    private static AbstractBinder applicationBinder = new AbstractBinder() {
        @Override
        protected void configure() {
            bind(CatalogImpl.class).to(Catalog.class).in(Singleton.class);
        }
    };
    public static void main(String[] args) {
        start();
    }

    public static void start() {
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(9998).build();
        ResourceConfig config = new ResourceConfig();
        config.registerClasses(CdResource.class);
        config.register(applicationBinder);

        server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config);
        System.out.println("API server started on " + baseUri.getHost() + ":" + baseUri.getPort());
    }

    public static void stop() {
        server.shutdownNow();
    }

    public static void setApplicationBinder(AbstractBinder applicationBinder) {
        ApiServer.applicationBinder = applicationBinder;
    }
}
