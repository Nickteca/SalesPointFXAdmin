package com.salespointfxadmin.www.service.printer;

import javax.print.PrintService;

import org.springframework.stereotype.Service;

import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.escpos.EscPosConst;
import com.github.anastaciocintra.escpos.EscPosConst.Justification;
import com.github.anastaciocintra.escpos.Style;
import com.github.anastaciocintra.escpos.Style.FontName;
import com.github.anastaciocintra.escpos.Style.FontSize;
import com.github.anastaciocintra.output.PrinterOutputStream;
import com.salespointfxadmin.www.model.SucursalRecoleccion;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImprimirRecoleccion {
	private final ImpresoraTermica it;

	public void imprimirRecoleccion(SucursalRecoleccion sr) {
		try {
			PrintService defaultPrintService = it.impresoraTermicaDefault();

			// Crear el stream de la impresora
			PrinterOutputStream printerOutputStream = new PrinterOutputStream(defaultPrintService);
			EscPos escpos = new EscPos(printerOutputStream);

			// 🔹 ESTILO: Título (Nombre de la empresa)
			Style titleStyle = new Style().setBold(true).setFontSize(Style.FontSize._2, Style.FontSize._2).setJustification(EscPosConst.Justification.Center);
			// @eSTILO PARA SUBTITULOS
			Style subtitleStyle = new Style().setBold(true).setFontSize(FontSize._1, FontSize._1).setJustification(Justification.Center);
			Style headercontete = new Style().setFontName(FontName.Font_C).setJustification(Justification.Left_Default);
			// @eSTILO PARA SUBTITULOS
			Style headerStyle = new Style().setBold(true).setFontSize(FontSize._1, FontSize._1);

			// Style fontA = new Style().setFontName(Style.FontName.Font_A_Default) //
			// Usa Font A (normal) .setFontSize(Style.FontSize._1, Style.FontSize._1);

			Style fontB = new Style().setFontName(Style.FontName.Font_B).setBold(true).setFontSize(FontSize._1, FontSize._1);
			Style fontB2 = new Style().setFontName(Style.FontName.Font_B).setBold(true);
			Style fontA = new Style().setFontName(Style.FontName.Font_A_Default).setBold(true);
			// Usa Font B (más pequeño)

			/*
			 * // 🔹 ESTILO: Subtítulo (Sucursal y dirección) Style subtitleStyle = new
			 * Style().setFontSize(Style.FontSize._1,
			 * Style.FontSize._1).setJustification(EscPosConst.Justification.Left_Default);
			 * 
			 * // 🔹 ESTILO: Encabezado de productos Style headerStyle = new
			 * Style().setBold(true).setFontSize(Style.FontSize._1,
			 * Style.FontSize._1).setJustification(EscPosConst.Justification.Left_Default);
			 * 
			 * // 🔹 ESTILO: Texto normal para los productos Style productStyle = new
			 * Style().setFontSize(Style.FontSize._1,
			 * Style.FontSize._1).setJustification(EscPosConst.Justification.Left_Default);
			 * 
			 * // 🔹 ESTILO: Total (Negrita y tamaño grande) Style totalStyle = new
			 * Style().setBold(true).setFontSize(Style.FontSize._2,
			 * Style.FontSize._2).setJustification(EscPosConst.Justification.Right);
			 * 
			 * // Style fontA = new Style().setFontName(Style.FontName.Font_A_Default) //
			 * Usa Font A (normal) .setFontSize(Style.FontSize._1, Style.FontSize._1);
			 * 
			 * Style fontB = new Style().setFontName(Style.FontName.Font_B).setBold(true);
			 * // Usa Font B (más pequeño)
			 */

			// 🏪 IMPRIMIR ENCABEZADO
			escpos.writeLF(titleStyle, "RECOLECCION: " + sr.getSucursal().getNombreSucursal());
			escpos.writeLF(fontA.setJustification(Justification.Center), sr.getSucursal().getEmpresa().getNombreEmpresa());
			// escpos.writeLF(headercontete, sr.getSucursal().getCalleSucursal() + " " +
			// sr.getSucursal().getCiudadSucursal() + " " +
			// sr.getSucursal().getEstadoSucursal());

			/*
			 * escpos.writeLF(headerStyle, String.format("%-24s %5s %8s", "Billete",
			 * "Cantidad", "subtotal")); escpos.writeLF(fontB,
			 * "----------------------------------------------------------------");
			 * List<Billete> lb = sr.getListBillete();
			 * lb.sort(Comparator.comparing(Billete::getBillete).reversed()); for (Billete
			 * rb : lb) { // if (rb.getCantidad() > 0) { String line =
			 * String.format("%-20s %10s %10s", rb.getBillete(), rb.getCantidad(), "$" +
			 * rb.getSubtotal()); escpos.writeLF(line); // } } escpos.writeLF(fontB,
			 * "----------------------------------------------------------------");
			 * escpos.writeLF(headerStyle.setBold(true), String.format("%-22s %5s %8s %8s",
			 * "", "", "Total", "$" + String.format("%,.0f", sr.getTotalRecoleccion())));
			 * 
			 * // Cortar papel y cerrar conexión escpos.feed(5);
			 * escpos.cut(EscPos.CutMode.FULL);
			 * 
			 * escpos.close();
			 */

		} catch (Exception e) {
			Alert infoAlert = new Alert(AlertType.ERROR);
			infoAlert.setTitle("Problema de impresora");
			infoAlert.setHeaderText("Alun detalle pasa con la impresosar, Se registro la recolecion pero no saco ticket");
			infoAlert.setContentText(e.getMessage() + " " + e.getCause());
			infoAlert.showAndWait();
		} finally {

		}
	}
}
