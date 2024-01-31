/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import application.Application;
import java.util.List;
import java.util.concurrent.TimeoutException;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import model.Evento;
import model.User;
import model.UserSesionType;
import model.UserType;
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
 * @author 2dam
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EventoControllerTest extends ApplicationTest {

    private TableView<Evento> tbvEvento;

    private Evento evento;

    private Button btnInsertar, btnEditar, btnEliminar, btnInforme, btnBuscar, btnLogin;

    private TableColumn tbcNombre, tbcDescripcion, tbcAforo, tbcCatering, tbcFechaEvento;

    private TextField tfNombre, tfAforo, txtEmail, tfFiltro;

    private PasswordField pswfPasswd;

    private TextArea taDescripcion;

    private Menu menNave;

    private MenuItem MitEve;

    private Pane paneEvento;

    private DatePicker dpFechaEvento;

    private CheckBox cbCatering;

    @BeforeClass
    public static void openWindow() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Application.class);
    }

    @Before
    public void getFields() {

        btnInsertar = lookup("#btnInsertar").query();
        btnEliminar = lookup("#btnEliminar").query();
        btnInforme = lookup("#btnInforme").query();
        btnLogin = lookup("#btnLogin").query();
        btnEditar = lookup("#btnEditar").query();
        btnBuscar = lookup("#btnBuscar").query();
        tbvEvento = lookup("#tbvEvento").query();
        Node menNave = lookup("#menNave").nth(1).query();
        Node MitEve = lookup("#MitEve").nth(1).query();
        dpFechaEvento = lookup("#dpFechaEvento").query();

        tfNombre = lookup("#tfNombre").query();
        taDescripcion = lookup("#taDescripcion").query();
        txtEmail = lookup("#txtEmail").query();
        pswfPasswd = lookup("#pswfPasswd").query();
        tfAforo = lookup("#tfAforo").query();
        tfFiltro = lookup("#tfFiltro").query();
        paneEvento = lookup("#paneEvento").query();
        cbCatering = lookup("#cbCatering").query();
    }

    @Test
    public void test1_InicioVentana() {
        //Inicio sesión
        clickOn(txtEmail);
        write("josuarr@gmail.com");
        clickOn(pswfPasswd);
        write("abcd*1234");
        clickOn(btnLogin);

        //Accedo a mi ventana desde la ventana principal
        clickOn("#menNave");
        clickOn("#MitEve");
        verifyThat("#paneEvento", isVisible());
    }

    @Test
    public void test2_CrearEvento() {

        int rowCount = tbvEvento.getItems().size();
        String NombreEvento = tfNombre.getText();
        clickOn(NombreEvento);
        write("KingsLeague");
        clickOn(taDescripcion);
        write("Liga de futbol con reglas inventadas");
        clickOn(tfAforo);
        write("24");
        //clickOn(cbCaterign);
        cbCatering.setSelected(true);
        clickOn(dpFechaEvento);
        write("3/02/2024");
        clickOn(btnInsertar);
        clickOn("Aceptar");

        assertEquals("EVENTO CREADO CON EXITO", rowCount + 1, tbvEvento.getItems().size());
        //look for event in table data model
        List<Evento> evento = tbvEvento.getItems();
        assertEquals("EVENTO CREADO CON EXITO",
                evento.stream().filter(u -> u.getNombre().equals("KingsLeague")).count(), 1);

    }

    @Test
    public void test3_ModificarEvento() {

        clickOn(tbvEvento).clickOn("KingsLeague");
        //int rowCount = tbvEvento.getItems().size();
        //String NombreEvento = tfNombre.getText();
        clickOn(tfNombre);
        tfNombre.clear();
        write("QueensLeague");
        clickOn(taDescripcion);
        taDescripcion.clear();
        write("reglas inventadas");
        clickOn(btnEditar);
        clickOn("Aceptar");
        // int selectedIndex=tbvEvento.getSelectionModel().getSelectedIndex();
        List<Evento> evento = tbvEvento.getItems();
        assertEquals("EVENTO MODIFICADO CON EXITO",
                evento.stream().filter(u -> u.getNombre().equals("QueensLeague")).count(), 1);

    }

    @Test
    public void test4_BorrarLugar() {
        int rowCount = tbvEvento.getItems().size();
        clickOn(tbvEvento).clickOn("QueensLeague");
        clickOn(btnEliminar);
        clickOn("Aceptar");
        List<Evento> evento = tbvEvento.getItems();
        assertEquals("EL EVENTO SE HA ELIMINADO CORRECTAMENTE", rowCount - 1, tbvEvento.getItems().size());

    }

    @Ignore
    public void test5_InicioVentana() {
        //Inicio sesión
        clickOn(txtEmail);
        write("empirico@gmail.com");
        clickOn(pswfPasswd);
        write("lol123");
        clickOn(btnLogin);

        //Accedo a mi ventana desde la ventana principal
        clickOn("#menNave");
        clickOn("#MitEve");
        verifyThat("#paneEvento", isVisible());
    }

    @Ignore
    public void test6_TipoUsuario() {

        UserSesionType miTipoSesion = UserSesionType.getInstance();
        User tipo = miTipoSesion.getTipoSesion();

        if (tipo.getUserType() == UserType.VOLUNTARIO) {
            assertTrue(tfNombre.isDisabled());
            assertTrue(taDescripcion.isDisabled());
            assertTrue(tfAforo.isDisabled());
            assertTrue(cbCatering.isDisabled());
            assertTrue(dpFechaEvento.isDisabled());
            assertTrue(btnInsertar.isDisabled());
            assertTrue(btnEditar.isDisabled());
            assertTrue(btnEliminar.isDisabled());

        }
    }

    @Ignore
    public void test7_filtroEventoAforo() {

        clickOn(tfFiltro);
        write("85");
        clickOn(btnBuscar);

        List<Evento> evento = tbvEvento.getItems();
        assertEquals(evento.stream().filter(u -> u.getNombre().equals("La Velado del año")).count(), 0);
        assertNotEquals(evento.stream().filter(u -> u.getNombre().equals("patatas")).count(), 1);

    }

}
