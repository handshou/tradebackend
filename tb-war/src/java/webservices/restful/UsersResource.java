package webservices.restful;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entity.UserEntity;
import error.NoResultException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.UserSessionLocal;

/**
 * REST Web Service
 *
 * @author hans
 */
@Path("/users")
public class UsersResource {
    @EJB
    private UserSessionLocal usl;
    Type listType = new TypeToken<List<UserEntity>>() {}.getType();
    
    @Context
    private HttpHeaders headers;
    
    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchUsers(@PathParam("username") String name) 
            throws NoResultException {
        if (!getAuthHeader().toLowerCase().equals("admin")) {
            return Response.status(401).build();
        }
        if (name != null) {
            List<UserEntity> users = usl.getUsers(name);
            List<UserEntity> result = new ArrayList<>();
            for (UserEntity user : users) {
                UserEntity u = new UserEntity();
                u.setId(user.getId());
                u.setUsername(user.getUsername());
                u.setName(user.getName());
                result.add(u);
            }
            Gson gson = new Gson();
            String json = gson.toJson(result, listType);
            return Response.status(200).entity(json).build();
        }
        else {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "No query conditions")
                    .build();
            return Response.status(404).entity(exception).build();
        }
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() throws NoResultException {
    
    // TODO enable to prevent access
    //  if (!getAuthHeader().toLowerCase().equals("admin")) {
    //  return Response.status(401).build();
    //  }
            
        List<UserEntity> users = usl.getUsers(null);
        Gson gson = new Gson();
        String json = gson.toJson(users, listType);
        
        return Response.status(200).entity(json).build();
    }
    
    public String getAuthHeader() {
        List<String> authHeaders = headers.getRequestHeader(HttpHeaders.AUTHORIZATION);
        return authHeaders.get(0);
    }
    
    
    
}
