/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import logic.EventRESTfulClient;

/**
 *
 * @author 2dam
 */
public class EventoManagerFactory {
 
    
     public EventoManager getFactory() {
        EventoManager eventm = new EventRESTfulClient();
        return eventm;

    }
}
