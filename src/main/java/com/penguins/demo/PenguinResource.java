package com.penguins.demo;

import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PenguinResource {
    
    @GET
    public List<Penguin> getPenguins(){
        return Penguin.listAll();
    }

    @POST
    @Transactional
    public Response addPenguin(Penguin penguin){
        penguin.persist();
        return Response.ok(penguin).status(201).build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response deletePenguin(@PathParam("id") Long id){
        Penguin penguin = Penguin.findById(id);
        penguin.delete();
        return Response.status(204).build();
    }
}
