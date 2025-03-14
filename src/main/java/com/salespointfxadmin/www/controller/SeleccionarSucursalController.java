package com.salespointfxadmin.www.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.salespointfxadmin.www.SalesPointFxAdminApplication;
import com.salespointfxadmin.www.component.SpringFXMLLoader;
import com.salespointfxadmin.www.model.Sucursal;
import com.salespointfxadmin.www.service.MovimientoCajaService;
import com.salespointfxadmin.www.service.SucursalService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SeleccionarSucursalController implements Initializable {
	private final SucursalService ss;
	private final SpringFXMLLoader springFXMLLoader;
	private final MovimientoCajaService mcs;

	private ConfigurableApplicationContext context;

	@FXML
	private Button buttonSeleccionar;

	@FXML
	private ListView<Sucursal> lViewSucursales;

	private ObservableList<Sucursal> sucursalesObserbableList;

	@FXML
	void seleccionar(ActionEvent event) {
		try {
			Sucursal seleccionada = lViewSucursales.getSelectionModel().getSelectedItem();
			if (seleccionada != null) {
				seleccionada.setEstatusSucursal(true);
				ss.activarSucursal(seleccionada);

				Stage currenstage = (Stage) buttonSeleccionar.getScene().getWindow();
				currenstage.close();
				if (mcs.getLastMovmientoCaja() != null) {
					FXMLLoader loader = springFXMLLoader.load("/fxml/starter" + ".fxml");// new
					// FXMLLoader(getClass().getResource("/fxml/movimientocaja.fxml"));
// loader.setControllerFactory(context::getBean);
					Parent root = loader.load();

					Stage newStage = new Stage();
					newStage.setScene(new Scene(root));
					newStage.setTitle("Movimiento de Caja");
					newStage.showAndWait();
				} else {
					// Abrir la ventana de Movimiento Caja
					FXMLLoader loader = springFXMLLoader.load("/fxml/starter" + ".fxml");// new
																							// FXMLLoader(getClass().getResource("/fxml/movimientocaja.fxml"));
					// loader.setControllerFactory(context::getBean);
					Parent root = loader.load();

					Stage newStage = new Stage();
					newStage.setScene(new Scene(root));
					newStage.setTitle("Movimiento de Caja");
					newStage.showAndWait();
				}

			} else {
				Alert infoAlert = new Alert(AlertType.WARNING);
				infoAlert.setTitle("Alerta!!!");
				infoAlert.setHeaderText("No has seleccionado una sucursal");
				infoAlert.setContentText("selecciona una sucursal para proseguir");
				infoAlert.show();
			}

		} catch (Exception e) {
			Alert infoAlert = new Alert(AlertType.ERROR);
			infoAlert.setTitle("Seleccinora sucursal error!!!");
			infoAlert.setHeaderText("Error al seleccionar la susucrsal");
			infoAlert.setContentText(e.getMessage() + "\n" + e.getCause());
			infoAlert.show();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		context = SalesPointFxAdminApplication.getContext();
		sucursalesObserbableList = FXCollections.observableArrayList(ss.getAllSucursales());
		lViewSucursales.setItems(sucursalesObserbableList);

	}

}
