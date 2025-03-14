package com.salespointfxadmin.www.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.salespointfxadmin.www.model.Categoria;
import com.salespointfxadmin.www.model.Producto;
import com.salespointfxadmin.www.model.SucursalProducto;
import com.salespointfxadmin.www.service.CategoriaService;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductoController2 implements Initializable {

	private final CategoriaService cs;
	private final SucursalProductoService sps;
	private final SucursalService ss;

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
			Alert informacion = new Alert(AlertType.INFORMATION);
			informacion.setTitle("no hay seleccion");
			informacion.setHeaderText("Al parecer no trae el producto seleccionado");
			informacion.setContentText("nada de nada");
			informacion.showAndWait();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		inicarTabla();
		tablaLsitener();
		olc = FXCollections.observableArrayList(cs.getAllCategorias());
		cBoxCategoria.setItems(olc);
		cBoxCategoria.getSelectionModel().selectFirst();

		seleccionarTextoAlHacerClick(tFieldDescripcion);
		seleccionarTextoAlHacerClick(tFieldPrecio);
		seleccionarTextoAlHacerClick(tFieldInventario);
		seleccionarTextoAlHacerClick(tFieldId);
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

		olsp = FXCollections.observableArrayList(sps.getAllProductosSucursalActive(ss.getSucursalActive()));
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
					cBoxVendible.setSelected(productoSeleccionado.isVendible());
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

}
