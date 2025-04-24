package com.salespointfxadmin.www.controller;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.salespointfxadmin.www.component.SpringFXMLLoader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StarterController {
	private final SpringFXMLLoader springFXMLLoader;
	@FXML
	private BorderPane bPanePrimcipal;
	@FXML
	private Button btnProductos;
	@FXML
	private Button btnCrearPaquetes;
	@FXML
	private Button btnGastos;
	@FXML
	private Button btnMovimientoInventario;
	@FXML
	private Button btnRecoleccion;
	@FXML
	private Button btnPedido;

	@FXML
	void cancelarVenta(ActionEvent event) {
		try {
			FXMLLoader loader = springFXMLLoader.load("/fxml/modal/cancelarVenta.fxml");
			StackPane root = loader.load();

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			// stage.initStyle(StageStyle.UNDECORATED); // Ventana sin bordes
			stage.setTitle("Cancelar Venta");
			stage.setScene(new Scene(root));
			stage.setResizable(false);
			stage.showAndWait();

			// Recargar la tabla después de que se cierre la ventana
			// iniciarTablaMovimientoInventario();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void pedido(ActionEvent event) {
		cargarVista("/fxml/pedido.fxml");
	}

	@FXML
	void productos(ActionEvent event) {
		cargarVista("/fxml/productos.fxml");
	}

	@FXML
	void movimiento(ActionEvent event) {
		cargarVista("/fxml/movimientoinventario.fxml");
	}

	@FXML
	void crearPaquetes(ActionEvent event) {
		cargarVista("/fxml/paquetes.fxml");
	}

	@FXML
	void gastos(ActionEvent event) {
		cargarVista("/fxml/gastos.fxml");
	}

	@FXML
	void recoleccion(ActionEvent event) {
		cargarVista("/fxml/recoleccion.fxml");
	}

	private void cargarVista(String fxmlPath) {
		try {
			FXMLLoader fxml = springFXMLLoader.load(fxmlPath);
			AnchorPane view = fxml.load();
			// BorderPane borderPane = (BorderPane) currentStage.getScene().getRoot();
			bPanePrimcipal.setCenter(view);

		} catch (IOException e) {
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("StarterController Error!!!");
			error.setHeaderText("Error al cargar la vista");
			error.setContentText(e.getMessage() + "\n" + e.getCause());
			error.show();
			e.printStackTrace();
		}
	}

	/*
	 * private void cargarModal(String fxmlPath) { try { FXMLLoader loader =
	 * springFXMLLoader.load(fxmlPath); StackPane root = (StackPane) loader.load();
	 * 
	 * Stage stage = new Stage(); stage.initModality(Modality.APPLICATION_MODAL); //
	 * stage.initStyle(StageStyle.UNDECORATED); // Ventana sin bordes
	 * stage.setTitle("registrar "); stage.setScene(new Scene(root));
	 * stage.setResizable(false); stage.showAndWait();
	 * 
	 * // Recargar la tabla después de que se cierre la ventana //
	 * iniciarTablaMovimientoInventario(); // cargarSuursalgasto(); } catch
	 * (IOException e) { e.printStackTrace(); } }
	 */
}
