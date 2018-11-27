package session;

import entity.CartEntity;
import entity.ItemEntity;
import entity.ProfileEntity;
import entity.StoreEntity;
import entity.UserEntity;
import error.NoResultException;
import java.util.Date;
import java.util.ArrayList;
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
    public boolean login(String username, String password) {
        Query q;
        q = em.createQuery("Select u FROM UserEntity u WHERE "
            + "LOWER(u.username) = :username AND "
            + "u.password = :password AND "
            + "u.isActivated = true");
        q.setParameter("username", username.toLowerCase());
        q.setParameter("password", password);
        System.out.println("#########" + "\n" + username + "\n" + password);
            
        return !q.getResultList().isEmpty();
    }

    @Override
    public boolean editUser(UserEntity u) {
        Query q;
        q = em.createQuery("Select u FROM UserEntity u WHERE "
            + "u.id = :userId");
        q.setParameter("userId", u.getId());
        
        UserEntity target = null;
        if (!q.getResultList().isEmpty()) {
            target = (UserEntity) q.getResultList().get(0);
            
            String newUsername = u.getUsername();
            String currentUsername = getUsername(u.getId());
            boolean changeUsername = !(currentUsername.toLowerCase().equals(newUsername.toLowerCase()));
            if (changeUsername && isExistingUsername(newUsername))
                return false;
            
            target.editUser(u);
            // might have bugs
            em.persist(target);
            return true;
        }
        return false;
    }

    @Override
    public String getUsername(long uId) {
        Query q = em.createQuery("SELECT u.username FROM UserEntity u WHERE "
                + "u.id = :id");
        q.setParameter("id", uId);
        
        return q.getSingleResult().toString();
    }

    @Override
    public boolean isItemSold(ItemEntity item) {
        return (item.getOrderId() != 0);
    }

    public enum Command {
        ADMIN,
        USER,
    }
    
    @Override
    public List<UserEntity> getUsers(String username) throws NoResultException {
        Query q;
        if (username != null) {
            q = em.createQuery("SELECT u FROM UserEntity u WHERE "
                + "LOWER(u.username) LIKE :username");
            q.setParameter("username", "%" + username.toLowerCase() + "%");
        } 
        else {
            q = em.createQuery("SELECT u FROM UserEntity u");
        }
        return q.getResultList();
    }
    
    @Override
    public List<UserEntity> getActivatedUsers(String username) throws NoResultException {
        Query q;
        if (username != null) {
            q = em.createQuery("SELECT u FROM UserEntity u WHERE "
                + "LOWER(u.username) LIKE :username AND "
                + "u.isActivated = true");
            q.setParameter("username", "%" + username.toLowerCase() + "%");
        } 
        else {
            q = em.createQuery("SELECT u FROM UserEntity u WHERE " +
                    "u.isActivated = true");
        }
        return q.getResultList();
    }
    
    
    
    @Override
    public UserEntity getUser(String username, Command type) throws NoResultException {
        if (type.equals(Command.ADMIN)) {
            for (UserEntity u : getUsers(null)) {
                if (u.getUsername().equals(username))
                    return u;
            }
        } else {
            for (UserEntity u : getActivatedUsers(null)) {
                if (u.getUsername().equals(username))
                    return u;
            }
        }
        return null;
    }
    
    @Override
    public UserEntity getUser(long id, Command type) {
        if (type.equals(Command.ADMIN)) {
            UserEntity u = em.find(UserEntity.class, id);
            return u;
        } else {
            Query q = em.createQuery("SELECT u FROM UserEntity u WHERE "
                + "u.id = :id AND "
                + "u.isActivated = true");
            q.setParameter("id", id);     
            UserEntity u = (UserEntity) q.getResultList().get(0);
            
            return u;
        }
    }
    
    @Override
    public boolean isExistingUsername(String username) {
        Query q = em.createQuery("SELECT u FROM UserEntity u WHERE "
                + "u.username = :username");
        q.setParameter("username", username);     
            
        return !q.getResultList().isEmpty();
    }
    
    @Override
    public boolean addUser(String username, String password, String name) {
        if (isExistingUsername(username)) {
            return false;
        }
        
        UserEntity newUser = new UserEntity();
        StoreEntity store = new StoreEntity();
        em.persist(newUser);
        em.persist(store);
        
        System.out.println("### creating user ###" + "\n" + username + "\n" + password);

        
        try {
            store.setUserId(newUser.getId());
            newUser.setName(name);
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setActivated(true);
            newUser.setCreated(new Date());
            newUser.setUserCart(new CartEntity());
            newUser.setUserProfile(new ProfileEntity());
            newUser.setUserStore(store);
            em.persist(newUser);
            em.persist(store);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
       
        return true;
    }
    
    @Override
    public List<ItemEntity> getItemsByKeyword(String keyword) {
        Query q = em.createQuery("SELECT i FROM ItemEntity i WHERE LOWER(i.name) LIKE :name");
        q.setParameter("name", "%" + keyword.toLowerCase() + "%");

        return q.getResultList();
    }

    @Override
    public List<ItemEntity> getItemsByCategory(String category) {
        Query q = em.createQuery("SELECT i FROM ItemEntity i WHERE LOWER(i.category) LIKE :category");
        q.setParameter("category", "%" + category.toLowerCase() + "%");

        return q.getResultList();
    }
    
    @Override
    public ItemEntity createItemInStore(String name, String desc, 
            String category, long storeId, double price) {
        ItemEntity newItem = new ItemEntity();
        newItem.setName(name);
        newItem.setDescription(desc);
        newItem.setCategory(category);
        newItem.setPrice(price);
        newItem.setStoreId(storeId);
        
        return newItem;
    }
    
    @Override
    public boolean updateItem(ItemEntity i) {
        ItemEntity actualItem = getItem(i.getId());
        if (actualItem != null) {
            actualItem.updateItem(i);
            em.persist(actualItem);
            return true;
        }
        return false;
    }
    
    @Override
    public ItemEntity getItem(long itemId) {
        ItemEntity item = em.find(ItemEntity.class, itemId);
        return item;
    }
    
    @Override
    public boolean addItemToStore(long storeId, ItemEntity item) {
        StoreEntity store = em.find(StoreEntity.class, storeId);
        if (store != null) {
            store.getItems().add(item);
            em.persist(store);
            return true;
        }
        return false;
    }

    @Override
    public boolean addItemToCart(long cartId, ItemEntity item) {
        CartEntity cart = em.find(CartEntity.class, cartId);
        if (cart != null) {
            cart.addItem(item);
            em.persist(cart);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean removeItemFromStore(long storeId, ItemEntity item) {
        StoreEntity store = em.find(StoreEntity.class, storeId);
        if (store != null) {
            store.getItems().remove(item);
            em.persist(store);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeItemFromCart(long cartId, ItemEntity item) {
        CartEntity cart = em.find(CartEntity.class, cartId);
        if (cart != null) {
            cart.removeItem(item);
            em.persist(cart);
            return true;
        }
        return false;
    }

    @Override
    public List<StoreEntity> getAllStores() {
        Query q = em.createQuery("SELECT s "
                + "FROM UserEntity u "
                + "JOIN StoreEntity s "
                + "ON u.id = s.userId");
        System.out.println(q.getResultList());
        return q.getResultList();
    }

    @Override
    public StoreEntity getStore(UserEntity u) {
        return u.getUserStore();
    }

    @Override
    public StoreEntity getStore(long storeId) {
        StoreEntity store = em.find(StoreEntity.class, storeId);
        return store;
    }

    @Override
    public List<Long> getStoreIds() {
        Query q = em.createQuery("SELECT s FROM StoreEntity s");
        
        List<Long> result = new ArrayList<>();
        for (StoreEntity store : (List<StoreEntity>) q.getResultList()) {
            result.add(store.getId());
        }
        return result;
    }

    @Override
    public StoreEntity getMyStore(UserEntity u) {
        return u.getUserStore();
    }

    @Override
    public ProfileEntity getMyProfile(UserEntity u) {
        return u.getUserProfile();
    }
    
}
