package io.picsou.service.report;

import io.picsou.config.ReportConfiguration;
import io.picsou.controller.CatalogueController;
import io.picsou.domain.Client;
import io.picsou.domain.UserInformation;
import io.picsou.service.ClientService;
import io.picsou.service.UserInformationService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.jfree.util.Log;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class FactureGenerationService {


	private final Logger log = LoggerFactory
			.getLogger(FactureGenerationService.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	UserInformationService userInformationService;

	@Autowired
	ReportConfiguration reportConfiguration;
	
	@Autowired
    private ResourceLoader resourceLoader;

	public void generate(Long clientId, Long contratId, String type) throws JRException, SQLException, IOException {
		
	    Resource resource = resourceLoader.getResource(reportConfiguration.getFolderReportResources()+"/Facture.jasper");
		
	    JasperReport jasperReport = (JasperReport) JRLoader.loadObject(resource.getInputStream());
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		Client client= clientService.getClient(clientId);
		UserInformation userInformation = userInformationService.findById(1L);
		
		DateTime d = DateTime.now();
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/YYYY");

		parameters.put("contratId", contratId);
		parameters.put("UserInfoContent", formatUserInfoContent(userInformation));
		parameters.put("ClientInfosContent", formatClientInfosContent(client));
		parameters.put("imagePath", reportConfiguration.getFolderReportResources()+"/images/logoMaxence.jpg");
		parameters.put("factureNumero", "numéro "+contratId);
		parameters.put("dateEdition", dtf.print(d));

		JasperPrint jasperPrint = JasperFillManager.fillReport(
				jasperReport, parameters, jdbcTemplate.getDataSource().getConnection());

		// Make sure the output directory exists.
		File outDir = new File(reportConfiguration.getFolderReportOut());
		outDir.mkdirs();

		// Export to PDF.
		JasperExportManager.exportReportToPdfFile(jasperPrint,
				reportConfiguration.getFolderReportOut()+"/"+"Facture_"+clientId+"_"+contratId+".pdf");
		
	}

	private String formatUserInfoContent(UserInformation userInformation){
		String resultat="";
		resultat+="<b>"+userInformation.getNomEntreprise()+"</b><br/>Siret: "+userInformation.getSiret()+"<br/><br/>";
		if(userInformation.getAdresse().getChamp1()!=null && userInformation.getAdresse().getChamp1().length()>0 ){
			System.out.println("champ1 :" +userInformation.getAdresse().getChamp1() );
			resultat+=""+userInformation.getAdresse().getChamp1()+"<br/>";
		}
		if(userInformation.getAdresse().getChamp2()!=null && userInformation.getAdresse().getChamp2().length()>0){
			System.out.println("champ2"+userInformation.getAdresse().getChamp2());
			resultat+=""+userInformation.getAdresse().getChamp2()+"<br/>";
		}
		if(userInformation.getAdresse().getChamp3()!=null && userInformation.getAdresse().getChamp3().length()>0){
			System.out.println("champ3"+userInformation.getAdresse().getChamp3());
			resultat+=""+userInformation.getAdresse().getChamp3()+"<br/>";
		}
		if(userInformation.getAdresse().getChamp4()!=null && userInformation.getAdresse().getChamp4().length()>0){
			System.out.println("champ4");
			resultat+=""+userInformation.getAdresse().getChamp4()+"<br/>";
		}
		if(userInformation.getAdresse().getChamp5()!=null && userInformation.getAdresse().getChamp5().length()>0){
			System.out.println("champ5");
			resultat+=""+userInformation.getAdresse().getChamp5()+"<br/>";
		}
		if(userInformation.getAdresse().getChamp6()!=null && userInformation.getAdresse().getChamp6().length()>0){
			resultat+=""+userInformation.getAdresse().getChamp6()+"<br/>";
		}
		resultat+="<br/><b>Contact :</b><br/> ";
		resultat+=""+userInformation.getEmail()+"<br/>";
		resultat+=""+userInformation.getTelephone();
		log.info(resultat);
		return resultat;
			
		
	}
	private String formatClientInfosContent(Client client){
		String resultat="";
		resultat+="<b>"+client.getNom()+"</b> <b>"+client.getPrenom()+"</b><br/><br/>";
		//TODO GENERICS
		if(client.getAdresse()!=null){
			if(client.getAdresse().getChamp1()!=null && client.getAdresse().getChamp1().length()>0){
				resultat+=""+client.getAdresse().getChamp1()+"<br/>";
			}
			if(client.getAdresse().getChamp2()!=null && client.getAdresse().getChamp2().length()>0){
				resultat+=""+client.getAdresse().getChamp2()+"<br/>";
			}
			if(client.getAdresse().getChamp3()!=null && client.getAdresse().getChamp3().length()>0){
				resultat+=""+client.getAdresse().getChamp3()+"<br/>";
			}
			if(client.getAdresse().getChamp4()!=null && client.getAdresse().getChamp4().length()>0){
				resultat+=""+client.getAdresse().getChamp4()+"<br/>";
			}
			if(client.getAdresse().getChamp5()!=null && client.getAdresse().getChamp5().length()>0){
				resultat+=""+client.getAdresse().getChamp5()+"<br/>";
			}
			if(client.getAdresse().getChamp6()!=null && client.getAdresse().getChamp6().length()>0){
				resultat+=""+client.getAdresse().getChamp6()+"<br/>";
			}
			if(client.getAdresse().getChamp7()!=null && client.getAdresse().getChamp7().length()>0){
				resultat+=""+client.getAdresse().getChamp7()+"<br/>";
			}
		}
		return resultat;
	}
}
