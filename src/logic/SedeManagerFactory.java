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

    /**
     * Recupera una instancia de la interfaz {@code EventoManager}.
     *
     * @return Una instancia de la interfaz {@code EventoManager}.
     */
    public SedeManager getFactory() {
        SedeManager sede = new SedeRESTClient();
        return sede;
    }
}
