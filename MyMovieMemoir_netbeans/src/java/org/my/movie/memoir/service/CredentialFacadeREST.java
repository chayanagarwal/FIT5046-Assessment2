/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.my.movie.memoir.service;

import java.io.InputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
@Path("org.my.movie.memoir.credential")
public class CredentialFacadeREST extends AbstractFacade<Credential> {

    @PersistenceContext(unitName = "MyMovieMemoirPU")
    private EntityManager em;

    public CredentialFacadeREST() {
        super(Credential.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Credential entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") String id, Credential entity) {
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
    public Credential find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Credential> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Credential> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @GET
    @Path("findByUsername/{username}")
    @Produces({"application/json"})
    public List<Credential> findByUsername(@PathParam("username") String
    username) {
        Query query = em.createNamedQuery("Credential.findByUsername");
        query.setParameter("username", username);
        return query.getResultList();
    }
    
    @GET
    @Path("findBySignupdate/{signupdate}")
    @Produces({"application/json"})
    public List<Credential> findBySignupdate(@PathParam("signupdate") String
    signupdate) {
        Query query = null;
        try
        {
            Date SignDate = new SimpleDateFormat("yyyy-MM-dd").parse(signupdate);
            query = em.createNamedQuery("Credential.findBySignupdate");
            query.setParameter("signupdate", SignDate);
            
        }
        catch(ParseException e)
        {
            System.out.println("Parse Exception Detected");
        }
        return query.getResultList();
    }
    
    @GET
    @Path("findById/{id}")
    @Produces({"application/json"})
    public List<Credential> findById(@PathParam("id") String
    id) {
        Query query = em.createNamedQuery("Credential.findById");
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @POST
    @Path("userAuthentication/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({"application/json"})
    public Response userAuthentication(String details) {
        Response response = null;
        JsonObject object;
        System.out.println(details);
        try ( //System.out.println(details);
                JsonReader jsonReader = Json.createReader(new StringReader(details))) {
            object = jsonReader.readObject();
        }
        JsonObject data = object.getJsonObject("nameValuePairs");
        String email = data.getString("email");
        String password = data.getString("password");
        //System.out.println(object);
        Query q = em.createNamedQuery("Credential.findByUsername");
        q.setParameter("username", email);
        try {
            Credential personCredential = (Credential) q.getSingleResult();
            String personPassword = personCredential.getPassword();
            if(personPassword.equalsIgnoreCase(password)){
                Query query = em.createNamedQuery("Person.findByCredId");
                query.setParameter("credid", personCredential);
                Person personInfo = (Person) query.getSingleResult();
                String firstName = personInfo.getFirstname();
                String surname = personInfo.getSurname();
                String gender = personInfo.getGender();
                String address = personInfo.getAddress();
                String state = personInfo.getState();
                int postcode = personInfo.getPostcode();
                String id = personInfo.getId();
                JsonObject successResponse = Json.createObjectBuilder()
                        .add("status", 200)
                        .add("data", Json.createObjectBuilder()
                            .add("id", id)
                            .add("firstName", firstName)
                            .add("username", email)
                            .add("surname", surname)
                            .add("gender", gender)
                            .add("address", address)
                            .add("state", state)
                            .add("postcode", postcode))
                        .build();

                response = Response.status(Response.Status.OK)
                    .entity(successResponse)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .build();
            }
            else{
                JsonObject errorResponse = Json.createObjectBuilder()
                        .add("status", 400)
                        .add("message", "Incorrect username or password")
                        .build();
                response = Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .build();
            }
        }
        catch(NoResultException e)
        {
            JsonObject errorResponse = Json.createObjectBuilder()
                        .add("status", 400)
                        .add("message", "Incorrect username or password")
                        .build();
                response = Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .build();
        }
        //q.setParameter("password", password);
        return response;
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
        String email = data.getString("email");
        Query q = em.createNamedQuery("Credential.findByUsername");
        q.setParameter("username", email);
        try{
            Credential personCredential = (Credential) q.getSingleResult();
            JsonObject errorResponse = Json.createObjectBuilder()
                        .add("status", 400)
                        .add("message", "Username already Exists")
                        .build();
                response = Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .build();
            
        }
        catch(NoResultException e)
        {
            String password = data.getString("password");
            Date currentDate = new Date();
            Date DateofBirth = null;
            //System.out.println(object);
            UUID cuuid = UUID.randomUUID();
            Credential c = new Credential(cuuid.toString(), email, password, currentDate);
            create(c);
            JsonObject successResponse = Json.createObjectBuilder()
                    .add("status", 200)
                    .add("credid", cuuid.toString())
                    .build();

            response = Response.status(Response.Status.OK)
                .entity(successResponse)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
        }
        //q.setParameter("password", password);
        return response;
    }
}

