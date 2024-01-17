/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import model.Evento;

/**
 * Jersey REST client generated for REST resource:EventoFacadeREST
 * [entity.evento]<br>
 * USAGE:
 * <pre>
 *        EventRESTClient client = new EventRESTClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author josu
 */
public class EventRESTClient implements EventoManager {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/G1R2LOL_Server/webresources";

    public EventRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entity.evento");
    }

    @Override
    public List<Evento> findEventByEventId_XML(Class<Evento> responseType, String id_evento) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("FindEventBy/{0}", new Object[]{id_evento}));
        // return (List<Evento>) resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML);
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(new GenericType<List<Evento>>() {
        });
    }

    @Override
    public List<Evento> findEventByEventId_JSON(Class<Evento> responseType, String id_evento) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("FindEventBy/{0}", new Object[]{id_evento}));
        //return (List<Evento>) resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON);
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(new GenericType<List<Evento>>() {
        });
    }

    @Override
    public List<Evento> viewEventoByDate_XML(Class<Evento> responseType, String fechaEvento) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("viewEventByDate/{0}", new Object[]{fechaEvento}));
        //return (List<Evento>) resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML);
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(new GenericType<List<Evento>>() {
        });
    }

    @Override
    public List<Evento> viewEventoByDate_JSON(Class<Evento> responseType, String fechaEvento) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("viewEventByDate/{0}", new Object[]{fechaEvento}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(new GenericType<List<Evento>>() {
        });
    }

    @Override
    public List<Evento> viewEvents_XML(Class<Evento> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(new GenericType<List<Evento>>() {
        });
    }

    @Override
    public List<Evento> viewEvents_JSON(Class<Evento> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(new GenericType<List<Evento>>() {
        });
    }

    @Override
    public List<Evento> viewEventoByAforoMax_XML(Class<Evento> responseType, String aforo) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("ViewEventByAforo/{0}", new Object[]{aforo}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(new GenericType<List<Evento>>(){});
    }

    @Override
    public List<Evento> viewEventoByAforoMax_JSON(Class<Evento> responseType, String aforo) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("ViewEventByAforo/{0}", new Object[]{aforo}));
         return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(new GenericType<List<Evento>>() {
        });
    }

    @Override
    public void modifyEvent_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Evento.class);
    }

    @Override
    public void modifyEvent_JSON(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Evento.class);
    }

    @Override
    public void createEvent_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Evento.class);
    }

    @Override
    public void createEvent_JSON(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Evento.class);
    }

    public void deleteEvent(String id_evento) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("DELETE-Evento/{0}", new Object[]{id_evento})).request().delete(Evento.class);
    }

    public <T> T findPatrocinadoresByEvento_XML(Class<T> responseType, String id_evento) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("getPatrocinadorByEvento/{0}", new Object[]{id_evento}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T findPatrocinadoresByEvento_JSON(Class<T> responseType, String id_evento) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("getPatrocinadorByEvento/{0}", new Object[]{id_evento}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void close() {
        client.close();
    }

}
