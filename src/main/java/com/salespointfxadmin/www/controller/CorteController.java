package com.salespointfxadmin.www.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.model.Corte;
import com.salespointfxadmin.www.service.CorteService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CorteController implements Initializable {
	private final CorteService cs;

	@FXML
	private Button buttonBuscar;

	@FXML
	private Button buttonImprimir;

	@FXML
	private TableColumn<Corte, LocalDateTime> columnApertura;

	@FXML
	private TableColumn<Corte, LocalDateTime> columnCierre;

	@FXML
	private TableColumn<Corte, String> columnFolioFinal;

	@FXML
	private TableColumn<Corte, String> columnFolioInicial;

	@FXML
	private TableColumn<Corte, Float> columnGasto;

	@FXML
	private TableColumn<Corte, Integer> columnId;

	@FXML
	private TableColumn<Corte, Float> columnInicial;

	@FXML
	private TableColumn<Corte, Integer> columnMovimiento;

	@FXML
	private TableColumn<Corte, Short> columnNumeroFolios;

	@FXML
	private TableColumn<Corte, Float> columnRecoleccion;

	@FXML
	private TableColumn<Corte, Float> columnSaldoFinal;

	@FXML
	private TableColumn<Corte, Float> columnVentas;

	@FXML
	private DatePicker dPickerFin;

	@FXML
	private DatePicker dPicketInicio;

	@FXML
	private TableView<Corte> tViewCorte;
	private ObservableList<Corte> olc;

	@FXML
	void buscar(ActionEvent event) {
		try {
			if (dPicketInicio.getValue().isAfter(dPickerFin.getValue())) {
				throw new IllegalArgumentException("Fecha Inicio no debe ser menor a fecha Fin");
			}
			List<Corte> lc = cs.findByLocaldate(dPicketInicio.getValue(), dPickerFin.getValue());
			if (lc.isEmpty()) {
				throw new IllegalArgumentException("No hay datos en al lista");
			}
			olc = FXCollections.observableArrayList(cs.findByLocaldate(dPicketInicio.getValue(), dPickerFin.getValue()));
			tViewCorte.setItems(olc);
			tViewCorte.refresh();
		} catch (IllegalArgumentException e) {
			showErrorDialog("Error", e.getMessage());
		} catch (Exception e) {
			showErrorDialog("Error desconocido", e.getMessage());
		}
	}

	@FXML
	void imprimir(ActionEvent event) {
		try {
			Corte c = tViewCorte.getSelectionModel().getSelectedItem();
			if (c == null) {
				throw new IllegalArgumentException("Selecciona un corte");
			}
			cs.imprimirCorte(c);
		} catch (IllegalArgumentException e) {
			showErrorDialog("Error", e.getMessage());
		} catch (Exception e) {
			showErrorDialog("Error", e.getMessage());
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dPickerFin.setValue(LocalDate.now());
		dPicketInicio.setValue(LocalDate.now());
		iniciarTablaCorte();
	}

	private void iniciarTablaCorte() {
		try {
			columnId.setCellValueFactory(new PropertyValueFactory<>("idCorte"));
			// columnId.prefWidthProperty().bind(tViewCorte.widthProperty().multiply(0.1));

			columnInicial.setCellValueFactory(new PropertyValueFactory<>("inicial"));
			// columnInicial.prefWidthProperty().bind(tViewCorte.widthProperty().multiply(0.1));

			// columnMovimiento.setCellValueFactory(new
			// PropertyValueFactory<>("movimientoCaja"));
			// columnMovimiento.prefWidthProperty().bind(tViewCorte.widthProperty().multiply(0.1));

			columnVentas.setCellValueFactory(new PropertyValueFactory<>("ventas"));
			// columnId.prefWidthProperty().bind(tViewCorte.widthProperty().multiply(0.1));

			columnRecoleccion.setCellValueFactory(new PropertyValueFactory<>("recoleccion"));
			// columnId.prefWidthProperty().bind(tViewCorte.widthProperty().multiply(0.1));

			columnGasto.setCellValueFactory(new PropertyValueFactory<>("gasto"));
			// columnId.prefWidthProperty().bind(tViewCorte.widthProperty().multiply(0.1));

			columnSaldoFinal.setCellValueFactory(new PropertyValueFactory<>("saldoFinal"));
			// columnId.prefWidthProperty().bind(tViewCorte.widthProperty().multiply(0.1));

			columnApertura.setCellValueFactory(new PropertyValueFactory<>("apertuta"));
			// columnId.prefWidthProperty().bind(tViewCorte.widthProperty().multiply(0.1));
			// Agregar el formato para mostrar el precio como moneda
			columnApertura.setCellFactory(col -> {
				return new TableCell<Corte, LocalDateTime>() {
					@Override
					protected void updateItem(LocalDateTime item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null && !empty) {
							// Formatear el precio como moneda
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
							setText(formatter.format(item));
						} else {
							setText(null);
						}
					}
				};
			});

			columnCierre.setCellValueFactory(new PropertyValueFactory<>("cierre"));
			// columnId.prefWidthProperty().bind(tViewCorte.widthProperty().multiply(0.1));
			columnCierre.setCellFactory(col -> {
				return new TableCell<Corte, LocalDateTime>() {
					@Override
					protected void updateItem(LocalDateTime item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null && !empty) {
							// Formatear el precio como moneda
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
							setText(formatter.format(item));
						} else {
							setText(null);
						}
					}
				};
			});

			columnNumeroFolios.setCellValueFactory(new PropertyValueFactory<>("numFolios"));
			// columnId.prefWidthProperty().bind(tViewCorte.widthProperty().multiply(0.1));

			columnFolioInicial.setCellValueFactory(new PropertyValueFactory<>("folioIncial"));
			// columnId.prefWidthProperty().bind(tViewCorte.widthProperty().multiply(0.1));

			columnFolioFinal.setCellValueFactory(new PropertyValueFactory<>("folioFinal"));
			// columnId.prefWidthProperty().bind(tViewCorte.widthProperty().multiply(0.1));

		} catch (Exception e) {
			showErrorDialog("Error Initzialized", e.getMessage());
		}
	}

	private void showErrorDialog(String title, String mensage) {
		Alert error = new Alert(AlertType.ERROR);
		error.setTitle(title);
		error.setHeaderText("Bamos a ver el error");
		error.setContentText(mensage);
		error.showAndWait();
	}

}
