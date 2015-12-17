package io.picsou.controller.report;

import io.picsou.config.ReportConfiguration;
import io.picsou.controller.CatalogueController;
import io.picsou.domain.Contrat;
import io.picsou.service.ContratService;
import io.picsou.service.report.FactureGenerationService;
import io.picsou.service.report.FactureService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FactureController {

	private final Logger log = LoggerFactory.getLogger(CatalogueController.class);
	
	@Autowired
	FactureService factureService;
	
	@Autowired
	ContratService contratService;

	@Autowired
	FactureGenerationService factureGenerationService;

	@Autowired
	ReportConfiguration reportConfiguration;
	
	@RequestMapping(value = "/facture/{clientId}/{contratId}", method = RequestMethod.GET, produces = "application/pdf")
	public FileSystemResource generateFacture(
			@PathVariable Long clientId,
			@PathVariable Long contratId,
			HttpServletResponse response ) {
		
		String type="pdf";
		
		if (factureService.verifieContratClientAppartenance(clientId, contratId)) {
			log.info("concordance entre le clientId "+clientId+" et le contrat "+contratId+" vérifiée");
			Contrat contrat = contratService.getContrat(contratId);
			String nomFichier=reportConfiguration.getFolderReportOut()+"/Facture_"+clientId+"_"+contratId+".pdf";
			File factureFile=new File(nomFichier);
			if(factureFile.exists()){
				log.info("la facture existe déjà");
				return new FileSystemResource(factureFile); 
			}else{
				try {
					log.info("génération de la facture");
					factureGenerationService.generate(clientId,contratId,type);
				} catch (JRException | SQLException e) {
					log.error("Problème lors de la génération de la facture");
					e.printStackTrace();
				}
				return new FileSystemResource(factureFile);  
			}

		} else {
			//redirection 
			log.error("pas de concordance");
			try {
				response.sendRedirect("/index");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;


	}

}
