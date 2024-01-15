/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import logic.EventRESTfulClient;
/**
 * La clase {@code EventoManagerFactory} es responsable de proporcionar instancias de la
 * interfaz {@code EventoManager}. Específicamente, devuelve una instancia de la clase
 * {@code EventRESTfulClient} como implementación de {@code EventoManager}. Esta fábrica se utiliza
 * para obtener una implementación concreta de la interfaz {@code EventoManager},
 * promoviendo la flexibilidad y la abstracción en el sistema.
 *
 * Ejemplo de uso:
 * ```java
 * EventoManagerFactory factory = new EventoManagerFactory();
 * EventoManager eventManager = factory.getFactory();
 * ```
 *
 * @author josu
 */
public class EventoManagerFactory {

    /**
     * Recupera una instancia de la interfaz {@code EventoManager}.
     *
     * @return Una instancia de la interfaz {@code EventoManager}.
     */
    public EventoManager getFactory() {
        EventoManager eventm = new EventRESTfulClient();
        return eventm;
    }
}
