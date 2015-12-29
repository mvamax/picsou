package io.picsou.controller.parametres;

import io.picsou.domain.ParametreImposition;
import io.picsou.service.ParametresTauxImpositionService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ParametresTauxImpositionController {

	private final String page = "parametres/tauximposition";

	@Autowired
	ParametresTauxImpositionService  parametresTauxImpositionService;
	
	@ModelAttribute("tauxImpositions")
	public List<ParametreImposition> list(){
		return  parametresTauxImpositionService.findAll();
	}
	
	@RequestMapping(value = "/parametres/tauximposition", method = RequestMethod.GET)
	public String parametres(Model model){
		return page;
	}
	

	
}

