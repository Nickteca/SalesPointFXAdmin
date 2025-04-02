package com.salespointfxadmin.www.service.printer;

import java.io.IOException;

import javax.print.PrintService;

import org.springframework.stereotype.Service;

import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.escpos.EscPosConst.Justification;
import com.github.anastaciocintra.escpos.Style;
import com.github.anastaciocintra.escpos.Style.FontSize;
import com.github.anastaciocintra.output.PrinterOutputStream;
import com.salespointfxadmin.www.controller.RecoleccionController;
import com.salespointfxadmin.www.model.SucursalGasto;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImprimirGasto {
	private final ImpresoraTermica it;

	public void imprimirGasto(SucursalGasto sg) {
		try {
            PrintService printService = PrinterOutputStream.getDefaultPrintService();
            PrinterOutputStream printerOutputStream = new PrinterOutputStream(printService);
            EscPos escpos = new EscPos(printerOutputStream);

            // Estilos
            Style titleStyle = new Style().setBold(true).setFontSize(FontSize._2, FontSize._2).setJustification(Justification.Center);
            Style headerStyle = new Style().setBold(true).setFontSize(FontSize._1, FontSize._1);
            Style normalStyle = new Style().setFontSize(FontSize._1, FontSize._1);

            // Encabezado
            escpos.writeLF(titleStyle, "REGISTRO DE GASTO");
            escpos.writeLF("--------------------------------");

            // Datos del gasto
            escpos.writeLF(headerStyle, "Descripción: "+sg.getGasto().getDescripcionGasto());
            escpos.writeLF(normalStyle, "Monto: "+sg.getMontoGasto());
            escpos.writeLF(normalStyle, "Sucursal: "+sg.getSucursal().getNombreSucursal());
            escpos.writeLF(normalStyle, "Fecha: "+sg.getCreatedAt());
            escpos.writeLF(normalStyle, "Autorizado por: ");
            escpos.writeLF(normalStyle, "Recibido por: ");
            escpos.writeLF("--------------------------------");

            // Espacio para firmas
            escpos.writeLF("\n\n\n");
            escpos.writeLF("------------------------");
            escpos.writeLF("Firma Autorizado");
            escpos.writeLF("\n\n\n");
            escpos.writeLF("------------------------");
            escpos.writeLF("Firma Recibido");

            // Final
            escpos.feed(5);
            escpos.cut(EscPos.CutMode.FULL);
            escpos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
