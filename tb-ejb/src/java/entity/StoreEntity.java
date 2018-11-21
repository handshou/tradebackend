package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author hans
 */
@Entity
public class StoreEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * @return the numAllStores
     */
    public static int getNumAllStores() {
        return numAllStores;
    }

    /**
     * @param aNumAllStores the numAllStores to set
     */
    public static void setNumAllStores(int aNumAllStores) {
        numAllStores = aNumAllStores;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)  
    private List<ItemEntity> items;

    private static int numAllStores = 0;
    
    private long userId = 0;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the items
     */
    public List<ItemEntity> getItems() {
        return items;
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
        if (!(object instanceof StoreEntity)) {
            return false;
        }
        StoreEntity other = (StoreEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.StoreEntity[ id=" + id + " ]";
    }

    /**
     * @return the userId
     */
    public long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }
    
}
