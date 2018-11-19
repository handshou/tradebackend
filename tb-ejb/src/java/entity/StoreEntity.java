package entity;

import java.io.Serializable;
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
public class StoreEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)  
    private final List<ItemEntity> items = new ArrayList<>();
    
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)  
    private UserEntity owner;
    
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)  
    private final List<StoreEntity> allStores = new ArrayList<>();

    private static int numAllStores = 0;
    
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

    /**
     * @return the owner
     */
    public UserEntity getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    /**
     * @return the allStores
     */
    public List<StoreEntity> getAllStores() {
        return allStores;
    }
    
    public List<StoreEntity> getStores(int startId, int endId) {
        if (startId <= 0)
            return null;
        if (startId > endId || numAllStores == 0)
            return allStores;
        return allStores.stream()
                .filter(store -> 
                        store.getId() >= startId && store.getId() <= endId)
                .collect(null);
    }
    
    /**
     * @param appendStore refers to reference of store to be added
     * @return true if store is added
     */
    public boolean addStore(StoreEntity appendStore) {
        if (!allStores.add(appendStore)) {
            return false;
        }
        numAllStores = allStores.size();
        return true;
    }

    public boolean removeStore(StoreEntity removeStore) {
        if (!allStores.contains(removeStore)) {
            return false;
        }
        allStores.remove(removeStore);
        numAllStores = allStores.size();
        return true;
    }
    
    public StoreEntity getUserStore(UserEntity user) {
        return allStores.stream()
                .filter(store -> store.getOwner().equals(user))
                .distinct()
                .findFirst()
                .get();
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
    
}
