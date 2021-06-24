/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.my.movie.memoir.service;

import static com.oracle.jrockit.jfr.ContentType.Timestamp;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
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
import org.my.movie.memoir.Memoir;
import entities.MaxRating;
import java.io.StringReader;
import java.text.DateFormatSymbols;
import java.util.UUID;
import javax.json.JsonReader;
import javax.ws.rs.core.Response;
import org.my.movie.memoir.Cinema;
import org.my.movie.memoir.Person;

/**
 *
 * @author apple
 */
@Stateless
@Path("org.my.movie.memoir.memoir")
public class MemoirFacadeREST extends AbstractFacade<Memoir> {

    @PersistenceContext(unitName = "MyMovieMemoirPU")
    private EntityManager em;

    public MemoirFacadeREST() {
        super(Memoir.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Memoir entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") String id, Memoir entity) {
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
    public Memoir find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Memoir> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Memoir> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    public List<Memoir> findById(@PathParam("id") String
    id) {
        Query query = em.createNamedQuery("Memoir.findById");
        query.setParameter("id", id);
        return query.getResultList();
    }
    
    @GET
    @Path("findByPersonId/{personid}")
    @Produces({"application/json"})
    public List<Memoir> findByPersonId(@PathParam("personid") String
    personid) {
        Query q = em.createNamedQuery("Person.findById");
        q.setParameter("id", personid);
        Person p = (Person)q.getSingleResult();
        Query query = em.createNamedQuery("Memoir.findByPersonId");
        query.setParameter("personid", p);
        return query.getResultList();
    }
    
    @GET
    @Path("findByMoviename/{moviename}")
    @Produces({"application/json"})
    public List<Memoir> findByMoviename(@PathParam("moviename") String
    moviename) {
        Query query = em.createNamedQuery("Memoir.findByMoviename");
        query.setParameter("moviename", moviename);
        return query.getResultList();
    }
    
    @GET
    @Path("findByReleasedate/{releasedate}")
    @Produces({"application/json"})
    public List<Memoir> findByReleasedate(@PathParam("releasedate") String
    releasedate) {
        Query query = null;
        try
        {
            Date ReleaseDate = new SimpleDateFormat("yyyy-MM-dd").parse(releasedate);
            query = em.createNamedQuery("Memoir.findByReleasedate");
            query.setParameter("releasedate", ReleaseDate);
            
        }
        catch(ParseException e)
        {
            System.out.println("Parse Exception Detected");
        }
        return query.getResultList();
    }
    
    @GET
    @Path("findByUserwatchdate/{userwatchdate}")
    @Produces({"application/json"})
    public List<Memoir> findByUserwatchdate(@PathParam("userwatchdate") String
    userwatchdate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        Query query = null;
        try
        {
            Date userwatchParsedDate = dateFormat.parse(userwatchdate);
            Timestamp watchDate = new java.sql.Timestamp(userwatchParsedDate.getTime());
            query = em.createNamedQuery("Memoir.findByUserwatchdate");
            query.setParameter("userwatchdate", watchDate);
        }
        catch(ParseException e)
        {
            System.out.println("Parse Exception Detected");
        }
        return query.getResultList();
    }
    
    @GET
    @Path("findByUseropinion/{useropinion}")
    @Produces({"application/json"})
    public List<Memoir> findByUseropinion(@PathParam("useropinion") String
    useropinion) {
        Query query = em.createNamedQuery("Memoir.findByUseropinion");
        query.setParameter("useropinion", useropinion);
        return query.getResultList();
    }
    
    @GET
    @Path("findByUserrating/{userrating}")
    @Produces({"application/json"})
    public List<Memoir> findByUserrating(@PathParam("userrating") Double
    userrating) {
        Query query = em.createNamedQuery("Memoir.findByUserrating");
        query.setParameter("userrating", userrating);
        return query.getResultList();
    }
    
    @GET
    @Path("findByMovienameANDCinemaname/{moviename}/{cinemaname}")
    @Produces({"application/json"})
    public List<Memoir> findByMovienameANDCinemaname(@PathParam("moviename") String
    moviename, @PathParam("cinemaname") String cinemaname) {
        TypedQuery<Memoir> q = em.createQuery("SELECT m FROM Memoir m WHERE "
                + "m.moviename = :moviename AND m.cinemaid.name = :cinemaname", Memoir.class);
        q.setParameter("moviename", moviename);
        q.setParameter("cinemaname", cinemaname);
        return q.getResultList();
    }
    
    @GET
    @Path("findByUserratingANDPostcode/{userrating}/{postcode}")
    @Produces({"application/json"})
    public List<Memoir> findByUserratingANDPostcode(@PathParam("userrating") Double
    userrating, @PathParam("postcode") Integer postcode) {
        Query query = em.createNamedQuery("Memoir.findByUserratingANDPostcode");
        query.setParameter("userrating", userrating);
        query.setParameter("postcode", postcode);
        return query.getResultList();
    }
    
    @GET
    @Path("findByPersonIdandWatchTimestamp/{personId}/{startingDate}/{endingDate}")
    @Produces({"application/json"})
    public Object findByPersonIdandWatchTimestamp(@PathParam("personId") String
    personId, @PathParam("startingDate") String startingDate, 
    @PathParam("endingDate") String endingDate) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        TypedQuery<Object[]> query = null;
        List<Object[]> queryList;
        Date startingParsedDate = dateFormat.parse(startingDate);
        Timestamp startDate = new java.sql.Timestamp(startingParsedDate.getTime());
        Date endingParsedDate = dateFormat.parse(endingDate);
        Timestamp endDate = new java.sql.Timestamp(endingParsedDate.getTime());
        query = em.createQuery("SELECT m.cinemaid.postcode, count(m.moviename) FROM Memoir m "
                + "WHERE m.personid.id = :id AND m.userwatchdate BETWEEN :startDate AND :endDate "
                + "GROUP BY m.cinemaid.postcode", Object[].class);
        query.setParameter("id", personId);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        queryList = query.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Object[] row : queryList) {
            JsonObject personObject = Json.createObjectBuilder().add("PostCode", (Integer)row[0])
                                                                .add("MovieCount", (Long)row[1])
                                                                .build();
            arrayBuilder.add(personObject);
        }
        JsonArray jArray= arrayBuilder.build();
        return jArray;
    }
    
    @GET
    @Path("findByPersonIdandMaxRating/{personId}")
    @Produces({"application/json"})
    public Object findByPersonIdandMaxRating(@PathParam("personId") String personId) 
            throws Exception
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        TypedQuery<Object[]> query = null;
        List<Object[]> queryList;
        query = em.createQuery("SELECT m.moviename, m.userrating, m.releasedate "
                + "FROM Memoir m WHERE m.personid.id = :id AND m.userrating IN "
                + "(SELECT MAX(m.userrating) FROM Memoir m WHERE m.personid.id = :id)",
                Object[].class);
        query.setParameter("id", personId);
        queryList = query.getResultList();
        System.out.println("Result" + queryList);
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Object[] row : queryList) {
            JsonObject personObject = Json.createObjectBuilder()
                    .add("MovieName", (String)row[0])
                    .add("UserRating", (Double)row[1])
                    .add("ReleaseDate", (String) dateFormat.format(row[2])).build();
            arrayBuilder.add(personObject);
        }
        JsonArray jArray= arrayBuilder.build();
        return jArray;
    }
    
//    @GET
//    @Path("findByPersonIdandMaxRating/{personId}")
//    @Produces({"application/json"})
//    public List<MaxRating> findByPersonIdandMaxRating(@PathParam("personId") String personId) {
//        TypedQuery<MaxRating> query = em.createQuery("SELECT new entities.MaxRating(m.moviename, m.userrating, m.releasedate) FROM Memoir m WHERE m.personid.id = :id AND m.userrating IN (SELECT MAX(m.userrating) FROM Memoir m WHERE m.personid.id = :id)", MaxRating.class);
//        query.setParameter("id", personId);
//        return query.getResultList();
//    }
    
    @GET
    @Path("findByMonthnameAndPersonIdAndYear/{personId}/{userWatchYear}")
    @Produces({"application/json"})
    public Object findByMonthnameAndPersonIdAndYear(@PathParam("personId") String 
            personId, 
            @PathParam("userWatchYear") String userWatchYear) throws Exception
    {
        TypedQuery<Object[]> query = null;
        List<Object[]> queryList;
        query = em.createQuery("SELECT EXTRACT(MONTH from m.userwatchdate), count(m) "
                + "FROM Memoir m WHERE m.personid.id = :id AND EXTRACT(YEAR from "
                + "m.userwatchdate) = :userWatchYear GROUP BY EXTRACT(MONTH "
                + "from m.userwatchdate)", Object[].class);
        query.setParameter("id", personId);
        query.setParameter("userWatchYear", userWatchYear);
        queryList = query.getResultList();
        System.out.println("Result" + queryList);
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Object[] row : queryList) {
            JsonObject personObject = Json.createObjectBuilder()
                    .add("MonthName", (String) getMonthForInt((int)row[0] - 1))
                    .add("MovieCount", (Long)row[1]).build();
            arrayBuilder.add(personObject);
        }
        JsonArray jArray= arrayBuilder.build();
        return jArray;
    }
    
    @GET
    @Path("findByPersonIdandRepeatMovieName/{personId}")
    @Produces({"application/json"})
    public Object findByPersonIdandRepeatMovieName(
            @PathParam("personId") String personId) throws Exception
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        TypedQuery<Object[]> query = null;
        List<Object[]> queryList;
        query = em.createQuery("Select m.moviename, m.releasedate from Memoir m "
                + "where m.personid.id = :id and m.moviename in ( select m.moviename "
                + "from Memoir m where m.personid.id = :id group by m.moviename having "
                + "count(distinct m.releasedate) > 1)", Object[].class);
        query.setParameter("id", personId);
        queryList = query.getResultList();
        System.out.println("Result" + queryList);
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Object[] row : queryList) {
            JsonObject personObject = Json.createObjectBuilder()
                    .add("MovieName", (String)row[0])
                    .add("ReleaseDate", (String) dateFormat.format(row[1])).build();
            arrayBuilder.add(personObject);
        }
        JsonArray jArray= arrayBuilder.build();
        return jArray;
    }
    
    @GET
    @Path("findByPersonIdandSameReleaseYearandUserWatchYear/{personId}")
    @Produces({"application/json"})
    public Object findByPersonIdandSameReleaseYearandUserWatchYear(
            @PathParam("personId") String personId) throws Exception
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        TypedQuery<Object[]> query = null;
        List<Object[]> queryList;
        query = em.createQuery("Select m.moviename, m.userrating,m.releasedate from Memoir m "
                + "where m.personid.id = :id and EXTRACT(YEAR from "
                + "m.userwatchdate) = EXTRACT(YEAR from m.releasedate)", 
                Object[].class);
        query.setParameter("id", personId);
        queryList = query.getResultList();
        System.out.println("Result" + queryList);
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Object[] row : queryList) {
            JsonObject personObject = Json.createObjectBuilder()
                    .add("MovieName", (String)row[0])
                    .add("UserRating",(Double)row[1])
                    .add("ReleaseDate", (String) dateFormat.format(row[2])).build();
            arrayBuilder.add(personObject);
        }
        JsonArray jArray= arrayBuilder.build();
        return jArray;
    }
    
    @GET
    @Path("findByPersonIdBestRatedCurrentYear/{personId}")
    @Produces({"application/json"})
    public Object findByPersonIdBestRatedCurrentYear(
            @PathParam("personId") String personId) throws Exception
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        TypedQuery<Object[]> query = null;
        List<Object[]> queryList;
        query = em.createQuery("SELECT m.id, m.moviename, m.userrating, m.releasedate "
                + "FROM Memoir m WHERE m.personid.id = :id AND EXTRACT( YEAR from "
                + "m.releasedate) = EXTRACT(YEAR from current_timestamp) order by "
                + "m.userrating DESC", Object[].class);
        query.setParameter("id", personId);
        query.setMaxResults(5);
        queryList = query.getResultList();
        System.out.println("Result" + queryList);
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Object[] row : queryList) {
            JsonObject personObject = Json.createObjectBuilder()
                    .add("id", (String)row[0])
                    .add("MovieName", (String)row[1])
                    .add("UserRating", (Double)row[2])
                    .add("ReleaseDate", (String) dateFormat.format(row[3])).build();
            arrayBuilder.add(personObject);
        }
        JsonArray jArray= arrayBuilder.build();
        return jArray;
    }
    @POST
    @Path("memoirAddition/")
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
        String cid = data.getString("cinemaId");
        String title = data.getString("title");
        String rel_year = data.getString("rel_year");
        String opinion = data.getString("opinion");
        String rating = data.getString("user_rating");
        String pid = data.getString("personId");
        String userWatchDate = data.getString("user_watch_date");
        Date ReleaseYear = null;
        Date user_watch_date = null;
        try{
            ReleaseYear = new SimpleDateFormat("dd MMM yyyy").parse(rel_year);
            user_watch_date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").parse(userWatchDate);
            System.out.println(ReleaseYear);
        }
        catch(ParseException e)
        {
            System.out.println("Parse Exception Detected");
        }
        //System.out.println(object);
        Query query = em.createNamedQuery("Cinema.findById");
        query.setParameter("id", cid);
        Cinema cinemainfo = (Cinema) query.getSingleResult();
        Query query1 = em.createNamedQuery("Person.findById");
        query1.setParameter("id", pid);
        double usrrating = Double.parseDouble(rating);
        Person personInfo = (Person) query1.getSingleResult();
        UUID mid = UUID.randomUUID();
        Memoir m;
        m = new Memoir(mid.toString(),title, ReleaseYear, user_watch_date, usrrating, cinemainfo, opinion, personInfo);
        create(m);
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
    
    private String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }
    
}
