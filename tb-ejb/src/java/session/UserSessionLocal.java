package session;

import entity.UserEntity;
import error.NoResultException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author hans
 */
@Local
public interface UserSessionLocal {

    public List<UserEntity> searchUsers(String name) throws NoResultException;
}
