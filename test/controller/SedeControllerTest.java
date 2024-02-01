/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static org.hibernate.criterion.Projections.rowCount;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Sede;
import model.User;
import model.UserSesionType;
import model.UserType;
import org.apache.lucene.search.FilteredQuery;
import static org.hibernate.criterion.Projections.rowCount;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 *
 * @author Eneko.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SedeControllerTest extends ApplicationTest {

    private Stage stage;

    private TextField tPais;

    private TextField tAforoMax;

    private TextField tNumVolMax;

    private TextField tUbicacion;

    private DatePicker tFinDeContrato;

    private Button bInsert;

    private Button bEditar;

    private Button bEliminar;

    private TextField fPais;

    private TextField fAforoMax;

    private Button bBuscar;

    private Button informe;

    private TableView<Sede> tabla;

    private TableColumn<Sede, String> tblPais;

    private TableColumn<Sede, Integer> tblAforoMax;

    private TableColumn<Sede, Integer> tblNumVolMax;

    private TableColumn<Sede, String> tblUbicacion;

    private TableColumn<Sede, Date> tblFinDeContrato;

    private MenuBar menuBar;

    private Menu menuP;

    private Menu menuN;

    private Menu menuA;

    private Menu menuC;

    private MenuItem mVerEventos;

    private MenuItem menuSede;

    private MenuItem menuPatrocinador;

    private ContextMenu cMenu;

    private MenuItem mBorrarSede;

    private Pane panelSede;

    private TextField txtEmail;

    private PasswordField pswfPasswd;

    private Button btnLogin;

    private Button btnSignUp;

    @BeforeClass
    public static void openWindow() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(application.Application.class);
    }

    @Before
    public void getFields() {

        bBuscar = lookup("#bBuscar").query();
        bInsert = lookup("#bInsert").query();
        bEditar = lookup("#bEditar").query();
        bEliminar = lookup("#bEliminar").query();
        informe = lookup("#informe").query();
        btnLogin = lookup("#btnLogin").query();
        tabla = lookup("#tabla").query();
        txtEmail = lookup("#txtEmail").query();
        pswfPasswd = lookup("#pswfPasswd").query();

        tPais = lookup("#tPais").query();
        tAforoMax = lookup("#tAforoMax").query();
        tNumVolMax = lookup("#tNumVolMax").query();
        tUbicacion = lookup("#tUbicacion").query();
        tFinDeContrato = lookup("#tFinDeContrato").query();

        fPais = lookup("#fPais").query();
        fAforoMax = lookup("#fAforoMax").query();
        panelSede = lookup("#panelSede").query();
    }

    @Test
    public void test1_InicioVentana() {
        //Inicio sesión
        clickOn(txtEmail);
        write("josuarroyo@gmail.com");
        clickOn(pswfPasswd);
        write("abcd*1234");
        clickOn(btnLogin);

        //Accedo a mi ventana desde la ventana principal
        verifyThat("#panelSede", isVisible());
    }

    @Ignore
    public void test2_insertSede() {
        int rowCount = tabla.getItems().size();
        clickOn(tPais);
        write("Ecuador");
        clickOn(tAforoMax);
        write("300");
        clickOn(tNumVolMax);
        write("190");
        clickOn(tUbicacion);
        write("Cuenca");
        clickOn(tFinDeContrato);
        write("3/04/2025");
        clickOn(bInsert);
        assertEquals("SEDE CREADA CON EXITO!!!!!", rowCount + 1, tabla.getItems().size());
        clickOn("Aceptar");
        //look for user in table data model
        List<Sede> sede = tabla.getItems();
        assertEquals("Ha habido un fallo en la creacion de la sede",
                sede.stream().filter(u -> u.getPais().equals("Ecuador")).count(), 1);
    }

    @Ignore
    public void test3_ModificarSede() {

        clickOn(tabla).clickOn("Ecuador");
        clickOn(tPais);
        tPais.clear();
        write("Mozambique");
        clickOn(tAforoMax);
        tAforoMax.clear();
        write("500");
        clickOn(bEditar);
        clickOn("Aceptar");
        List<Sede> sede = tabla.getItems();
        assertEquals("Sede modificada con exito",
                sede.stream().filter(u -> u.getPais().equals("Mozambique")).count(), 1);

    }

    @Ignore
    public void test4_deleteSede() {
        int rowCount = tabla.getItems().size();
        clickOn(tabla).clickOn("Mozambique");
        clickOn(bEliminar);
        clickOn("Aceptar");
        List<Sede> sede = tabla.getItems();
        assertEquals("La sede se ha eliminado correctamente", rowCount - 1, sede.size());
        clickOn("Aceptar");

    }

    @Ignore
    public void test5_tipoUsuarios() {
        UserSesionType miTipoSesion = UserSesionType.getInstance();
        User tipo = miTipoSesion.getTipoSesion();

        if (tipo.getUserType() == UserType.VOLUNTARIO) {
            assertTrue(tPais.isDisabled());
            assertTrue(tAforoMax.isDisabled());
            assertTrue(tNumVolMax.isDisabled());
            assertTrue(tUbicacion.isDisabled());
            assertTrue(tFinDeContrato.isDisabled());
            assertTrue(bInsert.isDisabled());
            assertTrue(bEditar.isDisabled());
            assertTrue(bEliminar.isDisabled());
        }

    }

    @Ignore
    public void test6_filtroPais() {
        int rowCount = tabla.getItems().size();
        clickOn(fPais);
        write("Alemania");
        clickOn(bBuscar);
        List<Sede> sede = tabla.getItems();

        // Compara el valor obtenido con el valor esperado (en este caso, 200)
        assertEquals(sede.stream().filter(u -> u.getPais().equals("Alemania")).count(), 1);
        assertNotEquals(sede.stream().filter(u -> u.getPais().equals("España")).count(), 1);
    }

}
