package com.penguins.demo.api;

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

import com.penguins.demo.pojos.Penguin;
import com.penguins.demo.pojos.Placement;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PenguinResource {
    
    @GET
    @Path("/penguins")
    public List<Penguin> getPenguins(){
        return Penguin.listAll();
    }

    @GET
    @Path("/pengu/{id}")
    public Penguin getPenguins(@PathParam("id") Long id){
        Penguin penguin = Penguin.findById(id);
        return penguin;
    }

    @GET
    @Path("/placements")
    public List<Placement> getPlacements(){
        return Placement.listAll();
    }

    @POST
    @Path("/pengu")
    @Transactional
    public Response addPenguin(Penguin penguin){
        penguin.persist();
        return Response.ok(penguin).status(201).build();
    }

    @DELETE
    @Transactional
    @Path("/placements")
    public Response deletePlacements(){
        Placement.deleteAll();
        return Response.status(204).build();
    }

    @DELETE
    @Transactional
    @Path("/pengu/{id}")
    public Response deletePenguin(@PathParam("id") Long id){
        Penguin penguin = Penguin.findById(id);
        penguin.delete();
        return Response.status(204).build();
    }
}
