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

/**
 *
 * @author Eneko.
 */
@XmlRootElement
public class Sede implements Serializable {

    private Integer id_sede;

    private Date finDeContrato;

    private String pais;
    private Integer aforoMax;
    private Integer numVolMax;
    private String ubicacion;

    private List<Trabaja> trabaja;

    private List<Evento> evento;

    private List<Voluntario> voluntario;

    public void setId_sede(Integer id_sede) {
        this.id_sede = id_sede;
    }

    public Integer getId_sede() {
        return id_sede;
    }

    public void setFinDeContrato(Date finDeContrato) {
        this.finDeContrato = finDeContrato;
    }

    public Date getFinDeContrato() {
        return finDeContrato;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getPais() {
        return pais;
    }

    public void setNumVolMax(Integer numVolMax) {
        this.numVolMax = numVolMax;
    }

    public Integer getNumVolMax() {
        return numVolMax;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public Integer getAforoMax() {
        return aforoMax;
    }

    public void setAforoMax(Integer aforoMax) {
        this.aforoMax = aforoMax;
    }

    public List<Trabaja> getTrabaja() {
        return trabaja;
    }

    public void setTrabaja(List<Trabaja> trabaja) {
        this.trabaja = trabaja;
    }

    public void setEvento(List<Evento> evento) {
        this.evento = evento;
    }

    public List<Evento> getEvento() {
        return evento;
    }

    public List<Voluntario> getVoluntario() {
        return voluntario;
    }

    public void setVoluntario(List<Voluntario> voluntario) {
        this.voluntario = voluntario;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.id_sede;
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
        final Sede other = (Sede) obj;
        if (this.id_sede != other.id_sede) {
            return false;
        }
        return true;
    }
}
