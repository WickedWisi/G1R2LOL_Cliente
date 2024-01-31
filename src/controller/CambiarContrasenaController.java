/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import cipher.AsimetricC;
import java.security.PublicKey;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import logic.VoluntarioManagerFactory;
import model.User;
import model.UserSesionType;
import model.UserType;
import model.Voluntario;

/**
 *
 * @author 2dam
 */
public class CambiarContrasenaController {

    @FXML
    private Stage stage;

    @FXML
    private Button btnCambiarContrasena;

    @FXML
    private PasswordField pswfPass;

    @FXML
    private PasswordField pswfNewPass;

    @FXML
    private PasswordField pswfReNewPass;

    private User user;
    
    UserSesionType miTipoSesion = UserSesionType.getInstance();

    User tipo = miTipoSesion.getTipoSesion();

    private VoluntarioManagerFactory vmf = new VoluntarioManagerFactory();

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setTitle("Cambiar contraseña");
        stage.setScene(scene);
        btnCambiarContrasena.setOnAction(this::handleCambiarContrasena);

        stage.show();
    }

    private void handleCambiarContrasena(ActionEvent event) {

        AsimetricC asimetric = new AsimetricC();
        PublicKey publicKey;
        publicKey = asimetric.loadPublicKey();
        String contraHex = javax.xml.bind.DatatypeConverter.printHexBinary(asimetric.encryptAndSaveData(pswfNewPass.getText(), publicKey));
        Voluntario volun = vmf.getFactory().find_XML(Voluntario.class, tipo.getId_user().toString());
        Voluntario volunt = new Voluntario();
        System.out.println(tipo.toString());
        volunt.setPasswd(contraHex);
        volunt.setId_user(tipo.getId_user());
        volunt.setNombre(tipo.getNombre());
        volunt.setApellido(tipo.getApellido());
        volunt.setDNI(tipo.getDNI());
        volunt.setTelefono(tipo.getTelefono());
        volunt.setConfirmPasswd(tipo.getConfirmPasswd());
        volunt.setTelefono(tipo.getTelefono());
        volunt.setEmail(tipo.getEmail());

        vmf.getFactory().cambiarContra_XML(volunt);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(" Cambiar contraseña");
        alert.setHeaderText(null);
        alert.setContentText("Has cambiado la contraseña correctamente ");
        alert.showAndWait();

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
