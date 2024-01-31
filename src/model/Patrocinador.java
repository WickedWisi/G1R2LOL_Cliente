package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "patrocinador")
public class Patrocinador implements Serializable {

    private Integer id_Patrocinador;
    private String nombre;
    private String Descripcion;
    private String email;
    private Integer telefono;
    private Date DuracionPatrocinio;
    private List<Evento> evento;

    public Integer getId_Patrocinador() {
        return id_Patrocinador;
    }

    public void setId_Patrocinador(Integer id_Patrocinador) {
        this.id_Patrocinador = id_Patrocinador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public Date getDuracionPatrocinio() {
        return DuracionPatrocinio;
    }

    public void setDuracionPatrocinio(Date duracionPatrocinio) {
        this.DuracionPatrocinio = duracionPatrocinio;
    }

    public List<Evento> getEvento() {
        return evento;
    }

    public void setEvento(List<Evento> evento) {
        this.evento = evento;
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
        return this.id_Patrocinador.equals(other.id_Patrocinador);
    }

    @Override
    public String toString() {
        return "Patrocinador{" + "id_Patrocinador=" + id_Patrocinador + '}';
    }
}
