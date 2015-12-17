package io.picsou.controller;

import java.util.ArrayList;
import java.util.List;

import io.picsou.domain.CatalogueType;
import io.picsou.domain.Client;
import io.picsou.domain.Contrat;
import io.picsou.domain.EtatContrat;
import io.picsou.domain.EtatContratEnum;
import io.picsou.domain.ProduitContrat;
import io.picsou.repository.ProduitContratRepository;
import io.picsou.service.CatalogueService;
import io.picsou.service.ClientService;
import io.picsou.service.ContratService;

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

@Controller
public class ContratController {

	private final Logger log = LoggerFactory
			.getLogger(CatalogueController.class);

	@Autowired
	ClientService clientService;

	@Autowired
	ContratService contratService;

	@Autowired
	CatalogueService catalogueService;

	@Autowired
	LocalValidatorFactoryBean validator;

	@Autowired
	ProduitContratRepository produitContratRepository;

	@ModelAttribute(value = "types")
	public List<CatalogueType> types() {
		return catalogueService.getAllTypesInCatalogue();
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
		return "ficheClient/ficheClient";
	}

	@RequestMapping(value = "/ficheClient/{clientId}/addnewcontrat", method = RequestMethod.POST)
	public String addContrat(@PathVariable Long clientId,
			@ModelAttribute("contrat") Contrat contrat,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttrs,
			@RequestParam(required = false) String ParamAjoutPrestation,
			@RequestParam(required = false) String ParamSavePrestation,
			@RequestParam(required = false) String ParamRemovePrestation) {

		if (null == ParamAjoutPrestation && null == ParamSavePrestation && null == ParamRemovePrestation) {
			return "index/index";
		}

		Client client = clientService.getClient(clientId);
		model.addAttribute("client", client);

		if (ParamAjoutPrestation != null) {
			log.info("addContrat paramAjoutPrestation ");
			contrat.addProduitContrat(new ProduitContrat());
			model.addAttribute("activeTab", "widAjoutContrat");
			model = fillContrats(model, clientId);
			return "ficheClient/ficheClient";

		}
		
		if (ParamRemovePrestation != null) {
			log.info("addContrat ParamRemovePrestation "+ParamRemovePrestation);
			List<ProduitContrat> produitsContrat = new ArrayList<>();
			for( ProduitContrat pc:contrat.getProduitsContrat()){
				if(pc.getOrdre()!= Integer.valueOf(ParamRemovePrestation)){
					produitsContrat.add(pc);
				}
			}
			contrat.setProduitsContrat(produitsContrat);
			model.addAttribute("activeTab", "widAjoutContrat");
			model = fillContrats(model, clientId);
			return "ficheClient/ficheClient";

		}


		validator.validate(contrat, bindingResult);

		for (ProduitContrat pc : contrat.getProduitsContrat()) {
			System.out.println(pc.toString());
			validator.validate(pc, bindingResult);
		}

		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getFieldErrors());
			for (ObjectError err : bindingResult.getAllErrors()) {
				log.warn(err.toString());
			}
			fillContrats(model, clientId);
			model.addAttribute("contrat", contrat);
			model.addAttribute("activeTab", "widAjoutContrat");
			return "ficheClient/ficheClient";
		}

		if (ParamSavePrestation != null) {
			log.info("addContrat paramSavePrestation ");
			contrat.setClient(client);
			for (ProduitContrat c : contrat.getProduitsContrat()) {
				System.out.println(c.getOrdre() + "," + c.getIntitule());
			}
			log.info("contrat etat" + contrat.getEtatContrat().getIntitule());
			log.info("contrat etat" + contrat.getEtatContrat().getId());
			contrat.setEtatContrat(contratService.getEtatContrat(contrat
					.getEtatContrat().getId()));
			contratService.save(contrat);
			model = fillContrats(model, clientId);
			Contrat c = new Contrat();
			c.setEtatContrat(contratService
					.getEtatContrat(EtatContratEnum.EN_COURS.getValue()));
			model.addAttribute("contrat", c);
			model.addAttribute("activeTab", "widListContrat");

		}
		return "ficheClient/ficheClient";
	}

	@RequestMapping(value = "/ficheClient/{clientId}/{contratId}", method = RequestMethod.GET)
	public String addContratGet(@PathVariable Long clientId,
			@PathVariable Long contratId, Model model) {

		Client client = clientService.getClient(clientId);
		model.addAttribute("client", client);

		Contrat p = contratService.getContrat(contratId);
		log.info(p.getEtatContrat().getId().toString());
		model.addAttribute("contrat", p);
		model = fillContrats(model, clientId);
		model.addAttribute("activeTab", "widAjoutContrat");
		return "ficheClient/ficheClient";
	}

	@RequestMapping(value = "/ficheClient/{clientId}/{contratId}", method = RequestMethod.POST)
	public String addContratPost(@PathVariable Long clientId,
			@PathVariable Long contratId,
			@ModelAttribute("contrat") Contrat contrat,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttrs,
			@RequestParam(required = false) String ParamAjoutPrestation,
			@RequestParam(required = false) String ParamSavePrestation) {

		Client client = clientService.getClient(clientId);
		model.addAttribute("client", client);

		if (null == ParamAjoutPrestation && null == ParamSavePrestation) {
			log.info("aucun parametres");
			Contrat p = contratService.getContrat(contratId);
			log.info(p.getEtatContrat().getId().toString());
			model.addAttribute("contrat", contratService.getContrat(contratId));
			model = fillContrats(model, clientId);
			model.addAttribute("activeTab", "widAjoutContrat");
			return "ficheClient/ficheClient";
		}

		if (ParamAjoutPrestation != null) {
			log.info("addContrat paramAjoutPrestation ");
			contrat.addProduitContrat(new ProduitContrat());
			model.addAttribute("activeTab", "widAjoutContrat");
			model = fillContrats(model, clientId);
			return "ficheClient/ficheClient";
		}

		if (ParamSavePrestation != null) {

			validate(contrat, bindingResult);

			if (bindingResult.hasErrors()) {
				for (ObjectError err : bindingResult.getAllErrors()) {
					log.warn(err.toString());
				}
				fillContrats(model, clientId);
				model.addAttribute("contrat", contrat);
				model.addAttribute("activeTab", "widAjoutContrat");
				return "ficheClient/ficheClient";
			}

			// FROM CONTRAT EN COURS TO CONTRAT EN COURS -> on update le contrat
			// tel quel
			// FROM CONTRAT EN COURS TO CONTRAT EN ATTENTE PAIEMENT -> on update
			// le contrat tel quel
			// FROM CONTRAT EN COURS TO CONTRAT TERMINE -> on update le contrat
			// tel quel
			// FROM CONTRAT EN ATTENTE PAIEMENT TO CONTRAT TERMINE -> on update
			// uniquement deductible et l'intitulé
			Contrat contratBase = contratService.getContrat(contratId);
			if (contratBase.getEtatContrat().getId() == EtatContratEnum.EN_COURS
					.getValue()) {
				log.info("addContrat paramSavePrestation from EN_COURS ");
				contrat.setClient(client);
				contrat.setEtatContrat(contratService.getEtatContrat(contrat
						.getEtatContrat().getId()));
				contratService.save(contrat);

				model = fillContrats(model, clientId);
				Contrat c = new Contrat();
				c.setEtatContrat(contratService
						.getEtatContrat(EtatContratEnum.EN_COURS.getValue()));
				model.addAttribute("activeTab", "widListContrat");
				model.addAttribute("contrat", c);

			} else if (contratBase.getEtatContrat().getId() == EtatContratEnum.EN_ATTENTE_PAIEMENT.getValue()) {
				log.info("addContrat paramSavePrestation from EN_ATTENTE_PAIEMENT ");
				contratBase.setIntitule(contrat.getIntitule());
				for (ProduitContrat pc : contrat.getProduitsContrat()) {
					ProduitContrat pctemp = produitContratRepository.findOne(pc
							.getId());
					pctemp.setEstDeductible(pc.isEstDeductible());
					produitContratRepository.save(pctemp);
				}
				if (contrat.getEtatContrat().getId() == EtatContratEnum.EN_ATTENTE_PAIEMENT.getValue()
					|| contrat.getEtatContrat().getId() == EtatContratEnum.TERMINE.getValue()) {
					log.info("changement d'etatContrat "+contrat.getEtatContrat().getId());
					contratBase.setEtatContrat(contratService.getEtatContrat(contrat.getEtatContrat().getId()));
				}
				contratService.save(contratBase);
				Contrat c = new Contrat();
				c.setEtatContrat(contratService
						.getEtatContrat(EtatContratEnum.EN_COURS.getValue()));
				model.addAttribute("activeTab", "widListContrat");
				model.addAttribute("contrat", c);
				model = fillContrats(model, clientId);
			}

		}
		return "ficheClient/ficheClient";
	}

	public void validate(Contrat contrat, BindingResult bindingResult) {
		validator.validate(contrat, bindingResult);

		for (ProduitContrat pc : contrat.getProduitsContrat()) {
			System.out.println(pc.toString());
			validator.validate(pc, bindingResult);
		}
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
}
