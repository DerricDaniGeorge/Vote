package com.derric.vote.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.derric.vote.beans.User;
import com.derric.vote.constants.PageConstants;
import com.derric.vote.constants.URLConstants;
import com.derric.vote.forms.AdminPartyForm;
import com.derric.vote.services.ElectionServices;
import com.derric.vote.validators.AdminPartyValidator;
import com.derric.vote.validators.AdminStateValidator;

@Controller
@SessionAttributes(value = { "partyForm" })
public class AdminPartyController {
	@Autowired
	public AdminPartyValidator partyFormValidator;
	@Autowired
	public ElectionServices electionServices;

	@RequestMapping(value = "/" + URLConstants.ADMIN_PARTY, method = RequestMethod.GET)
	public String showAddPartyPage(Model model) {
		AdminPartyForm partyForm = new AdminPartyForm();
		model.addAttribute("partyForm", partyForm);
		model.addAttribute("parties", electionServices.getAllParties());
		return PageConstants.ADMIN_PARTY_PAGE;
	}

	@RequestMapping(value = "/" + URLConstants.ADMIN_PARTY, method = RequestMethod.POST)
	public String submitAddCandidate(Model model, RedirectAttributes redirectAttributes, HttpServletRequest request,
			@RequestParam("action") String action,
			@ModelAttribute("addPartyForm") @Validated AdminPartyForm partyForm, BindingResult result) {
		if (result.hasErrors()) {
			model.addAttribute("hasErrors", "true");
			model.addAttribute("parties", electionServices.getAllParties());
			return PageConstants.ADMIN_PARTY_PAGE;
		}
		if(action.equals("ADD")) {
			if (electionServices.addParty(partyForm, (User) request.getSession().getAttribute("user"))) {
				redirectAttributes.addFlashAttribute("partyStatus", "Party successfully added");
			} else {
				redirectAttributes.addFlashAttribute("partyStatus", "Party with same name already exists");
			}
		}
		if(action.equals("DELETE")) {
			electionServices.deleteParty(partyForm); 
				redirectAttributes.addFlashAttribute("partyStatus", "Party successfully deleted");	
		}
		return "redirect:/" + URLConstants.ADMIN_PARTY;
	}

	@InitBinder("addPartyForm")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(partyFormValidator);
	}

}
