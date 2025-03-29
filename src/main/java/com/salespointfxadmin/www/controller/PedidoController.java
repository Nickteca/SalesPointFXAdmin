package com.salespointfxadmin.www.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.salespointfxadmin.www.model.Producto;
import com.salespointfxadmin.www.service.ProductoService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PedidoController implements Initializable {
	private final ProductoService ps;

    @FXML
    private ListView<Producto> lViewProductos;
    private ObservableList<Producto> olp= FXCollections.observableArrayList();
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		olp = FXCollections.observableArrayList(ps.getProductos());
		lViewProductos.setItems(olp);
	}
    
    

}
