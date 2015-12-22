package io.picsou.report;



import io.picsou.App;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringApplicationConfiguration(App.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class JasperExecutionTest {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Test
	public void execute(){

		JasperReport jasperReport;
		try {
//			 jasperReport = JasperCompileManager.compileReport("/home/alexis/JaspersoftWorkspace/MyReports/Facture.jrxml");
			jasperReport = (JasperReport) JRLoader
					.loadObjectFromFile("/home/alexis/JaspersoftWorkspace/MyReports/Facture.jasper");
			// Parameters for report
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("contratId", 13L);
			parameters.put("UserInfoContent", "user info client");
			parameters.put("ClientInfosContent", "client info client");
			parameters.put("imagePath", "/home/alexis/Images/logoMaxence.jpg");
			parameters.put("factureNumero", "Facture-123-123");
			parameters.put("dateEdition", "01/01/2015");
			// DataSource
			// This is simple example, no database.
			// then using empty datasource.
			// JRDataSource dataSource = new JREmptyDataSource();
			System.out.println(jdbcTemplate.getDataSource()
			.getConnection().toString());
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, parameters, jdbcTemplate.getDataSource()
							.getConnection());

			// Make sure the output directory exists.
			File outDir = new File("/home/alexis/workspace/picsou/out");
			// outDir.mkdirs();

			// Export to PDF.
			JasperExportManager.exportReportToPdfFile(jasperPrint,
					"/home/alexis/workspace/picsou/out/StyledTextReport.pdf");

			JRRtfExporter exporter = new JRRtfExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleWriterExporterOutput(
					"/home/alexis/workspace/picsou/out/StyledTextReport.rtf"));
			exporter.exportReport();
			System.out.println("Done!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
