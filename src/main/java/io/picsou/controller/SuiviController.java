package io.picsou.controller;

import java.util.List;

import io.picsou.domain.Client;
import io.picsou.domain.EtatContratEnum;
import io.picsou.service.ContratService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SuiviController {

	private final Logger log = LoggerFactory.getLogger(SuiviController.class);
	private static final int nbJour=7;

	private final String pageTemplate = "suivi/suivi";
	private final String pageUrl = "/suivi";

	@Autowired
	ContratService contratService;

	@ModelAttribute(value = "nbContratsEnCours")
	public Long nbContratsEnCours() {
		return contratService.countByEtatContratId(EtatContratEnum.EN_COURS.getValue());
	}
	
	@ModelAttribute(value = "nbContratsTermines")
	public Long nbContratsTermines() {
		return contratService.countByEtatContratId(EtatContratEnum.TERMINE.getValue());
	}
	
	@ModelAttribute(value = "nbContratsAttentePaiement")
	public Long nbContratsAttentePaiement() {
		return contratService.countByEtatContratId(EtatContratEnum.EN_ATTENTE_PAIEMENT.getValue());
	}
	
	@ModelAttribute(value = "nbContratsAEcheance")
	public Long nbContratsAEcheance() {
		return contratService.countContratsAEcheance(nbJour);
	}
	
	
	@RequestMapping(value = "/suivi", method = RequestMethod.GET)
	public String suivi(
			@RequestParam(value="etatContrat",required=false) String etatContrat,
			Model model) {
		log.debug("suivi");
		etatContrat=(etatContrat==null?"null":etatContrat.toUpperCase());
		log.info(etatContrat);
		switch (etatContrat.toUpperCase()) {
		case "EN_COURS":
			model.addAttribute("contrats", contratService.findByEtatContratId(EtatContratEnum.EN_COURS.getValue()));
			break;
		case "EN_ATTENTE_PAIEMENT":
			model.addAttribute("contrats", contratService.findByEtatContratId(EtatContratEnum.EN_ATTENTE_PAIEMENT.getValue()));
			break;
		case "TERMINE":
			model.addAttribute("contrats", contratService.findByEtatContratId(EtatContratEnum.TERMINE.getValue()));
			break;
		default:
			model.addAttribute("contrats", contratService.findAllContratAEcheance(nbJour));
			break;
		}
		return pageTemplate;
	}

}
