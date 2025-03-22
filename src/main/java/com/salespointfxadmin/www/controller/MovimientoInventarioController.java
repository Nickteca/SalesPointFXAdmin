package com.salespointfxadmin.www.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.salespointfxadmin.www.component.SpringFXMLLoader;
import com.salespointfxadmin.www.enums.Naturaleza;
import com.salespointfxadmin.www.model.Folio;
import com.salespointfxadmin.www.model.MovimientoInventario;
import com.salespointfxadmin.www.model.Sucursal;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MovimientoInventarioController implements Initializable {
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
	private ChoiceBox<Folio> cBoxFolio;
	private ObservableList<Folio> olf;

	@FXML
	private TextField tFieldDescripcion;

	@FXML
	private TableColumn<MovimientoInventario, String> columnDescripcion;

	@FXML
	private TableColumn<MovimientoInventario, LocalDateTime> columnFecha;

	@FXML
	private TableColumn<MovimientoInventario, String> columnFolio;

	@FXML
	private TableColumn<MovimientoInventario, Integer> columnId;

	@FXML
	private TableColumn<MovimientoInventario, Naturaleza> columnTipoMovimiento;

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
		try {
			Folio f = cBoxFolio.getSelectionModel().getSelectedItem();
			List<MovimientoInventario> lmi = mis.findBySucursalAndCreatedAtBetweenAndNombreFolio(ss.getSucursalActive(), dPickerInicio.getValue().atStartOfDay(),
					dPicketFin.getValue().atTime(23, 59, 59), f.getNombreFolio());
			// for (MovimientoInventario movimientoInventario : lmi) {
			System.out.println(dPickerInicio.getValue().atStartOfDay() + " " + dPicketFin.getValue().atTime(23, 59, 59));
			// }
			if (lmi.isEmpty()) {
				throw new Exception("Esta vacia la lista");
			}
			olmi = FXCollections.observableArrayList(lmi);
			tViewMovimientoInventario.setItems(olmi);

		} catch (Exception e) {
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Error Movimienmto invetarii Controller!!!");
			error.setHeaderText("Buscando algo");
			error.setContentText(e.getMessage() + "\n" + e.getCause());
			error.show();
		}
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
			// stage.initStyle(StageStyle.UNDECORATED); // Ventana sin bordes
			stage.setTitle("Nuevo Movimiento de Inventario");
			stage.setScene(new Scene(root));
			stage.setResizable(false);
			stage.showAndWait();

			// Recargar la tabla después de que se cierre la ventana
			// iniciarTablaMovimientoInventario();

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
		//tFieldDescripcion.setText(null);
	}

	private void iniciarTablaMovimientoInventario() {
		/* SE INICIA TODO DE LOS PRODUCTO SUCURSAL */
		columnId.setCellValueFactory(new PropertyValueFactory<>("idMovimientoInventario"));
		columnId.prefWidthProperty().bind(tViewMovimientoInventario.widthProperty().multiply(0.1));

		columnDescripcion.setCellValueFactory(new PropertyValueFactory<>("decripcion"));
		columnDescripcion.prefWidthProperty().bind(tViewMovimientoInventario.widthProperty().multiply(0.3));

		columnFecha.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
		columnFecha.prefWidthProperty().bind(tViewMovimientoInventario.widthProperty().multiply(0.2));

		columnFolio.setCellValueFactory(new PropertyValueFactory<>("folio"));
		columnFolio.prefWidthProperty().bind(tViewMovimientoInventario.widthProperty().multiply(0.1));

		columnTipoMovimiento.setCellValueFactory(new PropertyValueFactory<>("naturaleza"));
		columnTipoMovimiento.prefWidthProperty().bind(tViewMovimientoInventario.widthProperty().multiply(0.2));

		columnSucursalDestino.setCellValueFactory(new PropertyValueFactory<>("sucursalDestino"));
		columnSucursalDestino.prefWidthProperty().bind(tViewMovimientoInventario.widthProperty().multiply(0.1));

		tViewMovimientoInventario.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) { // Doble clic
				MovimientoInventario mis = tViewMovimientoInventario.getSelectionModel().getSelectedItem();
				if (mis != null) {
					mostrarMovimiento(mis);
				}
			}
		});

	}

	private void iniciarChoiceBox() {
		olf = FXCollections.observableArrayList(fs.findBySucursal(ss.getSucursalActive()));
		cBoxFolio.setItems(olf);
		cBoxFolio.getSelectionModel().selectFirst();

	}

	private void mostrarMovimiento(MovimientoInventario mi) {
		try {
			FXMLLoader loader = springFXMLLoader.load("/fxml/movimientoinventariodetalle.fxml");
			AnchorPane root = loader.load();
			MovimientoInventarioDetalleController midc = loader.getController();
			midc.mostrarRegistro(mi);

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			// stage.initStyle(StageStyle.UNDECORATED); // Ventana sin bordes
			stage.setTitle("Nuevo Movimiento de Inventario");
			stage.setScene(new Scene(root));
			stage.setResizable(false);

			stage.showAndWait();

			// Recargar la tabla después de que se cierre la ventana
			// iniciarTablaMovimientoInventario();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
