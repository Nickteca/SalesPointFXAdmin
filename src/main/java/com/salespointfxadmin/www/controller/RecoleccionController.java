package com.salespointfxadmin.www.controller;

import java.time.LocalDateTime;

import com.salespointfxadmin.www.model.Sucursal;
import com.salespointfxadmin.www.model.SucursalRecoleccion;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class RecoleccionController {

	@FXML
	private Button btnBuscar;

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnRegistrar;

	@FXML
	private TableColumn<SucursalRecoleccion, LocalDateTime> columnFecha;

	@FXML
	private TableColumn<SucursalRecoleccion, Integer> columnId;

	@FXML
	private TableColumn<SucursalRecoleccion, Sucursal> columnSucursal;

	@FXML
	private TableColumn<SucursalRecoleccion, Float> columnTotal;

	@FXML
	private DatePicker dPickerFin;

	@FXML
	private DatePicker dPickerInicio;

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
	private TextField tFieldX1;

	@FXML
	private TextField tFieldX10;

	@FXML
	private TextField tFieldX100;

	@FXML
	private TextField tFieldX1000;

	@FXML
	private TextField tFieldX2;

	@FXML
	private TextField tFieldX200;

	@FXML
	private TextField tFieldX5;

	@FXML
	private TextField tFieldX50;

	@FXML
	private TextField tFieldX500;

	@FXML
	private TableView<SucursalRecoleccion> tViewSucursalRecoleccion;

	@FXML
	void buscar(ActionEvent event) {

	}

	@FXML
	void cancelar(ActionEvent event) {

	}

	@FXML
	void registrar(ActionEvent event) {

	}

}