package com.salespointfxadmin.www.service.printer;

import javax.print.PrintService;

import org.springframework.stereotype.Service;

import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.output.PrinterOutputStream;
import com.salespointfxadmin.www.controller.RecoleccionController;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImprimirGasto {
	private final ImpresoraTermica it;

	public void imprimirGasto(RecoleccionController r) {
		try {
			PrintService defaultPrintService = it.impresoraTermicaDefault();
			PrinterOutputStream printerOutputStream = new PrinterOutputStream(defaultPrintService);
			EscPos escpos = new EscPos(printerOutputStream);

		} catch (Exception e) {
			Alert infoAlert = new Alert(AlertType.ERROR);
			infoAlert.setTitle("Problema de impresora");
			infoAlert.setHeaderText("Alun detalle pasa con la impresosar, Se registro la recolecion pero no saco ticket");
			infoAlert.setContentText(e.getMessage() + " " + e.getCause());
			infoAlert.showAndWait();
		}
	}
}
