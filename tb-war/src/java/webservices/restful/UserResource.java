package webservices.restful;

import com.google.gson.Gson;
import entity.ItemEntity;
import entity.ProfileEntity;
import entity.StoreEntity;
import entity.UserEntity;
import error.NoResultException;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.UserSession.Command;
import session.UserSessionLocal;

/**
 * REST Web Service
 *
 * @author hans
 */
@Path("/user")
public class UserResource {
    @EJB
    private UserSessionLocal usl;
    
    @Context
    private HttpHeaders headers;
    
    public String getAuthHeader(int zeroIndex, String type) {
        List<String> authHeaders = headers.getRequestHeader(type);
        return authHeaders.get(zeroIndex);
    }
    
    public String getAuthHeader() {
        String type = HttpHeaders.AUTHORIZATION;
        return getAuthHeader(0, type);
    }
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(JsonObject jsonObj) throws NoResultException {
        String username = jsonObj.getString("username");
        String password = jsonObj.getString("password");
        if (usl.login(username, password)) {  
            UserEntity u = usl.getUser(username, Command.USER);
            Gson gson = new Gson();
            String json = gson.toJson(u);

            return Response.status(200).entity(json).build();  
        } else 
        {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Invalid credentials")
                    .build();
            return Response.status(400).entity(exception).build();
        }
    }

    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchUser(@PathParam("id") long uId) 
            throws NoResultException {
        Command type = (getAuthHeader().equals("admin")) ? Command.ADMIN : Command.USER;
        if (uId != 0) {
            UserEntity u = usl.getUser(uId, type);
            Gson gson = new Gson();
            String json = gson.toJson(u);
            return Response.status(200).entity(json).build();
        }
        else {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "No query conditions")
                    .build();
            return Response.status(404).entity(exception).build();
        }
    }
    
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(UserEntity u) throws NoResultException {
        if (usl.addUser(
                u.getUsername(), 
                u.getPassword(),
                u.getName())) {
            UserEntity newUser = usl.getUser(u.getUsername(), Command.USER);
            Gson gson = new Gson();
            String json = gson.toJson(newUser);
            return Response.status(200).entity(json).build();
        }
        
        JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Unable to create user")
                    .build();
        return Response.status(404).entity(exception).build();
    }    
    
    @GET
    @Path("/store")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyStore() throws NoResultException {
        String username = getAuthHeader();
        Command type = (username.equals("admin")) ? Command.ADMIN : Command.USER;

        UserEntity u = usl.getUser(username, type);
        
        if (u != null) {
            StoreEntity myStore = usl.getMyStore(u);
            Gson gson = new Gson();

            String json = gson.toJson(myStore);
            return Response.status(200).entity(json).build();
            
        } else {
            
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "User does not exist")
                    .build();
            return Response.status(401).entity(exception).build();
        }
    }
    
    @POST
    @Path("/store/item")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createItem(ItemEntity i) throws NoResultException {
        String username = getAuthHeader();
        Command type = (username.equals("admin")) ? Command.ADMIN : Command.USER;

        UserEntity u = usl.getUser(username, type);
        
        if (u != null) {
            StoreEntity myStore = usl.getMyStore(u);
            ItemEntity newItem = usl.createItemInStore(
                i.getName(),
                i.getDescription(),
                i.getCategory(),
                myStore.getId(),
                i.getPrice());
            
            usl.addItemToStore(myStore.getId(), newItem);
            
            Gson gson = new Gson();
            String json = gson.toJson(newItem);
            return Response.status(200).entity(json).build();
            
        } else {
            
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "User does not exist")
                    .build();
            return Response.status(401).entity(exception).build();
        }
    }

    @GET
    @Path("/{userId}/store")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserStore(@PathParam("userId") long uId) throws NoResultException {
        Command type = (getAuthHeader().equals("admin")) ? Command.ADMIN : Command.USER;

        UserEntity u = usl.getUser(uId, type);
        
        if (u != null) {
            StoreEntity myStore = usl.getMyStore(u);
            Gson gson = new Gson();

            String json = gson.toJson(myStore);
            return Response.status(200).entity(json).build();
            
        } else {
            
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "User does not exist")
                    .build();
            return Response.status(401).entity(exception).build();
        }
    }
    
    @GET
    @Path("/profile")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyProfile() throws NoResultException {
        String username = getAuthHeader();
        Command type = (username.equals("admin")) ? Command.ADMIN : Command.USER;
        
        UserEntity u = usl.getUser(username, type);
        
        if (u != null) {
            ProfileEntity myProfile = usl.getMyProfile(u);
            Gson gson = new Gson();

            String json = gson.toJson(myProfile);
            return Response.status(200).entity(json).build();
            
        } else {
            
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "User does not exist")
                    .build();
            return Response.status(401).entity(exception).build();
        }
    }
    
    @GET
    @Path("/{userId}/profile")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserProfile(@PathParam("userId") long uId) throws NoResultException {
        Command type = (getAuthHeader().equals("admin")) ? Command.ADMIN : Command.USER;
        UserEntity u = usl.getUser(uId, type);
        
        if (u != null) {
            ProfileEntity myProfile = usl.getMyProfile(u);
            Gson gson = new Gson();

            String json = gson.toJson(myProfile);
            return Response.status(200).entity(json).build();
            
        } else {
            
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "User does not exist")
                    .build();
            return Response.status(401).entity(exception).build();
        }
    }
    
    @PATCH
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editUser(UserEntity u) {
        if (usl.editUser(u)) {
            return Response.status(204).build();
        }
        
        JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Unable to edit user")
                    .build();
        return Response.status(404).entity(exception).build();
    }    
}