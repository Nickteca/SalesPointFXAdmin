package com.salespointfxadmin.www.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.salespointfxadmin.www.service.MovimientoCajaService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MovimientoCaja implements Initializable {
	private final MovimientoCajaService mcs;

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

	}

	@FXML
	void cancelar(ActionEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (mcs.getLastMovmientoCaja() == null) {
			this.txtSaldoAnterior.setEditable(true);
		}

	}

}
