package com.salespointfxadmin.www;

import java.io.IOException;

import org.springframework.context.ConfigurableApplicationContext;

import com.salespointfxadmin.www.model.MovimientoCaja;
import com.salespointfxadmin.www.service.MovimientoCajaService;
import com.salespointfxadmin.www.service.SucursalService;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SalesPointFxAdminApplicationFx extends Application {

	private MovimientoCajaService mcs;
	private SucursalService ss;
	private ConfigurableApplicationContext context;

	@Override
	public void init() throws Exception {
		context = SalesPointFxAdminApplication.getContext();
		mcs = context.getBean(MovimientoCajaService.class);
		ss = context.getBean(SucursalService.class);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		if (ss.getSucursalActive() != null) {
			MovimientoCaja mc = mcs.getLastMovmientoCaja();
			if (mc != null) {
				if (mc.getTipoMovimientoCaja().equals("APERTURA")) {
					Alert alerta = new Alert(AlertType.INFORMATION);
					alerta.setTitle("esta abierta la caja");
					alerta.setHeaderText("Apertura");
					alerta.setContentText("Apertura");
					alerta.showAndWait();
				} else {
					if (mc.getTipoMovimientoCaja().equals("CIERRE")) {
						Alert alerta = new Alert(AlertType.INFORMATION);
						alerta.setTitle("esta CERRADA la caja");
						alerta.setHeaderText("cerrada");
						alerta.setContentText("cerrada");
						alerta.showAndWait();
					}
				}
			} else {
				abrirCaja();
			}
		} else {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/seleccionarsucursal.fxml"));
			// Permitir que Spring gestione la inyección de dependencias en el controlador
			loader.setControllerFactory(context::getBean);
			Parent root = loader.load();

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Sistema de Ventas - Admin");
			primaryStage.show();
		}
	}

	@Override
	public void stop() throws Exception {
		context.close(); // Cerrar el contexto de Spring Boot
	}

	private void abrirCaja() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/movimientocaja.fxml"));
			// Permitir que Spring gestione la inyección de dependencias en el controlador
			loader.setControllerFactory(context::getBean);
			Parent root = loader.load();
			Scene scene = new Scene(root, 500, 400);
			Stage primaryStage = new Stage();
			primaryStage.setScene(scene);
			primaryStage.setTitle("Abiri Caja");
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
