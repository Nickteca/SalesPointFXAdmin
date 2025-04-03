package com.salespointfxadmin.www.service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.salespointfxadmin.www.model.SucursalPedido;
import com.salespointfxadmin.www.model.SucursalPedidoDetalle;
import com.salespointfxadmin.www.repositorie.SucursalPedidoDetalleRepo;
import com.salespointfxadmin.www.repositorie.SucursalPedidoRepo;
import com.salespointfxadmin.www.service.printer.ImprimirPedido;
import com.salespointfxadmin.www.service.report.JasperReportService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SucursalPedidoService {
	private final SucursalPedidoRepo spr;
	private final SucursalPedidoDetalleRepo spdr;
	private final SucursalService ss;
	private final ImprimirPedido p;
	private final JasperReportService jrs;

	@Transactional
	public SucursalPedido save(SucursalPedido sp) {
		sp.setCreatedAt(LocalDateTime.now());
		sp.setSucursal(ss.getSucursalActive());
		p.imprimirPedido(sp);

		return spr.save(sp);
	}

	public List<SucursalPedido> fingBySucursalEstatusSucursalTrueAndCreatedatBeetween(LocalDate inicio, LocalDate fin) {
		return spr.findBySucursalEstatusSucursalTrueAndCreatedAtBetween(inicio.atStartOfDay(), fin.atTime(23, 59, 59));
	}

	public void imprimir(SucursalPedido sp) {
		List<SucursalPedidoDetalle> lspd = spdr.findBySucursalPedido(sp);
		sp.setListSucursalpedidoDetalle(lspd);
		p.imprimirPedido(sp);
		try {
			byte[] pdf = jrs.generateReport(sp.getSucursalPedido());
			File file = new File("pedido_" + sp.getSucursalPedido() + ".pdf");
			try (FileOutputStream fos = new FileOutputStream(file)) {
				fos.write(pdf);
			}
			System.out.println("Reporte generado en: " + file.getAbsolutePath());
			System.out.println("Este es Id:" + sp.getSucursalPedido());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
