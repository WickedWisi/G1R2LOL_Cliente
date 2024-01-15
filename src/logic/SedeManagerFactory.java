/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 *
 * @author Eneko.
 */
public class SedeManagerFactory {

    public SedeManager getFactory() {
        SedeManager sedes = new SedeRESTClient();
        return sedes;
    }

}
