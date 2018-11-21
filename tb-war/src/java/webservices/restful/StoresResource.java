package webservices.restful;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entity.StoreEntity;
import entity.UserEntity;
import error.NoResultException;
import java.lang.reflect.Type;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.core.Context;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.UserSessionLocal;

/**
 * REST Web Service
 *
 * @author hans
 */
@Path("/stores")
public class StoresResource {
    @EJB
    private UserSessionLocal usl;
    Type listType = new TypeToken<List<UserEntity>>() {}.getType();
    
    @Context
    private HttpHeaders headers;
    
    public String getAuthHeader() {
        List<String> authHeaders = headers.getRequestHeader(HttpHeaders.AUTHORIZATION);
        if (authHeaders.isEmpty()) {
            return "";
        }
        return authHeaders.get(0);
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStores() throws NoResultException {
        if (!getAuthHeader().toLowerCase().equals("admin")) {
            return Response.status(401).build();
        }
            
        List<StoreEntity> stores = usl.getAllStores();
        
        Gson gson = new Gson();
        String json = gson.toJson(stores, listType);
        
        return Response.status(200).entity(json).build();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchUsers(@PathParam("id") long storeId) 
            throws NoResultException {
        List<Long> storeIds = usl.getStoreIds();
        if (storeId != 0 && storeIds.contains(storeId)) {
            StoreEntity store = usl.getStore(storeId);
            Gson gson = new Gson();
            String json = gson.toJson(store);
            return Response.status(200).entity(json).build();
        }
        else {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Store not found")
                    .build();
            return Response.status(404).entity(exception).build();
        }
    }
    
}
