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
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
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
import org.my.movie.memoir.Credential;
import org.my.movie.memoir.Person;

/**
 *
 * @author apple
 */
@Stateless
@Path("org.my.movie.memoir.person")
public class PersonFacadeREST extends AbstractFacade<Person> {

    @PersistenceContext(unitName = "MyMovieMemoirPU")
    private EntityManager em;

    public PersonFacadeREST() {
        super(Person.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Person entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") String id, Person entity) {
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
    public Person find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Person> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Person> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @GET
    @Path("findByFirstname/{firstname}")
    @Produces({"application/json"})
    public List<Person> findByFirstname(@PathParam("firstname") String
    firstname) {
        Query query = em.createNamedQuery("Person.findByFirstname");
        query.setParameter("firstname", firstname);
        return query.getResultList();
    }
    
    @GET
    @Path("findById/{id}")
    @Produces({"application/json"})
    public List<Person> findById(@PathParam("id") String
    id) {
        Query query = em.createNamedQuery("Person.findById");
        query.setParameter("id", id);
        return query.getResultList();
    }
    
    @GET
    @Path("findBySurname/{surname}")
    @Produces({"application/json"})
    public List<Person> findBySurname(@PathParam("surname") String
    surname) {
        Query query = em.createNamedQuery("Person.findBySurname");
        query.setParameter("surname", surname);
        return query.getResultList();
    }
    
    @GET
    @Path("findByGender/{gender}")
    @Produces({"application/json"})
    public List<Person> findByGender(@PathParam("gender") String
    gender) {
        Query query = em.createNamedQuery("Person.findByGender");
        query.setParameter("gender", gender);
        return query.getResultList();
    }
    
    @GET
    @Path("findByDob/{dob}")
    @Produces({"application/json"})
    public List<Person> findByDob(@PathParam("dob") String
    dob) {
        Query query = null;
        try
        {
            Date DateofBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dob);
            query = em.createNamedQuery("Person.findByDob");
            query.setParameter("dob", DateofBirth);
            
        }
        catch(ParseException e)
        {
            System.out.println("Parse Exception Detected");
        }
        return query.getResultList();
    }
    
    @GET
    @Path("findByAddress/{address}")
    @Produces({"application/json"})
    public List<Person> findByAddress(@PathParam("address") String
    address) {
        Query query = em.createNamedQuery("Person.findByAddress");
        query.setParameter("address", address);
        return query.getResultList();
    }
    
    @GET
    @Path("findByState/{state}")
    @Produces({"application/json"})
    public List<Person> findByState(@PathParam("state") String
    state) {
        Query query = em.createNamedQuery("Person.findByState");
        query.setParameter("state", state);
        return query.getResultList();
    }
    
    @GET
    @Path("findByPostcode/{postcode}")
    @Produces({"application/json"})
    public List<Person> findByPostcode(@PathParam("postcode") Integer
    postcode) {
        Query query = em.createNamedQuery("Person.findByPostcode");
        query.setParameter("postcode", postcode);
        return query.getResultList();
    }
    
    @GET
    @Path("findByAddressStateANDPostcode/{address}/{state}/{postcode}")
    @Produces({"application/json"})
    public List<Person> findByAddressStateANDPostcode(@PathParam("address") String
    address, @PathParam("state") String state, @PathParam("postcode") Integer postcode) {
        TypedQuery<Person> q = em.createQuery("SELECT p FROM Person p "
                + "WHERE p.address = :address "
                + "AND p.state = :state AND p.postcode = :postcode", Person.class);
        q.setParameter("address", address);
        q.setParameter("state", state);
        q.setParameter("postcode", postcode);
        return q.getResultList();
    }
    
    @POST
    @Path("userRegistration/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({"application/json"})
    public Response userRegistration(String details) {
        Response response;
        JsonObject object;
        System.out.println(details);
        try ( //System.out.println(details);
                JsonReader jsonReader = Json.createReader(new StringReader(details))) {
            object = jsonReader.readObject();
        }
        JsonObject data = object.getJsonObject("nameValuePairs");
        String cid = data.getString("credid");
        String firstname = data.getString("firstname");
        String surname = data.getString("surname");
        String address = data.getString("address");
        String gender = data.getString("gender");
        int postcode = data.getInt("postcode");
        String state = data.getString("state");
        String dob = data.getString("dob");
        Date DateofBirth = null;
        try{
            DateofBirth = new SimpleDateFormat("dd-MM-yyyy").parse(dob);
            System.out.println(DateofBirth);
        }
        catch(ParseException e)
        {
            System.out.println("Parse Exception Detected");
        }
        //System.out.println(object);
        Query query = em.createNamedQuery("Credential.findById");
        query.setParameter("id", cid);
        Credential credinfo = (Credential) query.getSingleResult();
        UUID puuid = UUID.randomUUID();
        Person p;
        p = new Person(puuid.toString(),firstname, surname, gender, DateofBirth, address, state, postcode, credinfo);
        create(p);
        JsonObject successResponse = Json.createObjectBuilder()
                .add("status", 200)
                .build();
            
        response = Response.status(Response.Status.OK)
            .entity(successResponse)
            .type(MediaType.APPLICATION_JSON_TYPE)
            .build();
        //q.setParameter("password", password);
        return response;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
