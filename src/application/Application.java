/**
 * Clase principal de la aplicación que extiende la clase javafx.application.Application.
 * Se encarga de iniciar la aplicación y cargar la ventana de inicio (SignIn).
 *
 * @author Eneko, Josu, Egoitz
 */
package application;

import controller.SedeController;
import controller.SignInController;
import controller.SignUpController;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * Clase principal que inicia la aplicación y carga la ventana de inicio.
 */
public class Application extends javafx.application.Application {

    /**
     * Logger para la clase Application.
     */
    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());

    /**
     * Método principal que inicia la aplicación y carga la ventana de inicio (SignIn).
     *
     * @param stage El objeto Stage principal de la aplicación.
     * @throws Exception Posibles excepciones durante la carga del FXML.
     */
    @Override
    public void start(Stage stage) throws Exception {
        LOGGER.info("Carga del FXML de ventana SignIn ");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignIn.fxml"));
        Parent root = (Parent) loader.load();
        LOGGER.info("Llamada al controlador del FXML");
        SignInController controller = ((SignInController) loader.getController());
        controller.setStage(stage);
        controller.initStage(root);
    }

    /**
     * Método principal que inicia la aplicación.
     *
     * @param args Los argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
