package webservices.restful;

import entity.UserEntity;
import error.NoResultException;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.UserSessionLocal;

/**
 * REST Web Service
 *
 * @author hans
 */
@Path("users")
public class UsersResource {
    @EJB
    private UserSessionLocal usl;
    
    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchUsers(@QueryParam("name") String name) 
            throws NoResultException {
        if (name != null) {
            List<UserEntity> results = usl.searchUsers(name);
            GenericEntity<List<UserEntity>> entity = 
                    new GenericEntity<List<UserEntity>>(results){};
            return Response.status(200).entity(entity).build();
        }
        else {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "No query conditions")
                    .build();
            return Response.status(400).entity(exception).build();
        }
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserEntity> getAllUsers() throws NoResultException {
        return usl.searchUsers(null);
    }
}
