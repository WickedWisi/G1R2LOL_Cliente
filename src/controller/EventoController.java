/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import exception.EmptyTextFieldsException;
import exception.FormatErrorException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.core.GenericType;
import logic.EventoManagerFactory;
import logic.SedeManagerFactory;
import model.Evento;
import model.Patrocinador;
import model.Sede;
import model.User;
import model.UserSesionType;
import model.UserType;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author josu
 */
public class EventoController {

    private static final Logger LOGGER = Logger.getLogger("controller");

    private Evento eventito = new Evento();

    private EventoManagerFactory eventofact = new EventoManagerFactory();

    private ObservableList<Evento> eventoData;

    private Set<Evento> listaEvento;
    @FXML
    private Stage stage;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfAforo;
    @FXML
    private TextField tfFiltro;
    @FXML
    private TextArea taDescripcion;
    @FXML
    private DatePicker dpFechaEvento;
    @FXML
    private CheckBox cbCatering;
    @FXML
    private Button btnInsertar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnInforme;
    @FXML
    private TableView<Evento> tbvEvento;
    @FXML
    private TableColumn tbcNombre;
    @FXML
    private TableColumn tbcDescripcion;
    @FXML
    private TableColumn tbcAforo;
    @FXML
    private TableColumn tbcCatering;
    @FXML
    private TableColumn tbcFechaEvento;
    @FXML
    private ContextMenu menuu;
    @FXML
    private MenuItem mtem4;
    @FXML
    private MenuItem mtem5;
    @FXML
    private ComboBox<Boolean> cbFiltro;

    private Sede sede;

    private Patrocinador patrocinador;

    private SedeManagerFactory sedefact = new SedeManagerFactory();

    UserSesionType miTipoSesion = UserSesionType.getInstance();

    User tipo = miTipoSesion.getTipoSesion();

    public void initStage(Parent root) {

        LOGGER.info("INIT STAGE CLASE CONTROLADORA DE EVENTO");

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        if (tipo.getUserType() == UserType.VOLUNTARIO) {
            //habilitamos los txt
            tfAforo.setDisable(true);
            tfFiltro.setDisable(false);
            tfNombre.setDisable(true);
            taDescripcion.setDisable(true);
            mtem4.setDisable(true);

            //habilitamos los botonoes
            btnInsertar.setDisable(true);
            btnEliminar.setDisable(true);
            btnEditar.setDisable(true);
            btnInforme.setDisable(false);
            cbCatering.setDisable(true);

            //habilitamos la tabla
            tbvEvento.setDisable(false);

            //habilitamos el DatePicker
            dpFechaEvento.setDisable(true);

            //habilitamos la comboBox 
            cbFiltro.setDisable(false);

            //El foco estará puesto en el campo de nombre de evento.
            tfNombre.requestFocus();

            //El título de la ventana es “Sign In”.
            stage.setTitle("EVENTO");

            //La ventana no es redimensionable
            stage.setResizable(false);

            //METODOS
            btnInsertar.setOnAction(this::handleCrearButtonAction);
            btnInforme.setOnAction(this::handleInformeButtonAction);
            btnEliminar.setOnAction(this::handleEliminarButtonAction);
            mtem4.setOnAction(this::handleMtem4);
            btnEditar.setOnAction(this::handleModificarButtonAction);
            btnBuscar.setOnAction(this::handleBuscarButtonAction);
            stage.setOnCloseRequest(this::handleExitButtonAction);
            //cbFiltro.getItems().addAll("true", "false", "ninguno");
            cbFiltro.getItems().addAll(true, false, null);

            tbvEvento.getSelectionModel().selectedItemProperty().addListener(this::handleUsersTableSelectionChanged);
            mtem5.setOnAction(this::handleViewPatrocinador);

            //Con el siguiente codigo asignamos a las columnas los tipos y los nombres
            tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            tbcDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            tbcAforo.setCellValueFactory(new PropertyValueFactory<>("aforo"));
            tbcCatering.setCellValueFactory(new PropertyValueFactory<>("catering"));
            tbcFechaEvento.setCellValueFactory(new PropertyValueFactory<>("fechaEvento"));
            //Aqui le ponemos un formato de fecha concreto
            tbcFechaEvento.setCellFactory(column -> {
                TableCell<Evento, Date> cell = new TableCell<Evento, Date>() {
                    private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                    @Override
                    protected void updateItem(Date item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            if (item != null) {
                                setText(format.format(item));
                            }

                        }
                    }
                };

                return cell;
            });

            //con las dos siguientes lineas agregamos a la tabla los datos
            eventoData = FXCollections.observableArrayList(eventofact.getFactory().viewEvents_XML(Evento.class));

            tbvEvento.setItems(eventoData);
        } else {
            //habilitamos los txt
            tfAforo.setDisable(false);
            tfFiltro.setDisable(false);
            tfNombre.setDisable(false);

            //habilitamos los botonoes
            btnInsertar.setDisable(false);
            btnEliminar.setDisable(false);
            btnEditar.setDisable(false);
            btnInforme.setDisable(false);

            //habilitamos la tabla
            tbvEvento.setDisable(false);
            //habilitamos la comboBox 
            cbFiltro.setDisable(false);

            //habilitamos el DatePicker
            dpFechaEvento.setDisable(false);

            //El foco estará puesto en el campo de nombre de evento.
            tfNombre.requestFocus();

            //El título de la ventana es “Sign In”.
            stage.setTitle("EVENTO");

            //La ventana no es redimensionable
            stage.setResizable(false);

            //METODOS
            btnInsertar.setOnAction(this::handleCrearButtonAction);
            btnInforme.setOnAction(this::handleInformeButtonAction);
            btnEliminar.setOnAction(this::handleEliminarButtonAction);
            btnEditar.setOnAction(this::handleModificarButtonAction);
            btnBuscar.setOnAction(this::handleBuscarButtonAction);
            stage.setOnCloseRequest(this::handleExitButtonAction);
            //vale aqui estamos haciendo lo del filtrado del catering primero cargando la combo 
            // cbFiltro.getItems().addAll("true", "false", "ninguno");
            cbFiltro.getItems().addAll(true, false, null);

            //habilitando que cuando cambiemos algo en la combo salte un evento 
            cbFiltro.valueProperty().addListener(this::handleFiltradoCatering);
            tbvEvento.getSelectionModel().selectedItemProperty().addListener(this::handleUsersTableSelectionChanged);
            mtem4.setOnAction(this::handleMtem4);
            mtem5.setOnAction(this::handleViewPatrocinador);

            //Con el siguiente codigo asignamos a las columnas los tipos y los nombres
            tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            tbcDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            tbcAforo.setCellValueFactory(new PropertyValueFactory<>("aforo"));
            tbcCatering.setCellValueFactory(new PropertyValueFactory<>("catering"));
            tbcFechaEvento.setCellValueFactory(new PropertyValueFactory<>("fechaEvento"));
            //Aqui le ponemos un formato de fecha concreto
            tbcFechaEvento.setCellFactory(column -> {
                TableCell<Evento, Date> cell = new TableCell<Evento, Date>() {
                    private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                    @Override
                    protected void updateItem(Date item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            if (item != null) {
                                setText(format.format(item));
                            }

                        }
                    }
                };

                return cell;
            });
        }

        //con las dos siguientes lineas agregamos a la tabla los datos
        eventoData = FXCollections.observableArrayList(eventofact.getFactory().viewEvents_XML(Evento.class));

        tbvEvento.setItems(eventoData);

        stage.show();

    }

    /**
     * Maneja el evento de clic en el botón de salida. Muestra una ventana de
     * confirmación para preguntar al usuario si desea salir. Si el usuario
     * elige salir, cierra la aplicación. Si elige cancelar, evita el cierre de
     * la ventana principal.
     *
     * @param event El evento de ventana asociado con el clic en el botón de
     * salida.
     */
    @FXML
    private void handleExitButtonAction(WindowEvent event) {
        // Crea una ventana de confirmación
        Alert ventanita = new Alert(Alert.AlertType.CONFIRMATION);
        ventanita.setHeaderText(null);
        ventanita.setTitle("Advertencia");
        ventanita.setContentText("¿Deseas Salir?");

        // Muestra la ventana y espera la respuesta del usuario
        Optional<ButtonType> action = ventanita.showAndWait();

        // Verifica la respuesta del usuario
        if (action.orElse(ButtonType.CANCEL) == ButtonType.OK) {
            // Si elige salir, cierra la aplicación por completo
            Platform.exit();
        } else {
            // Si elige cancelar, evita que la ventana principal se cierre
            event.consume();  // Consume el evento para evitar el cierre
        }
    }

    /**
     * Maneja el evento de clic en el botón de crear evento. Valida los campos
     * de entrada, como nombre, descripción, aforo y fecha, antes de crear y
     * almacenar un nuevo evento. Muestra mensajes de error si los campos no
     * están informados correctamente.
     *
     * @param event El evento de acción asociado al clic en el botón de crear
     * evento.
     */
    @FXML
    private void handleCrearButtonAction(ActionEvent event) {
        try {
            // Valida que los campos no estén vacíos
            if (this.tfNombre.getText().isEmpty() || this.taDescripcion.getText().isEmpty() || this.tfAforo.getText().isEmpty() || dpFechaEvento.getValue() == null) {
                throw new EmptyTextFieldsException("CAMPOS NO INFORMADOS");
            }

            // Valida que el aforo sea un número positivo
            String text = tfAforo.getText();
            if (!text.matches("\\d+") || Integer.parseInt(tfAforo.getText()) <= 0) {
                throw new FormatErrorException("El aforo máximo debe contener solo números y ser positivos");
            } else {
                try {
                    // Llena los datos del evento
                    eventito.setNombre(tfNombre.getText());
                    eventito.setDescripcion(taDescripcion.getText());
                    eventito.setAforo(Integer.parseInt(tfAforo.getText()));
                    eventito.setCatering(cbCatering.isSelected());
                    eventito.setFechaEvento(Date.from(dpFechaEvento.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));

                    // Crea y guarda el evento en la base de datos
                    eventofact.getFactory().createEvent_XML(eventito);

                    // Actualiza la tabla de eventos
                    eventoData = FXCollections.observableArrayList(cargarTodo());

                    // Restablece los campos a sus valores predeterminados después de crear el evento
                    tfNombre.setText("");
                    taDescripcion.setText("");
                    tfAforo.setText("");
                    cbCatering.isDisable();
                    dpFechaEvento.setValue(null);

                    // Muestra un mensaje de éxito
                    throw new Exception("EVENTO CREADO CON ÉXITO");
                } catch (Exception e) {
                    // Muestra un mensaje de error en caso de cualquier excepción
                    new Alert(Alert.AlertType.INFORMATION, e.getMessage()).showAndWait();
                }
            }
        } catch (EmptyTextFieldsException | FormatErrorException e) {
            // Muestra un mensaje de error si alguna validación falla y restablece los campos
            new Alert(Alert.AlertType.INFORMATION, e.getMessage()).showAndWait();
            tfNombre.setText("");
            taDescripcion.setText("");
            tfAforo.setText("");
            cbCatering.isDisable();
            dpFechaEvento.setValue(null);
        }
    }

    /**
     * Maneja el evento de clic en el botón de generar informe. Compila un
     * informe Jasper con los datos de la tabla de eventos de la ventana actual
     * y lo muestra en una ventana de visualización.
     *
     * @param event El evento de acción asociado al clic en el botón de informe.
     */
    @FXML
    private void handleInformeButtonAction(ActionEvent event) {
        try {
            // Compila el informe Jasper utilizando el archivo JRXML asociado
            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/EventReport.jrxml"));

            // Crea una fuente de datos para los elementos JRBeanCollectionDataSource
            JRBeanCollectionDataSource dataItems;
            dataItems = new JRBeanCollectionDataSource((Collection<Evento>) this.tbvEvento.getItems());

            // Configura los parámetros del informe
            Map<String, Object> parameters = new HashMap<>();

            // Rellena el informe con datos y genera un JasperPrint
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);

            // Crea una ventana de visualización JasperViewer y la muestra
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);

        } catch (JRException ex) {
            // Maneja las excepciones relacionadas con JasperReports
            Logger.getLogger(EventoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Maneja el evento de clic en el botón de eliminar evento. Elimina el
     * evento seleccionado de la tabla y de la base de datos después de
     * confirmar la acción. Muestra mensajes de advertencia y éxito o
     * cancelación de la operación.
     *
     * @param event El evento de acción asociado al clic en el botón de eliminar
     * evento.
     */
    @FXML
    private void handleEliminarButtonAction(ActionEvent event) {
        // Selecciona el evento de la fila actualmente seleccionada en la tabla
        Evento selectedEvento = (Evento) tbvEvento.getSelectionModel().getSelectedItem();
        try {
            // Muestra una ventana de confirmación para asegurar que el usuario quiere eliminar el evento
            Alert ventanita = new Alert(Alert.AlertType.CONFIRMATION);
            ventanita.setHeaderText(null);
            ventanita.setTitle("Advertencia");
            ventanita.setContentText("¿Estás seguro de que quieres eliminar ese evento?");

            // Muestra la ventana y espera la respuesta del usuario
            Optional<ButtonType> action = ventanita.showAndWait();

            // Si elige OK, procede con la eliminación
            if (action.get() == ButtonType.OK) {
                // Elimina el evento de la base de datos utilizando el identificador del evento
                eventofact.getFactory().deleteEvent(selectedEvento.getId_evento().toString());

                // Actualiza la tabla de eventos
                eventoData = FXCollections.observableArrayList(cargarTodo());

                // Restablece los campos a sus valores predeterminados después de la eliminación
                tfNombre.setText("");
                taDescripcion.setText("");
                tfAforo.setText("");
                cbCatering.isDisable();
                dpFechaEvento.setValue(null);

                // Muestra un mensaje de éxito
                throw new Exception("EL EVENTO SE HA ELIMINADO CORRECTAMENTE");
            } else {
                // Si elige cancelar, cierra la ventana de confirmación
                ventanita.close();
            }
        } catch (Exception e) {
            // Muestra un mensaje de error en caso de cualquier excepción
            new Alert(Alert.AlertType.INFORMATION, e.getMessage()).showAndWait();
        }
    }

    @FXML
    private void handleModificarButtonAction(ActionEvent event) {

        try {
            //aqui estamos validando que los campos no esten vacios
            if (this.tfNombre.getText().isEmpty() || this.taDescripcion.getText().isEmpty() || this.tfAforo.getText().isEmpty() || dpFechaEvento.getValue() == null) {

                throw new EmptyTextFieldsException("CAMPOS NO INFORMADOS");

            }
            //Validar que el aforo esta solo formado por numeros

            String text = tfAforo.getText();

            if (!text.matches("\\d+") || Integer.parseInt(tfAforo.getText()) <= 0) {
                throw new FormatErrorException("El aforo maximo debe contener solo numeros y ser positivos");
            } /* /*
            //validar que la fecha este en el formato correcto
            String dateStr = dpFechaEvento.getEditor().getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            if (dateStr.matches("\\d{2}-\\d{2}-\\d{4}")) {
                throw new FormatErrorException("Error, Por favor, ingrese el formato de fecha correcto.");
            }
             */ else {
                try {

                    //escribimos en el objeto lugar los fields de los campos ha introducir
                    eventito.setId_evento(tbvEvento.getSelectionModel().getSelectedItem().getId_evento());
                    eventito.setNombre(tfNombre.getText());
                    eventito.setDescripcion(taDescripcion.getText());
                    eventito.setAforo(Integer.parseInt(tfAforo.getText()));
                    eventito.setCatering(cbCatering.isSelected());
                    eventito.setFechaEvento(Date.from(dpFechaEvento.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));

                    //llamamos a la factoria para crear ese lugar y lo introduzca en la base de datos
                    eventofact.getFactory().modifyEvent_XML(eventito);
                    //llamamos a nuestro metodo de cargarTodo para refrescar nuestra tabla y salga el nuevo lugar creado
                    eventoData = FXCollections.observableArrayList(cargarTodo());
                    //Una vez creado el lugar pondremos en blanco de nuevo los campos y mostraremos un mensaje de lugar creado con exito
                    tfNombre.setText("");
                    taDescripcion.setText("");
                    tfAforo.setText("");
                    cbCatering.isDisable();
                    dpFechaEvento.setValue(null);
                    throw new Exception("EVENTO MODIFICADO CON EXITO");

                } catch (Exception e) {
                    new Alert(Alert.AlertType.INFORMATION, e.getMessage()).showAndWait();
                }

            }

        } catch (EmptyTextFieldsException | FormatErrorException e) {
            //si alguna de las validacioens no ha salido bn saldra un mensaje de error y nos vaciara los campos nuevamente
            new Alert(Alert.AlertType.INFORMATION, e.getMessage()).showAndWait();
            tfNombre.setText("");
            taDescripcion.setText("");
            tfAforo.setText("");
            cbCatering.isDisable();
            dpFechaEvento.setValue(null);

        }

    }

    @FXML
    private void handleBuscarButtonAction(ActionEvent event) {

        try {
            String filtro = tfFiltro.getText();

            // Verificar si el TextField está vacío
            if (filtro.isEmpty()) {
                eventoData = FXCollections.observableArrayList(cargarTodo());
                tbvEvento.setItems(eventoData);

            } else {
                // Resto del código para buscar por aforo o fecha
                if (filtro.matches("\\d+")) {
                    ObservableList<Evento> eventosPorAforo = FXCollections.observableArrayList(
                            eventofact.getFactory().viewEventoByAforoMax_XML(Evento.class,
                                    filtro)
                    );
                    tbvEvento.setItems(eventosPorAforo);
                } else {
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date fecha = dateFormat.parse(filtro);

                        ObservableList<Evento> eventosPorFecha = FXCollections.observableArrayList(
                                eventofact.getFactory().viewEventoByDate_XML(Evento.class,
                                        dateFormat.format(fecha))
                        );
                        tbvEvento.setItems(eventosPorFecha);
                    } catch (ParseException ex) {
                        throw new FormatErrorException("Formato de filtro no válido. Debe ser aforo (número positivo) o fecha (yyyy-MM-dd).");
                    }
                }
            }

            tbvEvento.refresh();
        } catch (FormatErrorException e) {
            new Alert(Alert.AlertType.INFORMATION, e.getMessage()).showAndWait();
            tfFiltro.setText("");
        }

    }

    @FXML
    private void handleUsersTableSelectionChanged(ObservableValue observable, Object oldValue, Object newValue) {

        if (newValue != null) {

            Evento evento = (Evento) newValue;
            tfNombre.setText(evento.getNombre());
            taDescripcion.setText(evento.getDescripcion());
            tfAforo.setText(evento.getAforo().toString());
            cbCatering.setSelected(evento.getCatering());
            dpFechaEvento.setValue(evento.getFechaEvento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Carga y muestra todos los eventos desde la base de datos en la tabla de
     * la interfaz gráfica.
     *
     * @return Una ObservableList que contiene todos los eventos cargados.
     */
    @FXML
    private ObservableList<Evento> cargarTodo() {
        ObservableList<Evento> listaEvento;
        List<Evento> todosEventos;

        // Obtiene todos los eventos de la base de datos utilizando la factoría de eventos
        todosEventos = eventofact.getFactory().viewEvents_XML(Evento.class);

        // Convierte la lista de eventos a una ObservableList para su uso en la interfaz gráfica
        listaEvento = FXCollections.observableArrayList(todosEventos);

        // Establece la lista de eventos en la tabla y actualiza la vista
        tbvEvento.setItems(listaEvento);
        tbvEvento.refresh();

        return listaEvento;
    }

    @FXML
    private void handleMtem4(ActionEvent event) {

        //lo primero que hacemos sera seleccionar una fila de nuestra tabla
        Evento selectedEvento = (Evento) tbvEvento.getSelectionModel().getSelectedItem();

        try {
            Alert ventanita = new Alert(Alert.AlertType.CONFIRMATION);
            ventanita.setHeaderText(null);
            ventanita.setTitle("Advertencia");
            ventanita.setContentText("¿Estas seguro de que quieres eliminar ese evento?");
            //Con este Optional<ButtonType> creamos botones de Ok y cancelar
            Optional<ButtonType> action = ventanita.showAndWait();
            //Si le da a OK el borrara ese lugar
            if (action.get() == ButtonType.OK) {
                eventofact.getFactory().deleteEvent(selectedEvento.getId_evento().toString());
                eventoData = FXCollections.observableArrayList(cargarTodo());
                tfNombre.setText("");
                taDescripcion.setText("");
                tfAforo.setText("");
                cbCatering.isDisable();
                dpFechaEvento.setValue(null);
                throw new Exception("EL EVENTO SE HA ELIMINADO CORRECTAMENTE");
            } else {
                //Si le da a cancelar la ventana emergente se cerrará
                ventanita.close();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.INFORMATION, e.getMessage()).showAndWait();
        }

    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public void setPatrocinador(Patrocinador patrocinador) {
        this.patrocinador = patrocinador;
    }

    /**
     * Carga y muestra los eventos filtrados por una sede específica en la tabla
     * de la interfaz gráfica.
     *
     * @return Una ObservableList que contiene los eventos filtrados por la
     * sede.
     */
    public ObservableList<Evento> cargarFiltroEvento() {
        ObservableList<Evento> listaEvento = null;
        List<Evento> filtradoParam;

        try {
            // Intenta obtener la lista de eventos asociados a la sede especificada
            filtradoParam = FXCollections.observableArrayList(sedefact.getFactory().findEventoBySede_XML(Evento.class,
                    sede.getId_sede().toString())
            );
            listaEvento = FXCollections.observableArrayList(filtradoParam);

            // Establece la lista de eventos filtrados en la tabla y actualiza la vista
            tbvEvento.setItems(listaEvento);
            tbvEvento.refresh();

            // Muestra una advertencia si la tabla está vacía
            if (tbvEvento.getItems().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("TABLA VACÍA");
                alert.setHeaderText(null);
                alert.setContentText("No hay ningún evento asociado a esta sede.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            // Maneja las excepciones y muestra el mensaje de error
            e.getMessage();
        }
        return listaEvento;
    }

    @FXML
    public void handleViewPatrocinador(ActionEvent event) {

        Evento selectedEvento = tbvEvento.getSelectionModel().getSelectedItem();

        try {
            if (selectedEvento == null) {
                // Mostrar un mensaje al usuario indicando que debe seleccionar una zona.

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error evento");
                alert.setHeaderText(null);
                alert.setContentText("El evento que has seleccionado no tiene patrocinadores asignados");
                alert.showAndWait();

                return;
            }
            // Verificar si hay patrocinadores en el evento

            // Cerrar la ventana actual
            Stage ventanaActual = (Stage) tbvEvento.getScene().getWindow();
            ventanaActual.close();

            // Abrir la nueva ventana
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Patrocinador.fxml"));
            Parent root = loader.load();

            PatrocinadorController patrocinadorController = ((PatrocinadorController) loader.getController());
            patrocinadorController.setEvento(selectedEvento);
            patrocinadorController.setStage(stage);
            patrocinadorController.initStage(root);
            patrocinadorController.cargarFiltroPatrocinadores();

        } catch (Exception ex) {
            // Manejo de excepciones generales
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error evento");
            alert.setHeaderText(null);
            alert.setContentText("Ha ocurrido un error al ejecutar esta accion");
            alert.showAndWait();
            Logger
                    .getLogger(EventoController.class
                            .getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void handleFiltradoCatering(ObservableValue observable, Object oldValue, Object newValue) {

        Boolean filtro = cbFiltro.getValue();
        if (filtro != null) {
            if (filtro) {
                cargarFiltro();
            } else {
                cargarFiltro();
            }
        } else {
            cargarTodo();
        }

    }

    @FXML
    private ObservableList<Evento> cargarFiltro() {
        ObservableList<Evento> listaEvento;
        List<Evento> todosEventos;

        todosEventos = eventofact.getFactory().viewEvents_XML(Evento.class);

        // Obtener el valor seleccionado del ComboBox
        Boolean filtro = cbFiltro.getSelectionModel().getSelectedItem();

        // Filtrar eventos según el valor seleccionado del ComboBox
        List<Evento> eventosFiltrados = todosEventos.stream()
                .filter(evento -> evento.getCatering() != null && evento.getCatering().equals(filtro))
                .collect(Collectors.toList());

        listaEvento = FXCollections.observableArrayList(eventosFiltrados);
        tbvEvento.setItems(listaEvento);
        tbvEvento.refresh();

        return listaEvento;
    }

}
