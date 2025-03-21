package com.salespointfxadmin.www.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import com.salespointfxadmin.www.service.MovimientoInventarioService;
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
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MovimientoInventarioDetalleController implements Initializable {
	private final SucursalProductoService sps;
	private final SucursalService ss;
	private final FolioService fs;
	private final MovimientoInventarioService mis;
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

	@FXML
	private TextField tFieldDescripcion;
	@FXML
	private TextField tFieldFolio;

	@FXML
	private VBox vBoxProductosSeleccionados;
	private ObservableList<SucursalProducto> olsp;

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
		hbox.getStyleClass().add("item-hbox");

		// Estilo opcional para el HBox (bordes visibles para depuración)
		// hbox.setStyle("-fx-padding: 5; -fx-border-color: lightgray;");

		Label label = new Label(nombreProducto);
		label.getStyleClass().add("item-label");
		label.setPrefWidth(100);
		TextField cantidadTextField = new TextField("1"); // Cantidad por defecto
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

		hbox.getChildren().add(label);
		hbox.getChildren().add(cantidadTextField);
		hbox.getChildren().add(eliminarBtn);

		vBoxProductosSeleccionados.getChildren().add(hbox);
	}

	private boolean existeProductoEnVBox(String nombreProducto) {
		for (Node node : vBoxProductosSeleccionados.getChildren()) {
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

	@FXML
	void cancelar(ActionEvent event) {
		Stage estaa = (Stage) btnCancelar.getScene().getWindow();
		estaa.close();
	}

	@FXML
	void guardar(ActionEvent event) {
		try {
			List<MovimientoInventarioDetalle> lmid = new ArrayList<MovimientoInventarioDetalle>();
			for (Node node : vBoxProductosSeleccionados.getChildren()) {
				if (node instanceof HBox) {
					HBox hbox = (HBox) node;

					// Asumiendo que el HBox tiene exactamente: Label, TextField, Button (en ese
					// orden)
					Label label = (Label) hbox.getChildren().get(0);
					TextField cantidadTextField = (TextField) hbox.getChildren().get(1);

					// Obtener los valores
					String nombreProducto = label.getText();
					String cantidadTexto = cantidadTextField.getText();
					MovimientoInventarioDetalle mid = new MovimientoInventarioDetalle(null, Short.parseShort(cantidadTexto),
							sps.findBySucursalAndProductoNombreProducto(ss.getSucursalActive(), nombreProducto));
					lmid.add(mid);
				}
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
			Folio f = cBoxFolio.getSelectionModel().getSelectedItem();
			MovimientoInventario mi = new MovimientoInventario(null, f.folioCompuesto(), f.getNaturalezaFolio(), f.getNombreFolio(), tFieldDescripcion.getText(), ss.getSucursalActive(),
					cBoxSucursal.getSelectionModel().getSelectedItem());
			if (mis.save(mi, lmid) == null) {
				throw new Exception("Es probabel que n este abierta la caja");
			} else {
				btnCancelar.fire();
			}

		} catch (Exception e) {
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Movimiento inventario detalle error!!!");
			error.setHeaderText("Error al insertar");
			error.setContentText(e.getMessage() + " " + e.getCause());
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
		if (event.getGestureSource() != vBoxProductosSeleccionados && event.getDragboard().hasString()) {
			event.acceptTransferModes(TransferMode.MOVE);
		}
		event.consume();
	}

	private void iniciarlVieeProductos() {
		olsp = FXCollections.observableArrayList(sps.findBySucursalAndProductoEsPaqueteFalse(ss.getSucursalActive()));
		lViewProductos.setItems(olsp);
	}

	private void inicirFolios() {
		olf = FXCollections.observableArrayList(fs.findBySucursal(ss.getSucursalActive()));
		cBoxFolio.setItems(olf);
		cBoxFolio.getSelectionModel().selectFirst();
		tFieldFolio.setText(cBoxFolio.getSelectionModel().getSelectedItem().folioCompuesto());
		// Listener para detectar cambios en la selección del ChoiceBox
		cBoxFolio.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (NombreFolio.Traspaso_Entrada.equals(newValue.getNombreFolio()) || NombreFolio.Trspaso_Salida.equals(newValue.getNombreFolio())) { // Reemplaza con el nombre
				tFieldFolio.setText(cBoxFolio.getSelectionModel().getSelectedItem().folioCompuesto());
				tFieldDescripcion.setDisable(false); // Habilitar el TextField
				cBoxSucursal.setDisable(false);
			} else {
				tFieldFolio.setText(cBoxFolio.getSelectionModel().getSelectedItem().folioCompuesto());
				tFieldDescripcion.setDisable(true); // Deshabilitar el TextField
				cBoxSucursal.setDisable(true);
			}
		});
	}

	private void iniciarCBoxSucursal() {
		ols = FXCollections.observableArrayList(ss.getAllSucursales());
		cBoxSucursal.setItems(ols);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		iniciarlVieeProductos();
		inicirFolios();
		iniciarCBoxSucursal();
		tFieldDescripcion.setDisable(true);

	}

	public void mostrarRegistro(MovimientoInventario mi) {
		tFieldDescripcion.setText(mi.getDecripcion());
		tFieldFolio.setText(mi.getFolio());
		cBoxSucursal.getSelectionModel().select(mi.getSucursalDestino());
		List<MovimientoInventarioDetalle> lmid = mids.findByMovimiento(mi);
		for (MovimientoInventarioDetalle mid : lmid) {
			// HBox para contener el producto y la cantidad
			HBox hbox = new HBox();
			hbox.setAlignment(Pos.CENTER_LEFT);
			hbox.getStyleClass().add("item-hbox");

			// Estilo opcional para el HBox (bordes visibles para depuración)
			// hbox.setStyle("-fx-padding: 5; -fx-border-color: lightgray;");

			Label label = new Label(mid.getSucursalProducto().getProducto().getNombreProducto());
			label.getStyleClass().add("item-label");
			label.setPrefWidth(100);
			TextField cantidadTextField = new TextField(); // Cantidad por defecto
			cantidadTextField.getStyleClass().add("item-textfield");

			cantidadTextField.setOnMouseClicked(event -> {
				cantidadTextField.selectAll(); // Selecciona todo el texto al hacer clic
			});
			cantidadTextField.setText(mid.getUnidades() + "");
			cantidadTextField.setEditable(false);
			cantidadTextField.setPrefWidth(50);

			Button eliminarBtn = new Button("❌");
			eliminarBtn.getStyleClass().add("button-eliminar");
			eliminarBtn.setOnAction(e -> vBoxProductosSeleccionados.getChildren().remove(hbox));

			hbox.getChildren().add(label);
			hbox.getChildren().add(cantidadTextField);
			hbox.getChildren().add(eliminarBtn);

			vBoxProductosSeleccionados.getChildren().add(hbox);
		}

	}

}
