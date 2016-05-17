/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.luis.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.NotFoundException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

/** An endpoint class we are exposing */
@Api(
        name = "myPoliticianApi",
        version = "v1",
        description = "An Api to manage the politician records",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.Luis.example.com",
                ownerName = "backend.myapplication.Luis.example.com",
                packagePath=""
        )
)
public class MyEndpoint {

    public static List<Politician> politicians = new ArrayList<Politician>();

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "add")
    public Politician addPolitician(@Named("id") long id, @Named("name") String name, @Named("voteNumber") String voteNumber,
                                    @Named("position") String position) {
        int index = politicians.indexOf(new Politician(id, name, voteNumber, position));
        if (index != -1) try {
            throw new NotFoundException("Politician Record already exists");
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        Politician p = new Politician(id, name, voteNumber, position);
        politicians.add(p);

        return p;
    }

    @ApiMethod(name="update")
    public Politician updateQuote(Politician p) throws NotFoundException {
        int index = politicians.indexOf(p);
        if (index == -1)
            throw new NotFoundException("Quote Record does not exist");
        Politician currentPolitician = politicians.get(index);
        currentPolitician.setName(p.getName());
        currentPolitician.setVoteNumber(p.getVoteNumber());
        return p;
    }

    @ApiMethod(name="remove")
    public void removePolitician(@Named("id") Integer id) throws NotFoundException {
        int index = politicians.indexOf(new Politician(id));
        if (index == -1)
            throw new NotFoundException("Quote Record does not exist");
        politicians.remove(index);
    }

    @ApiMethod(name="list")
    public List<Politician> getPoliticians() {
        return politicians;
    }

    @ApiMethod(name="listByPosition")
    public List<Politician> getPoliticiansByPosition(@Named("position") String position) {
        List<Politician> results = new ArrayList<Politician>();
        for (Politician quote : politicians) {
            if (quote.getPosition().indexOf(position) != -1) {
                results.add(quote);
            }
        }
        return results;
    }

}
