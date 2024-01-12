package model;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Voluntario extends User {

    private static final long serialVersionUID = 1L;

    private int numero_Voluntariados;

    private Sede sede;

    public void setNumero_Voluntariados(int numero_Voluntariados) {
        this.numero_Voluntariados = numero_Voluntariados;
    }

    public Sede getSede() {
        return sede;
    }

    public int getNumero_Voluntariados() {
        return numero_Voluntariados;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.numero_Voluntariados;
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
        final Voluntario other = (Voluntario) obj;
        if (this.numero_Voluntariados != other.numero_Voluntariados) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Voluntario{" + "numero_Voluntariados=" + numero_Voluntariados + '}';
    }

}
