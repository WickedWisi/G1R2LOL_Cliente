/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.UserManagerFactory;
import logic.VoluntarioManagerFactory;
import model.User;
import model.UserSesionType;
import model.UserType;
import model.Voluntario;

/**
 *
 * @author Eneko.
 */
public class SignInController {

    private UserManagerFactory userfact = new UserManagerFactory();
    @FXML
    private Stage stage;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField pswfPasswd;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnSignUp;

    private UserType loggedInUserType;

    public void initStage(javafx.scene.Parent root) {
        javafx.scene.Scene scene = new javafx.scene.Scene(root);
        stage.setScene(scene);

        stage.setOnCloseRequest(this::handleExitButtonAction);
        btnSignUp.setOnAction(this::handleSignUpAction);
        btnLogin.setOnAction(this::handleSignInAction);
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

    private void handleSignInAction(ActionEvent event) {
        try {
            String email = txtEmail.getText();
            String password = pswfPasswd.getText();
            // Lógica de inicio de sesión, reemplázala con tu propia implementación
            // User user = userfact.getFactory().findUserByEmailAndPasswd_XML(User.class, email, password);
            //User user = userfact.getFactory().findUserByEmailAndPasswd_XML(email, password);
            List<User> userList = userfact.getFactory().findUserByEmailAndPasswd_XML(email, password);

            if (userList != null) {
                loggedInUserType = userList.get(0).getUserType();
                UserSesionType miTipoSesion = UserSesionType.getInstance();
                miTipoSesion.setTipoSesion(loggedInUserType);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Sede.fxml"));
                Parent root = (Parent) loader.load();
                SedeController controller = ((SedeController) loader.getController());

                // Pasa el tipo de usuario al controlador de Sede
                controller.setLoggedInUserType(loggedInUserType);

                controller.setStage(stage);
                controller.initStage(root);
            } else {
                mostrarError("Error de inicio de sesión", "Credenciales incorrectas. Por favor, inténtalo de nuevo.");
            }
        } catch (NullPointerException e) {
            e.printStackTrace(); // Imprimir la traza de la excepción
            // Manejar el error de acuerdo a tus necesidades
        } catch (IOException ex) {
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Método auxiliar para mostrar un cuadro de diálogo de error
    private void mostrarError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void handleSignUpAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignUp.fxml"));
            Parent root = (Parent) loader.load();
            SignUpController controller = ((SignUpController) loader.getController());
            controller.setStage(stage);
            controller.initStage(root);

            /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignUp.fxml"));
            javafx.scene.Parent root = loader.load();

            SignUpController signUpController = loader.getController();
            signUpController.setStage(new Stage());

            Scene signUpScene = new Scene(root);
            signUpController.getStage().setScene(signUpScene);
            signUpController.getStage().show();
             */
        } catch (Exception e) {

        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
