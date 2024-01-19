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
import model.User;

/**
 *
 * @author Eneko.
 */
public interface UserManager {

    public void edit_XML(Object requestEntity, String id) throws WebApplicationException;

    public void edit_JSON(Object requestEntity, String id) throws WebApplicationException;

    public List<User> findUserByEmailAndPasswd_XML(String email, String passwd) throws WebApplicationException;

    public List<User> findUserByEmailAndPasswd_JSON(String email, String passwd) throws WebApplicationException;

    public <T> T find_XML(Class<T> responseType, String id) throws WebApplicationException;

    public <T> T find_JSON(Class<T> responseType, String id) throws WebApplicationException;

    public <T> T findRange_XML(Class<T> responseType, String from, String to) throws WebApplicationException;

    public <T> T findRange_JSON(Class<T> responseType, String from, String to) throws WebApplicationException;

    public void create_XML(Object requestEntity) throws WebApplicationException;

    public void create_JSON(Object requestEntity) throws WebApplicationException;

    public List<User> findAll_XML(Class<User> responseType) throws WebApplicationException;

    public List<User> findAll_JSON(Class<User> responseType) throws WebApplicationException;

    public List<User> findForUserType_XML(Class<User> responseType, String userType) throws WebApplicationException;

    public List<User> findForUserType_JSON(Class<User> responseType, String userType) throws WebApplicationException;

    public void remove(String id) throws WebApplicationException;
}
