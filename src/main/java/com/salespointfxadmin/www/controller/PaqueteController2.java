package com.salespointfxadmin.www.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.salespointfxadmin.www.DTO.PaqueteProductoDto;
import com.salespointfxadmin.www.model.Producto;
import com.salespointfxadmin.www.model.SucursalProducto;
import com.salespointfxadmin.www.service.SucursalProductoService;
import com.salespointfxadmin.www.service.SucursalService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaqueteController2 implements Initializable {

	private final SucursalProductoService sps;
	private final SucursalService ss;

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnGuardar;

	@FXML
	private ChoiceBox<?> cBoxCategoria;

	@FXML
	private TableColumn<PaqueteProductoDto, Float> columnCantidadPP;

	@FXML
	private TableColumn<SucursalProducto, ?> columnDescripcion;

	@FXML
	private TableColumn<PaqueteProductoDto, Producto> columnDescripcionPP;

	@FXML
	private TableColumn<SucursalProducto, ?> columnId;

	@FXML
	private TableColumn<PaqueteProductoDto, PaqueteProductoDto> columnIdPP;

	@FXML
	private TextField tFieldDescripcion;

	@FXML
	private TextField tFieldId;

	@FXML
	private TextField tFieldPrecio;

	@FXML
	private TableView<SucursalProducto> tViewProductos;
	private ObservableList<SucursalProducto> olsp;

	@FXML
	private TableView<PaqueteProductoDto> tViewProductosPaquete;
	private ObservableList<?> ol;

	@FXML
	void setOnDragDetected(MouseEvent event) {

	}

	@FXML
	void setOnDragDropped(DragEvent event) {

	}

	@FXML
	void setOnDragOver(DragEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		iniciarTablaPauqtes();
	}

	private void iniciarTablaPauqtes() {
		/* SE INICIA TODO DE LOS PRODUCTO SUCURSAL */
		columnId.setCellValueFactory(new PropertyValueFactory<>("idSucursalProducto"));
		columnId.prefWidthProperty().bind(tViewProductos.widthProperty().multiply(0.1));

		columnDescripcion.setCellValueFactory(new PropertyValueFactory<>("producto"));
		columnDescripcion.prefWidthProperty().bind(tViewProductos.widthProperty().multiply(0.4));

		olsp = FXCollections.observableArrayList(sps.findBySucursalAndProductoEsPaqueteFalse(ss.getSucursalActive()));
		tViewProductos.setItems(olsp);
	}

}
