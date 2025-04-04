package com.salespointfxadmin.www.service.report;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
@RequiredArgsConstructor
public class JasperReportService {
	private final DataSource dataSource;

	public byte[] generateReport(Integer idPedido) throws Exception {
		// Cargar el archivo .jasper
		InputStream reportStream = new ClassPathResource("/pdf/Pedido.jasper").getInputStream();
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportStream);

		// Configurar parámetros
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("idP", idPedido); // Pasar el parámetro
		parameters.put("REPORT_CONNECTION", dataSource.getConnection());

		// Llenar el reporte con datos
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource.getConnection());
		// JasperViewer.viewReport(jasperPrint, false);
		System.out.println("Conexión a la base de datos: " + dataSource.getConnection());

		// Exportar a PDF
		// JasperPrintManager.printReport(jasperPrint, false);
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}
}
