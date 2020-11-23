package com.penguins.demo.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api")
@Produces(MediaType.TEXT_HTML)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    private static long counter;

    @GET
    @Path("/user")
    public String getPenguins(){
        counter++;
        return "user" + counter;
    }
}
