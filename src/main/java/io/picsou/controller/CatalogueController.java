package io.picsou.controller;

import io.picsou.domain.CatalogueProduit;
import io.picsou.domain.CatalogueType;
import io.picsou.service.CatalogueService;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CatalogueController {

	private final Logger log = LoggerFactory.getLogger(CatalogueController.class);
	
	private final String page = "catalogue/catalogue";

	@Autowired
	CatalogueService catalogueService;

//	@ModelAttribute(value = "produits")
//	public List<CatalogueProduit> produits() {
//		return catalogueService.getAllProduitsInCatalogue();
//	}

	@ModelAttribute(value = "types")
	public List<CatalogueType> types() {
		return catalogueService.getAllTypesInCatalogue();
	}

	@RequestMapping(value = "/catalogue")
	public String list(Model model) {
//		model.addAttribute("produit", new CatalogueProduit());
		model.addAttribute("typeProduit", new CatalogueType());
		return page;
	}

//	@RequestMapping(value = "/catalogue/produit/add", method = RequestMethod.POST)
//	public String list(
//			@Valid @ModelAttribute("produit") CatalogueProduit catalogueProduit,
//			BindingResult bindingResult, Model model,
//			RedirectAttributes redirectAttrs) {
//		
//		if (bindingResult.hasErrors()) {
//			log.info("erreur de binding");
//			log.debug(bindingResult.toString());
//			model.addAttribute("typeProduit", new CatalogueType());
//			model.addAttribute("produit", catalogueProduit);
//			model.addAttribute("activeTab", "addProduit");
//			return page;
//		}
//		log.info("sauvegarde du catalogueProduit");
//		catalogueService.saveProduitInCatalogue(catalogueProduit);
//		redirectAttrs.addFlashAttribute("flash.message", "Produit enregistré");
//		model.addAttribute("produit", new CatalogueProduit());
//		return "redirect:/catalogue";
//	}

	@RequestMapping(value = "/catalogue/type/add", method = RequestMethod.POST)
	public String list(
			@Valid @ModelAttribute("typeProduit") CatalogueType catalogueType,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttrs) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("typeProduit", catalogueType);
			model.addAttribute("produit", new CatalogueProduit());
			return page;
		}
		String formatType = Character.toUpperCase(catalogueType.getType()
				.charAt(0))
				+ catalogueType.getType().substring(1).toLowerCase();
		System.out.println(formatType);
		catalogueType.setType(formatType);
		System.out.println(catalogueType);
		catalogueService.saveTypeInCatalogue(catalogueType);
		redirectAttrs.addFlashAttribute("flash.message", "type ["
				+ catalogueType.getType() + "] enregistré");
		model.addAttribute("typeProduit", new CatalogueType());
		return "redirect:/catalogue";
	}

}
