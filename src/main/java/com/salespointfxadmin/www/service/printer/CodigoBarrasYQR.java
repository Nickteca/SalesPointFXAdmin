package com.salespointfxadmin.www.service.printer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.escpos.Style;
import com.github.anastaciocintra.escpos.barcode.BarCode;
import com.github.anastaciocintra.escpos.barcode.BarCode.BarCodeSystem;
import com.github.anastaciocintra.escpos.barcode.QRCode;
import com.github.anastaciocintra.output.PrinterOutputStream;

public class CodigoBarrasYQR {
	public static void codigodebarraas(String impresora) {
		try {
			PrintService printService = findPrintService(impresora);
			if (printService == null) {
				System.out.println("Impresora no encontrada.");
				return;
			}

			// Crear un buffer para escribir los datos de impresión
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			EscPos escpos = new EscPos(baos);

			escpos.writeLF(new Style().setFontSize(Style.FontSize._2, Style.FontSize._2), "TICKET DE VENTA");
			escpos.feed(2);
			escpos.writeLF("Producto 1       $10.00");
			escpos.writeLF("Producto 2       $15.00");
			escpos.feed(2);
			escpos.writeLF(new Style().setBold(true), "TOTAL:        $25.00");
			escpos.feed(2);

			// Agregar un código de barras (Corrección)
			escpos.writeLF("Código de barras:");
			escpos.feed(1);
			BarCode barcode = new BarCode();
			barcode.setSystem(BarCodeSystem.CODE39_A);
			escpos.write(barcode, "123456"); // writeBarCode(barcode); // ✅ Método correcto

			escpos.feed(5); // Avanzar el papel
			escpos.cut(EscPos.CutMode.FULL);

			escpos.close();

			// Convertir los datos del ticket en un documento imprimible
			byte[] data = baos.toByteArray();
			DocPrintJob job = printService.createPrintJob();
			Doc doc = new SimpleDoc(data, DocFlavor.BYTE_ARRAY.AUTOSENSE, null);
			PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
			job.print(doc, attributes);

			System.out.println("Ticket impreso correctamente.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static PrintService findPrintService(String printerName) {
		PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
		for (PrintService service : services) {
			if (service.getName().equalsIgnoreCase(printerName)) {
				return service;
			}
		}
		return null;
	}

	public void codigoBarras() {
		try {
			// Buscar la impresora predeterminada
			PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
			if (printService == null) {
				System.out.println("No se encontró una impresora.");
				return;
			}

			// Crear el stream de la impresora
			PrinterOutputStream printerOutputStream = new PrinterOutputStream(printService);
			EscPos escpos = new EscPos(printerOutputStream);

			// Crear y configurar el código QR
			QRCode qrCode = new QRCode();
			qrCode.setSize(10); // Tamaño del QR (1-16)
			qrCode.setModel(QRCode.QRModel._1_Default); // Modelo QR
			qrCode.setErrorCorrectionLevel(QRCode.QRErrorCorrectionLevel.QR_ECLEVEL_M_Default); // Nivel de corrección
			qrCode.setJustification(EscPos.Justification.Center); // Centrar QR

			// Enviar el QR a la impresora
			escpos.write(qrCode, "https://www.tiendaejemplo.com");

			escpos.feed(5); // Espaciado
			escpos.cut(EscPos.CutMode.FULL); // Corte de papel
			escpos.close();

			System.out.println("Código QR impreso correctamente.");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
