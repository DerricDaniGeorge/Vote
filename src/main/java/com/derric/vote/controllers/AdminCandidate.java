package com.derric.vote.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.derric.vote.constants.PageConstants;
import com.derric.vote.constants.URLConstants;
import com.derric.vote.forms.AdminCandidateForm;

@Controller
@SessionAttributes(value = {"candidateForm"})
public class AdminCandidate {
	
	@RequestMapping(value="/"+URLConstants.ADMIN_CANDIDATE)
	public String showAddCandidate(Model model) {
		AdminCandidateForm candidateForm=new AdminCandidateForm();
		model.addAttribute("candidateForm",candidateForm);
		return PageConstants.ADMIN_CANDIDATE_PAGE;
	}
}
