package com.salespointfxadmin.www.service.report;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
@RequiredArgsConstructor
public class JasperReportService {
	private final DataSource dataSource;

  

    public byte[] generateReport(Integer idPedido) throws Exception {
        // Cargar el archivo .jasper
        InputStream reportStream =  new ClassPathResource("/pdf/PedidoReport.jasper").getInputStream();
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportStream);

        // Configurar parámetros
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("idP", idPedido); // Pasar el parámetro

        // Llenar el reporte con datos
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource.getConnection());

        // Exportar a PDF
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
