/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import logic.EventoManagerFactory;
import model.Evento;

/**
 *
 * @author josu
 */
public class EventoController {

    private static final Logger LOGGER = Logger.getLogger("controller");

    Evento eventito = new Evento();

    private EventoManagerFactory eventofact = new EventoManagerFactory();

    private ObservableList<Evento> eventoData;
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
    private TableView tbvEvento;
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

    public void initStage(Parent root) {
        
         LOGGER.info("HOLA HOLA HOLA HOLA");

        Scene scene = new Scene(root);
        stage.setScene(scene);

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

        //habilitamos el DatePicker
        dpFechaEvento.setDisable(false);

        //El foco estará puesto en el campo de nombre de evento.
        tfNombre.requestFocus();

        //El título de la ventana es “Sign In”.
        stage.setTitle("EVENTO");

        //La ventana no es redimensionable
        stage.setResizable(false);

        //METODOS
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tbcDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tbcAforo.setCellValueFactory(new PropertyValueFactory<>("aforo"));
        tbcCatering.setCellValueFactory(new PropertyValueFactory<>("catering"));
        tbcFechaEvento.setCellValueFactory(new PropertyValueFactory<>("fechaEvento"));
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



        eventoData = FXCollections.observableArrayList(eventofact.getFactory().viewEvents_XML(Evento.class));
        
        tbvEvento.setItems(eventoData);

        stage.show();
       
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
