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
import com.salespointfxadmin.www.service.SucursalService;

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
	private final SucursalService ss;

	@FXML
	private Button btnAgregar;
	@FXML
	private Button btnCancelar;
	@FXML
	private Button btnCancelar2;
	@FXML
	private Button btnEditar;
	@FXML
	private CheckBox cBoxVendible;
	@FXML
	private CheckBox cBoxVendible2;

	@FXML
	private ChoiceBox<Categoria> cbCategoria;

	@FXML
	private ChoiceBox<Categoria> cbCategoria2;

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
	private TextField textDescripcion;
	@FXML
	private TextField textId;

	@FXML
	private TextField textPrecio;

	@FXML
	private TextField textPrecio2;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/* CARGANDO CATEGORIAS */
		ObservableList<Categoria> categorias = FXCollections.observableArrayList(cs.getAllCategorias());
		cbCategoria.setItems(categorias);
		cbCategoria.getSelectionModel().selectFirst();
		cbCategoria2.setItems(categorias);
		/* SE INICIA TODO DE LOS PRODITOS */
		pColumnId.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
		pColumnId.prefWidthProperty().bind(pTViewProductos.widthProperty().multiply(0.2));

		pColumnDescripcion.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
		pColumnDescripcion.prefWidthProperty().bind(pTViewProductos.widthProperty().multiply(0.7));

		productos = FXCollections.observableArrayList(ps.getAllProductos());
		pTViewProductos.setItems(productos);

		/* SE INICIA TODO DE LOS PRODUCTO SUCURSAL */
		columnId.setCellValueFactory(new PropertyValueFactory<>("idSucursalProducto"));
		columnId.prefWidthProperty().bind(tVSucursalProducto.widthProperty().multiply(0.1));

		columnDescripcion.setCellValueFactory(new PropertyValueFactory<>("producto"));
		columnDescripcion.prefWidthProperty().bind(tVSucursalProducto.widthProperty().multiply(0.3));

		columnInventario.setCellValueFactory(new PropertyValueFactory<>("inventario"));
		columnInventario.prefWidthProperty().bind(tVSucursalProducto.widthProperty().multiply(0.1));

		columnCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
		columnCategoria.prefWidthProperty().bind(tVSucursalProducto.widthProperty().multiply(0.2));

		columnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
		columnPrecio.prefWidthProperty().bind(tVSucursalProducto.widthProperty().multiply(0.15));

		columnVendible.setCellValueFactory(new PropertyValueFactory<>("vendible"));
		columnVendible.prefWidthProperty().bind(tVSucursalProducto.widthProperty().multiply(0.15));

		sucursalProducto = FXCollections.observableArrayList(sps.getAllProductosSucursalActive(ss.getSucursalActive()));
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

		TextFormatter<String> formatter2 = new TextFormatter<>(change -> {
			String newText = change.getControlNewText();

			// Permitir solo números que no inicien con '0', excepto si es solo '0'
			if (newText.matches("[1-9][0-9]*|0|")) {
				return change; // Aceptar el cambio
			}
			return null; // Rechazar el cambio
		});

		// Asignar el TextFormatter al TextField
		textPrecio.setTextFormatter(formatter);
		textPrecio2.setTextFormatter(formatter2);
		/* SE AGREGA N ESCUCHADOR A LA TABLA CSUCURSALPRODUCTO */
		tVSucursalProducto.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) { // Doble clic
				SucursalProducto productoSeleccionado = tVSucursalProducto.getSelectionModel().getSelectedItem();
				if (productoSeleccionado != null) {
					textDescripcion.setText(productoSeleccionado.getProducto().getNombreProducto());
					textPrecio2.setText(productoSeleccionado.getPrecio() + "");
					textId.setText(productoSeleccionado.getIdSucursalProducto() + "");
					cbCategoria2.getSelectionModel().select(productoSeleccionado.getCategoria());
					cBoxVendible2.setSelected(productoSeleccionado.isVendible());
				}
			}
		});
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

			sucursalProducto.add(sps.saveSucursalProducto(precio, vendibe, c, p, ss.getSucursalActive()));
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

	@FXML
	void cancelar2(ActionEvent event) {

	}

	@FXML
	void editar(ActionEvent event) {

	}

}
