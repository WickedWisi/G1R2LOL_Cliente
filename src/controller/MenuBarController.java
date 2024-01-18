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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javax.ws.rs.WebApplicationException;
import model.User;

/**
 *
 * @author 2dam
 */
public class MenuBarController {

    // private Stage stage = new Stage();
    private Stage stage;

    private User user;

    @FXML
    private Menu menPrin;

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
        /*
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Evento.fxml"));
            Parent root = loader.load();

            // Obtén el controlador de la nueva ventana
            EventoController eventoController = loader.getController();

            // Configura el controlador según sea necesario (por ejemplo, pasa datos o configura el escenario)
            eventoController.setStage(stage);
            eventoController.initStage(root);

        } catch (IOException e) {
            e.printStackTrace();
            // Agrega manejo de errores según sea necesario
        }
        */
         try {
            
           
                Stage EventoStage = new Stage();
                //cargar el fxml de la ventana de sign up utilizando un cargador no estatico
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Evento.fxml"));

                Parent root = (Parent) loader.load();

                EventoController eventoController = ((EventoController) loader.getController());

                eventoController.initStage(root);
                
            
        } catch (IOException e) {
            
        }
         

      
    }

    @FXML
    private void mitPatro(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Patrocinador.fxml"));

            Parent root = (Parent) loader.load();

            //   PatrocinadorController patrocinadorController = ((PatrocinadorController) loader.getController());
            //   patrocinadorController.initiStage(root, user);
        } catch (IOException e) {

        }
    }

    @FXML
    private void mitSede(ActionEvent event) {

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
    private void menCerr(ActionEvent event) {

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

}
