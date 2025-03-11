package com.salespointfxadmin.www.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.salespointfxadmin.www.service.MovimientoCajaService;
import com.salespointfxadmin.www.service.SucursalService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MovimientoCajaController implements Initializable {
	private final MovimientoCajaService mcs;
	private final SucursalService ss;

	@FXML
	private ImageView btnAbrir;

	@FXML
	private Button btnCancelar;

	@FXML
	private Label lblSubtitulo;

	@FXML
	private BorderPane lblTitulo;

	@FXML
	private Label lbltitulo;

	@FXML
	private TextField txtSaldoAnterior;

	@FXML
	void abrir(ActionEvent event) {
		try {
			float numero = Float.parseFloat(txtSaldoAnterior.getText());
			if(numero<=0) {
				throw new NumberFormatException("El Saldo anterior no debe ser menor o igual a 0");
			}
		} catch (NumberFormatException e) {
			Alert errorNumero = new Alert(AlertType.ERROR);
			errorNumero.setTitle("Movimiento Caja Error");
			errorNumero.setHeaderText("Error numerico");
			errorNumero.setContentText(e.getMessage()+"\n"+e.getCause());
			errorNumero.showAndWait();
		}
	}

	@FXML
	void cancelar(ActionEvent event) {
		Stage ventana = (Stage) this.btnCancelar.getScene().getWindow();
		ventana.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (mcs.getLastMovmientoCaja() == null) {
			this.txtSaldoAnterior.setEditable(true);
		}
		this.lbltitulo.setText(ss.getSucursalActive().getNombreSucursal());
		this.lblSubtitulo.setText("Abrir Caja");
		// Crear un TextFormatter para validar la entrada
		TextFormatter<String> formatter = new TextFormatter<>(change -> {
		    String newText = change.getControlNewText();

		    // Permitir solo números que no inicien con '0', excepto si es solo '0'
		    if (newText.matches("[1-9][0-9]*|0|")) {
		        return change; // Aceptar el cambio
		    }
		    return null; // Rechazar el cambio
		});

		// Asignar el TextFormatter al TextField
		txtSaldoAnterior.setTextFormatter(formatter);
	}

}
