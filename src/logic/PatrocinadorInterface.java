/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.WebTarget;
import model.Patrocinador;

/**
 *
 * @author Egoitz
 */
public interface PatrocinadorInterface {

    public void edit_XML(Object requestEntity) throws WebApplicationException;
       

    public void edit_JSON(Object requestEntity) throws WebApplicationException;

    public void create_XML(Object requestEntity) throws WebApplicationException;

    public void create_JSON(Object requestEntity) throws WebApplicationException;

    public List<Patrocinador> viewPatrocinadorByName_XML(Class<Patrocinador> responseType, String nombre) throws WebApplicationException;

    public List<Patrocinador> viewPatrocinadorByName_JSON(Class<Patrocinador> responseType, String nombre) throws WebApplicationException;

    public List<Patrocinador> viewPatrocinadorByDuration_XML(Class<Patrocinador> responseType, String DuracionPatrocinio) throws WebApplicationException;

    public List<Patrocinador> viewPatrocinadorByDuration_JSON(Class<Patrocinador> responseType, String DuracionPatrocinio) throws WebApplicationException;

    public List<Patrocinador> findPatrocinadoresByEvento_XML(Class<Patrocinador> responseType, String id_patrocinador) throws WebApplicationException;

    public List<Patrocinador> findPatrocinadoresByEvento_JSON(Class<Patrocinador> responseType, String id_patrocinador) throws WebApplicationException;

    public List<Patrocinador> findAll_XML(Class<Patrocinador> responseType) throws WebApplicationException;

    public List<Patrocinador> findAll_JSON(Class<Patrocinador> responseType) throws WebApplicationException;

    public void remove(String id_patrocinador) throws WebApplicationException;
}
