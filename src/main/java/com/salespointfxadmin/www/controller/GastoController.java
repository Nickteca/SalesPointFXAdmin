package com.salespointfxadmin.www.controller;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.salespointfxadmin.www.component.SpringFXMLLoader;
import com.salespointfxadmin.www.model.Gasto;
import com.salespointfxadmin.www.model.Sucursal;
import com.salespointfxadmin.www.model.SucursalGasto;
import com.salespointfxadmin.www.service.GastoService;
import com.salespointfxadmin.www.service.SucursalGastoService;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GastoController implements Initializable {
	private final GastoService gs;
	private final SucursalGastoService sgs;
	private final SpringFXMLLoader springFXMLLoader;

	@FXML
	private Button btnCancelar;
	@FXML
	private Button btnGuardar;
	@FXML
	private Button btnRegistrarGasto;
    @FXML
    private Button btnimprimir;

	@FXML
	private TableColumn<Gasto, String> columnDescripcinGasto;

	@FXML
	private TableColumn<Gasto, Short> columnId;

	@FXML
	private TableColumn<Gasto, LocalDateTime> colunCreatedAt;

	@FXML
	private TextField tFieldDescripcion;

	@FXML
	private TextField tFieldId;

	@FXML
	private TableView<Gasto> tViewGatos;
	private ObservableList<Gasto> olg;

	@FXML
	private TableColumn<SucursalGasto, String> columnContratoSucursalGasto;
	@FXML
	private TableColumn<SucursalGasto, LocalDateTime> columnCreatedAtSucursalGasto;;
	@FXML
	private TableColumn<SucursalGasto, Gasto> columnDescripcionGastoSucursalGasto;
	@FXML
	private TableColumn<SucursalGasto, Integer> columnIdGastoSucursalGasto;
	@FXML
	private TableColumn<SucursalGasto, Float> columnMontoSucursalGasto;
	@FXML
	private TableColumn<SucursalGasto, String> columnObservaciones;
	@FXML
	private TableColumn<SucursalGasto, Sucursal> columnSucursalSucursalGasto;

	@FXML
	private TableView<SucursalGasto> tViewSucursalGasto;
	private ObservableList<SucursalGasto> olsg = FXCollections.observableArrayList();

	@FXML
	private DatePicker dPickerFin;

	@FXML
	private DatePicker dPickerInicio;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		columnId.setCellValueFactory(new PropertyValueFactory<>("idGasto"));
		columnId.prefWidthProperty().bind(tViewGatos.widthProperty().multiply(0.1));

		columnDescripcinGasto.setCellValueFactory(new PropertyValueFactory<>("descripcionGasto"));
		columnDescripcinGasto.prefWidthProperty().bind(tViewGatos.widthProperty().multiply(0.6));

		colunCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdt"));
		colunCreatedAt.prefWidthProperty().bind(tViewGatos.widthProperty().multiply(0.3));

		olg = FXCollections.observableArrayList(gs.findAll());
		tViewGatos.setItems(olg);
		tablaLsitener();
		iniciarTablasucursalProducto();
	}

	@FXML
	void buscar(ActionEvent event) {
		try {
			olsg = FXCollections.observableArrayList(sgs.findBySucursalEstatusSucursalTrueAndCreatedAtBetween(dPickerInicio.getValue(), dPickerFin.getValue()));
			tViewSucursalGasto.setItems(olsg);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@FXML
	void cancelar(ActionEvent event) {
		limpiar();
	}

	@FXML
	void guardar(ActionEvent event) {
		try {
			if (tFieldDescripcion.getText().isEmpty()) {
				throw new Exception("Debe contener descripcion el gasto");
			}
			Gasto gastoSeleccionado = tViewGatos.getSelectionModel().getSelectedItem();
			if (gastoSeleccionado != null) {
				gastoSeleccionado.setDescripcionGasto(tFieldDescripcion.getText());
				gs.save(gastoSeleccionado);
				tViewGatos.refresh();
			} else {

				Gasto g = new Gasto(null, tFieldDescripcion.getText());
				g = gs.save(g);
				olg.add(g);
			}
		} catch (

		Exception e) {
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Erro al insertar Gasto");
			error.setHeaderText("No se puede ingresr el gasto");
			error.setContentText(e.getMessage() + "\n" + e.getCause());
			error.show();
		} finally {
			limpiar();
		}
	}
    @FXML
    void imprimir(ActionEvent event) {
    	try {
			SucursalGasto sg= tViewSucursalGasto.getSelectionModel().getSelectedItem();
			if(sg !=null) {
				sgs.imprimir(sg);
			}
		} catch (Exception e) {
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Erro al imprimir");
			error.setHeaderText("No se puede imprimir el gasto");
			error.setContentText(e.getMessage() + "\n" + e.getCause());
		} finally {
			tViewSucursalGasto.getSelectionModel().clearSelection();
		}
    }

	@FXML
	void registrarGasto(ActionEvent event) {
		try {
			FXMLLoader loader = springFXMLLoader.load("/fxml/modal/registrargasto.fxml");
			StackPane root = (StackPane) loader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			// stage.initStyle(StageStyle.UNDECORATED); // Ventana sin bordes
			stage.setTitle("Nuevo Registro de Gasto");
			stage.setScene(new Scene(root));
			stage.setResizable(false);
			stage.showAndWait();

			// Recargar la tabla después de que se cierre la ventana
			// iniciarTablaMovimientoInventario();
			cargarSuursalgasto();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void tablaLsitener() {
		/* SE AGREGA N ESCUCHADOR A LA TABLA CSUCURSALPRODUCTO */
		tViewGatos.setOnMouseClicked(event -> {
			if (event.getClickCount() == 1) { // Doble clic
				Gasto gasto = tViewGatos.getSelectionModel().getSelectedItem();
				if (gasto != null) {
					tFieldDescripcion.setText(gasto.getDescripcionGasto());
					// tFieldPrecio.commitValue();
					tFieldId.setText(gasto.getIdGasto() + "");
					tFieldDescripcion.requestFocus();
				}
			}
		});
	}

	private void limpiar() {
		tFieldId.setText(null);
		tFieldDescripcion.setText("");
		tViewGatos.getSelectionModel().clearSelection();
	}

	private void iniciarTablasucursalProducto() {
		columnIdGastoSucursalGasto.setCellValueFactory(new PropertyValueFactory<>("idSucursalGasto"));
		columnIdGastoSucursalGasto.prefWidthProperty().bind(tViewSucursalGasto.widthProperty().multiply(0.1));

		columnMontoSucursalGasto.setCellValueFactory(new PropertyValueFactory<>("montoGasto"));
		columnMontoSucursalGasto.prefWidthProperty().bind(tViewSucursalGasto.widthProperty().multiply(0.1));
		columnMontoSucursalGasto.setCellFactory(col -> {
			return new TableCell<SucursalGasto, Float>() {
				@Override
				protected void updateItem(Float item, boolean empty) {
					super.updateItem(item, empty);
					if (item != null && !empty) {
						// Formatear el precio como moneda
						DecimalFormat df = new DecimalFormat("#.##");
						setText("$" + df.format(item));
					} else {
						setText(null);
					}
				}
			};
		});

		columnContratoSucursalGasto.setCellValueFactory(new PropertyValueFactory<>("contrato"));
		columnContratoSucursalGasto.prefWidthProperty().bind(tViewSucursalGasto.widthProperty().multiply(0.1));

		columnCreatedAtSucursalGasto.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
		columnCreatedAtSucursalGasto.prefWidthProperty().bind(tViewSucursalGasto.widthProperty().multiply(0.15));

		columnDescripcionGastoSucursalGasto.setCellValueFactory(new PropertyValueFactory<>("gasto"));
		columnDescripcionGastoSucursalGasto.prefWidthProperty().bind(tViewSucursalGasto.widthProperty().multiply(0.2));

		columnSucursalSucursalGasto.setCellValueFactory(new PropertyValueFactory<>("sucursal"));
		columnSucursalSucursalGasto.prefWidthProperty().bind(tViewSucursalGasto.widthProperty().multiply(0.15));

		columnObservaciones.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
		columnObservaciones.prefWidthProperty().bind(tViewSucursalGasto.widthProperty().multiply(0.2));
		setFechaDtaPicker();
		// columnObservaciones
		cargarSuursalgasto();

	}

	private void setFechaDtaPicker() {
		this.dPickerInicio.setValue(LocalDate.now());
		this.dPickerFin.setValue(LocalDate.now());
	}

	private void cargarSuursalgasto() {

		if (!olsg.isEmpty()) {
			olsg.clear();
		}
		olsg = FXCollections.observableArrayList(sgs.findBySucursalEstatusSucursalTrueAndCreatedAtBetween(dPickerInicio.getValue(), dPickerFin.getValue()));
		tViewSucursalGasto.setItems(olsg);
	}

}
