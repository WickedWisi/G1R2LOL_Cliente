/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;
import model.Patrocinador;
import model.Sede;
import model.Socio;

/**
 *
 * @author Josu
 */
@XmlRootElement(name = "evento")
public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id_evento;

    private String nombre;

    private Date fechaEvento;

    private String descripcion;

    private Integer aforo;

    private Boolean catering;

    private Set<Patrocinador> patrocinador;

    private Sede sede;

    private Socio socio;
    
     @Override
    public String toString() {
        return "Evento{" + "id_evento=" + id_evento + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.id_evento);
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
        final Evento other = (Evento) obj;
        if (!Objects.equals(this.id_evento, other.id_evento)) {
            return false;
        }
        return true;
    }

    public Integer getId_evento() {
        return id_evento;
    }

    public void setId_evento(Integer id_evento) {
        this.id_evento = id_evento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getAforo() {
        return aforo;
    }

    public void setAforo(Integer aforo) {
        this.aforo = aforo;
    }

    public Boolean getCatering() {
        return catering;
    }

    public void setCatering(Boolean catering) {
        this.catering = catering;
    }

    public Set<Patrocinador> getPatrocinador() {
        return patrocinador;
    }

    public void setPatrocinador(Set<Patrocinador> patrocinador) {
        this.patrocinador = patrocinador;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

}



   

