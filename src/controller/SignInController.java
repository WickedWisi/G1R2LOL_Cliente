/**
 * Controlador para la ventana de inicio de sesión (SIGN IN).
 */
package controller;

import cipher.AsimetricC;
import exception.UserNotFoundException;
import java.io.IOException;
import java.security.PublicKey;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.security.auth.login.CredentialException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import logic.UserManagerFactory;
import logic.VoluntarioManagerFactory;
import model.User;
import model.UserSesionType;
import model.UserType;
import model.Voluntario;

/**
 * Controlador para la ventana de inicio de sesión (SIGN IN).
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
    @FXML
    private Hyperlink hpPass;

    private User userFull;

    /**
     * Inicializa la escena.
     *
     * @param root La raíz del componente de la escena.
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("SIGN IN");
        stage.setOnCloseRequest(this::handleExitButtonAction);
        btnSignUp.setOnAction(this::handleSignUpAction);
        btnLogin.setOnAction(this::handleSignInAction);
        hpPass.setOnAction(this::handleRecuperarContra);
        stage.setResizable(false);

        stage.show();
    }

    /**
     * Maneja el evento de cierre de la ventana.
     *
     * @param event El evento de ventana.
     */
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

    /**
     * Maneja el evento de inicio de sesión.
     *
     * @param event El evento de acción.
     */
    private void handleSignInAction(ActionEvent event) {
        try {
            AsimetricC asimetric = new AsimetricC();
            PublicKey publicKey;
            publicKey = asimetric.loadPublicKey();
            User user = new User();
            user.setPasswd(pswfPasswd.getText());
            String contraHex = javax.xml.bind.DatatypeConverter.printHexBinary(asimetric.encryptAndSaveData(pswfPasswd.getText(), publicKey));
            String email = txtEmail.getText();

            List<User> userList = userfact.getFactory().findUserByEmailAndPasswd_XML(email, contraHex);

            if (!userList.isEmpty()) {
                userFull = userList.get(0);
                UserSesionType miTipoSesion = UserSesionType.getInstance();
                miTipoSesion.setTipoSesion(userFull);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Sede.fxml"));
                Parent root = (Parent) loader.load();
                SedeController controller = ((SedeController) loader.getController());

                controller.setStage(stage);
                controller.initStage(root);
                stage.close();
            } else {
                throw new UserNotFoundException();
            }
        } catch (ProcessingException e2) {
            mostrarErrorConexionNoDisponible();
        } catch (UserNotFoundException ex) {
            new Alert(Alert.AlertType.ERROR, "Las credenciales proporcionadas no son correctas. Por favor, verifica tu email y contraseña e inténtalo nuevamente.").showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Muestra una alerta de error cuando la conexión no está disponible.
     */
    private void mostrarErrorConexionNoDisponible() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de conexión");
        alert.setHeaderText(null);
        alert.setContentText("La conexión al servidor no está disponible en este momento. Inténtelo nuevamente más tarde.");
        alert.showAndWait();
    }

    /**
     * Maneja el evento de registro.
     *
     * @param event El evento de acción.
     */
    @FXML
    private void handleSignUpAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignUp.fxml"));
            Parent root = (Parent) loader.load();
            SignUpController controller = ((SignUpController) loader.getController());
            controller.setStage(stage);
            controller.initStage(root);
        } catch (Exception e) {
            e.getMessage();
        }
    }
/**
     * Maneja el evento de recuperación de contraseña.
     *
     * @param event El evento de acción.
     */
    private void handleRecuperarContra(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/recuperarContrasena.fxml"));
            Parent root = (Parent) loader.load();
            RecuperarContrasenaController controller = ((RecuperarContrasenaController) loader.getController());
            controller.setStage(stage);
            controller.initStage(root);
        }  catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * Establece el escenario de la aplicación.
     *
     * @param stage El escenario principal de la aplicación.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
