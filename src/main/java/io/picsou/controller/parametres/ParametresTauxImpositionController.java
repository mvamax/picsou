package io.picsou.controller.parametres;

import io.picsou.domain.ParametreImposition;
import io.picsou.service.ParametresTauxImpositionService;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ParametresTauxImpositionController {

	private final String page = "parametres/tauximposition";
	private final String pageForm = "parametres/tauximpositionform";
	@Autowired
	ParametresTauxImpositionService  parametresTauxImpositionService;
	
	@ModelAttribute("tauxImpositions")
	public List<ParametreImposition> list(){
		return  parametresTauxImpositionService.findAll();
	}
	
	@RequestMapping(value = "/parametres/tauximposition", method = RequestMethod.GET)
	public String parametres(Model model){
		model.addAttribute("tauxForm", new ParametreImposition());
		return page;
	}
	
	@RequestMapping(value = "/parametres/tauximposition/{id}",
			method = RequestMethod.GET)
	public String getForm(Model model,
			@PathVariable Long id){
		 ParametreImposition pi = parametresTauxImpositionService.findById(id);
		
		if(pi==null){
			return "redirect:/home";
		}
		model.addAttribute("pi",pi);
		return pageForm;
	}
	
	@RequestMapping(value = "/parametres/tauximposition/{id}",method = RequestMethod.POST)
	public String modify(Model model,
			@PathVariable Long id,
			@ModelAttribute("pi") ParametreImposition pi,
			BindingResult bindingResult){
		
		if(bindingResult.hasErrors()){
			model.addAttribute("pi", pi);
			return pageForm;
		}
		ParametreImposition piBefore = parametresTauxImpositionService.findById(id);
		
		if(piBefore==null || pi.getId()!= id){
			System.out.println("bad controls");
			return "redirect:/home";
		}
		else{
			parametresTauxImpositionService.save(pi);
		}
		return page;
	}
	

	
}

