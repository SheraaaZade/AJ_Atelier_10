package services;

import domain.CompactDisc;
import domain.CompactDiscImpl;
import domain.DomainFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CatalogTest {
    private DomainFactory factory = new DomainFactory();

    @Test
    void cdIsFound() {
        Catalog catalog = new CatalogImpl();
        CompactDiscImpl cd1 = new CompactDiscImpl("Young Mystic", "Bob Marley", 3, 12,"666-777");
        CompactDisc cd2 = new CompactDiscImpl("Time Out", "Dave Brubeck Quartet", 10, 111.1, "123-456");
        catalog.initCatalog(cd1, cd2);
        assertAll(
                () -> assertEquals(1, catalog.findCds("young mystic", "").size()),
                () -> assertEquals(cd1, catalog.findCds("young mystic", "").get(0))
        );
    }
}