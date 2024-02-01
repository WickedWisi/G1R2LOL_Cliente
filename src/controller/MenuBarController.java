/**
 * Controlador para la barra de menú principal de la aplicación.
 * Gestiona la navegación entre diferentes vistas y proporciona opciones como cerrar sesión y ayuda.
 * Se utiliza en conjunto con las vistas y controladores correspondientes para cada funcionalidad.
 *
 * @author josu
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.ws.rs.WebApplicationException;
import model.User;

public class MenuBarController {

    private Stage stage;
    private Scene scene;
    private User user;

    @FXML
    private Menu menPrin;

    @FXML
    private MenuBar mbLol;

    @FXML
    private Menu menNave;

    @FXML
    private Menu menAyud;

    @FXML
    private Menu menCerr;

    @FXML
    private MenuItem mitPatro;

    @FXML
    private MenuItem MitEve;

    @FXML
    private MenuItem mitPrincipal;

    @FXML
    private MenuItem mitSede;

    @FXML
    private MenuItem mitCerrarSesion;

    @FXML
    private MenuItem MitContrasena;

    private boolean cerrarSesionEjecutado = false;

    /**
     * Inicializa la barra de menú con las opciones y eventos correspondientes.
     *
     * @param url La URL no utilizada.
     * @param rb El ResourceBundle no utilizado.
     */
    public void initialize(URL url, ResourceBundle rb) {
        // Habilitación del menú
        menPrin.setDisable(false);
        menNave.setDisable(false);
        menAyud.setDisable(false);
        menCerr.setDisable(false);
        mitPatro.setDisable(false);
        MitEve.setDisable(false);
        mitSede.setDisable(false);
        MitContrasena.setDisable(false);
    }

    /**
     * Maneja el evento de selección del menú principal, abre la vista de Sede.
     *
     * @param event El evento de acción.
     */
    @FXML
    private void menPrin(ActionEvent event) {
        try {
            cerrarVentanaActual();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Sede.fxml"));
            Parent root = (Parent) loader.load();
            SedeController sedeController = ((SedeController) loader.getController());
            sedeController.initStage(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Maneja el evento de selección del menú de Eventos, abre la vista de
     * Evento.
     *
     * @param event El evento de acción.
     */
    @FXML
    private void mitEve(ActionEvent event) {
        try {
            cerrarVentanaActual();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Evento.fxml"));
            Parent root = (Parent) loader.load();
            EventoController eventoController = ((EventoController) loader.getController());
            eventoController.initStage(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Maneja el evento de selección del menú de Patrocinadores, abre la vista
     * de Patrocinador.
     *
     * @param event El evento de acción.
     */
    @FXML
    private void mitPatro(ActionEvent event) {
        try {
            cerrarVentanaActual();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Patrocinador.fxml"));
            Parent root = (Parent) loader.load();
            PatrocinadorController patrocinadorController = ((PatrocinadorController) loader.getController());
            patrocinadorController.initStage(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Maneja el evento de selección del menú de Sedes, abre la vista de Sede.
     *
     * @param event El evento de acción.
     */
    @FXML
    private void mitSede(ActionEvent event) {
        try {
            cerrarVentanaActual();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Sede.fxml"));
            Parent root = (Parent) loader.load();
            SedeController sedeController = ((SedeController) loader.getController());
            sedeController.initStage(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Maneja el evento de selección del menú de Cerrar Sesión. Cierra la sesión
     * actual y abre la ventana de inicio de sesión.
     *
     * @param event El evento de acción.
     */
    @FXML
    private void menCerr(ActionEvent event) {
        try {
            // Verificar si el método ya se ejecutó
            if (!cerrarSesionEjecutado) {
                cerrarSesionEjecutado = true;

                // Cerrar la ventana actual
                Stage currentStage = (Stage) mbLol.getScene().getWindow();
                currentStage.close();

                // Abrir la ventana de inicio de sesión
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignIn.fxml"));
                Parent root = (Parent) loader.load();
                SignInController signInController = ((SignInController) loader.getController());
                Stage signInStage = new Stage();
                signInController.setStage(signInStage);
                signInController.initStage(root);
                signInStage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
            // Maneja la excepción si es necesario
        }
    }

    /**
     * Maneja el evento de selección del menú de Ayuda. Abre la ventana de ayuda
     * correspondiente según el título de la ventana actual.
     *
     * @param event El evento de acción.
     */
    @FXML
    private void menAyud(ActionEvent event) {
        switch (((Stage) this.mbLol.getScene().getWindow()).getTitle()) {
            case "EVENTO":
                try {
                    Stage mainStage = new Stage();
                    URL viewLink = getClass().getResource("/view/HelpEvento.fxml");
                    FXMLLoader loader = new FXMLLoader(viewLink);
                    Parent root = (Parent) loader.load();
                    HelpEventController mainStageController = ((HelpEventController) loader.getController());
                    mainStageController.setStage(mainStage);
                    mainStageController.initStage(root);
                } catch (IOException ex) {
                    Logger.getLogger(EventoController.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
                break;
            case "SEDE":
                try {
                    Stage mainStage = new Stage();
                    URL viewLink = getClass().getResource("/view/HelpSede.fxml");
                    FXMLLoader loader = new FXMLLoader(viewLink);
                    Parent root = (Parent) loader.load();
                    HelpSedeController mainStageController = ((HelpSedeController) loader.getController());
                    mainStageController.setStage(mainStage);
                    mainStageController.initStage(root);
                } catch (IOException ex) {
                    Logger.getLogger(EventoController.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
                break;
            case "PATROCINADOR":
                try {
                    Stage mainStage = new Stage();
                    URL viewLink = getClass().getResource("/view/HelpPatrocinador.fxml");
                    FXMLLoader loader = new FXMLLoader(viewLink);
                    Parent root = (Parent) loader.load();
                    HelpPatrocinadorController mainStageController = ((HelpPatrocinadorController) loader.getController());
                    mainStageController.setStage(mainStage);
                    mainStageController.initStage(root);
                } catch (IOException ex) {
                    Logger.getLogger(EventoController.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
                break;

            default:
                // Cargar la ayuda predeterminada o mostrar un mensaje de error
                break;
        }
        // Resto de la lógica según la clase actual
    }

    /**
     * Maneja el evento de selección del menú de Cambiar Contraseña. Abre la
     * ventana de cambio de contraseña.
     *
     * @param event El evento de acción.
     */
    @FXML
    private void mitContrasena(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/cambiarContra.fxml"));
            Parent root = (Parent) loader.load();
            CambiarContrasenaController controller
                    = ((CambiarContrasenaController) loader.getController());
            controller.setStage(stage);
            controller.initStage(root);

        } catch (Exception e) {
            e.getMessage();
        }
    }

  /**
     * Establece la escena actual.
     *
     * @param scene La escena actual.
     */
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * Cierra la ventana actual.
     */
    private void cerrarVentanaActual() {
        // Obtener el Stage actual desde cualquier nodo en la escena
        Stage stage = (Stage) mbLol.getScene().getWindow();

        // Cerrar la ventana actual
        stage.close();
    }
}
