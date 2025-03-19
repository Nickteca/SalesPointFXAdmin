package com.salespointfxadmin.www.controller;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.salespointfxadmin.www.component.SpringFXMLLoader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
	private Button btnMovimientoInventario;

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
}
