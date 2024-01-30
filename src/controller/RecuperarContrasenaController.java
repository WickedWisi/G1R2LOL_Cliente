/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import exception.FormatErrorException;
import exception.UserNotFoundException;
import java.util.List;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.EventoManagerFactory;
import logic.UserManagerFactory;
import logic.VoluntarioManagerFactory;
import model.User;
import model.Voluntario;

/**
 *
 * @author josu
 */
public class RecuperarContrasenaController {

    @FXML
    private Stage stage;
    @FXML
    private Button btnEmail;
    @FXML
    private TextField txtEmailRecup;
    @FXML
    private static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    @FXML
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    
     private UserManagerFactory userfact = new UserManagerFactory();
     
     private VoluntarioManagerFactory voluntariofact = new VoluntarioManagerFactory();
     

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        btnEmail.setOnAction(this::handleEnviarEmail);

        stage.show();
    }

    private void handleEnviarEmail(ActionEvent event) {
        try {
            // Validar si el email contiene un dominio como (….@gmail.com).
            String email = this.txtEmailRecup.getText();

            if (!(EMAIL_PATTERN.matcher(email).matches())) {
                throw new FormatErrorException("Formato de E-mail no valido");
            }
            
            List<User> listUser;
            listUser= userfact.getFactory().findUserforEmail_XML(User.class, txtEmailRecup.getText());
            
            if (listUser.isEmpty()) {
                throw new UserNotFoundException();
            }
            Voluntario voluntario = new Voluntario();
            voluntario.setEmail(txtEmailRecup.getText());
            System.out.println(listUser.get(0).getId_user());
            Voluntario vol = voluntariofact.getFactory().find_XML(Voluntario.class, listUser.get(0).getId_user().toString());
            
            
            voluntario.setId_user(vol.getId_user());
            voluntario.setNombre(vol.getNombre());
            voluntario.setApellido(vol.getApellido());
            voluntario.setDNI(vol.getDNI());
            voluntario.setPasswd(vol.getPasswd());
            voluntario.setConfirmPasswd(vol.getConfirmPasswd());
            voluntario.setNumero_Voluntariados(vol.getNumero_Voluntariados());
            voluntario.setTelefono(vol.getTelefono());
            voluntario.setEmail(vol.getEmail());
           
            voluntariofact.getFactory().RecuperarContra_XML(voluntario);
            voluntariofact.getFactory().cambiarContra_XML(voluntario);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(" Recuperar contraseña");
            alert.setHeaderText(null);
            alert.setContentText("Te hemos enviado un correo con la nueva contraseña ");
            alert.showAndWait();
            
            
        } catch (Exception e) {
            e.getMessage();
        }

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
