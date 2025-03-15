package com.salespointfxadmin.www.controller;


import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.salespointfxadmin.www.model.Sucursal;
import com.salespointfxadmin.www.model.SucursalProducto;
import com.salespointfxadmin.www.service.ProductoService;
import com.salespointfxadmin.www.service.SucursalProductoService;
import com.salespointfxadmin.www.service.SucursalService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaqueteController implements Initializable {
	private final ProductoService ps;
	private final SucursalProductoService sps;
	private final SucursalService ss;

    @FXML
    private ListView<SucursalProducto> lViewPaquetes;
    @FXML
    private VBox vBoxProductos;
    private ObservableList<SucursalProducto> oLSucursalProducto;


    @FXML
    void setOnDragDetected(MouseEvent event) {
    	if (!lViewPaquetes.getSelectionModel().isEmpty()) {
            Dragboard db = lViewPaquetes.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(lViewPaquetes.getSelectionModel().getSelectedItem().toString());
            db.setContent(content);
            event.consume();
        }
    }
    @FXML
    void setOnDragDropped(DragEvent event) {
    	 Dragboard db = event.getDragboard();
         if (db.hasString()) {
        	 agregarProductoAlContenedor(db.getString());
             event.setDropCompleted(true);
         } else {
             event.setDropCompleted(false);
         }
         event.consume();
    }
    @FXML
    void setOnDragOver(DragEvent event) {
    	if (event.getGestureSource() != vBoxProductos && event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }
   

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		oLSucursalProducto = FXCollections.observableArrayList(sps.getAllProductosSucursalActive(ss.getSucursalActive()));
		lViewPaquetes.setItems(oLSucursalProducto);
	}

	private void agregarProductoAlContenedor(String nombreProducto) {
        // HBox para contener el producto y la cantidad
        HBox hbox = new HBox(10);
        
        Label label = new Label(nombreProducto);
        TextField cantidadTextField = new TextField("1"); // Cantidad por defecto
        

        Button eliminarBtn = new Button("❌");
        eliminarBtn.setOnAction(e -> vBoxProductos.getChildren().remove(hbox));

        hbox.getChildren().add(label);
        hbox.getChildren().add(cantidadTextField);
        hbox.getChildren().add(eliminarBtn);
        vBoxProductos.getChildren().add(hbox);
    }
}
