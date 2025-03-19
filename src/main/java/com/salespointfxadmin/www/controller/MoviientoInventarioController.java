package com.salespointfxadmin.www.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.salespointfxadmin.www.component.SpringFXMLLoader;
import com.salespointfxadmin.www.model.Folio;
import com.salespointfxadmin.www.model.MovimientoCaja.TipoMovimiento;
import com.salespointfxadmin.www.model.MovimientoInventario;
import com.salespointfxadmin.www.model.Sucursal;
import com.salespointfxadmin.www.repositorie.FolioRepo;
import com.salespointfxadmin.www.repositorie.MovimientoInventarioRepo;
import com.salespointfxadmin.www.service.FolioService;
import com.salespointfxadmin.www.service.MovimientoInventarioService;
import com.salespointfxadmin.www.service.SucursalService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
public class MoviientoInventarioController implements Initializable {
	private final SucursalService ss;
	private final MovimientoInventarioService mis;
	private final FolioService fs;
	private final SpringFXMLLoader springFXMLLoader;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnNuevo;

    @FXML
    private ChoiceBox<Folio> cBoxSelecionar;
    private ObservableList<Folio> olf;

    @FXML
    private TableColumn<MovimientoInventario, String> columnDescripcion;

    @FXML
    private TableColumn<MovimientoInventario, LocalDateTime> columnFecha;

    @FXML
    private TableColumn<MovimientoInventario, String> columnFolio;

    @FXML
    private TableColumn<MovimientoInventario, Integer> columnId;

    @FXML
    private TableColumn<MovimientoInventario, TipoMovimiento> columnTipoMovimiento;

    @FXML
    private TableColumn<MovimientoInventario, Sucursal> columnSucursalDestino;

    @FXML
    private DatePicker dPickerInicio;

    @FXML
    private DatePicker dPicketFin;

    @FXML
    private TableView<MovimientoInventario> tViewMovimientoInventario;
    private ObservableList<MovimientoInventario> olmi;

    @FXML
    void buscar(ActionEvent event) {

    }

    @FXML
    void cancelar(ActionEvent event) {
    	
    }

    @FXML
    void nuevo(ActionEvent event) {
    	try {
            FXMLLoader loader = springFXMLLoader.load("/fxml/movimientoinventariodetalle.fxml");
            AnchorPane root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED); // Ventana sin bordes
            stage.setTitle("Nuevo Movimiento de Inventario");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait();
            
            // Recargar la tabla después de que se cierre la ventana
            iniciarTablaMovimientoInventario();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dPickerInicio.setValue(LocalDate.now());
		dPicketFin.setValue(LocalDate.now());
		iniciarTablaMovimientoInventario();
		iniciarChoiceBox();
	}
	
	private void iniciarTablaMovimientoInventario() {
		/* SE INICIA TODO DE LOS PRODUCTO SUCURSAL */
		columnId.setCellValueFactory(new PropertyValueFactory<>("idMovimientoInventario"));
		columnId.prefWidthProperty().bind(tViewMovimientoInventario.widthProperty().multiply(0.1));

		columnDescripcion.setCellValueFactory(new PropertyValueFactory<>("decripcion"));
		columnDescripcion.prefWidthProperty().bind(tViewMovimientoInventario.widthProperty().multiply(0.3));

		columnFecha.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
		columnFecha.prefWidthProperty().bind(tViewMovimientoInventario.widthProperty().multiply(0.1));

		columnFolio.setCellValueFactory(new PropertyValueFactory<>("folio"));
		columnFolio.prefWidthProperty().bind(tViewMovimientoInventario.widthProperty().multiply(0.2));

		columnTipoMovimiento.setCellValueFactory(new PropertyValueFactory<>("tipoMovimiento"));
		columnTipoMovimiento.prefWidthProperty().bind(tViewMovimientoInventario.widthProperty().multiply(0.15));

		columnSucursalDestino.setCellValueFactory(new PropertyValueFactory<>("sucursalDestino"));
		columnSucursalDestino.prefWidthProperty().bind(tViewMovimientoInventario.widthProperty().multiply(0.15));
		
		olmi = FXCollections.observableArrayList(mis.findByCreatedAtBetween(LocalDateTime.now(), LocalDateTime.now()));
		tViewMovimientoInventario.setItems(olmi);
	}
	
	private void iniciarChoiceBox() {
		olf= FXCollections.observableArrayList(fs.findBySucursal(ss.getSucursalActive()));
		cBoxSelecionar.setItems(olf);
		cBoxSelecionar.getSelectionModel().selectFirst();
	}

}
