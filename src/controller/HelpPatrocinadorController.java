/**
 * Controlador para la ventana de ayuda de Patrocinador.
 * Muestra un manual en formato HTML en una WebView dentro de la interfaz gráfica.
 * Se inicializa con el método `initStage` y utiliza un archivo HTML ubicado en "/view/PatrocinadorHelp.html".
 *
 * @author josu,egoitz,eneko
 */
package controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class HelpPatrocinadorController {

    Stage stage = new Stage();

    @FXML
    private WebView webView1;

    /**
     * Inicializa la ventana de ayuda con el manual en formato HTML.
     *
     * @param root El nodo raíz de la escena.
     */
    public void initStage(Parent root) {
        // Inicializa la escena con el nodo raíz proporcionado por el controlador principal.
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setOnShowing(this::shoWindow);
        stage.setTitle("Manual de ventana de Patrocinador");
        stage.show();
    }

    /**
     * Carga el manual en formato HTML en la WebView al mostrar la ventana.
     *
     * @param event El evento de ventana asociado al mostrar la ventana.
     */
    @FXML
    private void shoWindow(WindowEvent event) {
        // Obtiene la instancia de WebEngine y carga el archivo HTML desde la ubicación especificada.
        WebEngine webEngine = webView1.getEngine();
        webEngine.load(getClass().getResource("/view/PatrocinadorHelp.html").toExternalForm());
    }

    /**
     * Establece el escenario para el controlador.
     *
     * @param stage El escenario principal.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
