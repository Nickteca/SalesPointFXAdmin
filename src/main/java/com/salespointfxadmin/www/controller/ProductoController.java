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
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

	private final CategoriaService cs;
	private final SucursalProductoService sps;
	private final SucursalService ss;
	private final ProductoService ps;

	@FXML
	private Button buttonCancelar;
	@FXML
	private Button buttonGuardar;
	@FXML
	private ChoiceBox<Categoria> cBoxCategoria;
	private ObservableList<Categoria> olc;
	@FXML
	private CheckBox cBoxVendible;
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
	private TextField tFieldBuscar;
	@FXML
	private TextField tFieldDescripcion;
	@FXML
	private TextField tFieldId;
	@FXML
	private TextField tFieldInventario;
	@FXML
	private TextField tFieldPrecio;
	@FXML
	private TableView<SucursalProducto> tVeiwSucursalProductos;
	private ObservableList<SucursalProducto> olsp;

	@FXML
	void cancelar(ActionEvent event) {
		limpiarCampos();
	}

	@FXML
	void guardar(ActionEvent event) {
		try {
			// Obtener el producto seleccionado de la tabla
			SucursalProducto productoSeleccionado = tVeiwSucursalProductos.getSelectionModel().getSelectedItem();
			if (productoSeleccionado != null) {
				Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
				confirmacion.setTitle("Confirmar actualización");
				confirmacion.setHeaderText("¿Deseas actualizar el producto?");
				confirmacion.setContentText("Producto: " + productoSeleccionado.getProducto().getNombreProducto());
				// Esperar la respuesta del usuario
				var resultado = confirmacion.showAndWait();
				// Actualizar el producto con los valores de los campos de texto y selección
				if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
					productoSeleccionado.getProducto().setNombreProducto(tFieldDescripcion.getText());
					productoSeleccionado.setPrecio(Float.parseFloat(tFieldPrecio.getText()));
					productoSeleccionado.setInventario(Float.parseFloat(tFieldInventario.getText()));
					productoSeleccionado.setCategoria(cBoxCategoria.getSelectionModel().getSelectedItem());
					productoSeleccionado.setVendible(cBoxVendible.isSelected());

					// Guardar el producto actualizado en la base de datos
					sps.saveSucursalProducto(productoSeleccionado);

					// Refrescar la tabla
					tVeiwSucursalProductos.refresh();
					limpiarCampos();
				} else {
					limpiarCampos();
				}
			} else {
				Producto p = new Producto(null, tFieldDescripcion.getText(), false);
				p = ps.save(p);
				SucursalProducto sp = new SucursalProducto(null, Float.parseFloat(tFieldPrecio.getText()), Float.parseFloat(tFieldInventario.getText()), cBoxVendible.isSelected(), p,
						cBoxCategoria.getSelectionModel().getSelectedItem(), ss.getSucursalActive());
				sp = sps.saveSucursalProducto(sp);
				if (sp != null) {
					Alert confirmacion = new Alert(AlertType.CONFIRMATION);
					confirmacion.setTitle("Exito!!!");
					confirmacion.setHeaderText("Dao de alta correctamente");
					confirmacion.setContentText("El sucursalProducto:" + sp.getProducto().getNombreProducto() + " dado de alta correctamente");
					confirmacion.show();
					olsp.add(sp);
					tVeiwSucursalProductos.refresh();
					limpiarCampos();
				}
			}
		} catch (Exception e) {
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("ProductoCiontroller Error!!!");
			error.setHeaderText("Error al insertar ya sea producto o sucursalproducto");
			error.setContentText(e.getMessage() + "\n" + e.getCause());
			error.show();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		inicarTabla();
		tablaLsitener();
		textFielNumeros();
		olc = FXCollections.observableArrayList(cs.getAllCategorias());
		cBoxCategoria.setItems(olc);
		cBoxCategoria.getSelectionModel().selectFirst();

		seleccionarTextoAlHacerClick(tFieldDescripcion);
		seleccionarTextoAlHacerClick(tFieldPrecio);
		seleccionarTextoAlHacerClick(tFieldInventario);
		seleccionarTextoAlHacerClick(tFieldId);

		tFieldPrecio.setOnAction(event -> buttonGuardar.fire());
		filtro();
	}

	private void filtro() {
		FilteredList<SucursalProducto> filteredData = new FilteredList<>(olsp, p -> true);
		// Asociar el filtro con el TextField
		tFieldBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(producto -> {
				// Si el campo de búsqueda está vacío, muestra todo
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Convierte el texto a minúsculas para una búsqueda sin distinción de
				// mayúsculas/minúsculas
				String lowerCaseFilter = newValue.toLowerCase();

				// Compara con el nombre y la categoría del producto
				if (producto.getProducto().getNombreProducto().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (producto.getCategoria().getNombreCategoria().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}
				return false; // No coincide
			});
		});
		// Crear un SortedList a partir del FilteredList
		SortedList<SucursalProducto> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tVeiwSucursalProductos.comparatorProperty());

		// Asignar los datos filtrados y ordenados a la tabla
		tVeiwSucursalProductos.setItems(sortedData);
	}

	private void inicarTabla() {
		/* SE INICIA TODO DE LOS PRODUCTO SUCURSAL */
		columnId.setCellValueFactory(new PropertyValueFactory<>("idSucursalProducto"));
		columnId.prefWidthProperty().bind(tVeiwSucursalProductos.widthProperty().multiply(0.1));

		columnDescripcion.setCellValueFactory(new PropertyValueFactory<>("producto"));
		columnDescripcion.prefWidthProperty().bind(tVeiwSucursalProductos.widthProperty().multiply(0.3));

		columnInventario.setCellValueFactory(new PropertyValueFactory<>("inventario"));
		columnInventario.prefWidthProperty().bind(tVeiwSucursalProductos.widthProperty().multiply(0.1));

		columnCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
		columnCategoria.prefWidthProperty().bind(tVeiwSucursalProductos.widthProperty().multiply(0.2));

		columnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
		columnPrecio.prefWidthProperty().bind(tVeiwSucursalProductos.widthProperty().multiply(0.15));

		columnVendible.setCellValueFactory(new PropertyValueFactory<>("vendible"));
		columnVendible.prefWidthProperty().bind(tVeiwSucursalProductos.widthProperty().multiply(0.15));

		olsp = FXCollections.observableArrayList(sps.findBySucursalEstatusSucursalTrueAndProductoEsPaqueteFalse());
		tVeiwSucursalProductos.setItems(olsp);

	}

	private void tablaLsitener() {
		/* SE AGREGA N ESCUCHADOR A LA TABLA CSUCURSALPRODUCTO */
		tVeiwSucursalProductos.setOnMouseClicked(event -> {
			if (event.getClickCount() == 1) { // Doble clic
				SucursalProducto productoSeleccionado = tVeiwSucursalProductos.getSelectionModel().getSelectedItem();
				if (productoSeleccionado != null) {
					tFieldDescripcion.setText(productoSeleccionado.getProducto().getNombreProducto());
					tFieldPrecio.setText(productoSeleccionado.getPrecio() + "");
					tFieldId.setText(productoSeleccionado.getIdSucursalProducto() + "");
					cBoxCategoria.getSelectionModel().select(productoSeleccionado.getCategoria());
					tFieldInventario.setText(productoSeleccionado.getInventario() + "");
					tFieldInventario.setEditable(false);
					cBoxVendible.setSelected(productoSeleccionado.isVendible());
					tFieldPrecio.requestFocus();
				}
			}
		});
	}

	private void limpiarCampos() {
		tFieldId.setText(null);
		tFieldDescripcion.setText(null);
		tFieldInventario.setText(null);
		tFieldPrecio.setText(null);
		tVeiwSucursalProductos.getSelectionModel().clearSelection();
	}

	private void seleccionarTextoAlHacerClick(TextField textField) {
		textField.setOnMouseClicked(event -> textField.selectAll());
	}

	private void textFielNumeros() {
		TextFormatter<String> formatter2 = new TextFormatter<>(change -> {
			String newText = change.getControlNewText();

			// Permitir solo números que no inicien con '0', excepto si es solo '0'
			if (newText.matches("[1-9][0-9]*|0|")) {
				return change; // Aceptar el cambio
			}
			return null; // Rechazar el cambio
		});
		tFieldPrecio.setTextFormatter(formatter2);
	}

}
