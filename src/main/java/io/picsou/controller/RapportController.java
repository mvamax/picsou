package io.picsou.controller;

import io.picsou.controller.web.RapportWeb;
import io.picsou.service.ContratService;
import io.picsou.service.StatService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RapportController {

	private final String pageTemplate = "rapport/rapport";
	
	private final Logger log = LoggerFactory
			.getLogger(RapportController.class);
	
	@Autowired
	StatService statService;
	
	@Autowired
	ContratService contratService;

	
	@ModelAttribute(value="years")
	public List<String> years(){
		List<String> years= new ArrayList<>();
		int now = DateTime.now().getYear();
		years.add(String.valueOf(now));
		for(int i = 1;i<10;i++){
			years.add(String.valueOf(now-i));
		}
		return years;
	}
	
	@ModelAttribute(value = "months")
	public Map<String,String> months(){
		Map<String,String> months = new LinkedHashMap<>();
		months.put("01","Janvier");
		months.put("02","Fevrier");
		months.put("03","Mars");
		months.put("04","Avril");
		months.put("05","Mai");
		months.put("06","Juin");
		months.put("07","Juillet");
		months.put("08","Aout");
		months.put("09","Septembre");
		months.put("10","Octobre");
		months.put("11","Novembre");
		months.put("12","Decembre");
		return months;
	}
	
	@RequestMapping(value = "/rapport", method = RequestMethod.GET)
	public String rapport(Model model) {
		int now = DateTime.now().getYear();
		String year=(String.valueOf(now));
		RapportWeb rweb = new RapportWeb();
		rweb.setYear(year);
		log.info(year);
		model.addAttribute("rweb",rweb);
		model.addAttribute("histogram", statService.getHistogram(rweb.getYear()));
		model.addAttribute("revenus",contratService.getRevenuByYear(year));
		return pageTemplate;
	}
	
	@RequestMapping(value = "/rapport", method = RequestMethod.POST)
	public String rapport(Model model,
			@ModelAttribute("rweb") RapportWeb rweb,
			BindingResult bindingResult
			) {
		log.info(rweb.toString());
		model.addAttribute("histogram", statService.getHistogram(rweb.getYear()));
		model.addAttribute("revenus",contratService.getRevenuByYear(rweb.getYear()));
		return pageTemplate;
	}
	
	
}
