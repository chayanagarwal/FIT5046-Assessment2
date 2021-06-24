/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.my.movie.memoir.service;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.my.movie.memoir.Cinema;


/**
 *
 * @author apple
 */
@Stateless
@Path("org.my.movie.memoir.cinema")
public class CinemaFacadeREST extends AbstractFacade<Cinema> {

    @PersistenceContext(unitName = "MyMovieMemoirPU")
    private EntityManager em;

    public CinemaFacadeREST() {
        super(Cinema.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Cinema entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") String id, Cinema entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Cinema find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cinema> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cinema> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @GET
    @Path("findById/{id}")
    @Produces({"application/json"})
    public List<Cinema> findById(@PathParam("id") String
    id) {
        Query query = em.createNamedQuery("Cinema.findById");
        query.setParameter("id", id);
        return query.getResultList();
    }
    
    @GET
    @Path("findByName/{name}")
    @Produces({"application/json"})
    public List<Cinema> findByName(@PathParam("name") String
    name) {
        Query query = em.createNamedQuery("Cinema.findByName");
        query.setParameter("name", name);
        return query.getResultList();
    }
    
    @GET
    @Path("findByPostcode/{postcode}")
    @Produces({"application/json"})
    public List<Cinema> findByPostcode(@PathParam("postcode") Integer
    postcode) {
        Query query = em.createNamedQuery("Cinema.findByPostcode");
        query.setParameter("postcode", postcode);
        return query.getResultList();
    }
    
    @POST
    @Path("cinemaAddition/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({"application/json"})
    public Response cinemaAddition(String details) {
        Response response = null;
        JsonObject object;
        System.out.println(details);
        try ( //System.out.println(details);
                JsonReader jsonReader = Json.createReader(new StringReader(details))) {
            object = jsonReader.readObject();
        }
        JsonObject data = object.getJsonObject("nameValuePairs");
        String cname = data.getString("cinema_name");
        int postcode = data.getInt("cinema_postcode");
        //System.out.println(object);
        Query query = em.createNamedQuery("Cinema.findByNameandPostcode");
        query.setParameter("name", cname);
        query.setParameter("postcode", postcode);
        try{
                Cinema cinemaInfo = (Cinema) query.getSingleResult();
                JsonObject errorResponse = Json.createObjectBuilder()
                        .add("status", 400)
                        .add("message", "Cinema already Exists")
                        .build();
                response = Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .build();
            }
        catch(NoResultException e)
        {
            UUID puuid = UUID.randomUUID();
            Cinema c;
            c = new Cinema(puuid.toString(),cname, postcode);
            create(c);
            JsonObject successResponse = Json.createObjectBuilder()
                    .add("status", 200)
                    .build();

            response = Response.status(Response.Status.OK)
                .entity(successResponse)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
        }
        //q.setParameter("password", password);
        return response;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
