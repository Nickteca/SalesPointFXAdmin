package com.salespointfxadmin.www.controller.modal;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.salespointfxadmin.www.enums.BilleteValor;
import com.salespointfxadmin.www.model.Billete;
import com.salespointfxadmin.www.model.SucursalRecoleccion;
import com.salespointfxadmin.www.service.BilleteService;
import com.salespointfxadmin.www.service.SucursalRecoleccionService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SucursalRecoleccionEditController implements Initializable {
	private final BilleteService bs;
	private final SucursalRecoleccionService srs;

	private SucursalRecoleccion sr;
	private List<Billete> lb;

	@FXML
	private Button btnCancelar;
	@FXML
	private Button btnEditar;
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
	private TextField tField1;
	@FXML
	private TextField tField10;
	@FXML
	private TextField tField100;
	@FXML
	private TextField tField1000;
	@FXML
	private TextField tField2;
	@FXML
	private TextField tField20;
	@FXML
	private TextField tField200;
	@FXML
	private TextField tField5;
	@FXML
	private TextField tField50;
	@FXML
	private TextField tField500;

	private Label[] labels;
	private final int[] valores = { 1000, 500, 200, 100, 50, 20, 10, 5, 2, 1 };
	private final String[] denominacion = { "$1000", "$500", "$200", "$100", "$50", "$20", "$10", "$5", "$2", "$1" };

	@FXML
	void cancelar(ActionEvent event) {
		Stage tage = (Stage) btnCancelar.getScene().getWindow();
		tage.close();
	}

	@FXML
	void editar(ActionEvent event) {
		try {
			// SucursalRecoleccion sr = new SucursalRecoleccion();
			TextField[] textFields = { tField1000, tField500, tField200, tField100, tField50, tField20, tField10, tField5, tField2, tField1 };
			int total = 0;
			List<Billete> lb = new ArrayList<Billete>();
			for (int i = 0; i < textFields.length; i++) {
				if (!textFields[i].getText().isEmpty() || textFields[i].getText() != "0") {
					int cantidad = Integer.parseInt(textFields[i].getText());
					int subtotal = cantidad * valores[i];
					total += subtotal;
					Billete b = new Billete(this.lb.get(i).getIdBillete(), BilleteValor.valueOf(denominacion[i]), cantidad, subtotal, sr);
					lb.add(b);
				}
			}
			sr.setTotalRecoleccion(total);
			sr.setListBillete(lb);

			if (srs.save(sr, total, lb) != null) {
				btnCancelar.fire();
				// btnBuscar.fire();
			}

		} catch (Exception e) {
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Error Insertando recoleecion");
			error.setHeaderText("Hay un errpor o no se pudeo ingresar recoleccion");
			error.setContentText(e.getMessage() + "\n" + e.getCause());
			error.show();
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lsitenerTextField();

	}

	public void cargarBilletes(SucursalRecoleccion sr) {
		this.sr = sr;
		this.lb = bs.findBySucursalRecoleccion(sr);
		TextField[] textFields = { tField1000, tField500, tField200, tField100, tField50, tField20, tField10, tField5, tField2, tField1 };
		labels = new Label[] { label1000, label500, label200, label100, label50, label20, label10, label5, label2, label1 };
		for (Billete b : lb) {
			switch (String.valueOf(b.getBillete())) {
			case "$1000": {
				tField1000.setText(b.getCantidad() + "");
				label1000.setText(b.getSubtotal() + "");
				actualizarTotal();
				break;
			}
			case "$500": {
				tField500.setText(b.getCantidad() + "");
				label500.setText(b.getSubtotal() + "");
				actualizarTotal();
				break;
			}
			case "$200": {
				tField200.setText(b.getCantidad() + "");
				label200.setText(b.getSubtotal() + "");
				actualizarTotal();
				break;
			}
			case "$100": {
				tField100.setText(b.getCantidad() + "");
				label100.setText(b.getSubtotal() + "");
				actualizarTotal();
				break;
			}
			case "$50": {
				tField50.setText(b.getCantidad() + "");
				label50.setText(b.getSubtotal() + "");
				actualizarTotal();
				break;
			}
			case "$20": {
				tField20.setText(b.getCantidad() + "");
				label20.setText(b.getSubtotal() + "");
				actualizarTotal();
				break;
			}
			case "$10": {
				tField10.setText(b.getCantidad() + "");
				label10.setText(b.getSubtotal() + "");
				actualizarTotal();
				break;
			}
			case "$5": {
				tField5.setText(b.getCantidad() + "");
				label5.setText(b.getSubtotal() + "");
				actualizarTotal();
				break;
			}
			case "$2": {
				tField2.setText(b.getCantidad() + "");
				label2.setText(b.getSubtotal() + "");
				actualizarTotal();
				break;
			}
			case "$1": {
				tField1.setText(b.getCantidad() + "");
				label1.setText(b.getSubtotal() + "");
				actualizarTotal();
				break;
			}
			default:
				break;
			}
		}
	}

	private void lsitenerTextField() {
		TextField[] textFields = { tField1000, tField500, tField200, tField100, tField50, tField20, tField10, tField5, tField2, tField1 };
		labels = new Label[] { label1000, label500, label200, label100, label50, label20, label10, label5, label2, label1 };

		for (int i = 0; i < textFields.length; i++) {
			final int index = i;
			textFields[i].textProperty().addListener((obs, oldValue, newValue) -> {
				actualizarTotal();
			});
		}
	}

	private void actualizarTotal() {
		TextField[] textFields = { tField1000, tField500, tField200, tField100, tField50, tField20, tField10, tField5, tField2, tField1 };
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

		labelTotal.setText("" + total);
	}
}
