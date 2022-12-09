package domain;

public class DomainFactory {
    public CompactDisc createCompactDisc(String title, String artist, int stock, double price,String id) {
        return new CompactDiscImpl(title, artist, stock, price, id);
    }
}
