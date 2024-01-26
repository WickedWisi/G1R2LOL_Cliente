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
import model.Sede;

/**
 * Jersey REST client generated for REST resource:SedeFacadeREST
 * [entity.sede]<br>
 * USAGE:
 * <pre>
 *        SedeRESTClient client = new SedeRESTClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Eneko.
 */
public class SedeRESTClient implements SedeManager {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/G1R2LOL_Server/webresources";

    public SedeRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entity.sede");
    }

    public void close() {
        client.close();
    }

    @Override
    public void deleteSede(String id_sede) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("DELETE-Sede/{0}", new Object[]{id_sede})).request().delete(Sede.class);
    }

    @Override
    public void createSede_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Sede.class);
    }

    @Override
    public void createSede_JSON(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    @Override
    public List<Sede> viewSedes_XML(Class<Sede> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(new GenericType<List<Sede>>() {
        }
        );
    }

    @Override
    public List<Sede> viewSedes_JSON(Class<Sede> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(new GenericType<List<Sede>>() {
        }
        );
    }

    @Override
    public <Sede> Sede viewSedeById_XML(Class<Sede> responseType, String id_sede) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("ViewSedeById/{0}", new Object[]{id_sede}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <Sede> Sede viewSedeById_JSON(Class<Sede> responseType, String id_sede) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("ViewSedeById/{0}", new Object[]{id_sede}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    @Override
    public List<Evento> findEventoBySede_XML(Class<Evento> responseType, String id_sede) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("viewEventoBySede/{0}", new Object[]{id_sede}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(new GenericType<List<Evento>>() {
        }
        );
    }

    @Override
    public List<Evento> findEventoBySede_JSON(Class<Evento> responseType, String id_sede) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("viewEventoBySede/{0}", new Object[]{id_sede}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(new GenericType<List<Evento>>() {
        }
        );
    }

    @Override
    public List<Sede> viewSedeByCountry_XML(Class<Sede> responseType, String pais) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("ViewSedeBy/String/{0}", new Object[]{pais}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(new GenericType<List<Sede>>() {
        }
        );
    }

    @Override
    public List<Sede> viewSedeByCountry_JSON(Class<Sede> responseType, String pais) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("ViewSedeBy/String/{0}", new Object[]{pais}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(new GenericType<List<Sede>>() {
        }
        );
    }

    @Override
    public List<Sede> viewSedeByAforoMax_XML(Class<Sede> responseType, String aforoMax) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("ViewSedeByAforoMax/{0}", new Object[]{aforoMax}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(new GenericType<List<Sede>>() {
        });
    }

    @Override
    public List<Sede> viewSedeByAforoMax_JSON(Class<Sede> responseType, String aforoMax) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("ViewSedeByAforoMax/{0}", new Object[]{aforoMax}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(new GenericType<List<Sede>>() {
        });
    }

    @Override
    public void modifySede_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Sede.class);
    }

    @Override
    public void modifySede_JSON(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Sede.class);
    }

}