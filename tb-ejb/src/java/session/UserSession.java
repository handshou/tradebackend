package session;

import entity.UserEntity;
import error.NoResultException;
import java.util.Arrays;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author hans
 */
@Stateless
public class UserSession implements UserSessionLocal {
    @PersistenceContext
    private EntityManager em;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public List<UserEntity> searchUsers(String name) throws NoResultException {
        Query q;
        if (name != null) {
            q = em.createQuery("SELECT u FROM UserEntity u WHERE "
                + "LOWER(u.name) LIKE :name");
            q.setParameter("name", "%" + name.toLowerCase() + "%");
        } 
        else {
            q = em.createQuery("SELECT u FROM UserEntity u");
        }
        return q.getResultList();
    }
}
