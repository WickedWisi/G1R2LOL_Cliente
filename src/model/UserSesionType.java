
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Eneko.
 */
public class UserSesionType {

    private static UserSesionType instance = null;
    private UserType tipo;

    private UserSesionType() {
        tipo = null;
    }

    public static UserSesionType getInstance() {
        if (instance == null) {
            instance = new UserSesionType();
        }
        return instance;
    }

    public void setTipoSesion(UserType tipo) {

        this.tipo = tipo;

    }

    public UserType getTipoSesion() {

        return tipo;
    }

}
