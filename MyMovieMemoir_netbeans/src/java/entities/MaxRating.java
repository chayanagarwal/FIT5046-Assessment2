/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author apple
 */
@XmlRootElement
@Entity
public class MaxRating implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String movieName;
    private Double userRating;
    @Temporal(TemporalType.DATE)
    private Date releaseDate;
    
    public MaxRating(){
    }
    
    public MaxRating(String movieName, Double userRating, Date releaseDate)
    {
        this.movieName = movieName;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
    }
            
    public String getId() {
        return movieName;
    }

    public void setId(String id) {
        this.movieName = id;
    }
    
    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releasedate) {
        this.releaseDate = releasedate;
    }
    
    public double getUserRating() {
        return userRating;
    }

    public void setUserRating(double userrating) {
        this.userRating = userrating;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (movieName != null ? movieName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MaxRating)) {
            return false;
        }
        MaxRating other = (MaxRating) object;
        if ((this.movieName == null && other.movieName != null) || (this.movieName != null && !this.movieName.equals(other.movieName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.MaxRating[ id=" + movieName + " ]";
    }
    
}
