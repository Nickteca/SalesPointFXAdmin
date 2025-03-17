package com.salespointfxadmin.www.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.salespointfxadmin.www.DTO.PaqueteProductoDto;
import com.salespointfxadmin.www.model.Categoria;
import com.salespointfxadmin.www.model.Producto;
import com.salespointfxadmin.www.model.ProductoPaquete;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.util.converter.FloatStringConverter;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CrearPaqueteController implements Initializable {

	private final SucursalProductoService sps;
	private final SucursalService ss;
	private final CategoriaService cs;
	private final ProductoService ps;

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnGuardar;

	@FXML
	private ChoiceBox<Categoria> cBoxCategoria;
	private ObservableList<Categoria> olc;

	@FXML
	private TableColumn<PaqueteProductoDto, Float> columnCantidadPP;

	@FXML
	private TableColumn<SucursalProducto, Producto> columnDescripcion;

	@FXML
	private TableColumn<PaqueteProductoDto, String> columnDescripcionPP;

	@FXML
	private TableColumn<SucursalProducto, Short> columnId;

	@FXML
	private TableColumn<PaqueteProductoDto, Integer> columnIdPP;

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
	private ObservableList<PaqueteProductoDto> ol = FXCollections.observableArrayList();

	/*
	 * @FXML private TableColumn<SucursalProducto, Short> columnIdPaquete;
	 * 
	 * @FXML private TableColumn<SucursalProducto, Float> columnPrecioPaquete;
	 * 
	 * @FXML private TableColumn<SucursalProducto, Boolean> columnVendiblePaquete;
	 * 
	 * @FXML private TableColumn<SucursalProducto, Producto>
	 * columnDescroipcionPaquete;
	 * 
	 * @FXML private TableView<SucursalProducto> tViewPaquetes; private
	 * ObservableList<SucursalProducto> olspp;
	 * 
	 * @FXML private TableColumn<PaqueteProductoDto, Float> columnCantidadpdp;
	 * 
	 * @FXML private TableColumn<PaqueteProductoDto, String> columnDescropcionpdp;
	 * 
	 * @FXML private TableColumn<PaqueteProductoDto, Short> columnIdpdp;
	 * 
	 * @FXML private TableView<PaqueteProductoDto> tViewProductosdelPaquete;
	 */
	@FXML
	void cancelar(ActionEvent event) {
		ol.clear();
		tFieldDescripcion.setText(null);
		tFieldId.setText(null);
		tFieldPrecio.setText(null);
		cBoxCategoria.getSelectionModel().selectFirst();
	}

	@FXML
	void guardarPaquete(ActionEvent event) {
		try {
			Producto p = new Producto(null, tFieldDescripcion.getText(), true);
			List<ProductoPaquete> lpp = new ArrayList<ProductoPaquete>();
			for (PaqueteProductoDto producto : ol) {
				ProductoPaquete pp = new ProductoPaquete(producto.getCantidad(), new Producto(producto.getIdProducto()));
				lpp.add(pp);
			}
			p = ps.saveProductoPaquete(p, lpp, Float.parseFloat(tFieldPrecio.getText()), cBoxCategoria.getSelectionModel().getSelectedItem(), ss.getSucursalActive());

			Alert confirmacion = new Alert(AlertType.CONFIRMATION);
			confirmacion.setTitle("Crear Paquete Confirmacion!!!!");
			confirmacion.setHeaderText("Se creo correctamente!!");
			confirmacion.setContentText("Dado de alta correctamente emn, producto, productopaquete y sucursalproducto");
			confirmacion.showAndWait();
			cancelar(event);

		} catch (Exception e) {
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Crear Paquete Errro!!!!");
			error.setHeaderText("Error al crear el producto, paquete y sucursalpaquete");
			error.setContentText(e.getMessage() + "\n" + e.getCause());
			error.showAndWait();
		}
	}

	@FXML
	void setOnDragDetected(MouseEvent event) {
		SucursalProducto selectedProducto = tViewProductos.getSelectionModel().getSelectedItem();
		if (selectedProducto != null) {
			// Iniciar un Dragboard
			Dragboard dragboard = tViewProductos.startDragAndDrop(TransferMode.COPY);

			// Colocar el objeto en el Dragboard
			ClipboardContent content = new ClipboardContent();
			content.putString(String.valueOf(selectedProducto.getIdSucursalProducto())); // O cualquier dato único
			dragboard.setContent(content);

			event.consume();
		}
	}

	@FXML
	void setOnDragDropped(DragEvent event) {
		Dragboard dragboard = event.getDragboard();
		boolean success = false;

		if (dragboard.hasString()) {
			String idProducto = dragboard.getString();

			// Buscar el producto en la lista observable de productos (olsp)
			SucursalProducto productoArrastrado = olsp.stream().filter(sp -> String.valueOf(sp.getIdSucursalProducto()).equals(idProducto)).findFirst().orElse(null);

			if (productoArrastrado != null) {
				// Crear un nuevo objeto ProductoPaquete con los datos arrastrados
				PaqueteProductoDto nuevoProducto = new PaqueteProductoDto(productoArrastrado.getProducto().getIdProducto(), productoArrastrado.getProducto().getNombreProducto(), // O el nombre del
																																													// producto
						1.0f // Cantidad por defecto, se puede modificar luego
				);

				// Agregarlo a la lista observable del TableView de paquetes
				ol.add(nuevoProducto);
				tViewProductosPaquete.setItems(ol);

				success = true;
			}
		}
		event.setDropCompleted(success);
		event.consume();
	}

	@FXML
	void setOnDragOver(DragEvent event) {
		if (event.getGestureSource() != tViewProductosPaquete && event.getDragboard().hasString()) {
			event.acceptTransferModes(TransferMode.COPY);
		}
		event.consume();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		iniciarTablaProductos();
		iniciartabalaProductosPaquete();
		// inciarTablaPaquetes();
		cargarCategoria();
		configurarMenuContextual(); // Activa el menú contextual para eliminación
		textFielNumeros();
		limpiarListaProductos();
	}

	private void iniciarTablaProductos() {
		/* SE INICIA TODO DE LOS PRODUCTO SUCURSAL */
		columnId.setCellValueFactory(new PropertyValueFactory<>("idSucursalProducto"));
		columnId.prefWidthProperty().bind(tViewProductos.widthProperty().multiply(0.1));

		columnDescripcion.setCellValueFactory(new PropertyValueFactory<>("producto"));
		columnDescripcion.prefWidthProperty().bind(tViewProductos.widthProperty().multiply(0.4));

		olsp = FXCollections.observableArrayList(sps.findBySucursalAndProductoEsPaqueteFalse(ss.getSucursalActive()));
		tViewProductos.setItems(olsp);
	}

	/*
	 * private void inciarTablaPaquetes() { columnIdPaquete.setCellValueFactory(new
	 * PropertyValueFactory<>("idSucursalProducto"));
	 * columnIdPaquete.prefWidthProperty().bind(tViewProductos.widthProperty().
	 * multiply(0.1));
	 * 
	 * columnPrecioPaquete.setCellValueFactory(new
	 * PropertyValueFactory<>("precio"));
	 * columnPrecioPaquete.prefWidthProperty().bind(tViewProductos.widthProperty().
	 * multiply(0.2));
	 * 
	 * columnVendiblePaquete.setCellValueFactory(new
	 * PropertyValueFactory<>("vendible"));
	 * columnVendiblePaquete.prefWidthProperty().bind(tViewProductos.widthProperty()
	 * .multiply(0.2));
	 * 
	 * columnDescroipcionPaquete.setCellValueFactory(new
	 * PropertyValueFactory<>("producto"));
	 * columnDescroipcionPaquete.prefWidthProperty().bind(tViewProductos.
	 * widthProperty().multiply(0.5));
	 * 
	 * olspp =
	 * FXCollections.observableArrayList(sps.findBySucursalAndProductoEsPaqueteTrue(
	 * ss.getSucursalActive())); tViewPaquetes.setItems(olspp); }
	 */

	private void iniciartabalaProductosPaquete() {
		/* SE INICIA TODO DE LOS PRODUCTO SUCURSAL */
		columnIdPP.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
		columnIdPP.prefWidthProperty().bind(tViewProductos.widthProperty().multiply(0.1));

		columnDescripcionPP.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
		columnDescripcionPP.prefWidthProperty().bind(tViewProductos.widthProperty().multiply(0.4));

		columnCantidadPP.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
		columnCantidadPP.prefWidthProperty().bind(tViewProductos.widthProperty().multiply(0.4));
		columnCantidadPP.setCellFactory(col -> {
			TextFieldTableCell<PaqueteProductoDto, Float> cell = new TextFieldTableCell<>(new FloatStringConverter()) {

				@Override
				public void startEdit() {
					super.startEdit();

					// Asegurarse de que el TextField sea accesible y su contenido se seleccione
					// automáticamente
					TextField textField = (TextField) getGraphic();
					if (textField != null) {
						textField.selectAll();
					}
				}
			};
			return cell;
		});
		columnCantidadPP.setOnEditCommit(event -> {
			PaqueteProductoDto productoEditado = event.getRowValue();
			productoEditado.setCantidad(event.getNewValue()); // Actualiza el modelo con el nuevo valor
			tViewProductosPaquete.refresh(); // Refresca la tabla para mostrar el cambio
		});
		// Habilitar edición en la tabla
		tViewProductosPaquete.setEditable(true);
	}

	private void cargarCategoria() {
		olc = FXCollections.observableArrayList(cs.getAllCategorias());
		cBoxCategoria.setItems(olc);
		cBoxCategoria.getSelectionModel().selectFirst();
	}

	@FXML
	void eliminarProductoDelPaquete() {
		PaqueteProductoDto seleccionado = tViewProductosPaquete.getSelectionModel().getSelectedItem();
		if (seleccionado != null) {
			ol.remove(seleccionado); // Se elimina de la lista observable
		}
	}

	// Método para configurar el menú contextual
	private void configurarMenuContextual() {
		ContextMenu menuContextual = new ContextMenu();
		MenuItem eliminarItem = new MenuItem("Eliminar Producto");

		eliminarItem.setOnAction(event -> eliminarProductoDelPaquete());
		menuContextual.getItems().add(eliminarItem);

		// Configuramos el menu contextual en cada fila de la tabla
		tViewProductosPaquete.setRowFactory(tv -> {
			TableRow<PaqueteProductoDto> fila = new TableRow<>();

			fila.setOnContextMenuRequested(event -> {
				if (!fila.isEmpty()) {
					menuContextual.show(fila, event.getScreenX(), event.getScreenY());
				}
			});

			return fila;
		});
	}

	// Método para limpiar la lista
	public void limpiarListaProductos() {
		if (ol != null) {
			ol.clear(); // Limpia la lista observable
			// Si tienes una tabla asociada, asegúrate de actualizarla
			tViewProductosPaquete.setItems(ol); // Asegura que la tabla esté vacía
		}
	}

	private void textFielNumeros() {
		TextFormatter<String> formatter = new TextFormatter<>(change -> {
			String newText = change.getControlNewText();

			// Permitir solo números que no inicien con '0', excepto si es solo '0'
			if (newText.matches("[1-9][0-9]*|0|")) {
				return change; // Aceptar el cambio
			}
			return null; // Rechazar el cambio
		});
		tFieldPrecio.setTextFormatter(formatter);
	}

}
