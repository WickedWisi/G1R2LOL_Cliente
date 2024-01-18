/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

    private Stage stage = new Stage();

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
    private MenuItem mitEve;

    @FXML
    private MenuItem mitSede;

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
    private void miEve(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Evento.fxml"));

            Parent root = (Parent) loader.load();

            EventoController eventcontroller = ((EventoController) loader.getController());

            eventcontroller.setStage(stage);
            try {
                eventcontroller.initStage(root);
            } catch (WebApplicationException ex) {
                Logger.getLogger(MenuBarController.class.getName()).log(Level.SEVERE, null, ex);
            }

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

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignIn.fxml"));

            Parent root = (Parent) loader.load();

            //   SignInController signinController = ((SignInController) loader.getController());
            //   signinController.initiStage(root, user);
        } catch (IOException e) {

        }
    }

}
