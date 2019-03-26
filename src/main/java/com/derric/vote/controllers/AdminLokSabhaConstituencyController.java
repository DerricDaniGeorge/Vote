package com.derric.vote.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.derric.vote.beans.User;
import com.derric.vote.beans.State;
import com.derric.vote.beans.Constituency;
import com.derric.vote.constants.PageConstants;
import com.derric.vote.constants.URLConstants;
import com.derric.vote.forms.AddLokSabhaConstituencyForm;
import com.derric.vote.forms.AdminStateForm;
import com.derric.vote.services.ElectionServices;
import com.derric.vote.validators.AdminLokSabhaConstituencyValidator;

@Controller
@SessionAttributes(value = { "addLokSabhaConstituencyForm" })

public class AdminLokSabhaConstituencyController {
	@Autowired
	private AdminLokSabhaConstituencyValidator constituencyVaidator;
	@Autowired
	public ElectionServices electionServices;

	@RequestMapping(value = "/" + URLConstants.ADMIN_LOKSABHA_CONSTITUENCY, method = RequestMethod.GET)
	public String showAddLokSabhaConstituencyPage(Model model) {
		AddLokSabhaConstituencyForm addLokSabhaConstituencyForm = new AddLokSabhaConstituencyForm();
		model.addAttribute("addLokSabhaConstituencyForm", addLokSabhaConstituencyForm);
		model.addAttribute("constituencies", electionServices.getAllLokSabhaConstituenciesName());
		return PageConstants.ADMIN_LOKSABHA_CONSTITUENCY_PAGE;
	}

	@RequestMapping(value = "/" + URLConstants.ADMIN_LOKSABHA_CONSTITUENCY, method = RequestMethod.POST)
	public String submitAddLokSabhaConstituency(Model model, @RequestParam("action") String action,
			RedirectAttributes redirectAttributes, HttpServletRequest request,
			@ModelAttribute("addLokSabhaConstituencyForm") @Validated AddLokSabhaConstituencyForm addLokSabhaConstituencyForm,
			BindingResult result) {
		if (result.hasErrors()) {
			model.addAttribute("constituencies", electionServices.getAllLokSabhaConstituenciesName());
			return PageConstants.ADMIN_LOKSABHA_CONSTITUENCY_PAGE;
		}
		if (action.equals("ADD")) {
			if (electionServices.addLoksabhaConstituency(addLokSabhaConstituencyForm,
					(User) request.getSession().getAttribute("user"))) {
				redirectAttributes.addFlashAttribute("constituencyStatus", "Constituency successfully added");
			} else {
				redirectAttributes.addFlashAttribute("constituencyStatus", "Constituency with same name already exists");
			}
		}
		if(action.equals("DELETE")) {
			electionServices.deleteLoksabhaConstituency(addLokSabhaConstituencyForm);
				redirectAttributes.addFlashAttribute("constituencyStatus", "Constituency successfully deleted");	
		}
		return "redirect:/" + URLConstants.ADMIN_LOKSABHA_CONSTITUENCY;
	}
	@RequestMapping(value = "/" + URLConstants.ADMIN_MAP_LOKSABHA_CONSTITUENCY, method = RequestMethod.GET)
	public String showMapLokSabhaConstituencyPage(Model model) {
		model.addAttribute("states", electionServices.getAllStates());
		model.addAttribute("constituencies", electionServices.getAllLokSabhaConstituenciesName());
		return PageConstants.MAP_LOKSABHA_CONSTITUENCY_PAGE;
	}
	@RequestMapping(value = "/" + URLConstants.ADMIN_MAP_LOKSABHA_CONSTITUENCY, method = RequestMethod.POST)
	public String submitMapLokSabhaConstituencyPage(HttpServletRequest request,
			@RequestParam("selected_constituencies") String constituencies,
			@RequestParam("state") String stateName,RedirectAttributes redirectAttributes) {
		State state=new State();
		state.setStateName(stateName);
		electionServices.mapLoksabhaConstituencies(state,constituencies,(User)request.getSession().getAttribute("user"));
		redirectAttributes.addFlashAttribute("status", "Mapping successfully saved");	
		return "redirect:/" + URLConstants.ADMIN_MAP_LOKSABHA_CONSTITUENCY;
	}
	@RequestMapping(value = "/"+URLConstants.GET_MAPPED_LOKSABHA_CONSTITUENCY, method = RequestMethod.GET)
	@ResponseBody
	public List<Constituency> getMappedLoksabhaConstituencies(@RequestParam("state") String state) {
		return electionServices.getMappedConstituenciesOfState(state);
	}
	@InitBinder("addLokSabhaConstituencyForm")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(constituencyVaidator);
	}
}
