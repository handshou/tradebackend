package session;

import entity.ItemEntity;
import entity.ProfileEntity;
import entity.StoreEntity;
import entity.UserEntity;
import error.NoResultException;
import java.util.List;
import javax.ejb.Local;
import session.UserSession.Command;

/**
 *
 * @author hans
 */
@Local
public interface UserSessionLocal {

    public List<UserEntity> getUsers(String keyword) throws NoResultException;
    public List<UserEntity> getActivatedUsers(String keyword) throws NoResultException;
    public UserEntity getUser(long id, Command type);
    public UserEntity getUser(String username, Command type) throws NoResultException;
    public boolean addUser(String username, String password, String name);
    public ItemEntity createItemInStore(String name, String desc, String category, long storeId, double price);
    public boolean updateItem(ItemEntity i);
    public ItemEntity getItem(long itemId);
    public boolean isItemSold(ItemEntity item);
    public boolean removeItemFromStore(long storeId, ItemEntity item);
    public boolean removeItemFromCart(long cartId, ItemEntity item);
    public List<ItemEntity> getItemsByKeyword(String keyword);
    public List<ItemEntity> getItemsByCategory(String category);
    public boolean addItemToStore(long storeId, ItemEntity item);
    public boolean addItemToCart(long cartId, ItemEntity item);
    public List<StoreEntity> getAllStores();
    public StoreEntity getStore(UserEntity u);
    public StoreEntity getStore(long storeId);
    public List<Long> getStoreIds();
    public StoreEntity getMyStore(UserEntity u);
    public ProfileEntity getMyProfile(UserEntity u);
    public boolean login(String username, String password);
    public boolean editUser(UserEntity u);
    public boolean isExistingUsername(String username);
    public String getUsername(long uId);

}
