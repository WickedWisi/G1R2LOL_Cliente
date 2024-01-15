package logic;

import java.util.List;
import javax.ws.rs.ClientErrorException;
import model.Evento;


/**
 * La interfaz {@code EventoManager} define los métodos para gestionar eventos, incluyendo
 * la búsqueda de eventos por ID, la modificación de eventos, la creación de eventos,
 * la visualización de eventos por fecha, la visualización de todos los eventos y la eliminación de eventos.
 * También proporciona métodos para visualizar eventos por capacidad máxima de asientos de la sede.
 *
 * @author josu
 */
public interface EventoManager {

    /**
     * Recupera una lista de eventos en formato XML basada en el ID del evento proporcionado.
     *
     * @param responseType La clase del tipo de respuesta.
     * @param id_evento El ID del evento.
     * @return Una lista de eventos.
     * @throws ClientErrorException Si hay un error en la solicitud del cliente.
     */
    public List<Evento> findEventByEventId_XML(Class<Evento> responseType, String id_evento) throws ClientErrorException;

    /**
     * Recupera una lista de eventos en formato JSON basada en el ID del evento proporcionado.
     *
     * @param responseType La clase del tipo de respuesta.
     * @param id_evento El ID del evento.
     * @return Una lista de eventos.
     * @throws ClientErrorException Si hay un error en la solicitud del cliente.
     */
    public List<Evento> findEventByEventId_JSON(Class<Evento> responseType, String id_evento) throws ClientErrorException;

    /**
     * Modifica un evento en formato XML basado en la entidad de solicitud proporcionada.
     *
     * @param requestEntity La entidad que contiene las modificaciones para el evento.
     * @throws ClientErrorException Si hay un error en la solicitud del cliente.
     */
    public void modifyEvent_XML(Object requestEntity) throws ClientErrorException;

    /**
     * Modifica un evento en formato JSON basado en la entidad de solicitud proporcionada.
     *
     * @param requestEntity La entidad que contiene las modificaciones para el evento.
     * @throws ClientErrorException Si hay un error en la solicitud del cliente.
     */
    public void modifyEvent_JSON(Object requestEntity) throws ClientErrorException;

    /**
     * Crea un evento en formato XML basado en la entidad de solicitud proporcionada.
     *
     * @param requestEntity La entidad que contiene la información para crear el evento.
     * @throws ClientErrorException Si hay un error en la solicitud del cliente.
     */
    public void createEvent_XML(Object requestEntity) throws ClientErrorException;

    /**
     * Crea un evento en formato JSON basado en la entidad de solicitud proporcionada.
     *
     * @param requestEntity La entidad que contiene la información para crear el evento.
     * @throws ClientErrorException Si hay un error en la solicitud del cliente.
     */
    public void createEvent_JSON(Object requestEntity) throws ClientErrorException;

    /**
     * Recupera una lista de eventos en formato XML basada en la fecha proporcionada.
     *
     * @param responseType La clase del tipo de respuesta.
     * @param fechaEvento La fecha de los eventos a recuperar.
     * @return Una lista de eventos.
     * @throws ClientErrorException Si hay un error en la solicitud del cliente.
     */
    public List<Evento> viewEventoByDate_XML(Class<Evento> responseType, String fechaEvento) throws ClientErrorException;

    /**
     * Recupera una lista de eventos en formato JSON basada en la fecha proporcionada.
     *
     * @param responseType La clase del tipo de respuesta.
     * @param fechaEvento La fecha de los eventos a recuperar.
     * @return Una lista de eventos.
     * @throws ClientErrorException Si hay un error en la solicitud del cliente.
     */
    public List<Evento> viewEventoByDate_JSON(Class<Evento> responseType, String fechaEvento) throws ClientErrorException;

    /**
     * Recupera una lista de todos los eventos en formato XML.
     *
     * @param responseType La clase del tipo de respuesta.
     * @return Una lista de todos los eventos.
     * @throws ClientErrorException Si hay un error en la solicitud del cliente.
     */
    public List<Evento> viewEvents_XML(Class<Evento> responseType) throws ClientErrorException;

    /**
     * Recupera una lista de todos los eventos en formato JSON.
     *
     * @param responseType La clase del tipo de respuesta.
     * @return Una lista de todos los eventos.
     * @throws ClientErrorException Si hay un error en la solicitud del cliente.
     */
    public List<Evento> viewEvents_JSON(Class<Evento> responseType) throws ClientErrorException;

    /**
     * Elimina un evento basado en el ID proporcionado.
     *
     * @param id_evento El ID del evento a eliminar.
     * @throws ClientErrorException Si hay un error en la solicitud del cliente.
     */
    public void deleteEvent(String id_evento) throws ClientErrorException;

    /**
     * Recupera una lista de eventos en formato XML basada en la capacidad máxima de asientos proporcionada.
     *
     * @param responseType La clase del tipo de respuesta.
     * @param aforo La capacidad máxima de asientos de la sede.
     * @return Una lista de eventos.
     * @throws ClientErrorException Si hay un error en la solicitud del cliente.
     */
    public List<Evento> viewSedeByAforoMax_XML(Class<Evento> responseType, String aforo) throws ClientErrorException;

    /**
     * Recupera una lista de eventos en formato JSON basada en la capacidad máxima de asientos proporcionada.
     *
     * @param responseType La clase del tipo de respuesta.
     * @param aforo La capacidad máxima de asientos de la sede.
     * @return Una lista de eventos.
     * @throws ClientErrorException Si hay un error en la solicitud del cliente.
     */
    public List<Evento> viewSedeByAforoMax_JSON(Class<Evento> responseType, String aforo) throws ClientErrorException;

}

