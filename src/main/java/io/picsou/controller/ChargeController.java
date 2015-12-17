package io.picsou.controller;

import io.picsou.domain.Charge;
import io.picsou.service.ChargeService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ChargeController {

	@Autowired
	ChargeService chargeService;
	
	@ModelAttribute("charges")
	public List<Charge> listCharge(){
		return chargeService.findAll();
	}
	
	@RequestMapping("/charge")
	public String charge(Model model){
		model.addAttribute("charge",new Charge());
		return "charge/charge";
	}
	
	@RequestMapping(value="/charge/add",method=RequestMethod.POST)
	public String charge(Model model,
			@ModelAttribute("charge") Charge charge,
			BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			return "charge/charge";
		}
		chargeService.save(charge);
		model.addAttribute("charge",new Charge());
		return "redirect:/charge";
	}
	
}
