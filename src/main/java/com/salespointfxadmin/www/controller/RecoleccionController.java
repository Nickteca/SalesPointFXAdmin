package com.salespointfxadmin.www.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import org.springframework.stereotype.Component;

import com.salespointfxadmin.www.component.SpringFXMLLoader;
import com.salespointfxadmin.www.controller.modal.SucursalRecoleccionEditController;
import com.salespointfxadmin.www.enums.BilleteValor;
import com.salespointfxadmin.www.model.Billete;
import com.salespointfxadmin.www.model.Sucursal;
import com.salespointfxadmin.www.model.SucursalRecoleccion;
import com.salespointfxadmin.www.service.BilleteService;
import com.salespointfxadmin.www.service.SucursalRecoleccionService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RecoleccionController implements Initializable {
	private final SpringFXMLLoader springFXMLLoader;
	private final SucursalRecoleccionService srs;
	private final BilleteService bs;

	@FXML
	private Button btnBuscar;
	@FXML
	private Button btnCancelar;
	@FXML
	private Button btnRegistrar;
	@FXML
	private TableColumn<SucursalRecoleccion, LocalDateTime> columnFecha;
	@FXML
	private TableColumn<SucursalRecoleccion, Integer> columnId;
	@FXML
	private TableColumn<SucursalRecoleccion, Sucursal> columnSucursal;
	@FXML
	private TableColumn<SucursalRecoleccion, Float> columnTotal;
	@FXML
	private DatePicker dPickerFin;
	@FXML
	private DatePicker dPickerInicio;
	@FXML
	private Label label1;
	@FXML
	private Label label10;
	@FXML
	private Label label100;
	@FXML
	private Label label1000;
	@FXML
	private Label label2;
	@FXML
	private Label label20;
	@FXML
	private Label label200;
	@FXML
	private Label label5;
	@FXML
	private Label label50;
	@FXML
	private Label label500;
	@FXML
	private Label labelTotal;
	@FXML
	private TextField tFieldX1;
	@FXML
	private TextField tFieldX10;
	@FXML
	private TextField tFieldX100;
	@FXML
	private TextField tFieldX1000;
	@FXML
	private TextField tFieldX2;
	@FXML
	private TextField tFieldX20;
	@FXML
	private TextField tFieldX200;
	@FXML
	private TextField tFieldX5;
	@FXML
	private TextField tFieldX50;
	@FXML
	private TextField tFieldX500;
	@FXML
	private TableView<SucursalRecoleccion> tViewSucursalRecoleccion;
	private ObservableList<SucursalRecoleccion> olsr = FXCollections.observableArrayList();

	private final int[] valores = { 1000, 500, 200, 100, 50, 20, 10, 5, 2, 1 };
	private final String[] denominacion = { "$1000", "$500", "$200", "$100", "$50", "$20", "$10", "$5", "$2", "$1" };
	private TextField[] textFields;
	private Label[] labels;

	@FXML
	void buscar(ActionEvent event) {
		try {
			olsr = FXCollections.observableArrayList(srs.findBySucursalEstatusSucursalTrueAndCreatedAtBetween(dPickerInicio.getValue(), dPickerFin.getValue()));
			tViewSucursalRecoleccion.setItems(olsr);
		} catch (Exception e) {
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Error buscando la recoleccion");
			error.setHeaderText("Hay un errpor o no se pudeo encontrar la recoleccion");
			error.setContentText(e.getMessage() + "\n" + e.getCause());
			error.show();
		}
	}

	@FXML
	void cancelar(ActionEvent event) {
		TextField[] textFields = { tFieldX1000, tFieldX500, tFieldX200, tFieldX100, tFieldX50, tFieldX20, tFieldX10, tFieldX5, tFieldX2, tFieldX1 };
		int total = 0;

		for (int i = 0; i < textFields.length; i++) {

			textFields[i].setText("0");
			labels[i].setText("$0"); // Actualizar cada Label

		}

		labelTotal.setText("$0");
	}

	@FXML
	void registrar(ActionEvent event) {
		try {
			SucursalRecoleccion sr = new SucursalRecoleccion();
			TextField[] textFields = { tFieldX1000, tFieldX500, tFieldX200, tFieldX100, tFieldX50, tFieldX20, tFieldX10, tFieldX5, tFieldX2, tFieldX1 };
			int total = 0;
			List<Billete> lb = new ArrayList<Billete>();
			for (int i = 0; i < textFields.length; i++) {
				if (!textFields[i].getText().isEmpty() || textFields[i].getText() != "0") {
					int cantidad = Integer.parseInt(textFields[i].getText());
					int subtotal = cantidad * valores[i];
					total += subtotal;
					Billete b = new Billete(null, BilleteValor.valueOf(denominacion[i]), cantidad, subtotal, sr);
					lb.add(b);
				}
			}
			sr.setIdSucursalRecoleccion(null);
			sr.setTotalRecoleccion(total);
			sr.setListBillete(lb);
			if (srs.save(sr, total, lb) != null) {
				btnCancelar.fire();
				btnBuscar.fire();
			}

		} catch (Exception e) {
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Error Insertando recoleecion");
			error.setHeaderText("Hay un errpor o no se pudeo ingresar recoleccion");
			error.setContentText(e.getMessage() + "\n" + e.getCause());
			error.show();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		iniciarDataPicher();
		numerosTexfiel();
		seleccinarContenidoTextField();
		textfieldCantidades();
		llenarTablaInicial();
		tablaLsitener();
	}

	private void iniciarDataPicher() {
		dPickerFin.setValue(LocalDate.now());
		dPickerInicio.setValue(LocalDate.now());
	}

	private void numerosTexfiel() {
		UnaryOperator<TextFormatter.Change> numericFilter = change -> {
			// Asegura que la entrada solo contenga números (0-9) y se permita el cambio
			String text = change.getText();
			if (text.matches("[0-9]*")) { // Solo acepta dígitos del 0 al 9
				return change; // Si el texto es numérico, permite el cambio
			}
			return null; // Si no es numérico, no permite el cambio
		};

		// Crear un TextFormatter con el filtro
		// Aplicar el filtro a los TextFields ya definidos
		tFieldX1.setTextFormatter(new TextFormatter<>(numericFilter));
		tFieldX10.setTextFormatter(new TextFormatter<>(numericFilter));
		tFieldX100.setTextFormatter(new TextFormatter<>(numericFilter));
		tFieldX1000.setTextFormatter(new TextFormatter<>(numericFilter));
		tFieldX2.setTextFormatter(new TextFormatter<>(numericFilter));
		tFieldX200.setTextFormatter(new TextFormatter<>(numericFilter));
		tFieldX5.setTextFormatter(new TextFormatter<>(numericFilter));
		tFieldX50.setTextFormatter(new TextFormatter<>(numericFilter));
		tFieldX500.setTextFormatter(new TextFormatter<>(numericFilter));
	}

	private void seleccinarContenidoTextField() {
		tFieldX1.setOnMouseClicked(event -> tFieldX1.selectAll());
		tFieldX10.setOnMouseClicked(event -> tFieldX10.selectAll());
		tFieldX100.setOnMouseClicked(event -> tFieldX100.selectAll());
		tFieldX1000.setOnMouseClicked(event -> tFieldX1000.selectAll());
		tFieldX2.setOnMouseClicked(event -> tFieldX2.selectAll());
		tFieldX20.setOnMouseClicked(event -> tFieldX20.selectAll());
		tFieldX200.setOnMouseClicked(event -> tFieldX200.selectAll());
		tFieldX5.setOnMouseClicked(event -> tFieldX5.selectAll());
		tFieldX50.setOnMouseClicked(event -> tFieldX5.selectAll());
		tFieldX500.setOnMouseClicked(event -> tFieldX500.selectAll());
	}

	private void textfieldCantidades() {
		TextField[] textFields = { tFieldX1000, tFieldX500, tFieldX200, tFieldX100, tFieldX50, tFieldX20, tFieldX10, tFieldX5, tFieldX2, tFieldX1 };
		labels = new Label[] { label1000, label500, label200, label100, label50, label20, label10, label5, label2, label1 };

		for (int i = 0; i < textFields.length; i++) {
			final int index = i;
			textFields[i].textProperty().addListener((obs, oldValue, newValue) -> {
				actualizarTotal();
			});
		}
	}

	private void actualizarTotal() {
		TextField[] textFields = { tFieldX1000, tFieldX500, tFieldX200, tFieldX100, tFieldX50, tFieldX20, tFieldX10, tFieldX5, tFieldX2, tFieldX1 };
		int total = 0;

		for (int i = 0; i < textFields.length; i++) {
			try {
				int cantidad = Integer.parseInt(textFields[i].getText());
				int subtotal = cantidad * valores[i];
				labels[i].setText("$" + subtotal); // Actualizar cada Label
				total += subtotal;
			} catch (NumberFormatException e) {
				labels[i].setText("$0"); // Si no es un número, pone $0
			}
		}

		labelTotal.setText("$" + total);
	}

	private void llenarTablaInicial() {

		columnId.setCellValueFactory(new PropertyValueFactory<>("idSucursalRecoleccion"));
		columnId.prefWidthProperty().bind(tViewSucursalRecoleccion.widthProperty().multiply(0.1));

		columnTotal.setCellValueFactory(new PropertyValueFactory<>("TotalRecoleccion"));
		columnTotal.prefWidthProperty().bind(tViewSucursalRecoleccion.widthProperty().multiply(0.2));

		columnSucursal.setCellValueFactory(new PropertyValueFactory<>("sucursal"));
		columnSucursal.prefWidthProperty().bind(tViewSucursalRecoleccion.widthProperty().multiply(0.4));

		columnFecha.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
		columnFecha.prefWidthProperty().bind(tViewSucursalRecoleccion.widthProperty().multiply(0.3));

		olsr = FXCollections.observableArrayList(srs.findBySucursalEstatusSucursalTrueAndCreatedAtBetween(dPickerInicio.getValue(), dPickerFin.getValue()));
		tViewSucursalRecoleccion.setItems(olsr);
	}

	private void tablaLsitener() {
		/* SE AGREGA N ESCUCHADOR A LA TABLA CSUCURSALPRODUCTO */
		tViewSucursalRecoleccion.setOnMouseClicked(event -> {
			if (event.getClickCount() == 1) { // Doble clic
				SucursalRecoleccion sucursalRecoleccion = tViewSucursalRecoleccion.getSelectionModel().getSelectedItem();
				if (sucursalRecoleccion != null) {
					try {
						FXMLLoader loader = springFXMLLoader.load("/fxml/modal/sucursalrecoleccionedit.fxml");
						StackPane root = loader.load();
						SucursalRecoleccionEditController sre = loader.getController();
						sre.cargarBilletes(sucursalRecoleccion);
						Stage stage = new Stage();
						stage.initModality(Modality.APPLICATION_MODAL);
						// stage.initStyle(StageStyle.UNDECORATED); // Ventana sin bordes
						stage.setTitle("Nuevo Movimiento de Inventario");
						stage.setScene(new Scene(root));
						stage.setResizable(false);
						stage.showAndWait();

						// Recargar la tabla después de que se cierre la ventana
						btnBuscar.fire();

					} catch (IOException e) {
						Alert error = new Alert(AlertType.ERROR);
						error.setTitle("erro al abrir el modal!!");
						error.setHeaderText("No abrio el modal de los detalles billete");
						error.setContentText(e.getMessage() + "\n" + e.getCause());
						error.show();
					}
				}
			}
		});
	}
}