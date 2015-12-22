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

	@ModelAttribute(value = "types")
	public List<CatalogueType> types() {
		return catalogueService.getAllTypesInCatalogue();
	}

	@RequestMapping(value = "/catalogue")
	public String list(Model model) {
		model.addAttribute("typeProduit", new CatalogueType());
		return page;
	}


	@RequestMapping(value = "/catalogue/type/add", method = RequestMethod.POST)
	public String list(
			@Valid @ModelAttribute("typeProduit") CatalogueType catalogueType,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttrs) {
		
		if (bindingResult.hasErrors()) {
			log.warn("binding result error");
			model.addAttribute("typeProduit", catalogueType);
			model.addAttribute("produit", new CatalogueProduit());
			return page;
		}
		
		String formatType = Character.toUpperCase(catalogueType.getType()
				.charAt(0))
				+ catalogueType.getType().substring(1).toLowerCase();
		catalogueType.setType(formatType);
		log.info("sauvegarde du nouveau label de catalogue");
		catalogueService.saveTypeInCatalogue(catalogueType);
		redirectAttrs.addFlashAttribute("flash.message", "type ["
				+ catalogueType.getType() + "] enregistré");
		model.addAttribute("typeProduit", new CatalogueType());
		return "redirect:/catalogue";
	}

}
