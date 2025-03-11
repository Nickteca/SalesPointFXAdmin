package com.salespointfxadmin.www.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.salespointfxadmin.www.component.SpringFXMLLoader;
import com.salespointfxadmin.www.model.MovimientoCaja;
import com.salespointfxadmin.www.model.MovimientoCaja.TipoMovimiento;
import com.salespointfxadmin.www.model.Usuario;
import com.salespointfxadmin.www.service.MovimientoCajaService;
import com.salespointfxadmin.www.service.SucursalService;
import com.salespointfxadmin.www.service.UsuarioService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginController implements Initializable {
	private final UsuarioService us;
	private final SpringFXMLLoader springFXMLLoader;
	private final SucursalService ss;
	private final MovimientoCajaService mcs;
	@FXML
	private Button btnAceptar;

	@FXML
	private Button btnCancelar;

	@FXML
	private PasswordField passUsuario;

	@FXML
	private TextField textUsuario;

	@FXML
	void aceptar(ActionEvent event) {
		try {
			Usuario u = us.findByusuario(textUsuario.getText(), passUsuario.getText());
			if (u != null) {
				Stage currentStage = (Stage) btnAceptar.getScene().getWindow();
				currentStage.close();
				if (ss.getSucursalActive() != null) {
					/*
					 * ESTOS SON COMPARACION PARA SABER SI HAY MOVIMIENTOS CAJA, FUNCIONAARA PARA EL
					 * DE VENTA
					 */
					/*
					 * MovimientoCaja mc = mcs.getLastMovmientoCaja(); if (mc != null) { if
					 * (mc.getTipoMovimientoCaja() == TipoMovimiento.APERTURA) { Alert alerta = new
					 * Alert(AlertType.CONFIRMATION); alerta.setTitle("Login Controller");
					 * alerta.setHeaderText("El movimiento caja es Apertura"); alerta.
					 * setContentText("Ya esta abierta caja. deberias abor el istema principal");
					 * alerta.showAndWait(); } else if (mc.getTipoMovimientoCaja() ==
					 * TipoMovimiento.CIERRE) { abrirCaja(); } } else { abrirCaja(); }
					 */
					starter();
				} else {
					seleccionarSucursal();
				}

			} else {
				throw new Exception("Usuario no encontrado");
			}
		} catch (Exception e) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Probelmas con esl usurio");
			alerta.setHeaderText("Uuario o contraseña invalidos");
			alerta.setContentText(e.getMessage());
			alerta.showAndWait();
		}
	}

	@FXML
	void cancelar(ActionEvent event) {
		Stage stage = (Stage) btnAceptar.getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	private void seleccionarSucursal() {
		try {
			FXMLLoader loader = springFXMLLoader.load("/fxml/seleccionarsucursal" + ".fxml");
			Parent root = loader.load();
			Stage newStage = new Stage();
			newStage.setTitle("Selecciona la sucursal");
			newStage.setScene(new Scene(root));
			newStage.setResizable(false);
			newStage.show();
		} catch (IOException e) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Login Controller Error");
			alerta.setHeaderText("No se puede cargar Seleccionar Sucursal");
			alerta.setContentText(e.getMessage() + "\n" + e.getCause());
			alerta.showAndWait();
		}

	}

	private void abrirCaja() {
		try {
			FXMLLoader loader = springFXMLLoader.load("/fxml/movimientocaja" + ".fxml");
			Parent root = loader.load();
			Stage newStage = new Stage();
			newStage.setTitle("Movimiento Caja");
			newStage.setScene(new Scene(root));
			newStage.setResizable(false);
			newStage.show();
		} catch (IOException e) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Login Controller Error");
			alerta.setHeaderText("No se puede cargar Abrir Caja");
			alerta.setContentText(e.getMessage() + "\n" + e.getCause());
			alerta.showAndWait();
		}
	}
	private void starter() {
		try {
			FXMLLoader loader = springFXMLLoader.load("/fxml/starter" + ".fxml");
			Parent root = loader.load();
			Stage newStage = new Stage();
			newStage.setTitle("Pagina principal");
			newStage.setScene(new Scene(root));
			newStage.setMinHeight(768);
			newStage.setMinWidth(1024);
			newStage.show();
		} catch (IOException e) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Login Controller Error");
			alerta.setHeaderText("No se puede cargar Starter");
			alerta.setContentText(e.getMessage() + "\n" + e.getCause());
			alerta.showAndWait();
		}
	}
}
