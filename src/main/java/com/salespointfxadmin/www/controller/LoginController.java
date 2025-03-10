package com.salespointfxadmin.www.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@Component
public class LoginController implements Initializable {
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

	}

	@FXML
	void cancelar(ActionEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
}
