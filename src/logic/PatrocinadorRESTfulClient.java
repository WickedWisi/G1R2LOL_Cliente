/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.List;
import java.util.ResourceBundle;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import model.Patrocinador;

/**
 * Jersey REST client generated for REST resource:PatrocinadorFacadeREST
 * [entity.patrocinador]<br>
 * USAGE:
 * <pre>
 *        PatrocinadorRESTfulClient client = new PatrocinadorRESTfulClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author 2dam
 */
public class PatrocinadorRESTfulClient implements PatrocinadorInterface {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = ResourceBundle.getBundle("properties.Url").getString("url");

    public PatrocinadorRESTfulClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entity.patrocinador");
    }

    public void edit_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Patrocinador.class);
    }

    public void edit_JSON(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Patrocinador.class);
    }

    public void create_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Patrocinador.class);
    }

    public void create_JSON(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Patrocinador.class);
    }

    @Override
    public List<Patrocinador> viewPatrocinadorByName_XML(Class<Patrocinador> responseType, String nombre) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("ViewBy/String/{0}", new Object[]{nombre}));
        return resource
                .request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get(new GenericType<List<Patrocinador>>() {
                });
    }

    @Override
    public List<Patrocinador> viewPatrocinadorByName_JSON(Class<Patrocinador> responseType, String nombre) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("ViewBy/String/{0}", new Object[]{nombre}));
        return resource
                .request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Patrocinador>>() {
                });
    }

    @Override
    public List<Patrocinador> viewPatrocinadorByDuration_XML(Class<Patrocinador> responseType, String DuracionPatrocinio) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("findByDuracion/{0}", new Object[]{DuracionPatrocinio}));
        return resource
                .request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get(new GenericType<List<Patrocinador>>() {
                });
    }

    @Override
    public List<Patrocinador> viewPatrocinadorByDuration_JSON(Class<Patrocinador> responseType, String DuracionPatrocinio) throws WebApplicationException {
       WebTarget resource = webTarget.path(java.text.MessageFormat.format("findByDuracion/{0}", new Object[]{DuracionPatrocinio}));
        return resource
                .request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Patrocinador>>() {
                });
    }

    @Override
    public List<Patrocinador> findPatrocinadoresByEvento_XML(Class<Patrocinador> responseType, String id_patrocinador) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("getEventoByPatrocinador/{0}", new Object[]{id_patrocinador}));
        return resource
                .request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get(new GenericType<List<Patrocinador>>() {
                });
    }

    @Override
    public List<Patrocinador> findPatrocinadoresByEvento_JSON(Class<Patrocinador> responseType, String id_patrocinador) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("getEventoByPatrocinador/{0}", new Object[]{id_patrocinador}));
        return resource
                .request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Patrocinador>>() {
                });
    }

    @Override
    public List<Patrocinador> findAll_XML(Class<Patrocinador> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource
                .request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get(new GenericType<List<Patrocinador>>() {
                });
    }

    @Override
    public List<Patrocinador> findAll_JSON(Class<Patrocinador> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource
                .request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Patrocinador>>() {
                });
    }

    public void remove(String id_patrocinador) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("deletePatrocinador/{0}", new Object[]{id_patrocinador})).request().delete(Patrocinador.class);
    }

    public void close() {
        client.close();
    }

}
