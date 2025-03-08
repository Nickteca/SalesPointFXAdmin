package com.salespointfxadmin.www.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.salespointfxadmin.www.model.Sucursal;
import com.salespointfxadmin.www.service.SucursalService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StarterController implements Initializable {
	private final SucursalService ss;

	@FXML
	private Button buttonSeleccionar;

	@FXML
	private ListView<Sucursal> lViewSucursales;

	private ObservableList<Sucursal> sucursalesObserbableList;

	@FXML
	void seleccionar(ActionEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sucursalesObserbableList = FXCollections.observableArrayList(ss.getAllSucursales());
		lViewSucursales.setItems(sucursalesObserbableList);

	}

}
