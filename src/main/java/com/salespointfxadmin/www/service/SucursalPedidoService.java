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
import com.salespointfxadmin.www.service.email.EmailService;
import com.salespointfxadmin.www.service.printer.ImprimirPedido;
import com.salespointfxadmin.www.service.report.JasperReportService;
import com.salespointfxadmin.www.service.wats.WhatsAppService;

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
	private final WhatsAppService ws;
	private final EmailService es;

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
			// System.out.println(file.getName());
			// System.out.println(file.getAbsolutePath());
			String mensage = "";
			for (SucursalPedidoDetalle spd : lspd) {
				mensage = mensage += "	" + spd.getSucursalProducto().getProducto().getNombreProducto() + "		" + spd.getCantidad() + "\n";
			}
			ws.sendWhatsAppMessage("4341327947", mensage, file.getAbsolutePath(), file.getName());
			es.sendEmail("isaaclunaavila@gmail.com", "Pedido", "Pedido de una sucursal", file.getAbsolutePath());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
