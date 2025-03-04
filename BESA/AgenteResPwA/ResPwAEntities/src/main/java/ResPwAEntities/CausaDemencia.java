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
@Table(name = "causa_demencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CausaDemencia.findAll", query = "SELECT c FROM CausaDemencia c"),
    @NamedQuery(name = "CausaDemencia.findByCondicion", query = "SELECT c FROM CausaDemencia c WHERE c.condicion = :condicion")})
public class CausaDemencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "condicion")
    private String condicion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "causaDemenciaCondicion", fetch = FetchType.EAGER)
    private List<PerfilMedico> perfilMedicoList;

    public CausaDemencia() {
    }

    public CausaDemencia(String condicion) {
        this.condicion = condicion;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    @XmlTransient
    public List<PerfilMedico> getPerfilMedicoList() {
        return perfilMedicoList;
    }

    public void setPerfilMedicoList(List<PerfilMedico> perfilMedicoList) {
        this.perfilMedicoList = perfilMedicoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (condicion != null ? condicion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CausaDemencia)) {
            return false;
        }
        CausaDemencia other = (CausaDemencia) object;
        if ((this.condicion == null && other.condicion != null) || (this.condicion != null && !this.condicion.equals(other.condicion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.CausaDemencia[ condicion=" + condicion + " ]";
    }
    
}
