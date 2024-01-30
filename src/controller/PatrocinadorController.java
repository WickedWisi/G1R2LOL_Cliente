/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import exception.EmptyTextFieldsException;
import exception.FormatErrorException;
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
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
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
import logic.PatrocinadorFactory;

import model.Evento;
import model.Patrocinador;
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
public class PatrocinadorController {

    private static final Logger LOGGER = Logger.getLogger("controller");

    private Patrocinador patrocin = new Patrocinador();

    private PatrocinadorFactory patFact = new PatrocinadorFactory();
    private EventoManagerFactory eveFact = new EventoManagerFactory();

    private ObservableList<Patrocinador> patData;

    private Set<Patrocinador> listPat;

    private Evento evento;

    /**
     * Expresión regular para validar el formato del correo electrónico.
     */
    @FXML
    private static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Patrón compilado para la validación del correo electrónico.
     */
    @FXML
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @FXML
    private Stage stage;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfBusqueda;
    @FXML
    private TextArea taDescripcion;
    @FXML
    private DatePicker dpDuracion;
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
    private TableView<Patrocinador> tbPatrocinador;
    @FXML
    private TableColumn tbColNombre;
    @FXML
    private TableColumn tbColDescripcion;
    @FXML
    private TableColumn tbColEmail;
    @FXML
    private TableColumn tbColTelefono;
    @FXML
    private TableColumn tbColDuracion;
    @FXML
    private ContextMenu cmMenu;
    @FXML
    private MenuItem miEliminar;
    @FXML
    private MenuItem miEvento;

    public void initStage(Parent root) {

        LOGGER.info("INIT STAGE CLASE CONTROLADORA DE PATROCINADOR");

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        //habilitamos los txt
        tfTelefono.setDisable(false);
        tfEmail.setDisable(false);
        tfNombre.setDisable(false);

        //habilitamos los botonoes
        btnInsertar.setDisable(false);
        btnEliminar.setDisable(false);
        btnEditar.setDisable(false);
        btnInforme.setDisable(false);

        //habilitamos el date picker
        dpDuracion.setDisable(false);

        //habilitamos la tabla
        tbPatrocinador.setDisable(false);

        //El foco estará puesto en el campo de nombre de evento.
        tfNombre.requestFocus();

        //El título de la ventana es “Patrocinador”.
        stage.setTitle("PATROCINADOR");

        //La ventana no es redimensionable
        stage.setResizable(false);

        //METODOS
        btnInsertar.setOnAction(this::handleCrearButtonAction);
        btnInforme.setOnAction(this::handleInformeButtonAction);
        btnEliminar.setOnAction(this::handleEliminarButtonAction);
        btnEditar.setOnAction(this::handleModificarButtonAction);
        btnBuscar.setOnAction(this::handleBuscarButtonAction);
        stage.setOnCloseRequest(this::handleExitButtonAction);
        tbPatrocinador.getSelectionModel().selectedItemProperty().addListener(this::handleUsersTableSelectionChanged);
        miEliminar.setOnAction(this::handleMiEliminar);

        //Con el siguiente codigo asignamos a las columnas los tipos y los nombres
        tbColNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tbColDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tbColEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tbColTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        tbColDuracion.setCellValueFactory(new PropertyValueFactory<>("DuracionPatrocinio"));
        //Aqui le ponemos un formato de fecha concreto
        tbColDuracion.setCellFactory(column -> {
            TableCell<Patrocinador, Date> cell = new TableCell<Patrocinador, Date>() {
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
        patData = FXCollections.observableArrayList(patFact.getFactory().findAll_JSON(Patrocinador.class));

        tbPatrocinador.setItems(patData);

        stage.show();

    }

    @FXML
    private void handleExitButtonAction(WindowEvent event) {
        Alert ventanita = new Alert(Alert.AlertType.CONFIRMATION);
        ventanita.setHeaderText(null);
        ventanita.setTitle("Advertencia");
        ventanita.setContentText("¿Deseas Salir?");
        // Con este Optional<ButtonType> creamos botones de Ok y cancelar
        Optional<ButtonType> action = ventanita.showAndWait();
        // Si le da a OK el programa cesará de existir, se cierra por completo
        if (action.orElse(ButtonType.CANCEL) == ButtonType.OK) {
            Platform.exit();
        } else {
            // Si le da a cancelar la ventana emergente se cerrará pero la ventana principal se mantiene
            event.consume();  // Consume el evento para evitar que se cierre
        }
    }

    @FXML
    private void handleCrearButtonAction(ActionEvent event) {

        try {
            //aqui estamos validando que los campos no esten vacios
            if (this.tfNombre.getText().isEmpty() || this.taDescripcion.getText().isEmpty() || this.tfEmail.getText().isEmpty() || this.tfTelefono.getText().isEmpty() || dpDuracion.getValue() == null) {
                throw new EmptyTextFieldsException("CAMPOS NO INFORMADOS");
            }
            // Validar si el email contiene un dominio como (….@gmail.com).
            String email = this.tfEmail.getText();

            if (!(EMAIL_PATTERN.matcher(email).matches())) {
                throw new FormatErrorException("Formato de E-mail no valido");
            }
            if (tfTelefono.getText().length() != 9) {
                throw new FormatErrorException("El numero de telefono no tiene el formato correcto");
            } else {
                try {
                    //escribimos en el objeto lugar los fields de los campos ha introducir
                    patrocin.setNombre(tfNombre.getText());
                    patrocin.setDescripcion(taDescripcion.getText());
                    patrocin.setEmail(tfEmail.getText());
                    patrocin.setTelefono(Integer.parseInt(tfTelefono.getText()));
                    patrocin.setDuracionPatrocinio(Date.from(dpDuracion.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));

                    //llamamos a la factoria para crear ese lugar y lo introduzca en la base de datos
                    patFact.getFactory().create_XML(patrocin);
                    //llamamos a nuestro metodo de cargarTodo para refrescar nuestra tabla y salga el nuevo lugar creado
                    patData = FXCollections.observableArrayList(cargarTodo());
                    //Una vez creado el lugar pondremos en blanco de nuevo los campos y mostraremos un mensaje de lugar creado con exito
                    tfNombre.setText("");
                    taDescripcion.setText("");
                    tfEmail.setText("");
                    tfTelefono.setText(" ");
                    dpDuracion.setValue(null);
                    throw new Exception("PATROCINADOR CREADO CON EXITO");

                } catch (Exception e) {
                    new Alert(Alert.AlertType.INFORMATION, e.getMessage()).showAndWait();
                }

            }

        } catch (EmptyTextFieldsException | FormatErrorException e) {
            //si alguna de las validacioens no ha salido bn saldra un mensaje de error y nos vaciara los campos nuevamente
            new Alert(Alert.AlertType.INFORMATION, e.getMessage()).showAndWait();
            tfNombre.setText("");
            taDescripcion.setText("");
            tfEmail.setText("");
            tfTelefono.setText(" ");
            dpDuracion.setValue(null);

        }

    }

    @FXML
    private void handleInformeButtonAction(ActionEvent event) {

        try {
            //este metodo sirve para sacar un report con los datos que hay en la tabla de la ventana
            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/PatrocinadorReports.jrxml"));
            JRBeanCollectionDataSource dataItems;
            dataItems = new JRBeanCollectionDataSource((Collection<Patrocinador>) this.tbPatrocinador.getItems());
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);

        } catch (JRException ex) {

            Logger.getLogger(PatrocinadorController.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    @FXML
    private void handleEliminarButtonAction(ActionEvent event) {

        //lo primero que hacemos sera seleccionar una fila de nuestra tabla
        Patrocinador selectedPatrocinador = (Patrocinador) tbPatrocinador.getSelectionModel().getSelectedItem();
        try {
            Alert ventana = new Alert(Alert.AlertType.CONFIRMATION);
            ventana.setHeaderText(null);
            ventana.setTitle("Advertencia");
            ventana.setContentText("¿Estas seguro de que quieres eliminar ese evento?");
            //Con este Optional<ButtonType> creamos botones de Ok y cancelar
            Optional<ButtonType> action = ventana.showAndWait();
            //Si le da a OK el borrara ese lugar
            if (action.get() == ButtonType.OK) {
                patFact.getFactory().remove(selectedPatrocinador.getId_Patrocinador().toString());
                patData = FXCollections.observableArrayList(cargarTodo());
                tfNombre.setText("");
                taDescripcion.setText("");
                tfEmail.setText("");
                tfTelefono.setText(" ");
                dpDuracion.setValue(null);
                throw new Exception("EL PATROCINADOR SE HA ELIMINADO CORRECTAMENTE");
            } else {
                //Si le da a cancelar la ventana emergente se cerrará
                ventana.close();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }

    @FXML
    private void handleModificarButtonAction(ActionEvent event) {

        try {
            //aqui estamos validando que los campos no esten vacios
            if (this.tfNombre.getText().isEmpty() || this.taDescripcion.getText().isEmpty() || this.tfEmail.getText().isEmpty() || this.tfTelefono.getText().isEmpty() || dpDuracion.getValue() == null) {
                throw new EmptyTextFieldsException("CAMPOS NO INFORMADOS");
            }
            // Validar si el email contiene un dominio como (….@gmail.com).
            String email = this.tfEmail.getText();

            if (!(EMAIL_PATTERN.matcher(email).matches())) {
                throw new FormatErrorException("Formato de E-mail no valido");
            }
            if (tfTelefono.getText().length() != 9) {
                throw new FormatErrorException("El numero de telefono no tiene el formato correcto");
            } else {
                try {
                    //escribimos en el objeto lugar los fields de los campos ha introducir
                    patrocin.setId_Patrocinador(tbPatrocinador.getSelectionModel().getSelectedItem().getId_Patrocinador());
                    patrocin.setNombre(tfNombre.getText());
                    patrocin.setDescripcion(taDescripcion.getText());
                    patrocin.setEmail(tfEmail.getText());
                    patrocin.setTelefono(Integer.parseInt(tfTelefono.getText()));
                    patrocin.setDuracionPatrocinio(Date.from(dpDuracion.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));

                    //llamamos a la factoria para crear ese lugar y lo introduzca en la base de datos
                    patFact.getFactory().edit_XML(patrocin);
                    //llamamos a nuestro metodo de cargarTodo para refrescar nuestra tabla y salga el nuevo lugar creado
                    patData = FXCollections.observableArrayList(cargarTodo());
                    //Una vez creado el lugar pondremos en blanco de nuevo los campos y mostraremos un mensaje de lugar creado con exito
                    tfNombre.setText("");
                    taDescripcion.setText("");
                    tfEmail.setText("");
                    tfTelefono.setText(" ");
                    dpDuracion.setValue(null);
                    throw new Exception("PATROCINADOR EDITADO CON EXITO");
                } catch (Exception e) {
                    new Alert(Alert.AlertType.INFORMATION, e.getMessage()).showAndWait();
                }

            }

        } catch (EmptyTextFieldsException | FormatErrorException e) {
            //si alguna de las validacioens no ha salido bn saldra un mensaje de error y nos vaciara los campos nuevamente
            new Alert(Alert.AlertType.INFORMATION, e.getMessage()).showAndWait();
            tfNombre.setText("");
            taDescripcion.setText("");
            tfEmail.setText("");
            tfTelefono.setText(" ");
            dpDuracion.setValue(null);

        }
    }

    private void handleBuscarButtonAction(ActionEvent event) {
        try {
            String filtro = tfBusqueda.getText();

            // Verificar si el TextField está vacío
            if (filtro.isEmpty()) {
                patData = FXCollections.observableArrayList(cargarTodo());
                tbPatrocinador.setItems(patData);
            } else {
                // Resto del código para buscar por aforo o fecha
                if (filtro.matches("\\D+")) {
                    // Utilizar \\D+ para verificar si la cadena contiene solo caracteres no numéricos
                    ObservableList<Patrocinador> patPorNombre = FXCollections.observableArrayList(
                            patFact.getFactory().viewPatrocinadorByName_XML(Patrocinador.class, filtro)
                    );
                    tbPatrocinador.setItems(patPorNombre);
                } else {
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date fecha = dateFormat.parse(filtro);

                        ObservableList<Patrocinador> patPorDuracion = FXCollections.observableArrayList(
                                patFact.getFactory().viewPatrocinadorByDuration_XML(Patrocinador.class, dateFormat.format(fecha))
                        );
                        tbPatrocinador.setItems(patPorDuracion);
                    } catch (ParseException ex) {
                        throw new FormatErrorException("Formato de filtro no válido. Debe ser un nombre o fecha (yyyy-MM-dd).");
                    }
                }
            }

            tbPatrocinador.refresh();
        } catch (FormatErrorException e) {
            new Alert(Alert.AlertType.INFORMATION, e.getMessage()).showAndWait();
            tfBusqueda.setText("");
        }
    }

    @FXML
    private void handleUsersTableSelectionChanged(ObservableValue observable, Object oldValue, Object newValue) {

        if (newValue != null) {

            Patrocinador pat = (Patrocinador) newValue;
            tfNombre.setText(pat.getNombre());
            taDescripcion.setText(pat.getDescripcion());
            tfEmail.setText(pat.getEmail());
            tfTelefono.setText(pat.getTelefono().toString());
            dpDuracion.setValue(pat.getDuracionPatrocinio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private ObservableList<Patrocinador> cargarTodo() {
        ObservableList<Patrocinador> listaPatrocinador;
        List<Patrocinador> todosPatrocinador;
        todosPatrocinador = patFact.getFactory().findAll_XML(Patrocinador.class);

        listaPatrocinador = FXCollections.observableArrayList(todosPatrocinador);
        tbPatrocinador.setItems(listaPatrocinador);
        tbPatrocinador.refresh();
        return listaPatrocinador;
    }

    @FXML
    private void handleMiEliminar(ActionEvent event) {

        //lo primero que hacemos sera seleccionar una fila de nuestra tabla
        Patrocinador selectedPatrocinador = (Patrocinador) tbPatrocinador.getSelectionModel().getSelectedItem();
        try {

            Alert ventanita = new Alert(Alert.AlertType.CONFIRMATION);
            ventanita.setHeaderText(null);
            ventanita.setTitle("Advertencia");
            ventanita.setContentText("¿Estas seguro de que quieres eliminar ese patrocinador?");
            //Con este Optional<ButtonType> creamos botones de Ok y cancelar
            Optional<ButtonType> action = ventanita.showAndWait();
            //Si le da a OK el borrara ese lugar
            if (action.get() == ButtonType.OK) {
                patFact.getFactory().remove(selectedPatrocinador.getId_Patrocinador().toString());
                patData = FXCollections.observableArrayList(cargarTodo());
                tfNombre.setText("");
                taDescripcion.setText("");
                tfEmail.setText("");
                tfTelefono.setText("");
                dpDuracion.setValue(null);
                throw new Exception("EL PATROCINADOR SE HA ELIMINADO CORRECTAMENTE");
            } else {
                //Si le da a cancelar la ventana emergente se cerrará
                ventanita.close();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.INFORMATION, e.getMessage()).showAndWait();
        }

    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public ObservableList<Patrocinador> cargarFiltroPatrocinadores() {

        ObservableList<Patrocinador> listaPatrocinadores = null;
        List<Patrocinador> filtradoParam;

        try {
            // Intenta obtener la lista de patrocinadores asociados al evento
            filtradoParam = FXCollections.observableArrayList(eveFact.getFactory().viewEventoByPatrocinador_XML(Patrocinador.class,
                     evento.getId_evento().toString()));
            listaPatrocinadores = FXCollections.observableArrayList(filtradoParam);
            tbPatrocinador.setItems(listaPatrocinadores);
            tbPatrocinador.refresh();

            if (tbPatrocinador.getItems().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("TABLA VACIA");
                alert.setHeaderText(null);
                alert.setContentText("No hay ningún patrocinador en ese evento.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            // Maneja la excepción, por ejemplo, imprime el error
            e.printStackTrace();
            // O muestra un mensaje de error al usuario si es apropiado
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error al cargar patrocinadores. Detalles: " + e.getMessage());
            alert.showAndWait();
            // Puedes ajustar la lógica de manejo de errores según tus necesidades
        }
        return listaPatrocinadores;
    }

}
