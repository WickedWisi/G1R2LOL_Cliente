/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import model.Sede;

/**
 *
 * @author Eneko.
 */
public interface SedeManager {

    public void deleteSede(String id_sede) throws WebApplicationException;

    public void createSede_XML(Object requestEntity) throws WebApplicationException;

    public void createSede_JSON(Object requestEntity) throws WebApplicationException;

    public List<Sede> viewSedes_XML(Class<Sede> responseType) throws WebApplicationException;

    public List<Sede> viewSedes_JSON(Class<Sede> responseType) throws WebApplicationException;

    public <Sede> Sede viewSedeById_XML(Class<Sede> responseType, String id_sede) throws WebApplicationException;

    public <Sede> Sede viewSedeById_JSON(Class<Sede> responseType, String id_sede) throws WebApplicationException;

    public <Evento> Evento findEventoBySede_XML(Class<Evento> responseType, String id_sede) throws WebApplicationException;

    public <Evento> Evento findEventoBySede_JSON(Class<Evento> responseType, String id_sede) throws WebApplicationException;

    public List<Sede> viewSedeByCountry_XML(Class<Sede> responseType, String pais) throws WebApplicationException;

    public List<Sede> viewSedeByCountry_JSON(Class<Sede> responseType, String pais) throws WebApplicationException;

    public List<Sede> viewSedeByAforoMax_XML(Class<Sede> responseType, String aforoMax) throws WebApplicationException;

    public List<Sede> viewSedeByAforoMax_JSON(Class<Sede> responseType, String aforoMax) throws WebApplicationException;

    public void modifySede_XML(Object requestEntity) throws WebApplicationException;

    public void modifySede_JSON(Object requestEntity) throws WebApplicationException;
}
