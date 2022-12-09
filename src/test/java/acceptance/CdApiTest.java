package acceptance;

import domain.CompactDisc;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import main.ApiServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.Catalog;
import services.CatalogImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CdApiTest {
    private Catalog defaultCatalog;
    @BeforeEach
    void setUp() {
        defaultCatalog = new CatalogImpl();
        CompactDisc cd1 = new CompactDisc("Young Mystic", "Bob Marley",3,12,"666-777");
        CompactDisc cd2 = new CompactDisc("Time Out", "Dave Brubeck Quartet",10,111.1,"123-456");
        defaultCatalog.initCatalog(cd1,cd2);
        ApiServer.setApplicationBinder((new AbstractBinder() {
            @Override
            protected void configure() {
                bind(defaultCatalog).to(Catalog.class);
            }
        }));
        ApiServer.start();
    }

    @AfterEach
    void tearDown() {
        ApiServer.stop();
    }

    @Test
    void findCdInStockFromTitle() {
        String expectedResponse = "title:Young Mystic, artist:Bob Marley, stock:3, price:12.0€, id:666-777";
        String actualResponse = getRequest("cds", "title=young mystic");
        assertEquals(expectedResponse, actualResponse);
    }

    private String getRequest(String path, String queryParam1) {
        Client c = ClientBuilder.newClient();
        WebTarget target = c.target("http://localhost:9998/");
        String key = queryParam1.split("=")[0];
        String value = queryParam1.split("=")[1];
        String responseBody = target.path(path).queryParam(key, value)
                .request().get(String.class);
        return responseBody;
    }
}
