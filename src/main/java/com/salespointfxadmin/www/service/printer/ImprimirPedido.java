package com.salespointfxadmin.www.service.printer;

import java.util.List;

import javax.print.PrintService;

import org.springframework.stereotype.Service;

import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.escpos.EscPosConst;
import com.github.anastaciocintra.escpos.EscPosConst.Justification;
import com.github.anastaciocintra.escpos.Style;
import com.github.anastaciocintra.escpos.Style.FontName;
import com.github.anastaciocintra.output.PrinterOutputStream;
import com.salespointfxadmin.www.model.SucursalPedido;
import com.salespointfxadmin.www.model.SucursalPedidoDetalle;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImprimirPedido {
	private final ImpresoraTermica it;

	public void imprimirPedido(SucursalPedido sp) {
		try {
			PrintService defaultPrintService = it.impresoraTermicaDefault();
			PrinterOutputStream printerOutputStream = new PrinterOutputStream(defaultPrintService);
			EscPos escpos = new EscPos(printerOutputStream);

			// 🔹 ESTILO: Título (Nombre de la empresa)
			Style titleStyle = new Style().setBold(true).setFontSize(Style.FontSize._2, Style.FontSize._2).setJustification(EscPosConst.Justification.Center);
			// 🔹 subtitulo: Título (Nombre de la empresa)
			Style subtitulo = new Style().setBold(true).setFontSize(Style.FontSize._2, Style.FontSize._2).setJustification(EscPosConst.Justification.Center);
			Style headerContent = new Style().setFontName(FontName.Font_C).setJustification(Justification.Center);
			// No se si eimprima una linea
			Style linea = new Style().setLineSpacing(1);

			Style fontNomal = new Style().setBold(true).setFontName(FontName.Font_B);
			Style fontA = new Style().setFontName(FontName.Font_A_Default);

			escpos.writeLF(subtitulo, "PEDIDO: " + sp.getSucursal().getNombreSucursal());
			// 🏪 IMPRIMIR ENCABEZADO
			escpos.writeLF(headerContent, sp.getSucursal().getEmpresa().getNombreEmpresa());
			// escpos.writeLF(headerContent, sp.getSucursal().getCalleSucursal() + " " +
			// sp.getSucursal().getCiudadSucursal() + " " +
			// sp.getSucursal().getEstadoSucursal());

			// String line = String.format("%-40s %24s", "PRODUCTO", "CANTIDAD");
			escpos.feed(1);
			escpos.writeLF(fontA, String.format("%-30s %-17s", "PRODUCTO", "CANTIDAD"));
			escpos.writeLF(fontNomal, "----------------------------------------------------------------");
			List<SucursalPedidoDetalle> spd = sp.getListSucursalpedidoDetalle();

			for (SucursalPedidoDetalle sucursalPedidoDetalle : spd) {
				escpos.writeLF(fontA, String.format("%-30s %7.0f", sucursalPedidoDetalle.getSucursalProducto().getProducto().getNombreProducto(), sucursalPedidoDetalle.getCantidad()));
			}
			escpos.feed(4);
			escpos.cut(EscPos.CutMode.FULL);

			escpos.close();

		} catch (Exception e) {
			Alert infoAlert = new Alert(AlertType.ERROR);
			infoAlert.setTitle("Problema de impresora");
			infoAlert.setHeaderText("Alun detalle pasa con la impresosar, Se registro la recolecion pero no saco ticket");
			infoAlert.setContentText(e.getMessage() + " " + e.getCause());
			infoAlert.showAndWait();
		}
	}
}
