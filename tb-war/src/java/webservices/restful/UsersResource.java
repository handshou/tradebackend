package webservices.restful;

import entity.UserEntity;
import error.NoResultException;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import session.UserSessionLocal;

/**
 * REST Web Service
 *
 * @author hans
 */
@Path("users")
public class UsersResource {
    @EJB
    private UserSessionLocal userSessionLocal;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserEntity> getAllUsers() throws NoResultException {
        return userSessionLocal.searchUsers(null);
    }
}
