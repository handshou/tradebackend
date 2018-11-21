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

/**
 *
 * @author hans
 */
@Entity
public class CartEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)  
    private List<ItemEntity> itemList;
    
    private double totalPrice;
    private long userId;

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
        if (!(object instanceof CartEntity)) {
            return false;
        }
        CartEntity other = (CartEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CartEntity[ id=" + id + " ]";
    }

    /**
     * @return the itemList
     */
    public List<ItemEntity> getItemList() {
        return itemList;
    }

    /**
     * @param itemList the itemList to set
     */
    public void setItemList(List<ItemEntity> itemList) {
        this.itemList = itemList;
    }
    
    public boolean isEmpty() {
        if (itemList == null) {
            itemList = new ArrayList<>();
        }
        return itemList.isEmpty();
    }
    
    public boolean addItem(ItemEntity item) {
        isEmpty();
        return itemList.add(item);
    }
    
    public boolean removeItem(ItemEntity item) {
        if (isEmpty()) {
            return false;
        }
        return itemList.remove(item);
    }
    
    public boolean updateItem(ItemEntity original, ItemEntity item) {
        if (isEmpty()) {
            return false;
        }
        if (!itemList.contains(original) || 
                item.getId() == null ||
                item.getId().equals(original.getId())
                ) {
            return false;
        }
        original.updateItem(item);
        return true;
    }

    /**
     * @return the totalPrice
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * @param totalPrice the totalPrice to set
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public double calculateTotalPrice() {
        double result = 0;
        for (ItemEntity item : itemList) {
            result += item.getPrice();
        }
        totalPrice = result;
        return totalPrice;
    }
    
    public void checkout() {
        
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
