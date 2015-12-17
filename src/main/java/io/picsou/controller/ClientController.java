package io.picsou.controller;

import io.picsou.domain.Client;
import io.picsou.service.ClientService;

import java.util.List;

import javax.annotation.PostConstruct;
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
public class ClientController {

	private final String pageTemplate = "client/client";
	private final String pageUrl = "/client";

	@Autowired
	ClientService clientService;

	
	@ModelAttribute(value = "clients")
	public List<Client> clients() {
		return clientService.getAllClients();
	}

	@RequestMapping(value = "/client")
	public String list(Model model) {
		model.addAttribute("client", new Client());
		return pageTemplate;
	}

	@RequestMapping(value = "/client/add", method = RequestMethod.POST)
	public String list(@Valid Client client, BindingResult bindingResult,
			Model model, RedirectAttributes redirectAttrs) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("client", client);
			model.addAttribute("activeTab", "addClient");
			System.out.println(bindingResult);
			return pageTemplate;
		}

		System.out.println(client);
		clientService.save(client);
		redirectAttrs.addFlashAttribute("flash.message", "client enregistré");
		model.addAttribute("client", new Client());
		return "redirect:" + pageUrl;
	}

}
