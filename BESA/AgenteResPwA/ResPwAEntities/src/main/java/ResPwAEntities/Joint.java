/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author tesispepper
 */
@Entity
@Table(name = "joint")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Joint.findAll", query = "SELECT j FROM Joint j"),
    @NamedQuery(name = "Joint.findById", query = "SELECT j FROM Joint j WHERE j.id = :id"),
    @NamedQuery(name = "Joint.findByNombre", query = "SELECT j FROM Joint j WHERE j.nombre = :nombre"),
    @NamedQuery(name = "Joint.findByAngulo", query = "SELECT j FROM Joint j WHERE j.angulo = :angulo"),
    @NamedQuery(name = "Joint.findByTiempo", query = "SELECT j FROM Joint j WHERE j.tiempo = :tiempo")})
public class Joint implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "angulo")
    private double angulo;
    @Basic(optional = false)
    @Column(name = "tiempo")
    private double tiempo;
    @ManyToMany(mappedBy = "jointList", fetch = FetchType.EAGER)
    private List<Accion> accionList;

    public Joint() {
    }

    public Joint(Integer id) {
        this.id = id;
    }

    public Joint(Integer id, String nombre, double angulo, double tiempo) {
        this.id = id;
        this.nombre = nombre;
        this.angulo = angulo;
        this.tiempo = tiempo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getAngulo() {
        return angulo;
    }

    public void setAngulo(double angulo) {
        this.angulo = angulo;
    }

    public double getTiempo() {
        return tiempo;
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }

    @XmlTransient
    public List<Accion> getAccionList() {
        return accionList;
    }

    public void setAccionList(List<Accion> accionList) {
        this.accionList = accionList;
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
        if (!(object instanceof Joint)) {
            return false;
        }
        Joint other = (Joint) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Joint[ id=" + id + " ]";
    }
    
}
