/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.my.movie.memoir;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author apple
 */
@Entity
@Table(name = "MEMOIR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Memoir.findAll", query = "SELECT m FROM Memoir m")
    , @NamedQuery(name = "Memoir.findById", query = "SELECT m FROM Memoir m WHERE m.id = :id")
    , @NamedQuery(name = "Memoir.findByPersonId", query = "SELECT m FROM Memoir m WHERE m.personid = :personid")
    , @NamedQuery(name = "Memoir.findByMoviename", query = "SELECT m FROM Memoir m WHERE m.moviename = :moviename")
    , @NamedQuery(name = "Memoir.findByReleasedate", query = "SELECT m FROM Memoir m WHERE m.releasedate = :releasedate")
    , @NamedQuery(name = "Memoir.findByUserwatchdate", query = "SELECT m FROM Memoir m WHERE m.userwatchdate = :userwatchdate")
    , @NamedQuery(name = "Memoir.findByUseropinion", query = "SELECT m FROM Memoir m WHERE m.useropinion = :useropinion")
    , @NamedQuery(name = "Memoir.findByUserratingANDPostcode", query = "SELECT m FROM Memoir m "
            + "WHERE m.userrating = :userrating AND m.cinemaid.postcode=:postcode")
    , @NamedQuery(name = "Memoir.findByUserrating", query = "SELECT m FROM Memoir m WHERE m.userrating = :userrating")})
public class Memoir implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "MOVIENAME")
    private String moviename;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RELEASEDATE")
    @Temporal(TemporalType.DATE)
    private Date releasedate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "USERWATCHDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date userwatchdate;
    @Size(max = 200)
    @Column(name = "USEROPINION")
    private String useropinion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "USERRATING")
    private double userrating;
    @JoinColumn(name = "CINEMAID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Cinema cinemaid;
    @JoinColumn(name = "PERSONID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Person personid;

    public Memoir() {
    }

    public Memoir(String id) {
        this.id = id;
    }

    public Memoir(String id, String moviename, Date releasedate, Date userwatchdate, double userrating, Cinema cinemaId, String useropinion, Person personId) {
        this.id = id;
        this.moviename = moviename;
        this.releasedate = releasedate;
        this.userwatchdate = userwatchdate;
        this.userrating = userrating;
        this.useropinion = useropinion;
        this.cinemaid = cinemaId;
        this.personid = personId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public Date getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(Date releasedate) {
        this.releasedate = releasedate;
    }

    public Date getUserwatchdate() {
        return userwatchdate;
    }

    public void setUserwatchdate(Date userwatchdate) {
        this.userwatchdate = userwatchdate;
    }

    public String getUseropinion() {
        return useropinion;
    }

    public void setUseropinion(String useropinion) {
        this.useropinion = useropinion;
    }

    public double getUserrating() {
        return userrating;
    }

    public void setUserrating(double userrating) {
        this.userrating = userrating;
    }

    public Cinema getCinemaid() {
        return cinemaid;
    }

    public void setCinemaid(Cinema cinemaid) {
        this.cinemaid = cinemaid;
    }

    public Person getPersonid() {
        return personid;
    }

    public void setPersonid(Person personid) {
        this.personid = personid;
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
        if (!(object instanceof Memoir)) {
            return false;
        }
        Memoir other = (Memoir) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.my.movie.memoir.Memoir[ id=" + id + " ]";
    }
    
}
