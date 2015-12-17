package io.picsou.controller;

import io.picsou.domain.UserInformation;
import io.picsou.service.UserInformationService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ParametresController {

	private final String page = "parametres/parametres";
	
	@Autowired
	UserInformationService userInformationService;
	
	@RequestMapping(value = "/parametres", method = RequestMethod.GET)
	public String parametres(Model model){
		model.addAttribute("userInformations", userInformationService.findById(1L));
		return page;
	}
	
	@RequestMapping(value = "/parametres/userinformations/add", method = RequestMethod.POST)
	public String list(@Valid  @ModelAttribute("userInformations") UserInformation userInformations, BindingResult bindingResult,
			Model model, RedirectAttributes redirectAttrs) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("userInformations", userInformations);
			return page;
		}
		userInformations.getAdresse();
		userInformationService.save(userInformations);
		return page;
	}
	
}

