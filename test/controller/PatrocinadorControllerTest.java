package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import model.Evento;
import model.Patrocinador;
import model.UserType;
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
 * @author 2dam
 */
public class PatrocinadorControllerTest extends ApplicationTest {

    private TableView<Patrocinador> tbPatrocinador;

    private Patrocinador patrocin;

    private Button btnInsertar, btnEditar, btnEliminar, btnInforme, btnBuscar, btnLogin;

    private TableColumn tbColNombre, tbColDescripcion, tbColEmail, tbColTelefono, tbColDuracion;

    private TextField txtEmail, tfNombre, tfTelefono, tfEmail, tfBusqueda;

    private PasswordField pswfPasswd;

    private TextArea taDescripcion;

    private Menu menNave;

    private MenuItem MitPatro;

    private Pane panePatrocinador;

    private DatePicker dpDuracion;

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
        tbPatrocinador = lookup("#tbPatrocinador").query();
        Node menNave = lookup("#menNave").nth(1).query();
        Node MitPatro = lookup("#MitPatro").nth(1).query();
        dpDuracion = lookup("#dpDuracion").query();
        txtEmail = lookup("#txtEmail").query();
        tfNombre = lookup("#tfNombre").query();
        taDescripcion = lookup("#taDescripcion").query();
        tfEmail = lookup("#tfEmail").query();
        pswfPasswd = lookup("#pswfPasswd").query();
        tfTelefono = lookup("#tfTelefono").query();
        tfBusqueda = lookup("#tfBusqueda").query();
        panePatrocinador = lookup("#panePatrocinador").query();

    }

    @Test
    public void test1_InicioVentana() {
        //Inicio sesión
        clickOn(txtEmail);
        write("josuarr@gmail.com");
        clickOn(pswfPasswd);
        write("abcd*1234");
        clickOn(btnLogin);

        //Accedo a mi ventana desde la ventana sede
        clickOn("#menNave");
        clickOn("#MitPatro");
        verifyThat("#panePatrocinador", isVisible());
    }

    @Test
    public void test2_CrearPatrocinador() {
        int rowCount = tbPatrocinador.getItems().size();
        String nombrePatrocinador = tfNombre.getText();
        clickOn(nombrePatrocinador);
        write("Grefusa");
        clickOn(tfEmail);
        write("pipa@mail.com");
        clickOn(taDescripcion);
        write("Pipa tijuana esta mas buena que tu hermana");
        clickOn(tfTelefono);
        write("123456789");
        clickOn(dpDuracion);
        write("2024-02-03");
        clickOn(btnInsertar);
        clickOn("Aceptar");

        assertEquals("PATROCINADOR CREADO CON EXITO", rowCount + 1, tbPatrocinador.getItems().size());
        // Buscar el patrocinador en el modelo de datos de la tabla
        List<Patrocinador> patrocinadores = tbPatrocinador.getItems();
        assertEquals("PATROCINADOR CREADO CON EXITO",
                patrocinadores.stream().filter(p -> p.getNombre().equals("Grefusa")).count(), 1);
    }
    @Ignore
    @Test
    public void test3_ModificarPatrocinador() {
        clickOn(tbPatrocinador).clickOn("Grefusa");
        clickOn(tfNombre);
        tfNombre.clear();
        write("Facundo");
        clickOn(taDescripcion);
        taDescripcion.clear();
        write("Pipas facundo, las pipas de todo el mundo");
        clickOn(btnEditar);
        clickOn("Aceptar");

        List<Patrocinador> patrocinadores = tbPatrocinador.getItems();
        assertEquals("PATROCINADOR MODIFICADO CON EXITO",
                patrocinadores.stream().filter(p -> p.getNombre().equals("Facundo")).count(), 1);
    }
    @Ignore
    @Test
    public void test4_filtroEventoAforo() {

        clickOn(tfBusqueda);
        write("Facundo");
        clickOn(btnBuscar);

        List<Patrocinador> patrocinador = tbPatrocinador.getItems();
        assertEquals(patrocinador.stream().filter(u -> u.getEmail().equals("pipa@mail.com")).count(), 0);
        assertNotEquals(patrocinador.stream().filter(u -> u.getEmail().equals("grefusa@mail.com")).count(), 1);

    }
    @Ignore
    @Test
    public void test5_BorrarPatrocinador() {
        int rowCount = tbPatrocinador.getItems().size();
        clickOn(tbPatrocinador).clickOn("Facundo");
        clickOn(btnEliminar);
        clickOn("Aceptar");

        List<Patrocinador> patrocinadores = tbPatrocinador.getItems();
        assertEquals("EL PATROCINADOR SE HA ELIMINADO CORRECTAMENTE", rowCount - 1, tbPatrocinador.getItems().size());
    }


    

}
