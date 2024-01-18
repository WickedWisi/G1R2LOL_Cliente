/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Egoitz Fernandez
 */
@XmlRootElement
public class Patrocinador implements Serializable {


    private Integer id_Patrocinador;
    private String nombre;
    private String Descripcion;
    private String email;
    private Integer telefono;
    private Date DuracionPatrocinio;

    private List<Evento> evento;

    public void setId_Patrocinador(Integer id_Patrocinador) {
        this.id_Patrocinador = id_Patrocinador;
    }

    public Integer getId_Patrocinador() {
        return id_Patrocinador;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setDuracionPatrocinio(Date DuracionPatrocinio) {
        this.DuracionPatrocinio = DuracionPatrocinio;
    }

    public Date getDuracionPatrocinio() {
        return DuracionPatrocinio;
    }

    public void setEvento(List<Evento> evento) {
        this.evento = evento;
    }

    @XmlTransient
    public List<Evento> getEvento() {
        return evento;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id_Patrocinador;
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
        final Patrocinador other = (Patrocinador) obj;
        if (this.id_Patrocinador != other.id_Patrocinador) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Patrocinador{" + "id_Patrocinador=" + id_Patrocinador + '}';
    }

}
