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
import com.derric.vote.forms.AdminStateForm;
import com.derric.vote.services.ElectionServices;
import com.derric.vote.validators.AdminStateValidator;

@Controller
@SessionAttributes(value = { "stateForm" })
public class AdminStateController {

	@Autowired
	public AdminStateValidator stateFormValidator;
	@Autowired
	public ElectionServices electionServices;

	@RequestMapping(value = "/" + URLConstants.ADMIN_STATE, method = RequestMethod.GET)
	public String showAddStatePage(Model model) {
		AdminStateForm stateForm = new AdminStateForm();
		model.addAttribute("stateForm", stateForm);
		model.addAttribute("states", electionServices.getAllStates());
		return PageConstants.ADMIN_STATE_PAGE;
	}

	@RequestMapping(value = "/" + URLConstants.ADMIN_STATE, method = RequestMethod.POST)
	public String submitAddCandidate(Model model, RedirectAttributes redirectAttributes, HttpServletRequest request,
			@RequestParam("action") String action,
			@ModelAttribute("stateForm") @Validated AdminStateForm stateForm, BindingResult result) {
		if (result.hasErrors()) {
			model.addAttribute("hasErrors", "true");
			model.addAttribute("states", electionServices.getAllStates());
			return PageConstants.ADMIN_STATE_PAGE;
		}
		if(action.equals("ADD")) {
			if (electionServices.addState(stateForm, (User) request.getSession().getAttribute("user"))) {
				redirectAttributes.addFlashAttribute("stateStatus", "State successfully added");
			} else {
				redirectAttributes.addFlashAttribute("stateStatus", "State with same name already exists");
			}
		}
		if(action.equals("DELETE")) {
			electionServices.deleteState(stateForm); 
				redirectAttributes.addFlashAttribute("stateStatus", "State successfully deleted");	
		}
		return "redirect:/" + URLConstants.ADMIN_STATE;
	}

	@InitBinder("stateForm")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(stateFormValidator);
	}

}
