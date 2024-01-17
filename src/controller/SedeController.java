package controller;

import exception.FormatErrorException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Sede;
import logic.SedeManagerFactory;

import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.stage.WindowEvent;

public class SedeController {

    private SedeManagerFactory sedefact = new SedeManagerFactory();
    private ObservableList<Sede> sedeData;

    @FXML
    private Stage stage;
    @FXML
    private TextField tPais;
    @FXML
    private TextField tAforoMax;
    @FXML
    private TextField tNumVolMax;
    @FXML
    private TextField tUbicacion;
    @FXML
    private DatePicker tFinDeContrato;
    @FXML
    private Button bInsert;
    @FXML
    private Button bEditar;
    @FXML
    private Button bEliminar;
    @FXML
    private TextField fPais;
    @FXML
    private TextField fAforoMax;
    @FXML
    private Button bBuscar;
    @FXML
    private Button informe;
    @FXML
    private TableView<Sede> tabla;
    @FXML
    private TableColumn<Sede, String> tblPais;
    @FXML
    private TableColumn<Sede, Integer> tblAforoMax;
    @FXML
    private TableColumn<Sede, Integer> tblNumVolMax;
    @FXML
    private TableColumn<Sede, String> tblUbicacion;
    @FXML
    private TableColumn<Sede, Date> tblFinDeContrato;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu menuP;
    @FXML
    private Menu menuN;
    @FXML
    private Menu menuA;
    @FXML
    private Menu menuC;
    @FXML
    private MenuItem menuEvento;
    @FXML
    private MenuItem menuSede;
    @FXML
    private MenuItem menuPatrocinador;

    public void initStage(javafx.scene.Parent root) {
        javafx.scene.Scene scene = new javafx.scene.Scene(root);
        stage.setScene(scene);

        tPais.setDisable(false);
        tAforoMax.setDisable(false);
        tNumVolMax.setDisable(false);
        tUbicacion.setDisable(false);
        tFinDeContrato.setDisable(false);

        bBuscar.setDisable(false);
        bEditar.setDisable(false);
        bEliminar.setDisable(false);
        bInsert.setDisable(false);

        tabla.setDisable(false);
        tFinDeContrato.setDisable(false);

        tPais.requestFocus();

        stage.setTitle("SEDE");

        stage.setResizable(false);

        tblPais.setCellValueFactory(new PropertyValueFactory<>("pais"));
        tblAforoMax.setCellValueFactory(new PropertyValueFactory<>("aforoMax"));
        tblNumVolMax.setCellValueFactory(new PropertyValueFactory<>("numVolMax"));
        tblUbicacion.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));
        tblFinDeContrato.setCellValueFactory(new PropertyValueFactory<>("finDeContrato"));
        tblFinDeContrato.setCellFactory(column -> {
            TableCell<Sede, Date> cell = new TableCell<Sede, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(format.format(item));
                    }
                }
            };

            return cell;
        });

        sedeData = FXCollections.observableArrayList(sedefact.getFactory().viewSedes_XML(Sede.class));

        tabla.setItems(sedeData);

        tabla.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Sede>() {
            @Override
            public void changed(ObservableValue<? extends Sede> observable, Sede oldValue, Sede newValue) {
                if (newValue != null) {
                    tPais.setText(newValue.getPais());
                    tAforoMax.setText(String.valueOf(newValue.getAforoMax()));
                    tNumVolMax.setText(String.valueOf(newValue.getNumVolMax()));
                    tUbicacion.setText(newValue.getUbicacion());
                    tFinDeContrato.setValue(newValue.getFinDeContrato().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
                } else {
                    tPais.clear();
                    tAforoMax.clear();
                    tNumVolMax.clear();
                    tUbicacion.clear();
                    tFinDeContrato.setValue(null);
                }
            }
        });

        bInsert.setOnAction(this::handleInsertAction);
        bEliminar.setOnAction(this::handleEliminarAction);
        bEditar.setOnAction(this::handleModificarAction);

        stage.show();
    }

    private void handleInsertAction(ActionEvent event) {

        try {
            // Validar los campos antes de la creación
            if (validar()) {
                String pais = tPais.getText();
                int aforoMax = Integer.parseInt(tAforoMax.getText());
                int numVolMax = Integer.parseInt(tNumVolMax.getText());
                String ubicacion = tUbicacion.getText();
                Date finDeContrato = java.sql.Date.valueOf(tFinDeContrato.getValue());

                // Crear una instancia de Sede con los datos proporcionados
                Sede nuevaSede = new Sede();
                nuevaSede.setPais(pais);
                nuevaSede.setAforoMax(aforoMax);
                nuevaSede.setNumVolMax(numVolMax);
                nuevaSede.setUbicacion(ubicacion);
                nuevaSede.setFinDeContrato(finDeContrato);

                // Llamar al método createSede_XML con la instancia de Sede
                sedefact.getFactory().createSede_XML(nuevaSede);

                // Actualizar la lista ObservableList con la nueva fila
                sedeData.add(nuevaSede);

                // Actualizar la tabla para reflejar los cambios
                tabla.setItems(sedeData);
                // Limpiar los datos después de la creación
                limpiarDatos();
            }
        } catch (FormatErrorException e) {
            try {
                throw new FormatErrorException("Los datos no estan validados");
            } catch (FormatErrorException ex) {

            }
        }
    }

    @FXML
    private void handleModificarAction(ActionEvent event) {
        Sede sede = new Sede();
        try {

            try {
                if (validar()) {
                    //escribimos en el objeto lugar los fields de los campos ha introducir
                    sede.setId_sede(tabla.getSelectionModel().getSelectedItem().getId_sede());
                    sede.setPais(tPais.getText());
                    sede.setAforoMax(Integer.parseInt(tAforoMax.getText()));
                    sede.setNumVolMax(Integer.parseInt(tNumVolMax.getText()));
                    sede.setUbicacion(tUbicacion.getText());
                    sede.setFinDeContrato(Date.from(tFinDeContrato.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));

                    //llamamos a la factoria para crear ese lugar y lo introduzca en la base de datos
                    sedefact.getFactory().modifySede_XML(sede);
                    //llamamos a nuestro metodo de cargarTodo para refrescar nuestra tabla y salga el nuevo lugar creado
                    sedeData = FXCollections.observableArrayList(cargarTodo());
                    //Una vez creado el lugar pondremos en blanco de nuevo los campos y mostraremos un mensaje de lugar creado con exito
                    tPais.setText("");
                    tAforoMax.setText("");
                    tNumVolMax.setText("");
                    tUbicacion.setText("");
                    tFinDeContrato.setValue(null);
                    throw new Exception("Sede modificada con exito");
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.INFORMATION, e.getMessage()).showAndWait();
            }

        } catch (Exception e) {
            //si alguna de las validacioens no ha salido bn saldra un mensaje de error y nos vaciara los campos nuevamente
            new Alert(Alert.AlertType.INFORMATION, e.getMessage()).showAndWait();
            tPais.setText("");
            tAforoMax.setText("");
            tNumVolMax.setText("");
            tUbicacion.setText("");
            tFinDeContrato.setValue(null);

        }
    }

    @FXML
    private void handleEliminarAction(ActionEvent event) {

        //lo primero que hacemos sera seleccionar una fila de nuestra tabla
        Sede selectedSede = (Sede) tabla.getSelectionModel().getSelectedItem();
        try {
            try {
                Alert ventanita = new Alert(Alert.AlertType.CONFIRMATION);
                ventanita.setHeaderText(null);
                ventanita.setTitle("Advertencia");
                ventanita.setContentText("¿Estas seguro de que quieres eliminar esta sede?");
                //Con este Optional<ButtonType> creamos botones de Ok y cancelar
                Optional<ButtonType> action = ventanita.showAndWait();
                //Si le da a OK el borrara ese lugar
                if (action.get() == ButtonType.OK) {
                    sedefact.getFactory().deleteSede(selectedSede.getId_sede().toString());
                    sedeData = FXCollections.observableArrayList(cargarTodo());
                    tPais.setText("");
                    tAforoMax.setText("");
                    tNumVolMax.setText("");
                    tUbicacion.isDisable();
                    tFinDeContrato.setValue(null);
                    throw new Exception("La sede se ha eliminado correctamente");
                } else {
                    //Si le da a cancelar la ventana emergente se cerrará
                    ventanita.close();
                }

            } catch (Exception e) {
                new Alert(Alert.AlertType.INFORMATION, e.getMessage()).showAndWait();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }

    }

    private void limpiarDatos() {
        tPais.clear();
        tAforoMax.clear();
        tNumVolMax.clear();
        tUbicacion.clear();
        tFinDeContrato.setValue(null);
    }

    private boolean validar() throws FormatErrorException {
        Boolean bien = true;
        try {
            if (tPais.getText().isEmpty() || tAforoMax.getText().isEmpty() || tNumVolMax.getText().isEmpty()
                    || tUbicacion.getText().isEmpty() || tFinDeContrato.getValue() == null) {

                return false;

            } else {

                int aforoMax = Integer.parseInt(tAforoMax.getText());
                if (aforoMax < 0) {
                    bien = false;
                    throw new FormatErrorException("El Aforo Máximo debe ser un número positivo.");

                }

                int numVolMax = Integer.parseInt(tNumVolMax.getText());
                if (numVolMax < 0) {
                    bien = false;
                    throw new FormatErrorException("El numero de Voluntarios debe ser un número positivo.");

                }

                // Validar el formato del Fin de Contrato
                Date finDeContrato = java.sql.Date.valueOf(tFinDeContrato.getValue());
                if (finDeContrato.before(new Date())) {
                    bien = false;
                    throw new FormatErrorException("La fecha de Fin de Contrato debe ser posterior a la fecha actual.");
                }

            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.INFORMATION, e.getMessage()).showAndWait();
        }
        return bien;
    }

    @FXML
    private ObservableList<Sede> cargarTodo() {
        ObservableList<Sede> listaSede;
        List<Sede> todosSede;
        todosSede = sedefact.getFactory().viewSedes_XML(Sede.class);

        listaSede = FXCollections.observableArrayList(todosSede);
        tabla.setItems(listaSede);
        tabla.refresh();
        return listaSede;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
