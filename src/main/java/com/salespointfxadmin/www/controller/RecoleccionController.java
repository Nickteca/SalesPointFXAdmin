package com.salespointfxadmin.www.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import org.springframework.stereotype.Component;

import com.salespointfxadmin.www.model.Sucursal;
import com.salespointfxadmin.www.model.SucursalRecoleccion;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;

@Component
public class RecoleccionController implements Initializable {

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
	private TextField tFieldX200;

	@FXML
	private TextField tFieldX5;

	@FXML
	private TextField tFieldX50;

	@FXML
	private TextField tFieldX500;

	@FXML
	private TableView<SucursalRecoleccion> tViewSucursalRecoleccion;

	@FXML
	void buscar(ActionEvent event) {

	}

	@FXML
	void cancelar(ActionEvent event) {

	}


	
    @FXML
    void keytyped(KeyEvent event) {
    	try {
			TextField tf = (TextField) event.getSource();
			// Verificar si el campo de texto no está vacío
	        String text = tf.getText().trim();
	        if (text.isEmpty()) {
	            return;
	        }


			switch (tf.getId()) {
			case "tFieldX1000": {

				int numero = Integer.parseInt(tf.getText());
				int subtotal = numero * 1000;
				label1000.setText(subtotal + "");
				break;
			}
			case "tFieldX100": {

				int numero = Integer.parseInt(tf.getText());
				int subtotal = numero * 100;
				label100.setText(subtotal + "");
				break;
			}
			case "tFieldX10": {

				int numero = Integer.parseInt(tf.getText());
				int subtotal = numero * 10;
				label10.setText(subtotal + "");
				break;
			}
			case "tFieldX1": {

				int numero = Integer.parseInt(tf.getText());
				int subtotal = numero * 1;
				label1.setText(subtotal + "");
				break;
			}
			case "tFieldX200": {

				int numero = Integer.parseInt(tf.getText());
				int subtotal = numero * 200;
				label200.setText(subtotal + "");
				break;
			}
			case "tFieldX500": {

				int numero = Integer.parseInt(tf.getText());
				int subtotal = numero * 500;
				label500.setText(subtotal + "");
				break;
			}
			case "tFieldX50": {

				int numero = Integer.parseInt(tf.getText());
				int subtotal = numero * 50;
				label50.setText(subtotal + "");
				break;
			}
			case "tFieldX2": {

				int numero = Integer.parseInt(tf.getText());
				int subtotal = numero * 2;
				label2.setText(subtotal + "");
				break;
			}
			case "tFieldX5": {

				int numero = Integer.parseInt(tf.getText());
				int subtotal = numero * 5;
				label5.setText(subtotal + "");
				break;
			}
			default:
				break;
				//throw new NumberFormatException("No e sun numeor al parcer: " + tf.getId());
			}
		} catch (NumberFormatException e) {
			Alert err = new Alert(AlertType.ERROR);
			err.setTitle("error!!");
			err.setHeaderText("No es numero al oparcer");
			err.setContentText(e.getMessage() + "\n" + e.getCause());
			err.show();
			//e.printStackTrace();
		}
    }

	@FXML
	void registrar(ActionEvent event) {

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		iniciarDataPicher();
		numerosTexfiel();

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

}