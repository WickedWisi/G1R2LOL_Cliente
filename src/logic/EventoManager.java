/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author josu
 */
public interface EventoManager {

    public <T> T findEventByEventId_XML(Class<T> responseType, String id_evento) throws ClientErrorException;

    public <T> T findEventByEventId_JSON(Class<T> responseType, String id_evento) throws ClientErrorException;

    public void modifyEvent_XML(Object requestEntity) throws ClientErrorException;

    public void modifyEvent_JSON(Object requestEntity) throws ClientErrorException;

    public void createEvent_XML(Object requestEntity) throws ClientErrorException;

    public void createEvent_JSON(Object requestEntity) throws ClientErrorException;

    public <T> T viewEventoByDate_XML(Class<T> responseType, String fechaEvento) throws ClientErrorException;

    public <T> T viewEventoByDate_JSON(Class<T> responseType, String fechaEvento) throws ClientErrorException;

    public <T> T viewEvents_XML(Class<T> responseType) throws ClientErrorException;

    public <T> T viewEvents_JSON(Class<T> responseType) throws ClientErrorException;

    public void deleteEvent(String id_evento) throws ClientErrorException;

    public <T> T viewSedeByAforoMax_XML(Class<T> responseType, String aforo) throws ClientErrorException;

    public <T> T viewSedeByAforoMax_JSON(Class<T> responseType, String aforo) throws ClientErrorException;

}
