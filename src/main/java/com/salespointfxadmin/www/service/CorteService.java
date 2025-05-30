package com.salespointfxadmin.www.service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.print.PrintService;

import org.springframework.stereotype.Service;

import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.escpos.EscPosConst;
import com.github.anastaciocintra.escpos.Style;
import com.github.anastaciocintra.output.PrinterOutputStream;
import com.salespointfxadmin.www.model.Corte;
import com.salespointfxadmin.www.model.CorteDetalle;
import com.salespointfxadmin.www.model.Empresa;
import com.salespointfxadmin.www.repositorie.CorteDetalleRepo;
import com.salespointfxadmin.www.repositorie.CorteRepo;
import com.salespointfxadmin.www.service.printer.Printer2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CorteService {
	private final CorteRepo cr;
	private final CorteDetalleRepo cdr;
	private final Printer2 p2;

	private static final DecimalFormat CANTIDAD_FORMAT = new DecimalFormat("0.#");
	private static final DecimalFormat PESO_FORMAT = new DecimalFormat("0.##");
	private static final DecimalFormat DINERO_FORMAT = new DecimalFormat("$0.##");

	public List<Corte> findByLocaldate(LocalDate inicio, LocalDate fin) {
		try {
			LocalDateTime i = inicio.atStartOfDay();
			LocalDateTime f = fin.atTime(23, 59, 59);
			log.info(i + " " + f);
			return cr.findByCierreBetweenAndSucursalEstatusSucursalTrue(i, f);
		} catch (Exception e) {
			throw e;
		}
	}

	public boolean imprimirCorte(Corte c) throws IOException {
		PrinterOutputStream printerOutputStream = null;
		EscPos escpos = null;
		float peso = 0;
		try {

			c.setListCorteDetalle(cdr.findByCorte(c));
			log.info("el corte es : " + c.getIdCorte());
			// log.info("Los detalles es : " + c.getListCorteDetalle().size());
			Optional<PrintService> ops = p2.obtenerImpresoraPorNombre();
			if (ops.isPresent()) {
				/* SE OBTINE LA IMPRESORA Y SE ASIGNA PAR APROCEDER A IMPRIMIR */
				PrintService impresora = ops.get();
				printerOutputStream = new PrinterOutputStream(impresora);
				escpos = new EscPos(printerOutputStream);

				// Estilos
				Style titulo = new Style().setFontName(Style.FontName.Font_B).setUnderline(Style.Underline.OneDotThick).setJustification(EscPosConst.Justification.Left_Default);
				Style subtitulo = new Style().setFontName(Style.FontName.Font_A_Default);
				Style header = new Style().setFontName(Style.FontName.Font_A_Default);
				Style header2 = new Style().setFontName(Style.FontName.Font_B);

				Empresa e = c.getSucursal().getEmpresa();

				escpos.writeLF(titulo, c.getSucursal().getEmpresa().getNombreEmpresa());
				escpos.writeLF(titulo, c.getSucursal().getNombreSucursal() + ", DIRECCION: " + "" + c.getSucursal().getEstadoSucursal() + "" + c.getSucursal().getCiudadSucursal() + " "
						+ c.getSucursal().getCalleSucursal());
				escpos.writeLF(titulo, "RFC: " + c.getSucursal().getEmpresa().getRfcEmpresa());
				escpos.writeLF(new Style().setFontName(Style.FontName.Font_B), "-----------------------------FECHA------------------------------");
				escpos.writeLF(titulo, "APERTURA: " + c.getApertuta());
				escpos.writeLF(titulo, "CIERRE  : " + c.getCierre());
				escpos.writeLF(new Style().setFontName(Style.FontName.Font_B), "-----------------------------FONDO------------------------------");
				escpos.writeLF(titulo, "SALDO ANTERIOR:" + DINERO_FORMAT.format(c.getInicial()));
				escpos.writeLF(titulo, "VENTAS        :" + DINERO_FORMAT.format(c.getVentas()));
				escpos.writeLF(titulo, "GASTOS        :" + DINERO_FORMAT.format(c.getGasto()));
				escpos.writeLF(titulo, "RECOLECCION   :" + DINERO_FORMAT.format(c.getRecoleccion()));
				escpos.writeLF(titulo, "SALDO FINAL   :" + DINERO_FORMAT.format(c.getSaldoFinal()));
				escpos.writeLF(new Style().setFontName(Style.FontName.Font_B), "-------------------------DETALLE FOLIOS-------------------------");
				escpos.writeLF(titulo, "FOLIO INICIAL :" + c.getFolioIncial());
				escpos.writeLF(titulo, "FOLIO FINAL   :" + c.getFolioFinal());
				escpos.writeLF(titulo, "NUMERO FOLIOS :" + c.getNumFolios());
				escpos.writeLF(new Style().setFontName(Style.FontName.Font_B), "------------------------DETALLES PRODUCTOS----------------------");

				// Utiliza un ancho fijo para columnas
				String formato = "%-30s %10s";
				// INICIAL
				escpos.writeLF(new Style().setJustification(EscPosConst.Justification.Center).setFontName(Style.FontName.Font_B), "=========================== INICIAL ===========================");
				escpos.writeLF(header2, String.format(formato, "PRODUCTO", "CANTIDAD"));
				for (CorteDetalle cd : c.getListCorteDetalle()) {
					if (cd.getInicial() > 0) {
						escpos.writeLF(header2, String.format(formato, cd.getSucursalProducto().toString(), CANTIDAD_FORMAT.format(cd.getInicial())));
					}
				}

// ENTRADAS
				escpos.writeLF(new Style().setJustification(EscPosConst.Justification.Center).setFontName(Style.FontName.Font_B), "=========================== ENTRADAS ===========================");
				escpos.writeLF(header2, String.format(formato, "PRODUCTO", "CANTIDAD"));
				for (CorteDetalle cd : c.getListCorteDetalle()) {
					if (cd.getEntrada() > 0) {
						escpos.writeLF(header2, String.format(formato, cd.getSucursalProducto().toString(), CANTIDAD_FORMAT.format(cd.getEntrada())));
					}
				}
// SALIDAS
				escpos.writeLF(new Style().setJustification(EscPosConst.Justification.Center).setFontName(Style.FontName.Font_B), "========================== SALIDAS ===========================");
				escpos.writeLF(header2, String.format(formato, "PRODUCTO", "CANTIDAD"));
				for (CorteDetalle cd : c.getListCorteDetalle()) {
					if (cd.getSalida() > 0) {
						escpos.writeLF(header2, String.format(formato, cd.getSucursalProducto().toString(), CANTIDAD_FORMAT.format(cd.getSalida())));
					}
				}
// TRASPASO ENTRADA
				escpos.writeLF(new Style().setJustification(EscPosConst.Justification.Center).setFontName(Style.FontName.Font_B), "======================= TRASPASO ENTRADA =======================");
				escpos.writeLF(header2, String.format(formato, "PRODUCTO", "CANTIDAD"));
				for (CorteDetalle cd : c.getListCorteDetalle()) {
					if (cd.getTraspasoEntrada() > 0) {
						escpos.writeLF(header2, String.format(formato, cd.getSucursalProducto().toString(), CANTIDAD_FORMAT.format(cd.getTraspasoEntrada())));
					}
				}
// TRASPASO SALIDA
				escpos.writeLF(new Style().setJustification(EscPosConst.Justification.Center).setFontName(Style.FontName.Font_B), "======================= TRASPASO SALIDA =======================");
				escpos.writeLF(header2, String.format(formato, "PRODUCTO", "CANTIDAD"));
				for (CorteDetalle cd : c.getListCorteDetalle()) {
					if (cd.getTraspasoSalida() > 0) {
						escpos.writeLF(header2, String.format(formato, cd.getSucursalProducto().toString(), CANTIDAD_FORMAT.format(cd.getTraspasoSalida())));
					}
				}
// VENTAS
				escpos.writeLF(header2, "============================ VENTAS ============================");
				String formatoVenta = "%-33s %12s %12s";
				escpos.writeLF(header2, String.format(formatoVenta, "PRODUCTO", "CANTIDAD", "PESO"));
				for (CorteDetalle cd : c.getListCorteDetalle()) {
					if (cd.getVenta() > 0) {
						String pesoTexto = cd.getPeso() != 0 ? PESO_FORMAT.format(cd.getPeso()) + "Kg" : "";
						escpos.writeLF(header2, String.format(formatoVenta, cd.getSucursalProducto().toString(), CANTIDAD_FORMAT.format(cd.getVenta()), pesoTexto));
						peso += cd.getPeso();
					}
				}
// CANCELACIONES
				escpos.writeLF(header2, "========================== CANCELACIONES =======================");
				escpos.writeLF(header2, String.format(formato, "PRODUCTO", "CANTIDAD"));
				for (CorteDetalle cd : c.getListCorteDetalle()) {
					if (cd.getCanceladas() > 0) {
						escpos.writeLF(header2, String.format(formato, cd.getSucursalProducto().getProducto(), CANTIDAD_FORMAT.format(cd.getCanceladas())));
					}
				}
// EXISTENCIA FINAL
				escpos.writeLF(header2, "======================= EXISTENCIA FINAL =======================");
				escpos.writeLF(header2, String.format(formato, "PRODUCTO", "CANTIDAD"));
				for (CorteDetalle cd : c.getListCorteDetalle()) {
					if (cd.getExistencia() != 0) {
						escpos.writeLF(header2, String.format(formato, cd.getSucursalProducto().toString(), CANTIDAD_FORMAT.format(cd.getExistencia())));
					}

				}
				escpos.writeLF(new Style().setFontName(Style.FontName.Font_B), "________________________________________________________________");
				escpos.writeLF(header2, String.format("PESO TOTAL: %.3f", peso));
				escpos.feed(5);
				escpos.cut(EscPos.CutMode.FULL);
				// Suponiendo que escpos es una instancia de una clase que tiene el método write
				byte[] abrirCaja = new byte[] { 0x1B, 0x70, 0x00, 0x19, (byte) 0xFA };
				escpos.write(abrirCaja, 0, abrirCaja.length); // 0 es el offset y abrirCaja.length es la longitud
				return true;

			} else {
				log.error("❌ No existe la impresora");
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (escpos != null) {
					escpos.close();
				}
				if (printerOutputStream != null) {
					printerOutputStream.close();
				}
			} catch (IOException ex) {
				throw ex;
			}
		}

	}
}
