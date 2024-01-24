/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import controller.PatrocinadorController;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author 2dam
 */
public class Application extends javafx.application.Application {

    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());

    @Override
    public void start(Stage stage) throws Exception {

        LOGGER.info("Carga del FXML de ventana Patrocinador provisional ");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Patrocinador.fxml"));
        Parent root = (Parent) loader.load();
        LOGGER.info("Llamada al controlador del FXML");
        PatrocinadorController controller = ((PatrocinadorController) loader.getController());
        controller.setStage(stage);
        controller.initStage(root);

    }

    
    public static void main(String[] args) {
        launch(args);
    }

}