package com.salespointfxadmin.www.controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.salespointfxadmin.www.model.Categoria;
import com.salespointfxadmin.www.model.Producto;
import com.salespointfxadmin.www.model.ProductoPaquete;
import com.salespointfxadmin.www.model.SucursalProducto;
import com.salespointfxadmin.www.service.CategoriaService;
import com.salespointfxadmin.www.service.ProductoPaqueteService;
import com.salespointfxadmin.www.service.ProductoService;
import com.salespointfxadmin.www.service.SucursalProductoService;
import com.salespointfxadmin.www.service.SucursalService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaqueteController implements Initializable {
	private final ProductoService ps;
	private final SucursalProductoService sps;
	private final SucursalService ss;
	private final ProductoPaqueteService pps;
	private final CategoriaService cs;

	@FXML
	private Button btnCancelat;

	@FXML
	private Button btnGuardar;

	@FXML
	private ChoiceBox<Categoria> cBoxCategoria;

	private ObservableList<Categoria> olc;

	@FXML
	private CheckBox cBoxVendible;

	@FXML
	private TableColumn<SucursalProducto, Categoria> columnCategoria;

	@FXML
	private TableColumn<SucursalProducto, String> columnDescripcion;

	@FXML
	private TableColumn<SucursalProducto, Short> columnId;

	@FXML
	private TableColumn<SucursalProducto, Float> columnPrecio;

	@FXML
	private TableColumn<SucursalProducto, Boolean> columnVendible;

	@FXML
	private ListView<SucursalProducto> lViewProductos;

	@FXML
	private ListView<ProductoPaquete> lViewProductosPaquete;
	private ObservableList<ProductoPaquete> olp = FXCollections.observableArrayList();

	@FXML
	private TableView<SucursalProducto> tViewPaquetes;
	private ObservableList<SucursalProducto> olsp;

	@FXML
	private VBox vBoxProductos;
	private ObservableList<SucursalProducto> oLSucursalProducto;

	@FXML
	private TextField tFieldDescripcion;

	@FXML
	private TextField tFieldId;

	@FXML
	private TextField tFieldPrecio;

	@FXML
	void cancelar(ActionEvent event) {
		limpiarCampos();
	}

	@FXML
	void guardar(ActionEvent event) {
		try {
			SucursalProducto productoSeleccionado = tViewPaquetes.getSelectionModel().getSelectedItem();
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
					productoSeleccionado.setInventario(0);
					productoSeleccionado.setCategoria(cBoxCategoria.getSelectionModel().getSelectedItem());
					productoSeleccionado.setVendible(cBoxVendible.isSelected());
					sps.saveSucursalProducto(productoSeleccionado);
					tViewPaquetes.refresh();
					limpiarCampos();
				} else {
					limpiarCampos();
				}
			} else {
				List<ProductoPaquete> lpp = new ArrayList<ProductoPaquete>();
				for (Node node : vBoxProductos.getChildren()) {
					if (node instanceof HBox) {
						HBox hbox = (HBox) node;

						// Asumiendo que el HBox tiene exactamente: Label, TextField, Button (en ese
						// orden)
						Label label = (Label) hbox.getChildren().get(0);
						TextField cantidadTextField = (TextField) hbox.getChildren().get(1);

						// Obtener los valores
						String nombreProducto = label.getText();
						String cantidadTexto = cantidadTextField.getText();
						ProductoPaquete pp = new ProductoPaquete(Float.parseFloat(cantidadTexto), ps.findByProducto(nombreProducto));
						lpp.add(pp);
					}
				}
				if (lpp.size() <= 0) {
					throw new Exception("No hay productos en el paquete!!");
				}
				if (tFieldDescripcion.getText().isEmpty()) {
					throw new Exception("Agrega Descripcion");
				}
				if (Float.parseFloat(tFieldPrecio.getText()) <= 0) {
					throw new NumberFormatException("Preio est amal o vacio");
				}
				Producto p = new Producto(null, tFieldDescripcion.getText(), true);
				SucursalProducto sp = ps.saveProductoPaquete(p, lpp, Float.parseFloat(tFieldPrecio.getText()), cBoxCategoria.getSelectionModel().getSelectedItem(), cBoxVendible.isSelected(),
						ss.getSucursalActive());
				if (sp != null) {
					olsp.add(sp);
					tViewPaquetes.refresh();
					limpiarCampos();
				}
			}
		} catch (NumberFormatException e) {
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Paquete controller Guardar Error!!!");
			error.setHeaderText("No se dio de alta hay un error");
			error.setContentText(e.getMessage() + "\n" + e.getCause() + "\n" + e.getStackTrace());
			error.show();
		} catch (Exception e) {
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Paquete controller Guardar Error!!!");
			error.setHeaderText("No se dio de alta hay un error");
			error.setContentText(e.getMessage() + "\n" + e.getCause() + "\n" + e.getStackTrace());
			error.show();
		}
	}

	@FXML
	void setOnDragDetected(MouseEvent event) {
		if (!lViewProductos.getSelectionModel().isEmpty()) {
			Dragboard db = lViewProductos.startDragAndDrop(TransferMode.MOVE);
			ClipboardContent content = new ClipboardContent();
			content.putString(lViewProductos.getSelectionModel().getSelectedItem().getProducto().getNombreProducto());
			db.setContent(content);
			event.consume();
		}
	}

	@FXML
	void setOnDragDropped(DragEvent event) {
		Dragboard db = event.getDragboard();
		if (db.hasString()) {
			agregarProductoAlContenedor(db.getString());
			event.setDropCompleted(true);
		} else {
			event.setDropCompleted(false);
		}
		event.consume();
	}

	@FXML
	void setOnDragOver(DragEvent event) {
		if (event.getGestureSource() != vBoxProductos && event.getDragboard().hasString()) {
			event.acceptTransferModes(TransferMode.MOVE);
		}
		event.consume();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		oLSucursalProducto = FXCollections.observableArrayList(sps.findBySucursalEstatusSucursalTrueAndProductoEsPaqueteFalse());
		lViewProductos.setItems(oLSucursalProducto);
		iniciatViewPaquetes();
		cargarCategorias();
		agregarDobleClickTablapaquetes();
		textFielNumeros();
		tFieldPrecio.setOnAction(event -> btnGuardar.fire());
	}

	private void agregarProductoAlContenedor(String nombreProducto) {
		// Verificamos si el producto ya existe en el VBox
		if (existeProductoEnVBox(nombreProducto)) {
			Alert warning = new Alert(AlertType.WARNING);
			warning.setTitle("ÑProducto ya agregado!!");
			warning.setHeaderText("El producto");
			warning.setContentText(nombreProducto + " ya existe!!");
			warning.show();
			return; // No agregar duplicados
		}
		// HBox para contener el producto y la cantidad
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER_LEFT);
		hbox.getStyleClass().add("producto-item"); // Aplicar clase CSS
		// Estilo opcional para el HBox (bordes visibles para depuración)
		// hbox.setStyle("-fx-padding: 5; -fx-border-color: lightgray;");

		Label label = new Label(nombreProducto);
		label.getStyleClass().add("producto-label"); // Aplicar clase CSS
		label.setPrefWidth(100);
		TextField cantidadTextField = new TextField("1"); // Cantidad por defecto
		TextFormatter<String> formatter2 = new TextFormatter<>(change -> {
			String newText = change.getControlNewText();

			// Permitir solo números que no inicien con '0', excepto si es solo '0' o
			// números con punto decimal
			if (newText.matches("[0-9]*\\.?[0-9]*")) {
				return change; // Aceptar el cambio
			}
			return null; // Rechazar el cambio
		});
		cantidadTextField.getStyleClass().add("cantidad-field"); // Aplicar clase CSS
		cantidadTextField.setPrefWidth(50);
		cantidadTextField.setTextFormatter(formatter2);

		Button eliminarBtn = new Button("❌");
		eliminarBtn.getStyleClass().add("eliminar-btn"); // Aplicar clase CSS
		eliminarBtn.setOnAction(e -> vBoxProductos.getChildren().remove(hbox));

		hbox.getChildren().add(label);
		hbox.getChildren().add(cantidadTextField);
		hbox.getChildren().add(eliminarBtn);

		vBoxProductos.getChildren().add(hbox);
	}

	private void iniciatViewPaquetes() {
		/* SE INICIA TODO DE LOS PRODUCTO SUCURSAL */
		columnId.setCellValueFactory(new PropertyValueFactory<>("idSucursalProducto"));
		columnId.prefWidthProperty().bind(tViewPaquetes.widthProperty().multiply(0.1));

		columnDescripcion.setCellValueFactory(new PropertyValueFactory<>("producto"));
		columnDescripcion.prefWidthProperty().bind(tViewPaquetes.widthProperty().multiply(0.4));

		columnCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
		columnCategoria.prefWidthProperty().bind(tViewPaquetes.widthProperty().multiply(0.2));

		columnPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
		columnPrecio.prefWidthProperty().bind(tViewPaquetes.widthProperty().multiply(0.15));
		columnPrecio.setCellFactory(col -> {
			return new TableCell<SucursalProducto, Float>() {
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

		columnVendible.setCellValueFactory(new PropertyValueFactory<>("vendible"));
		columnVendible.prefWidthProperty().bind(tViewPaquetes.widthProperty().multiply(0.15));

		olsp = FXCollections.observableArrayList(sps.findBySucursalAndProductoEsPaqueteTrue(ss.getSucursalActive()));
		tViewPaquetes.setItems(olsp);
	}

	private void agregarDobleClickTablapaquetes() {
		/* SE AGREGA N ESCUCHADOR A LA TABLA CSUCURSALPRODUCTO */
		tViewPaquetes.setOnMouseClicked(event -> {
			if (event.getClickCount() == 1) { // Doble clic
				SucursalProducto productoSeleccionado = tViewPaquetes.getSelectionModel().getSelectedItem();
				if (productoSeleccionado != null) {

					tFieldDescripcion.setText(productoSeleccionado.getProducto().getNombreProducto());
					tFieldPrecio.setText(String.valueOf(productoSeleccionado.getPrecio()));
					tFieldId.setText(productoSeleccionado.getIdSucursalProducto() + "");
					cBoxCategoria.getSelectionModel().select(productoSeleccionado.getCategoria());
					cBoxVendible.setSelected(productoSeleccionado.isVendible());
					tFieldPrecio.requestFocus();
					cargarProductosDelPaquete(productoSeleccionado);
				}
			}
		});
	}

	private void cargarProductosDelPaquete(SucursalProducto sp) {
		List<ProductoPaquete> lpp = pps.findByPaquete(sp.getProducto());
		olp = FXCollections.observableArrayList(lpp);
		lViewProductosPaquete.setItems(olp);
	}

	private boolean existeProductoEnVBox(String nombreProducto) {
		for (Node node : vBoxProductos.getChildren()) {
			if (node instanceof HBox) {
				HBox hbox = (HBox) node;
				Label label = (Label) hbox.getChildren().get(0);

				if (label.getText().equalsIgnoreCase(nombreProducto)) {
					return true; // Producto ya existe
				}
			}
		}
		return false; // Producto no existe
	}

	private void limpiarCampos() {
		try {
			if (!olp.isEmpty() || olp != null || olp.equals(null)) {
				olp.clear();
			}
			tFieldDescripcion.setText(null);
			tFieldId.setText(null);
			tFieldPrecio.setText(null);
			cBoxCategoria.getSelectionModel().selectFirst();
			tViewPaquetes.getSelectionModel().clearSelection();
			vBoxProductos.getChildren().clear();
		} catch (Exception e) {
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Paquete Controller, Limpiar Error!!!");
			error.setHeaderText("No se puede limpiar por algun error: ");
			error.setContentText(e.getMessage() + "\n" + e.getCause());
			error.show();
		}

	}

	private void cargarCategorias() {
		olc = FXCollections.observableArrayList(cs.getAllCategorias());
		cBoxCategoria.setItems(olc);
		cBoxCategoria.getSelectionModel().selectFirst();
	}

	private void textFielNumeros() {
		TextFormatter<String> formatter2 = new TextFormatter<>(change -> {
			String newText = change.getControlNewText();

			// Permitir solo números que no inicien con '0', excepto si es solo '0' o
			// números con punto decimal
			if (newText.matches("[0-9]*\\.?[0-9]*")) {
				return change; // Aceptar el cambio
			}
			return null; // Rechazar el cambio
		});
		tFieldPrecio.setTextFormatter(formatter2);

	}

}
