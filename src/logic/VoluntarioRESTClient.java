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
import model.Voluntario;
import org.apache.poi.ss.formula.functions.Vlookup;

/**
 * Jersey REST client generated for REST resource:VoluntarioFacadeREST
 * [entity.voluntario]<br>
 * USAGE:
 * <pre>
 *        vlum client = new vlum();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author 2dam
 */
public class VoluntarioRESTClient implements VoluntarioManager{

    private WebTarget webTarget;
    private Client client;
     private static final String BASE_URI = ResourceBundle.getBundle("properties.Url").getString("url");

    public VoluntarioRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entity.voluntario");
    }

    @Override
    public Voluntario find_XML(Class<Voluntario> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("buscarVoluntarioPorId/{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(new GenericType<Voluntario>() {
        });
    }

    @Override
    public Voluntario  find_JSON(Class<Voluntario> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("buscarVoluntarioPorId/{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(new GenericType<Voluntario>() {
        });
    }

    @Override
    public void RecuperarContra_XML(Object requestEntity) throws WebApplicationException {
        webTarget.path("recuperarContra").request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    @Override
    public void RecuperarContra_JSON(Object requestEntity) throws WebApplicationException {
        webTarget.path("recuperarContra").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    @Override
    public void create_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    @Override
    public void create_JSON(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    @Override
    public List<Voluntario>  findAll_XML(Class<Voluntario> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(new GenericType<List<Voluntario>>() {
        });
    }

    @Override
    public List<Voluntario>  findAll_JSON(Class<Voluntario> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(new GenericType<List<Voluntario>>() {
        });
    }

    @Override
    public void cambiarContra_XML(Object requestEntity) throws WebApplicationException {
        webTarget.path("cambiarContra").request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    @Override
    public void cambiarContra_JSON(Object requestEntity) throws WebApplicationException {
        webTarget.path("cambiarContra").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    public void close() {
        client.close();
    }
    
}
