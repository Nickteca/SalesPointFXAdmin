package com.salespointfxadmin.www.controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.salespointfxadmin.www.enums.NombreFolio;
import com.salespointfxadmin.www.model.Folio;
import com.salespointfxadmin.www.model.MovimientoInventario;
import com.salespointfxadmin.www.model.MovimientoInventarioDetalle;
import com.salespointfxadmin.www.model.Sucursal;
import com.salespointfxadmin.www.model.SucursalProducto;
import com.salespointfxadmin.www.service.FolioService;
import com.salespointfxadmin.www.service.MovimientoInventarioDetalleService;
import com.salespointfxadmin.www.service.MovimientoInventarioService2;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputDialog;
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
public class MovimientoInventarioDetalleController2 implements Initializable {
	private final FolioService fs;
	private final MovimientoInventarioService2 mis2;
	private final SucursalService ss;
	private final SucursalProductoService sps;
	private final MovimientoInventarioDetalleService mids;

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnGuardar;

	@FXML
	private ChoiceBox<Folio> cBoxFolio;
	private ObservableList<Folio> olf;

	@FXML
	private ChoiceBox<Sucursal> cBoxSucursal;
	private ObservableList<Sucursal> ols;

	@FXML
	private ListView<SucursalProducto> lViewProductos;
	private ObservableList<SucursalProducto> olsp;

	@FXML
	private TextField tFieldDescripcion;

	@FXML
	private TextField tFieldFolioCompuesto;

	@FXML
	private TextField tFieldId;

	@FXML
	private VBox vBoxProductosSeleccionados;

	DecimalFormat formato = new DecimalFormat("0.#"); // Mostrar entero si es entero, o un decimal si tiene decimales

	@FXML
	void cancelar(ActionEvent event) {

	}

	@FXML
	void guardar(ActionEvent event) {
		try {
			MovimientoInventario mi = new MovimientoInventario((tFieldId.getText() != null && !tFieldId.getText().trim().isEmpty()) ? Integer.parseInt(tFieldId.getText()) : null,
					tFieldFolioCompuesto.getText(), cBoxFolio.getSelectionModel().getSelectedItem().getNaturalezaFolio(), cBoxFolio.getSelectionModel().getSelectedItem().getNombreFolio(),
					tFieldDescripcion.getText(), ss.getSucursalActive(), cBoxSucursal.getSelectionModel().getSelectedItem(), cBoxFolio.getSelectionModel().getSelectedItem());

			List<MovimientoInventarioDetalle> lmid = new ArrayList<MovimientoInventarioDetalle>();
			for (Node node : vBoxProductosSeleccionados.getChildren()) {
				if (node instanceof HBox) {
					HBox hbox = (HBox) node;

					Label labelId = (Label) hbox.getChildren().get(0);
					Label label = (Label) hbox.getChildren().get(1);
					TextField cantidadTextField = (TextField) hbox.getChildren().get(2);

					// Obtener los valores
					String nombreProducto = label.getText();
					String cantidadTexto = cantidadTextField.getText();
					/*
					 * MovimientoInventarioDetalle mid = new MovimientoInventarioDetalle(null,
					 * Short.parseShort(cantidadTexto),
					 * sps.findBySucursalAndProductoNombreProducto(ss.getSucursalActive(),
					 * nombreProducto), mi);
					 */
					MovimientoInventarioDetalle mid = new MovimientoInventarioDetalle((labelId.getText() != null && !labelId.getText().trim().isEmpty()) ? Integer.parseInt(labelId.getText()) : null,
							Float.parseFloat(cantidadTexto), sps.findBySucursalAndProductoNombreProducto(ss.getSucursalActive(), nombreProducto));
					lmid.add(mid);
				}
				if (lmid.size() <= 0) {
					throw new Exception("No hay productos en el Movimiento!!");
				}
				if (!tFieldDescripcion.isDisable() && tFieldDescripcion.getText().isEmpty()) {
					throw new Exception("Agrega algo en la descripcion del movimiento");
				}
				if (!cBoxSucursal.isDisable() && cBoxSucursal.getSelectionModel().getSelectedItem() == null) {
					throw new Exception("Sucursal no seleccionbada, debe seleccionar destino o de donde vienen");
				}
				// mi.setListMovimientoInventarioDetalle(lmid);
				if (mis2.save(mi, lmid, cBoxFolio.getSelectionModel().getSelectedItem()) == null) {
					throw new Exception("no se ha abierto Caja!!");
				} else {
					btnCancelar.fire();
				}
			}
		} catch (NumberFormatException e) {
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Movimiento inventario detalle error!!!");
			error.setHeaderText("Error numerico");
			error.setContentText(e.getMessage() + " " + e.getCause());
			error.show();
		} catch (Exception e) {
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Movimiento inventario detalle error!!!");
			error.setHeaderText("Error al insertar");
			error.setContentText(e.getMessage() + " " + e.getCause());
			error.show();
			e.printStackTrace();
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
		if (event.getGestureSource() != vBoxProductosSeleccionados && event.getDragboard().hasString()) {
			event.acceptTransferModes(TransferMode.MOVE);
		}
		event.consume();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cargarFolios();
		cargarSucursal();
		cargarProductos();
	}

	private void cargarFolios() {
		olf = FXCollections.observableArrayList(fs.findBySucursalEstatusSucursalTrue());
		olf.remove(0);
		cBoxFolio.setItems(olf);
		cBoxFolio.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (NombreFolio.Traspaso_Entrada.equals(newValue.getNombreFolio()) || NombreFolio.Trspaso_Salida.equals(newValue.getNombreFolio())) { // Reemplaza con el nombre
				tFieldFolioCompuesto.setText(cBoxFolio.getSelectionModel().getSelectedItem().folioCompuesto());
				tFieldDescripcion.setDisable(false); // Habilitar el TextField
				cBoxSucursal.setDisable(false);
			} else {
				tFieldFolioCompuesto.setText(cBoxFolio.getSelectionModel().getSelectedItem().folioCompuesto());
				tFieldDescripcion.setDisable(true); // Deshabilitar el TextField
				cBoxSucursal.setDisable(true);
			}
		});
		cBoxFolio.getSelectionModel().selectFirst();
	}

	private void cargarSucursal() {
		ols = FXCollections.observableArrayList(ss.getAllSucursales());
		cBoxSucursal.setItems(ols);
		cBoxSucursal.setDisable(true);
	}

	private void cargarProductos() {
		olsp = FXCollections.observableArrayList(sps.findBySucursalEstatusSucursalTrueAndProductoEsPaqueteFalse());
		lViewProductos.setItems(olsp);
		lViewProductos.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2 && lViewProductos.getSelectionModel().getSelectedItem() != null) {
				SucursalProducto sucursalProductoSeleccionado = lViewProductos.getSelectionModel().getSelectedItem();
				agregarProductoAlContenedor(sucursalProductoSeleccionado.getProducto().getNombreProducto());
			}
		});
	}

	private void agregarProductoAlContenedor(String nombreProducto) {
		// Verificamos si el producto ya existe en el VBox
		if (existeProductoEnVBox(nombreProducto)) {
			Alert warning = new Alert(AlertType.WARNING);
			warning.setTitle("Producto ya agregado!!");
			warning.setHeaderText("El producto");
			warning.setContentText(nombreProducto + " ya existe!!");
			warning.show();
			return; // No agregar duplicados
		}
		// Crear el diálogo
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Entrada de Cantidad");
		dialog.setHeaderText(nombreProducto + ":");
		dialog.setContentText("Cantidad:");

		// Mostrar el diálogo y esperar la respuesta
		Optional<String> result = dialog.showAndWait();
		result.ifPresentOrElse(cantidad -> {
			try {
				float valor = Float.parseFloat(cantidad);
				// HBox para contener el producto y la cantidad
				HBox hbox = new HBox(10);
				hbox.setAlignment(Pos.CENTER_LEFT);
				hbox.getStyleClass().add("item-hbox");

				// Estilo opcional para el HBox (bordes visibles para depuración)
				// hbox.setStyle("-fx-padding: 5; -fx-border-color: lightgray;");

				Label labelId = new Label();
				labelId.setMinWidth(20);
				labelId.setPrefWidth(20);
				labelId.setMaxWidth(20);
				Label label = new Label(nombreProducto);
				label.getStyleClass().add("item-label");
				label.setPrefWidth(100);
				TextField cantidadTextField = new TextField(formato.format(valor)); // Cantidad por defecto
				cantidadTextField.getStyleClass().add("item-textfield");
				TextFormatter<String> formatter = new TextFormatter<>(change -> {
					String newText = change.getControlNewText();

					// Permitir solo números que no inicien con '0', excepto si es solo '0'
					if (newText.matches("[1-9][0-9]*|0|")) {
						return change; // Aceptar el cambio
					}
					return null; // Rechazar el cambio
				});
				cantidadTextField.setOnMouseClicked(event -> {
					cantidadTextField.selectAll(); // Selecciona todo el texto al hacer clic
				});
				cantidadTextField.setPrefWidth(50);
				cantidadTextField.setTextFormatter(formatter);

				Button eliminarBtn = new Button("❌");
				eliminarBtn.getStyleClass().add("button-eliminar");
				eliminarBtn.setOnAction(e -> vBoxProductosSeleccionados.getChildren().remove(hbox));

				hbox.getChildren().add(labelId);
				hbox.getChildren().add(label);
				hbox.getChildren().add(cantidadTextField);
				hbox.getChildren().add(eliminarBtn);

				vBoxProductosSeleccionados.getChildren().add(hbox);
			} catch (NumberFormatException e) {
				Alert error = new Alert(AlertType.ERROR);
				error.setTitle("Error numerico!!!");
				error.setContentText("Entrada inválida. Por favor ingrese un número.");
				error.setContentText(e.getMessage() + "\n" + e.getCause());
				error.show();
			}
		}, () -> {
			Alert error2 = new Alert(AlertType.ERROR);
			error2.setTitle("Error!!!");
			error2.setContentText("Entrada inválida. Por favor ingrese un número.");
			error2.setContentText("Al parece rno as introducido nada");
			error2.show();
		});

	}

	private boolean existeProductoEnVBox(String nombreProducto) {
		for (Node node : vBoxProductosSeleccionados.getChildren()) {
			if (node instanceof HBox) {
				HBox hbox = (HBox) node;
				Label label = (Label) hbox.getChildren().get(1);

				if (label.getText().equalsIgnoreCase(nombreProducto)) {
					return true; // Producto ya existe
				}
			}
		}
		return false; // Producto no existe
	}

	private void eliminarProducto(Integer id) {
		try {
			if (id != null) {
				mids.dalete(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cargarProductosMovimiento(MovimientoInventario mi) {
		tFieldDescripcion.setText(mi.getDecripcion());
		cBoxSucursal.getSelectionModel().select(mi.getSucursalDestino());
		cBoxFolio.getSelectionModel().select(mi.getFolio());
		tFieldId.setText(mi.getIdMovimientoInventario() + "");
		tFieldFolioCompuesto.setText(mi.getFolioCompuesto());
		cBoxFolio.setDisable(true);

		List<MovimientoInventarioDetalle> lmid = mids.findByMovimiento(mi);
		for (MovimientoInventarioDetalle mid : lmid) {
			// HBox para contener el producto y la cantidad
			HBox hbox = new HBox(10);
			hbox.setAlignment(Pos.CENTER_LEFT);
			hbox.getStyleClass().add("item-hbox");

			// Estilo opcional para el HBox (bordes visibles para depuración)
			// hbox.setStyle("-fx-padding: 5; -fx-border-color: lightgray;");
			Label labelId = new Label(mid.getIdMovimientoInventarioDetalle() + "");
			labelId.setMinWidth(20);
			labelId.setPrefWidth(20);
			labelId.setMaxWidth(20);

			Label label = new Label(mid.getSucursalProducto().getProducto().getNombreProducto());
			label.getStyleClass().add("item-label");
			label.setPrefWidth(100);
			TextField cantidadTextField = new TextField(); // Cantidad por defecto
			cantidadTextField.getStyleClass().add("item-textfield");

			cantidadTextField.setOnMouseClicked(event -> {
				cantidadTextField.selectAll(); // Selecciona todo el texto al hacer clic
			});
			cantidadTextField.setText(formato.format(mid.getUnidades()));
			cantidadTextField.setEditable(true);
			cantidadTextField.setPrefWidth(50);

			Button eliminarBtn = new Button("❌");
			eliminarBtn.getStyleClass().add("button-eliminar");
			eliminarBtn.setOnAction(e -> {
				vBoxProductosSeleccionados.getChildren().remove(hbox);
				eliminarProducto((labelId.getText() != null && !labelId.getText().trim().isEmpty()) ? Integer.parseInt(labelId.getText()) : null);
			});

			hbox.getChildren().add(labelId);
			hbox.getChildren().add(label);
			hbox.getChildren().add(cantidadTextField);
			hbox.getChildren().add(eliminarBtn);

			vBoxProductosSeleccionados.getChildren().add(hbox);
		}
	}
}
