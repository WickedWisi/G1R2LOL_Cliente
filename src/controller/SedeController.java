package controller;

import java.util.logging.Logger;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.stage.WindowEvent;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class SedeController {

    private static final Logger LOGGER = Logger.getLogger("controller");

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
    @FXML
    private ContextMenu cMenu;
    @FXML
    private MenuItem mBorrarSede;

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
                    tFinDeContrato.setValue(newValue.getFinDeContrato().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
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
        stage.setOnCloseRequest(this::handleExitButtonAction);
        bBuscar.setOnAction(this::handleBuscarAction);
        mBorrarSede.setOnAction(this::handleBorrarMC);
        informe.setOnAction(this::handleInformeAction);

        stage.show();
    }

    @FXML
    private void handleInformeAction(ActionEvent event) {
        try {
            LOGGER.info("Beginning printing action...");
            JasperReport report
                    = JasperCompileManager.compileReport(getClass()
                            .getResourceAsStream("/report/SedeReport.jrxml"));
            //Data for the report: a collection of UserBean passed as a JRDataSource
            //implementationn
            JRBeanCollectionDataSource dataItems
                    = new JRBeanCollectionDataSource((Collection<Sede>) this.tabla.getItems());
            //Map of parameter to be passed to the report
            Map<String, Object> parameters = new HashMap<>();
            //Fill report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            //Create and show the report window. The second parameter false value makes
            //report window not to close app.
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
            // jasperViewer.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        } catch (JRException ex) {
            //If there is an error show message and
            //log it.
            LOGGER.log(Level.SEVERE,
                    "SedeController: Error printing report: {0}",
                    ex.getMessage());
        }
    }

    @FXML
    private void handleBorrarMC(ActionEvent event) {

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

    @FXML
    private void handleBuscarAction(ActionEvent event) {
        try {
            // Obtener valores de los campos de búsqueda
            String aforoMaxText = fAforoMax.getText();
            String paisText = fPais.getText();

            if (aforoMaxText.isEmpty() && paisText.isEmpty()) {
                // Ambos campos están vacíos, mostrar mensaje de advertencia
                throw new FormatErrorException("Debe ingresar al menos un criterio de búsqueda.");
            }

            ObservableList<Sede> listaSede;
            List<Sede> todosSede;

            // Verificar y buscar por el campo Aforo Maximo si no está vacío
            if (!aforoMaxText.isEmpty()) {
                if (!aforoMaxText.matches("\\d+") || Integer.parseInt(aforoMaxText) <= 0) {
                    throw new FormatErrorException("El aforo máximo debe contener solo números y ser positivos.");
                }

                todosSede = new ArrayList<>();
                for (Sede sede : sedefact.getFactory().viewSedes_XML(Sede.class)) {
                    if (sede.getAforoMax() <= Integer.parseInt(aforoMaxText)) {
                        todosSede.add(sede);
                    }
                }
            } else {
                // Buscar por el campo País si el campo Aforo Maximo está vacío
                if (!paisText.isEmpty() && paisText.matches("\\d+")) {
                    throw new FormatErrorException("El formato del país debe ser letras.");
                }

                todosSede = sedefact.getFactory().viewSedeByCountry_XML(Sede.class, paisText);
            }

            listaSede = FXCollections.observableArrayList(todosSede);
            tabla.setItems(listaSede);
            tabla.refresh();

        } catch (FormatErrorException e) {
            new Alert(Alert.AlertType.INFORMATION, e.getMessage()).showAndWait();
        }
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
                sedeData = FXCollections.observableArrayList(cargarTodo());
                // Actualizar la tabla para reflejar los cambios
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

                String aforoMax = tAforoMax.getText();

                if (!aforoMax.matches("\\d+")) {
                    bien = false;
                    throw new FormatErrorException("El Aforo Máximo debe ser un número positivo, ni contener letras.");

                }
                int aforoMax2 = Integer.parseInt(tAforoMax.getText());
                if (aforoMax2 <= 0) {
                    bien = false;
                    throw new FormatErrorException("El Aforo Máximo debe ser un número positivo, ni contener letras.");
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
    
     public String getIdentificadorVentana() {
        return "ventanaSede";
    }
}
