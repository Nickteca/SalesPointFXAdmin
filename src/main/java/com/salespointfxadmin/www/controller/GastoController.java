package com.salespointfxadmin.www.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.salespointfxadmin.www.model.Gasto;
import com.salespointfxadmin.www.model.Sucursal;
import com.salespointfxadmin.www.model.SucursalGasto;
import com.salespointfxadmin.www.service.GastoService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GastoController implements Initializable {
	private final GastoService gs;

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnGuardar;

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
    private TableColumn<SucursalGasto, Sucursal> columnSucursalSucursalGasto;
	
    @FXML
    private TableView<SucursalGasto> tViewSucursalGasto;
    private ObservableList<SucursalGasto> olsg;
    
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
	private void iniciarTablasucursalProducto(){
		columnIdGastoSucursalGasto.setCellValueFactory(new PropertyValueFactory<>("idSucursalGasto"));
		columnIdGastoSucursalGasto.prefWidthProperty().bind(tViewSucursalGasto.widthProperty().multiply(0.1));
		
		columnMontoSucursalGasto.setCellValueFactory(new PropertyValueFactory<>("montoGasto"));
		columnMontoSucursalGasto.prefWidthProperty().bind(tViewSucursalGasto.widthProperty().multiply(0.1));
		
		columnContratoSucursalGasto.setCellValueFactory(new PropertyValueFactory<>("contrato"));
		columnContratoSucursalGasto.prefWidthProperty().bind(tViewSucursalGasto.widthProperty().multiply(0.15));
		
		columnCreatedAtSucursalGasto.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
		columnCreatedAtSucursalGasto.prefWidthProperty().bind(tViewSucursalGasto.widthProperty().multiply(0.15));
		
		columnDescripcionGastoSucursalGasto.setCellValueFactory(new PropertyValueFactory<>("gasto"));
		columnDescripcionGastoSucursalGasto.prefWidthProperty().bind(tViewSucursalGasto.widthProperty().multiply(0.3));

		columnSucursalSucursalGasto.setCellValueFactory(new PropertyValueFactory<>("sucursal"));
		columnSucursalSucursalGasto.prefWidthProperty().bind(tViewSucursalGasto.widthProperty().multiply(0.2));

		/*olsg = FXCollections.observableArrayList();
		tViewSucursalGasto.setItems(olsg);*/

	}
}
