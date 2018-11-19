package entity;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author hans
 */
@Entity
public class ProfileEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String biography;
    private ZonedDateTime dateJoined;

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
        if (!(object instanceof ProfileEntity)) {
            return false;
        }
        ProfileEntity other = (ProfileEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ProfileEntity[ id=" + id + " ]";
    }

    /**
     * @return the biography
     */
    public String getBiography() {
        return biography;
    }

    /**
     * @param biography the biography to set
     */
    public void setBiography(String biography) {
        this.biography = biography;
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
    
}
