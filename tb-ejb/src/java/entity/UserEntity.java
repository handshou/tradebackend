package entity;

import java.io.Serializable;
import java.time.ZonedDateTime;
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

/**
 *
 * @author hans
 */
@Entity
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String username;
    private String password;
    private ZonedDateTime dateJoined;
    private boolean isDeactivated;
    
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)  
    private ProfileEntity userProfile;
    
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)  
    private StoreEntity userStore;
    
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)  
    private final List<FeedbackEntity> userFeedback = 
            new ArrayList<>();
    
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
     * @return the dateJoined
     */
    public ZonedDateTime getDateJoined() {
        return dateJoined;
    }

    /**
     * @param dateJoined the dateJoined to set
     */
    public void setDateJoined(ZonedDateTime dateJoined) {
        this.dateJoined = dateJoined;
    }

    /**
     * @return the isDeactivated
     */
    public boolean isDeactivated() {
        return isDeactivated;
    }

    /**
     * @param isDeactivated the isDeactivated to set
     */
    public void setDeactivated(boolean isDeactivated) {
        this.isDeactivated = isDeactivated;
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
    
}
