package com.salespointfxadmin.www.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.salespointfxadmin.www.model.Categoria;
import com.salespointfxadmin.www.model.SucursalProducto;
import com.salespointfxadmin.www.service.ProductoService;
import com.salespointfxadmin.www.service.SucursalProductoService;
import com.salespointfxadmin.www.service.SucursalService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
	private ListView<SucursalProducto> lViewPaquetes;

	@FXML
	private ListView<?> lViewProductosPaquete;

	@FXML
	private TableView<SucursalProducto> tViewPaquetes;
	private ObservableList<SucursalProducto> olsp;

	@FXML
	private VBox vBoxProductos;
	private ObservableList<SucursalProducto> oLSucursalProducto;

	@FXML
	void setOnDragDetected(MouseEvent event) {
		if (!lViewPaquetes.getSelectionModel().isEmpty()) {
			Dragboard db = lViewPaquetes.startDragAndDrop(TransferMode.MOVE);
			ClipboardContent content = new ClipboardContent();
			content.putString(lViewPaquetes.getSelectionModel().getSelectedItem().toString());
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
		oLSucursalProducto = FXCollections.observableArrayList(sps.findBySucursalAndProductoEsPaqueteFalse(ss.getSucursalActive()));
		lViewPaquetes.setItems(oLSucursalProducto);
		iniciatViewPaquetes();
	}

	private void agregarProductoAlContenedor(String nombreProducto) {
		// HBox para contener el producto y la cantidad
		HBox hbox = new HBox(10);
		hbox.setAlignment(Pos.CENTER_LEFT);
		// Estilo opcional para el HBox (bordes visibles para depuración)
		hbox.setStyle("-fx-padding: 5; -fx-border-color: lightgray;");

		Label label = new Label(nombreProducto);
		label.setPrefWidth(100);
		TextField cantidadTextField = new TextField("1"); // Cantidad por defecto
		cantidadTextField.setPrefWidth(150);

		Button eliminarBtn = new Button("❌");
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

		columnVendible.setCellValueFactory(new PropertyValueFactory<>("vendible"));
		columnVendible.prefWidthProperty().bind(tViewPaquetes.widthProperty().multiply(0.15));

		olsp = FXCollections.observableArrayList(sps.findBySucursalAndProductoEsPaqueteTrue(ss.getSucursalActive()));
		tViewPaquetes.setItems(olsp);
	}
}
