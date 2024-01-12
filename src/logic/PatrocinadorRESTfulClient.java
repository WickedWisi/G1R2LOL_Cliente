/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
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
public class PatrocinadorRESTfulClient {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/G1R2LOL_Server/webresources";

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

    public <T> T viewPatrocinadorByName_XML(Class<T> responseType, String nombre) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("ViewBy/String/{0}", new Object[]{nombre}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T viewPatrocinadorByName_JSON(Class<T> responseType, String nombre) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("ViewBy/String/{0}", new Object[]{nombre}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public <T> T viewPatrocinadorByDuration_XML(Class<T> responseType, String DuracionPatrocinio) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findByDuracion/{0}", new Object[]{DuracionPatrocinio}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T viewPatrocinadorByDuration_JSON(Class<T> responseType, String DuracionPatrocinio) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findByDuracion/{0}", new Object[]{DuracionPatrocinio}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public <T> T findPatrocinadoresByEvento_XML(Class<T> responseType, String id_patrocinador) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("getEventoByPatrocinador/{0}", new Object[]{id_patrocinador}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T findPatrocinadoresByEvento_JSON(Class<T> responseType, String id_patrocinador) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("getEventoByPatrocinador/{0}", new Object[]{id_patrocinador}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public <T> T findAll_XML(Class<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T findAll_JSON(Class<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void remove(String id_patrocinador) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("deletePatrocinador/{0}", new Object[]{id_patrocinador})).request().delete();
    }

    public void close() {
        client.close();
    }

}
