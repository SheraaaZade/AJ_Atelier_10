package api;

import domain.CompactDisc;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import services.Catalog;
import services.CatalogImpl;

import java.util.List;
import java.util.stream.Collectors;

@Path("/cds")
public class CdResource {
    @Inject
    private Catalog catalog;
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String findCds(@DefaultValue("") @QueryParam("title") String title,
                          @DefaultValue("") @QueryParam("artist") String artist) {
        System.out.println("find my CD ? " + title + " artist");
        List<CompactDisc> cdsFound = catalog.findCds(title, artist);
        if(cdsFound.isEmpty())
            return "No cds";
        String cdsFoundSerialized = cdsFound.stream().map(compactDisc -> compactDisc.toString())
                .collect(Collectors.joining("\n"));
        return cdsFoundSerialized;
    }
}
