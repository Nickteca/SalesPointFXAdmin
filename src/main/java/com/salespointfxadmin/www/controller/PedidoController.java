package com.salespointfxadmin.www.controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.salespointfxadmin.www.model.Producto;
import com.salespointfxadmin.www.model.Sucursal;
import com.salespointfxadmin.www.model.SucursalPedido;
import com.salespointfxadmin.www.model.SucursalPedidoDetalle;
import com.salespointfxadmin.www.model.SucursalProducto;
import com.salespointfxadmin.www.service.SucursalPedidoService;
import com.salespointfxadmin.www.service.SucursalProductoService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputDialog;
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
public class PedidoController implements Initializable {
	private final SucursalProductoService sps;
	private final SucursalPedidoService spedidos;

	@FXML
	private ContextMenu cMenu;
	@FXML
	private MenuItem mItem;

	@FXML
	private DatePicker dPickerFin;
	@FXML
	private DatePicker dPicketInicio;

	@FXML
	private Button btnCancelar;
	@FXML
	private Button btnEnviar;
	@FXML
	private Button btnBuscar;
	@FXML
	private Button btnImprimir;

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
	private TableColumn<SucursalPedidoDetalle, Float> columnCantidadPedido;
	@FXML
	private TableColumn<SucursalPedidoDetalle, SucursalProducto> columnProductoPedido;
	@FXML
	private TableColumn<SucursalPedidoDetalle, Integer> columnIdSucursalProductoPedidoDetalle;

	@FXML
	private TableView<SucursalPedidoDetalle> tViewPedido;
	private ObservableList<SucursalPedidoDetalle> olspd = FXCollections.observableArrayList();

	@FXML
	private TableColumn<SucursalPedido, LocalDateTime> columnCreatedAtSucursalPedido;
	@FXML
	private TableColumn<SucursalPedido, Integer> columnIdSucursalPeido;
	@FXML
	private TableColumn<SucursalPedido, Sucursal> columnSucursalSucursakPedido;
	@FXML
	private TableView<SucursalPedido> tViewSucusalPedido;
	private ObservableList<SucursalPedido> olspTable = FXCollections.observableArrayList();

	@FXML
	void buscar(ActionEvent event) {
		try {
			olspTable = FXCollections.observableArrayList(spedidos.fingBySucursalEstatusSucursalTrueAndCreatedatBeetween(dPicketInicio.getValue(), dPickerFin.getValue()));
			tViewSucusalPedido.setItems(olspTable);
		} catch (Exception e) {
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Error!");
			error.setHeaderText("No se puede buscar");
			error.setContentText(e.getMessage() + "\n" + e.getCause());
			error.show();
		}
	}

	@FXML
	void imprimir(ActionEvent event) {
		try {
			SucursalPedido sp = tViewSucusalPedido.getSelectionModel().getSelectedItem();
			if (sp != null) {
				new Thread(() -> {
					spedidos.imprimir(sp);
				}).start();
			} else {
				throw new Exception("Seleccione un pedido de la lista");
			}
		} catch (Exception e) {
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Error!");
			error.setHeaderText("Hno s epued eimprimir");
			error.setContentText(e.getMessage() + "\n" + e.getCause());
			error.show();
		} finally {
			tViewSucusalPedido.getSelectionModel().clearSelection();
		}
	}

	@FXML
	void eliminarRegistro(ActionEvent event) {
		SucursalPedidoDetalle spd = tViewPedido.getSelectionModel().getSelectedItem();
		if (spd != null) {
			olspd.remove(spd);
			tViewPedido.getSelectionModel().clearSelection();
		}
	}

	@FXML
	void cancelar(ActionEvent event) {
		olspd.clear();
	}

	@FXML
	void enviar(ActionEvent event) {
		try {
			SucursalPedido sp = new SucursalPedido();
			List<SucursalPedidoDetalle> lspd = new ArrayList<SucursalPedidoDetalle>();
			for (int i = 0; i < olspd.size(); i++) {
				SucursalPedidoDetalle spd = new SucursalPedidoDetalle();
				spd.setCantidad(olspd.get(i).getCantidad());
				spd.setIdSucursalPedidoDetalle(olspd.get(i).getIdSucursalPedidoDetalle());
				spd.setSucursalPedido(sp);
				spd.setSucursalProducto(olspd.get(i).getSucursalProducto());
				lspd.add(spd);
			}
			sp.setListSucursalpedidoDetalle(lspd);
			if (spedidos.save(sp) != null) {
				Alert confirmacion = new Alert(AlertType.CONFIRMATION);
				confirmacion.setTitle("Exito!");
				confirmacion.setHeaderText("Se registro correctmanete");
				confirmacion.setContentText("El pedido se regitro");
				confirmacion.show();
				btnCancelar.fire();
			}
		} catch (Exception e) {
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Error!");
			error.setHeaderText("NO se registro");
			error.setContentText(e.getMessage() + "\n" + e.getCause());
			error.show();
		} finally {
			btnBuscar.fire();
		}
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
		try {
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
					// VARIABLE SQUE COPUMANO
					float cantidadFloat = Float.parseFloat(cantidad);
					Short idScursalProducto = Short.parseShort(data[0]);

					// Buscar si el producto ya está en la lista
					boolean existe = olspd.stream().anyMatch(spd -> spd.getSucursalProducto().getIdSucursalProducto().equals(idScursalProducto));

					if (existe) {
						Alert alerta = new Alert(AlertType.WARNING);
						alerta.setTitle("Producto Duplicado");
						alerta.setHeaderText(null);
						alerta.setContentText("Este producto ya fue agregado.");
						alerta.show();
					} else {
						SucursalPedidoDetalle nuevoProducto = new SucursalPedidoDetalle(null, cantidadFloat, sps.findBySucursalEstatusSucursalTrueAndIdSucursalProducto(idScursalProducto));

						olspd.add(nuevoProducto);
						tViewPedido.setItems(olspd);
					}
					// olspd.add(nuevoProducto);
					// tViewPedido.setItems(olspd);
					// tViewPedido.getItems().add(nuevoProducto); // Agregar a la segunda tabla
					// tableView1.getItems().removeIf(p -> p.getNombre().equals(data[0])); //
					// Eliminar de la primera tabla

				}, () -> {
					Alert error2 = new Alert(AlertType.ERROR);
					error2.setTitle("Error!!!");
					error2.setContentText("Entrada inválida. Por favor ingrese un número.");
					error2.setContentText("Al parece rno as introducido nada");
					error2.show();
					return;
				});
				success = true;
			}

			event.setDropCompleted(success);
			event.consume();
		} catch (Exception e) {
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Error!!!");
			error.setContentText("Entrada inválida. Por favor ingrese un número.");
			error.setContentText("Al parece rno as introducido nada o no es numero:\n" + e.getMessage() + "\n" + e.getCause());
			error.show();
		}
	}

	@FXML
	void setOnDragOver(DragEvent event) {
		if (event.getGestureSource() != tViewPedido && event.getDragboard().hasString()) {
			event.acceptTransferModes(TransferMode.MOVE);
		}
		event.consume();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		iniciarTablaSp();
		iniciarTablap();
		iniciarTablaPedidos();
		iniciardataPicker();
	}

	private void iniciardataPicker() {
		dPickerFin.setValue(LocalDate.now());
		dPicketInicio.setValue(LocalDate.now());
	}

	private void iniciarTablaPedidos() {
		columnCreatedAtSucursalPedido.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
		columnCreatedAtSucursalPedido.prefWidthProperty().bind(tViewSucusalPedido.widthProperty().multiply(0.1));

		columnIdSucursalPeido.setCellValueFactory(new PropertyValueFactory<>("SucursalPedido"));
		columnIdSucursalPeido.prefWidthProperty().bind(tViewSucusalPedido.widthProperty().multiply(0.3));

		columnSucursalSucursakPedido.setCellValueFactory(new PropertyValueFactory<>("sucursal"));
		columnSucursalSucursakPedido.prefWidthProperty().bind(tViewSucusalPedido.widthProperty().multiply(0.2));

		olspTable = FXCollections.observableArrayList(spedidos.fingBySucursalEstatusSucursalTrueAndCreatedatBeetween(LocalDate.now(), LocalDate.now()));
		tViewSucusalPedido.setItems(olspTable);
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
		olspd.clear();
		tViewPedido.setEditable(true);
		columnIdSucursalProductoPedidoDetalle.setCellValueFactory(new PropertyValueFactory<>("idSucursalPedidoDetalle"));
		columnIdSucursalProductoPedidoDetalle.prefWidthProperty().bind(tViewPedido.widthProperty().multiply(0.1));

		columnProductoPedido.setCellValueFactory(new PropertyValueFactory<>("sucursalProducto"));
		columnProductoPedido.prefWidthProperty().bind(tViewPedido.widthProperty().multiply(0.3));

		columnCantidadPedido.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
		columnCantidadPedido.prefWidthProperty().bind(tViewPedido.widthProperty().multiply(0.2));
		// Establecer el comportamiento de edición y formateo
		columnCantidadPedido.setCellFactory(col -> {
			return new TextFieldTableCell<SucursalPedidoDetalle, Float>(new FloatStringConverter()) {
				@Override
				public void startEdit() {
					super.startEdit();
					if (getGraphic() instanceof TextField textField) {
						// Aplicar el filtro para solo permitir números con decimales
						textField.setTextFormatter(new TextFormatter<>(change -> {
							String newText = change.getControlNewText();
							if (newText.matches("\\d*\\.?\\d*")) { // Solo números y un punto decimal
								return change;
							} else {
								return null; // Rechazar cambios inválidos
							}
						}));
					}
				}

				@Override
				public void updateItem(Float item, boolean empty) {
					super.updateItem(item, empty);
					if (item != null && !empty) {
						DecimalFormat df = new DecimalFormat("#.##");
						setText(df.format(item));
					} else {
						setText(null);
					}
				}
			};
		});

		// Establecer el comportamiento de commit
		columnCantidadPedido.setOnEditCommit((CellEditEvent<SucursalPedidoDetalle, Float> event) -> {
			try {
				Float newValue = event.getNewValue();
				if (newValue != null) {
					event.getRowValue().setCantidad(newValue);
					tViewPedido.refresh();
				} else {
					throw new NumberFormatException("El valor ingresado no es válido");
				}
			} catch (NumberFormatException e) {
				// Manejar el error si el usuario ingresó un valor no válido
				System.out.println("Error: Ingrese un valor numérico válido.");
			}
		});

	}

}
