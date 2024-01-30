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
import model.Voluntario;

/**
 *
 * @author Eneko.
 */
public interface VoluntarioManager {

    public Voluntario find_XML(Class<Voluntario> responseType, String id) throws WebApplicationException;
    
    public Voluntario  find_JSON(Class<Voluntario> responseType, String id) throws WebApplicationException;

    public void RecuperarContra_XML(Object requestEntity) throws WebApplicationException;

    public void RecuperarContra_JSON(Object requestEntity) throws WebApplicationException ;

    public void create_XML(Object requestEntity) throws WebApplicationException;

    public void create_JSON(Object requestEntity) throws WebApplicationException ;

    public List<Voluntario>  findAll_XML(Class<Voluntario> responseType) throws WebApplicationException ;

    public List<Voluntario>  findAll_JSON(Class<Voluntario> responseType) throws WebApplicationException;

    public void cambiarContra_XML(Object requestEntity) throws WebApplicationException;

    public void cambiarContra_JSON(Object requestEntity) throws WebApplicationException ;
}
