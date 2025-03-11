package com.salespointfxadmin.www.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.salespointfxadmin.www.model.Categoria;
import com.salespointfxadmin.www.model.Producto;
import com.salespointfxadmin.www.model.SucursalProducto;
import com.salespointfxadmin.www.service.CategoriaService;
import com.salespointfxadmin.www.service.ProductoService;
import com.salespointfxadmin.www.service.SucursalProductoService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductoController implements Initializable {

	private final ProductoService ps;
	private final SucursalProductoService sps;
	private final CategoriaService cs;

	@FXML
	private Button btnAgregar;

	@FXML
	private Button btnCancelar;
	@FXML
	private CheckBox cBoxVendible;

	@FXML
	private ChoiceBox<Categoria> cbCategoria;

	@FXML
	private TableColumn<SucursalProducto, Categoria> columnCategoria;

	@FXML
	private TableColumn<SucursalProducto, Producto> columnDescripcion;

	@FXML
	private TableColumn<SucursalProducto, Short> columnId;

	@FXML
	private TableColumn<SucursalProducto, Float> columnInventario;

	@FXML
	private TableColumn<SucursalProducto, Float> columnPrecio;

	@FXML
	private TableColumn<SucursalProducto, Boolean> columnVendible;

	@FXML
	private TableColumn<Producto, String> pColumnDescripcion;

	@FXML
	private TableColumn<Producto, Short> pColumnId;
	@FXML
	private ObservableList<Producto> productos;
	@FXML
	private ObservableList<SucursalProducto> sucursalProducto;

	@FXML
	private TableView<Producto> pTViewProductos;

	@FXML
	private TableView<SucursalProducto> tVSucursalProducto;
	@FXML
	private TextField textPrecio;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/* CARGANDO CATEGORIAS */
		ObservableList<Categoria> categorias = FXCollections.observableArrayList(cs.getAllCategorias());
		cbCategoria.setItems(categorias);
		cbCategoria.getSelectionModel().selectFirst();
		/* SE INICIA TODO DE LOS PRODITOS */
		pColumnId.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
		pColumnId.prefWidthProperty().bind(pTViewProductos.widthProperty().multiply(0.1));

		pColumnDescripcion.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
		pColumnDescripcion.prefWidthProperty().bind(pTViewProductos.widthProperty().multiply(0.8));

		productos = FXCollections.observableArrayList(ps.getAllProductos());
		pTViewProductos.setItems(productos);

		/* SE INICIA TODO DE LOS PRODUCTO SUCURSAL */
		columnId.setCellValueFactory(new PropertyValueFactory<>("idSucursalProducto"));
		columnId.prefWidthProperty().bind(pTViewProductos.widthProperty().multiply(0.1));

		columnDescripcion.setCellValueFactory(new PropertyValueFactory<>("producto"));
		columnDescripcion.prefWidthProperty().bind(pTViewProductos.widthProperty().multiply(0.5));

		columnInventario.setCellValueFactory(new PropertyValueFactory<>("inventario"));
		columnInventario.prefWidthProperty().bind(pTViewProductos.widthProperty().multiply(0.2));

		columnCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
		columnCategoria.prefWidthProperty().bind(pTViewProductos.widthProperty().multiply(0.2));

		columnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
		columnPrecio.prefWidthProperty().bind(pTViewProductos.widthProperty().multiply(0.2));

		columnVendible.setCellValueFactory(new PropertyValueFactory<>("vendible"));
		columnVendible.prefWidthProperty().bind(pTViewProductos.widthProperty().multiply(0.2));

		sucursalProducto = FXCollections.observableArrayList(sps.getAllProductosSucursalActive());
		tVSucursalProducto.setItems(sucursalProducto);
		/* CheckBox por defaul chequeado */
		cBoxVendible.setSelected(true);
		/* HACEMPOS QUE SOLO ACEPTE NUMEROS EL PRECIO TEXT */
		TextFormatter<String> formatter = new TextFormatter<>(change -> {
			String newText = change.getControlNewText();

			// Permitir solo números que no inicien con '0', excepto si es solo '0'
			if (newText.matches("[1-9][0-9]*|0|")) {
				return change; // Aceptar el cambio
			}
			return null; // Rechazar el cambio
		});

		// Asignar el TextFormatter al TextField
		textPrecio.setTextFormatter(formatter);
	}

	@FXML
	void agergarProductoSucursal(ActionEvent event) {
		try {
			Producto p = pTViewProductos.getSelectionModel().getSelectedItem();
			if (p == null) {
				throw new Exception("Selecciona un producto");
			}
			if (textPrecio.getText().isEmpty()) {
				throw new Exception("Ingresa Cantidad");
			}
			float precio = Float.parseFloat(textPrecio.getText());
			boolean vendibe = cBoxVendible.isSelected();
			Categoria c = cbCategoria.getValue();
			boolean existe = sucursalProducto.stream().anyMatch(sp -> sp.getProducto().getIdProducto() == p.getIdProducto());
			if (existe) {
				throw new Exception("El producto ya está agregado a la sucursal.");
			}

			sucursalProducto.add(sps.saveSucursalProducto(precio, vendibe, c, p));
		} catch (

		Exception e) {
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Producto Controller Error!!!");
			error.setHeaderText("Hay error al asignar el producto a la sucursal");
			error.setContentText(e.getMessage() + "\n" + e.getCause());
			error.show();
		}
	}

	@FXML
	void cancelar(ActionEvent event) {
	}

}
