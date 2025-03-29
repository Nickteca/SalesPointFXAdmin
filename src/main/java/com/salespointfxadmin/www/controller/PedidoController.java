package com.salespointfxadmin.www.controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.util.converter.FloatStringConverter;

import org.springframework.stereotype.Component;

import com.salespointfxadmin.www.model.Producto;
import com.salespointfxadmin.www.model.SucursalProducto;
import com.salespointfxadmin.www.service.SucursalProductoService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PedidoController implements Initializable {
	private final SucursalProductoService sps;

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnEnviar;

	@FXML
	private TableColumn<SucursalProducto, Short> columnId;
	@FXML
	private TableColumn<SucursalProducto, Float> columnInventario;
	@FXML
	private TableColumn<SucursalProducto, Producto> columnProducto;
	@FXML
	private TableView<SucursalProducto> tViewSucursalProducto;
	ObservableList<SucursalProducto> olsp;

	@FXML
	private TableColumn<SucursalProducto, Float> columnPedidoCantidad;
	@FXML
	private TableColumn<SucursalProducto, Short> columnPedidoId;
	@FXML
	private TableColumn<SucursalProducto, Producto> columnPedidoProducto;

	@FXML
	private TableView<SucursalProducto> tViewPedido;

	@FXML
	void cancelar(ActionEvent event) {

	}

	@FXML
	void enviar(ActionEvent event) {

	}

	@FXML
	void setOnDragDetected(MouseEvent event) {
		SucursalProducto sp = tViewSucursalProducto.getSelectionModel().getSelectedItem();
		if (sp != null) {
			Dragboard db = tViewSucursalProducto.startDragAndDrop(TransferMode.MOVE);
			ClipboardContent content = new ClipboardContent();
			content.putString(sp.getIdSucursalProducto() + "," + sp.getProducto()); // Serializar datos
			db.setContent(content);
			event.consume();
		}

	}

	@FXML
	void setOnDragDropped(DragEvent event) {
		Dragboard db = event.getDragboard();
		boolean success = false;

		if (db.hasString()) {
			String[] data = db.getString().split(","); // Deserializar datos
			// Crear el diálogo
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Entrada de Cantidad");
			dialog.setHeaderText(data[1] + ":");
			dialog.setContentText("Cantidad:");
			// Mostrar el diálogo y esperar la respuesta
			Optional<String> result = dialog.showAndWait();
			result.ifPresentOrElse(cantidad -> {
				float cantidadFloat = Float.parseFloat(cantidad);
				SucursalProducto nuevoProducto = new SucursalProducto(Short.parseShort(data[0]),
						cantidadFloat, new Producto(data[1]));

				tViewPedido.getItems().add(nuevoProducto); // Agregar a la segunda tabla
				// tableView1.getItems().removeIf(p -> p.getNombre().equals(data[0])); //
				// Eliminar de la primera tabla

			}, () -> {
				Alert error2 = new Alert(AlertType.ERROR);
				error2.setTitle("Error!!!");
				error2.setContentText("Entrada inválida. Por favor ingrese un número.");
				error2.setContentText("Al parece rno as introducido nada");
				error2.show();
			});
			success = true;
		}

		event.setDropCompleted(success);
		event.consume();
	}

	@FXML
	void setOnDragOver(DragEvent event) {
		if (event.getGestureSource() != tViewPedido && event.getDragboard().hasString()) {
			event.acceptTransferModes(TransferMode.MOVE);
		}
		event.consume();
	}
    /*@FXML
    void setOnEditCommit(CellEditEvent<SucursalProducto, Float> event) {
    	 SucursalProducto producto = event.getRowValue(); // Obtener el producto de la fila editada
    	    producto.setInventario(event.getNewValue()); // Actualizar la cantidad
    	    tViewPedido.refresh(); // Refrescar la tabla
    }*/

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		iniciarTablaSp();
		iniciarTablap();
	}

	private void iniciarTablaSp() {
		columnId.setCellValueFactory(new PropertyValueFactory<>("idSucursalProducto"));
		columnId.prefWidthProperty().bind(tViewSucursalProducto.widthProperty().multiply(0.1));

		columnProducto.setCellValueFactory(new PropertyValueFactory<>("producto"));
		columnProducto.prefWidthProperty().bind(tViewSucursalProducto.widthProperty().multiply(0.3));

		columnInventario.setCellValueFactory(new PropertyValueFactory<>("inventario"));
		columnInventario.prefWidthProperty().bind(tViewSucursalProducto.widthProperty().multiply(0.2));

		olsp = FXCollections.observableArrayList(sps.findBySucursalEstatusSucursalTrueAndProductoEsPaqueteFalse());
		tViewSucursalProducto.setItems(olsp);
	}

	private void iniciarTablap() {
		tViewPedido.setEditable(true);
		columnPedidoId.setCellValueFactory(new PropertyValueFactory<>("idSucursalProducto"));
		columnPedidoId.prefWidthProperty().bind(tViewPedido.widthProperty().multiply(0.1));

		columnPedidoProducto.setCellValueFactory(new PropertyValueFactory<>("producto"));
		columnPedidoProducto.prefWidthProperty().bind(tViewPedido.widthProperty().multiply(0.3));

		columnPedidoCantidad.setCellValueFactory(new PropertyValueFactory<>("inventario"));
		columnPedidoCantidad.prefWidthProperty().bind(tViewPedido.widthProperty().multiply(0.2));
		// Establecer el comportamiento de edición y formateo
		columnPedidoCantidad.setCellFactory(col -> {
		    // Habilitar la edición y aplicar el formateo al valor
		    return new TextFieldTableCell<SucursalProducto, Float>(new FloatStringConverter()) {
		        @Override
		        public void updateItem(Float item, boolean empty) {
		            super.updateItem(item, empty);
		            if (item != null && !empty) {
		                // Formatear el valor como número con dos decimales
		                DecimalFormat df = new DecimalFormat("#.##");
		                setText(df.format(item));
		            } else {
		                setText(null);
		            }
		        }
		    };
		});

		// Establecer el comportamiento de commit (guardar el valor editado)
		columnPedidoCantidad.setOnEditCommit((CellEditEvent<SucursalProducto, Float> event) -> {
		    SucursalProducto producto = event.getRowValue(); // Obtener el producto de la fila editada
		    producto.setInventario(event.getNewValue()); // Actualizar la cantidad
		    tViewPedido.refresh(); // Refrescar la tabla
		});


	}

}
