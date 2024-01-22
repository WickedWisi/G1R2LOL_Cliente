package controller;

import application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Socio;
import model.User;
import exception.FormatErrorException;
import java.util.Optional;
import java.util.logging.Logger;
import javafx.application.Platform;
import logic.UserManagerFactory;
import logic.VoluntarioManagerFactory;
import model.UserType;
import model.Voluntario;

public class SignUpController {

    private VoluntarioManagerFactory volfact = new VoluntarioManagerFactory();
    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());
    @FXML
    private Stage stage;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtDNI;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtSurname;
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private TextField txtNumeroVol;
    @FXML
    private PasswordField txtPasswd;
    @FXML
    private PasswordField txtConfirmPasswd;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    public void initStage(javafx.scene.Parent root) {
        javafx.scene.Scene scene = new javafx.scene.Scene(root);
        stage.setScene(scene);

        stage.setOnCloseRequest(this::handleExitButtonAction);
        btnCancel.setOnAction(this::handleCancelAction);
        btnSave.setOnAction(this::handleSignUpAction);

        stage.show();
    }

    @FXML
    private void handleExitButtonAction(WindowEvent event) {
        Alert ventanita = new Alert(Alert.AlertType.CONFIRMATION);
        ventanita.setHeaderText(null);
        ventanita.setTitle("Advertencia");
        ventanita.setContentText("¿Deseas Salir?");
        Optional<ButtonType> action = ventanita.showAndWait();
        if (action.orElse(ButtonType.CANCEL) == ButtonType.OK) {
            Platform.exit();
        } else {
            event.consume();
        }
    }

    @FXML
    private void handleSignUpAction(ActionEvent event) {
        if (validar()) {
            // Resto del código...
            Voluntario voluntario = new Voluntario();
            voluntario.setEmail(txtEmail.getText());
            voluntario.setDNI(txtDNI.getText());
            voluntario.setNombre(txtName.getText());
            voluntario.setApellido(txtSurname.getText());
            voluntario.setTelefono(Integer.parseInt(txtPhoneNumber.getText()));
            voluntario.setNumero_Voluntariados(Integer.parseInt(txtNumeroVol.getText()));
            voluntario.setPasswd(txtPasswd.getText());
            voluntario.setConfirmPasswd(txtConfirmPasswd.getText());
            voluntario.setUserType(UserType.VOLUNTARIO);
            volfact.getFactory().create_JSON(voluntario);
            limpiarDatos();
        }
    }

    private void limpiarDatos() {
        txtEmail.clear();
        txtDNI.clear();
        txtName.clear();
        txtNumeroVol.clear();
        txtSurname.clear();
        txtConfirmPasswd.clear();
        txtPasswd.clear();
        txtPhoneNumber.clear();
    }

    private void mostrarError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleCancelAction(ActionEvent event) {
        LOGGER.info("Carga del FXML de ventana SignINn ");
        Alert ventanita = new Alert(Alert.AlertType.CONFIRMATION);
        ventanita.setHeaderText(null);
        ventanita.setTitle("Advertencia");
        ventanita.setContentText("¿Deseas Salir?");
        Optional<ButtonType> action = ventanita.showAndWait();

        if (action.orElse(ButtonType.CANCEL) == ButtonType.OK) {
            // Si el usuario hace clic en "OK", cierra la ventana actual
            Stage currentStage = (Stage) btnCancel.getScene().getWindow();
            currentStage.close();
        } else {
            // Si el usuario hace clic en "Cancelar", consume el evento
            event.consume();
        }
    }

    private boolean validar() {
        // Validación de campos vacíos o nulos
        if (txtEmail.getText().isEmpty() || txtDNI.getText().isEmpty() || txtName.getText().isEmpty()
                || txtSurname.getText().isEmpty() || txtPhoneNumber.getText().isEmpty()
                || txtNumeroVol.getText().isEmpty() || txtPasswd.getText().isEmpty()
                || txtConfirmPasswd.getText().isEmpty()) {
            mostrarError("Campo Vacío", "Por favor, completa todos los campos obligatorios.");
            return false;
        }

        // Validación de coincidencia de contraseñas
        String password = txtPasswd.getText();
        String confirmPassword = txtConfirmPasswd.getText();
        if (!password.equals(confirmPassword)) {
            mostrarError("Contraseñas no coinciden", "Las contraseñas no coinciden. Por favor, verifica.");
            return false;
        }

        // Validación de formato de números
        try {
            Integer.parseInt(txtPhoneNumber.getText());
            Integer.parseInt(txtNumeroVol.getText());
        } catch (NumberFormatException e) {
            mostrarError("Error de formato", "Por favor, introduce números válidos en los campos correspondientes.");
            return false;
        }

        return true; // La validación es exitosa
    }

}