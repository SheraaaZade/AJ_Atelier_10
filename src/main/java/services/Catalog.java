package services;

import domain.CompactDisc;
import domain.CompactDiscImpl;

import java.util.List;

public interface Catalog {
    List<CompactDisc> findCds(String title, String artist);

    void initCatalog(CompactDisc... defaultItems);
}
