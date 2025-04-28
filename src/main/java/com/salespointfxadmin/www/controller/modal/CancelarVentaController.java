package com.salespointfxadmin.www.controller.modal;

import org.springframework.stereotype.Component;

import com.salespointfxadmin.www.model.Venta;
import com.salespointfxadmin.www.service.FolioService;
import com.salespointfxadmin.www.service.MovimientoInventarioService;
import com.salespointfxadmin.www.service.VentaService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CancelarVentaController {
	private final VentaService vs;
	private final MovimientoInventarioService mcs;
	private final FolioService fs;

	@FXML
	private TextField tFieldFolio;

	@FXML
	void canclarVenta(ActionEvent event) {
		try {
			if (tFieldFolio.getText().isEmpty()) {
				throw new IllegalArgumentException("Folio Vacio");
			}
			Venta v = vs.cancelarVenta(tFieldFolio.getText());
			if (v != null) {
				showsucces("Cancelacion Correcta", "La cancelacion del Folio:" + v.getFolio() + " Esta correcta");
				Stage stage = (Stage) tFieldFolio.getScene().getWindow();
				stage.close();
			}
		} catch (IllegalArgumentException e) {
			showError("IllegalArgumentException", e.getMessage());
		} catch (Exception e) {
			showError("Erro desconocido", e.getMessage());
		}
	}

	@FXML
	void cerrar(ActionEvent event) {
		Stage tage = (Stage) this.tFieldFolio.getScene().getWindow();
		tage.close();
	}

	private void showError(String tipoError, String mensage) {
		Alert error = new Alert(AlertType.ERROR);
		error.setTitle("Error");
		error.setHeaderText(tipoError);
		error.setContentText(mensage);
		error.show();
	}

	private void showsucces(String tipo, String mensage) {
		Alert success = new Alert(AlertType.CONFIRMATION);
		success.setTitle("Confimracion");
		success.setHeaderText(tipo);
		success.setContentText(mensage);
		success.show();
	}

}
