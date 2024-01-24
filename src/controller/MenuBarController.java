/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

/**
 *
 * @author 2dam
 */
public class MenuBarController {

    // private Stage stage = new Stage();
    private Stage stage;

    private Scene scene;

    private User user;

    // private EventoController ventanaActualController;
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
    private MenuItem mitSede;

    @FXML
    private MenuItem mitCerrarSesion;
    
    private boolean cerrarSesionEjecutado = false;

    public void initialize(URL url, ResourceBundle rb) {
        //Habilitación del menu
        menPrin.setDisable(false);
        menNave.setDisable(false);
        menAyud.setDisable(false);
        menCerr.setDisable(false);
        mitPatro.setDisable(false);
        MitEve.setDisable(false);
        mitSede.setDisable(false);

        /*
        //Meotdos de los menús y menúbars
        menPrin.setOnAction(this::menPrin);
        // menAyud.setOnAction(this::menAyud);
        menCerr.setOnAction(this::menCerr);
        mitPatro.setOnAction(this::mitPatro);
        MitEve.setOnAction(this::mitEve);
        mitSede.setOnAction(this::mitSede);
         */
    }

    @FXML
    private void menPrin(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Sede.fxml"));

            Parent root = (Parent) loader.load();

            //SedeController sedecontroller = ((SedeController) loader.getController());
            // sedecontroller.setStage(stage);
            try {
                //  sedecontroller.initStage(root);
            } catch (WebApplicationException ex) {
                Logger.getLogger(MenuBarController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (IOException e) {

        }
    }

    @FXML
    private void mitEve(ActionEvent event) {

        try {

            cerrarVentanaActual();
            // Al abrir una nueva ventana, actualiza ventanaActualController
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Evento.fxml"));
            Parent root = (Parent) loader.load();
            EventoController eventoController = ((EventoController) loader.getController());
            eventoController.initStage(root);

        } catch (IOException e) {

        }

    }

    @FXML
    private void mitPatro(ActionEvent event) {

        /*
        try {
            cerrarVentanaActual();
            // Al abrir una nueva ventana, actualiza ventanaActualController
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Patrocinador.fxml"));
            Parent root = (Parent) loader.load();
            PatrocinadorController patrocinadorController = ((PatrocinadorController) loader.getController());
            patrocinadorController.initStage(root);

        } catch (IOException e) {

        }
         */
    }

    @FXML
    private void mitSede(ActionEvent event) {

        try {
            cerrarVentanaActual();
            // Al abrir una nueva ventana, actualiza ventanaActualController
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Sede.fxml"));
            Parent root = (Parent) loader.load();
            SedeController sedeController = ((SedeController) loader.getController());
            sedeController.initStage(root);

        } catch (IOException e) {

        }
    }

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

    @FXML
    private void menAyud(ActionEvent event) {

        switch (((Stage) this.mbLol.getScene().getWindow()).getTitle()) {
            case "EVENTO":
                try {
                    Stage mainStage = new Stage();
                    URL viewLink = getClass().getResource("/view/HelpEvento.fxml");
                    // initialition loader
                    FXMLLoader loader = new FXMLLoader(viewLink);
                    //make the root with the loader
                    Parent root = (Parent) loader.load();
                    //Get the controller
                    HelpEventController mainStageController = ((HelpEventController) loader.getController());
                    //set the stage
                    mainStageController.setStage(mainStage);
                    //start the stage
                    mainStageController.initStage(root);
                    //this.stage.close();
                } catch (IOException ex) {
                    Logger.getLogger(EventoController.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
                break;
            case "SEDE":
                try {
                    Stage mainStage = new Stage();
                    URL viewLink = getClass().getResource("/view/HelpSede.fxml");
                    // initialition loader
                    FXMLLoader loader = new FXMLLoader(viewLink);
                    //make the root with the loader
                    Parent root = (Parent) loader.load();
                    //Get the controller
                    HelpSedeController mainStageController = ((HelpSedeController) loader.getController());
                    //set the stage
                    mainStageController.setStage(mainStage);
                    //start the stage
                    mainStageController.initStage(root);
                    //this.stage.close();
                } catch (IOException ex) {
                    Logger.getLogger(EventoController.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
                break;
            case "PATROCINADOR":
                System.out.println("AYUDA DE PATROCINADOR");
                break;
            // Agrega más casos según sea necesario
            default:
                // Cargar la ayuda predeterminada o mostrar un mensaje de error
                break;
        }

        // Resto de la lógica según la clase actual
    }

    // Otros métodos y atributos
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    private void cerrarVentanaActual() {
        // Obtener el Stage actual desde cualquier nodo en la escena
        Stage stage = (Stage) mbLol.getScene().getWindow();

        // Cerrar la ventana actual
        stage.close();
    }

}
