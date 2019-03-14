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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.derric.vote.beans.Election;
import com.derric.vote.beans.ElectionDetail;
import com.derric.vote.beans.User;
import com.derric.vote.constants.PageConstants;
import com.derric.vote.constants.URLConstants;
import com.derric.vote.forms.AdminElectionForm;
import com.derric.vote.services.ElectionServices;
import com.derric.vote.validators.AdminElectionValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@SessionAttributes(value = { "electionForm", "elections", "jsonElections" })
public class AdminElectionController {

	@Autowired
	private ElectionServices electionServices;
	@Autowired
	private AdminElectionValidator electionValidator;


	@RequestMapping(value = "/" + URLConstants.ADMIN_ELECTION, method = RequestMethod.GET)
	public String showAdminElectionForm(Model model) throws JsonProcessingException {
		String jSonString = null;
		AdminElectionForm electionForm = new AdminElectionForm();
		model.addAttribute("electionForm", electionForm);
		List<Election> elections = electionServices.getElectionsNotStartedAndCurrentlyRunning();
		elections.stream().parallel().forEach(action -> action.setDetail(ElectionDetail.START_DATE,
				action.getDetail(ElectionDetail.START_DATE).toString()));
		elections.stream().parallel().forEach(action -> action.setDetail(ElectionDetail.END_DATE,
				action.getDetail(ElectionDetail.END_DATE).toString()));
		model.addAttribute("elections", elections);
		try {
			ObjectMapper objM = new ObjectMapper();
			jSonString = objM.writeValueAsString(elections);
		} catch (JsonProcessingException jpe) {
			throw jpe;
		}
		System.out.println(jSonString);
		model.addAttribute("jsonElections", jSonString);
		return PageConstants.ADMIN_ELECTION_PAGE;
	}

	@RequestMapping(value = "/" + URLConstants.ADMIN_ELECTION, method = RequestMethod.POST)
	public String addElection(HttpServletRequest request, @RequestParam("action") String action,
			@RequestParam("election_id") String electionId,
			@RequestParam("election_startDate") String electionStartDate,
			@RequestParam("election_endDate") String electionEndDate, Model model,
			@ModelAttribute("electionForm") @Validated AdminElectionForm electionForm, BindingResult result,
			@ModelAttribute("elections") List<Election> elections,
			@ModelAttribute("jsonElections") String jsonElections, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			model.addAttribute("hasErrors", true);
			model.addAttribute("elections", elections);
			model.addAttribute("jsonElections", jsonElections);
			return PageConstants.ADMIN_ELECTION_PAGE;
		}
		if (action.equals("ADD")) {
			if (electionServices.addElection(electionForm, (User) request.getSession().getAttribute("user"))) {
				redirectAttributes.addFlashAttribute("electionStatus",
						"Election: " + electionForm.getElectionName() + " successfully added");
			} else {
				redirectAttributes.addFlashAttribute("electionStatus",
						"Election: " + electionForm.getElectionName() + " already exists. Try different election name");
			}
		}
		if(action.equals("MODIFY")) {
			electionServices.updateElection(electionForm, (User) request.getSession().getAttribute("user"), electionId, electionStartDate,electionEndDate);
			redirectAttributes.addFlashAttribute("electionStatus","Election successfully updated");	
		}
		if(action.equals("DELETE")) {
			electionServices.deleteElection(electionId,electionStartDate,electionEndDate);
			redirectAttributes.addFlashAttribute("electionStatus","Election has been deleted");
		}
		System.out.println("action:" + action);
		return "redirect:/" + PageConstants.ADMIN_ELECTION_PAGE;
	}

	@InitBinder("electionForm")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(electionValidator);
	}
}
