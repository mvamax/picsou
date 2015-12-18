package io.picsou.controller;

import io.picsou.domain.CatalogueProduit;
import io.picsou.domain.CatalogueType;
import io.picsou.domain.Client;
import io.picsou.domain.Contrat;
import io.picsou.domain.EtatContratEnum;
import io.picsou.domain.ProduitContrat;
import io.picsou.repository.ContratRepository;
import io.picsou.service.CatalogueService;
import io.picsou.service.ClientService;
import io.picsou.service.ContratService;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


// @SessionAttributes(value={"produits","types"})
public class FicheClientController {

	private final Logger log = LoggerFactory.getLogger(CatalogueController.class);
	
	private final String page = "ficheClient/ficheClient";

	@Autowired
	ClientService clientService;

	@Autowired
	ContratService contratService;

	@Autowired
	CatalogueService catalogueService;

	@Autowired
	LocalValidatorFactoryBean validator;

	@ModelAttribute(value = "produits")
	public List<CatalogueProduit> produits() {
		return catalogueService.getAllProduitsInCatalogue();
	}

	@ModelAttribute(value = "types")
	public List<CatalogueType> types() {
		return catalogueService.getAllTypesInCatalogue();
	}

	public Model fillContrats(Model model, Long clientId) {
		model.addAttribute("contratsEnCours", contratService
				.findByClientIdByEtatContratId(clientId,
						EtatContratEnum.EN_COURS.getValue()));
		model.addAttribute("contratsTermine", contratService
				.findByClientIdByEtatContratId(clientId,
						EtatContratEnum.TERMINE.getValue()));
		model.addAttribute("contratsAttentePaiement", contratService
				.findByClientIdByEtatContratId(clientId,
						EtatContratEnum.EN_ATTENTE_PAIEMENT.getValue()));
		return model;
	}
	
	@RequestMapping(value = "/contrat/form", method = RequestMethod.POST)
	public void contratForm(
			@Valid @ModelAttribute("contrat") Contrat contrat,
			BindingResult bindingResult,
			Model model) {
		System.out.println(contrat);
		if(bindingResult.hasErrors()){
			
			System.out.println(bindingResult.getFieldErrors());
			
		}
		System.out.println(contrat.getIntitule());
	}

	@RequestMapping(value = "/ficheClient/{clientId}", method = RequestMethod.GET)
	public String list(
			@PathVariable Long clientId,
			@RequestParam(value = "contratId", required = false) Long contratId,
			@RequestParam(value = "activeTab", required = false) String activeTab,
			Model model) {
		Contrat c=null;
		if(contratId!=null){			
			log.info("pas de contratId");
			c=contratService.getContrat(contratId);
			model.addAttribute("activeTab", "widAjoutContrat");
		}else{
			c=new Contrat();
			c.setEtatContrat(contratService
				.getEtatContrat(EtatContratEnum.EN_COURS.getValue()));
			model.addAttribute("activeTab", activeTab==null?"widListContrat":activeTab);
		}
		model.addAttribute("client", clientService.getClient(clientId));
		model.addAttribute("contrat", c);
		model = fillContrats(model, clientId);
		return page;
	}

	@RequestMapping(value = "/ficheClient/contrat/effectue/{clientId}/{contratId}", method = RequestMethod.GET)
	public String ChangeContratPaye(@PathVariable Long clientId,
			@PathVariable Long contratId) {
		Contrat c = contratService.getContrat(contratId);
		c.setEtatContrat(contratService
				.getEtatContrat(EtatContratEnum.EN_ATTENTE_PAIEMENT.getValue()));
		contratService.save(c);
		return "redirect:/ficheClient/" + clientId;
	}

//	@RequestMapping(value = "/ficheClient/contrat/paye/{clientId}/{contratId}", method = RequestMethod.GET)@RequestMapping(value = "/ficheClient/{clientId}/addnewcontrat", method = RequestMethod.POST)
//	public String addContrat(@PathVariable Long clientId,
//	@ModelAttribute("contrat") Contrat contrat,
//	BindingResult bindingResult, 
//	Model model,
//	RedirectAttributes redirectAttrs,
//	@RequestParam(required = false) String ParamAjoutPrestation,
//	@RequestParam(required = false) String ParamSavePrestation) {
//
//if (null == ParamAjoutPrestation && null == ParamSavePrestation) {
//	return "index";
//}
//
//Client client = clientService.getClient(clientId);
//model.addAttribute("client", client);
//
//if (ParamAjoutPrestation != null) {
//	System.out
//			.println("paramAjoutPrestation ->" + ParamAjoutPrestation);
//	contrat.addProduitContrat(new ProduitContrat());
//	model.addAttribute("activeTab", "widAjoutContrat");
//	model = fillContrats(model, clientId);
//	return page;
//}
//
//validator.validate(contrat, bindingResult);
//
//for (ProduitContrat pc : contrat.getProduitsContrat()) {
//	System.out.println(pc.toString());
//	validator.validate(pc, bindingResult);
//}
//
//if (bindingResult.hasErrors()) {
//	System.out.println(bindingResult.getFieldErrors());
//	for (ObjectError err : bindingResult.getAllErrors()) {
//		System.out.println(err);
//	}
//	fillContrats(model, clientId);
//	model.addAttribute("contrat", contrat);
//	model.addAttribute("activeTab", "widAjoutContrat");
//	return page;
//}
//
//if (ParamSavePrestation != null) {
//	log.info("paramSavePrestation " + ParamSavePrestation);
//	contrat.setClient(client);
//	for (ProduitContrat c : contrat.getProduitsContrat()) {
//		System.out.println(c.getOrdre() + "," + c.getIntitule());
//	}
//	log.info("contrat etat"+contrat.getEtatContrat().getIntitule());
////	contrat.setEtatContrat(contratService
////			.getEtatContrat(EtatContratEnum.EN_COURS.getValue()));
//	contratService.save(contrat);
//
//	model = fillContrats(model, clientId);
//}
//
//return "redirect:/ficheClient/" + clientId;
//}
	public String ChangeContratTermine(@PathVariable Long clientId,
			@PathVariable Long contratId) {
		Contrat c = contratService.getContrat(contratId);
		c.setEtatContrat(contratService.getEtatContrat(EtatContratEnum.TERMINE
				.getValue()));
		contratService.save(c);
		return "redirect:/ficheClient/" + clientId;
	}

//	@RequestMapping(value = "/ficheClient/{clientId}/addnewcontrat", method = RequestMethod.POST)
//	public String addContrat(@PathVariable Long clientId,
//			@ModelAttribute("contrat") Contrat contrat,
//			BindingResult bindingResult, 
//			Model model,
//			RedirectAttributes redirectAttrs,
//			@RequestParam(required = false) String ParamAjoutPrestation,
//			@RequestParam(required = false) String ParamSavePrestation) {
//
//		if (null == ParamAjoutPrestation && null == ParamSavePrestation) {
//			return "index";
//		}
//		
//		Client client = clientService.getClient(clientId);
//		model.addAttribute("client", client);
//
//		if (ParamAjoutPrestation != null) {
//			System.out
//					.println("paramAjoutPrestation ->" + ParamAjoutPrestation);
//			contrat.addProduitContrat(new ProduitContrat());
//			model.addAttribute("activeTab", "widAjoutContrat");
//			model = fillContrats(model, clientId);
//			return page;
//		}
//
//		validator.validate(contrat, bindingResult);
//		
//		for (ProduitContrat pc : contrat.getProduitsContrat()) {
//			System.out.println(pc.toString());
//			validator.validate(pc, bindingResult);
//		}
//
//		if (bindingResult.hasErrors()) {
//			System.out.println(bindingResult.getFieldErrors());
//			for (ObjectError err : bindingResult.getAllErrors()) {
//				System.out.println(err);
//			}
//			fillContrats(model, clientId);
//			model.addAttribute("contrat", contrat);
//			model.addAttribute("activeTab", "widAjoutContrat");
//			return page;
//		}
//
//		if (ParamSavePrestation != null) {
//			log.info("paramSavePrestation " + ParamSavePrestation);
//			contrat.setClient(client);
//			for (ProduitContrat c : contrat.getProduitsContrat()) {
//				System.out.println(c.getOrdre() + "," + c.getIntitule());
//			}
//			log.info("contrat etat"+contrat.getEtatContrat().getIntitule());
////			contrat.setEtatContrat(contratService
////					.getEtatContrat(EtatContratEnum.EN_COURS.getValue()));
//			contratService.save(contrat);
//
//			model = fillContrats(model, clientId);
//		}
//
//		return "redirect:/ficheClient/" + clientId;
//	}

	


	
	
	@RequestMapping(value = "/ficheClient/{clientId}/produit/add", method = RequestMethod.POST)
	public String addProduit(@PathVariable Long clientId,
			@Valid Contrat contrat, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttrs) {
		System.out.println("add produit");
		model.addAttribute("client", clientService.getClient(clientId));
		contrat.produitsContrat.add(new ProduitContrat());
		model.addAttribute("contrat", contrat);
		return page;
	}

}
