package com.penguins.demo;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/user")
@Produces(MediaType.TEXT_HTML)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    private static long counter;

    @GET
    public String getPenguins(){
        counter++;
        return "user" + counter;
    }
}
