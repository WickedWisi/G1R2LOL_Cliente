/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Eneko
 */
import java.io.Serializable;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Trabaja implements Serializable {


    private TrabajaId trabajaid;
    
    
    
    private String puesto;

    private Socio socio;
    

    private Sede sede;

    public TrabajaId getTrabajaid() {
        return trabajaid;
    }

    public void setTrabajaid(TrabajaId trabajaid) {
        this.trabajaid = trabajaid;
    }


    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.trabajaid);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Trabaja other = (Trabaja) obj;
        if (!Objects.equals(this.trabajaid, other.trabajaid)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Trabaja{" + "trabajaid=" + trabajaid + '}';
    }

   

    

    

}
