package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author hans
 */
@Entity
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 
     * @return the numUsers
     */
    public static int getNumUsers() {
        return numUsers;
    }

    /**
     * @param aNumUsers the numUsers to set
     */
    public static void setNumUsers(int aNumUsers) {
        numUsers = aNumUsers;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    private String username;
    private String password;
    private boolean isActivated;
    private static int numUsers = 0;
    
    @Temporal(TemporalType.DATE)
    private Date created;
    
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)  
    private ProfileEntity userProfile;
    
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)  
    private CartEntity userCart;
    
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)  
    private StoreEntity userStore;
    
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)  
    private final List<FeedbackEntity> userFeedback = 
            new ArrayList<>();
    
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)  
    private static final List<UserEntity> allUsers = new ArrayList<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserEntity)) {
            return false;
        }
        UserEntity other = (UserEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.UserEntity[ id=" + id + " ]";
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the isDeactivated
     */
    public boolean isDeactivated() {
        return !isActivated;
    }
    
    /**
     * @return the isActivated
     */
    public boolean isActivated() {
        return isActivated;
    }

    /**
     * @param isActivated to set
     */
    public void setActivated(boolean isActivated) {
        this.isActivated = isActivated;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
    
    /**
     * @return the userProfile
     */
    public ProfileEntity getUserProfile() {
        return userProfile;
    }

    /**
     * @param userProfile the userProfile to set
     */
    public void setUserProfile(ProfileEntity userProfile) {
        this.userProfile = userProfile;
    }

    /**
     * @return the userStore
     */
    public StoreEntity getUserStore() {
        return userStore;
    }

    /**
     * @param userStore the userStore to set
     */
    public void setUserStore(StoreEntity userStore) {
        this.userStore = userStore;
    }

    /**
     * @return the userFeedback
     */
    public List<FeedbackEntity> getUserFeedback() {
        return userFeedback;
    }

    /**
     * @return the allUsers
     */
//    public static List<UserEntity> getAllUsers() {
//        return allUsers;
//    }
    
//    public boolean addUser(UserEntity newUser) {
//        if (allUsers.add(newUser)) {
//            setNumUsers(allUsers.size());   
//            return true;
//        }
//        return false;
//    }
    
//    public boolean createUser(UserEntity newUser) {
//        if (allUsers.add(newUser)) {
//            setNumUsers(allUsers.size());   
//            return true;
//        }
//        return false;
//    }
    
    public boolean isSameUser(UserEntity user1, UserEntity user2) {
        return user1.equals(user2);
    }
    
//    public boolean isExistingUser(UserEntity userEntity) {
//        return allUsers.contains(userEntity);
//    }
    
//    public UserEntity getUser(int id) {
//        for (UserEntity user : allUsers) {
//            if (user.getId().equals((long) id)) {
//                return user;
//            }
//        }
//        return null;
//    }
    
//    public UserEntity getUser(String username) {
//        for (UserEntity user : allUsers) {
//            if (user.getUsername().equals(username)) {
//                return user;
//            }
//        }
//        return null;
//    }
    
    
//    public StoreEntity getUserStore(UserEntity user) {
//        for (UserEntity u : UserEntity.getAllUsers()) {
//            if (u.equals(user)) {
//                return user.getUserStore();
//            }
//        }
//        return null;
//    }

    /**
     * @return the userCart
     */
    public CartEntity getUserCart() {
        return userCart;
    }

    /**
     * @param userCart the userCart to set
     */
    public void setUserCart(CartEntity userCart) {
        this.userCart = userCart;
    }
    
    public boolean editUser(UserEntity newUser) {
        if (newUser == null)
            return false;
        if (isSameUser(newUser, this)) {
            if (newUser.isActivated() != this.isActivated())
                this.setActivated(newUser.isActivated());
            if (newUser.getName() != null)
                this.setName(newUser.getName());
            if (newUser.getUsername() != null)
                this.setUsername(newUser.getUsername());
            if (newUser.getPassword() != null)
                this.setPassword(newUser.getPassword());
            return true;
        }
        return false;
    }
    
//    public boolean isExistingUsername(String username) {
//        for (UserEntity u : allUsers) {
//            if (u.getUsername().toLowerCase().equals(username.toLowerCase())) {
//                return true;
//            }                
//        }
//        return false;
//    }
    
}
