package com.salespointfxadmin.www.controller.modal;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.salespointfxadmin.www.model.Gasto;
import com.salespointfxadmin.www.model.SucursalGasto;
import com.salespointfxadmin.www.service.GastoService;
import com.salespointfxadmin.www.service.SucursalGastoService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RegistrarGastoController implements Initializable {
	private final GastoService gs;
	private final SucursalGastoService sgs;

	@FXML
	private Button btnCancelar;
	@FXML
	private Button btnregistrar;
	@FXML
	private ChoiceBox<Gasto> cBoxGasto;
	private ObservableList<Gasto> olg;
	@FXML
	private TextField tFieldCantidad;
	@FXML
	private TextField tFieldContrato;
	@FXML
	private TextField tFieldObservaciones;

	@FXML
	void cancelar(ActionEvent event) {
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}

	@FXML
	void registrar(ActionEvent event) {
		try {
			if (tFieldObservaciones.getText().isEmpty() || tFieldCantidad.getText().isEmpty()) {
				throw new Exception("Obsevacion o cantidad vacio");
			}
			SucursalGasto sg = new SucursalGasto(null, Float.parseFloat(tFieldCantidad.getText()), tFieldContrato.getText(), tFieldObservaciones.getText(),
					cBoxGasto.getSelectionModel().getSelectedItem());
			if (sgs.save(sg) != null) {
				btnCancelar.fire();
			}

		} catch (Exception e) {
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("No se puede Registrar el gasto!!");
			error.setHeaderText("Hay error al momento de ingresar el Gasto");
			error.setContentText(e.getMessage() + "\n" + e.getCause());
			error.show();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cargarChoiceBoxGasto();
		numerosTexField();
	}

	private void cargarChoiceBoxGasto() {
		olg = FXCollections.observableArrayList(gs.findAll());
		cBoxGasto.setItems(olg);
		cBoxGasto.getSelectionModel().selectFirst();
	}

	private void numerosTexField() {
		TextFormatter<String> formatter = new TextFormatter<>(change -> {
			String newText = change.getControlNewText();

			// Permitir solo números que no inicien con '0', excepto si es solo '0'
			if (newText.matches("[1-9][0-9]*|0|")) {
				return change; // Aceptar el cambio
			}
			return null; // Rechazar el cambio
		});
		tFieldCantidad.setTextFormatter(formatter);
	}

}
