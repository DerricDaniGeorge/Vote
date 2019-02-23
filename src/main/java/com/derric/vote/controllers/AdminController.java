package com.derric.vote.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.derric.vote.beans.Election;
import com.derric.vote.beans.ElectionDetail;
import com.derric.vote.dao.IElectionDBService;
import com.derric.vote.services.ElectionServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class AdminController {

	@Autowired
	private ElectionServices electionServices;

	@RequestMapping(value = "/adminCandidate", method = RequestMethod.GET)
	public String showAdminCandidateForm() {
		return "adminCandidate";
	}
	
	@RequestMapping(value = "/adminElection", method = RequestMethod.GET)
	public String showAdminElectionForm(Model model) throws JsonProcessingException {
		List<Election> elections=electionServices.getElectionsNotStartedAndCurrentlyRunning();
		elections.stream().parallel().forEach(action->action.setDetail(ElectionDetail.START_DATE, action.getDetail(ElectionDetail.START_DATE).toString()));
		elections.stream().parallel().forEach(action->action.setDetail(ElectionDetail.END_DATE, action.getDetail(ElectionDetail.END_DATE).toString()));
		model.addAttribute("elections",elections);
		ObjectMapper objM=new ObjectMapper();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		objM.setDateFormat(dateFormat);
		String jSonString=objM.writeValueAsString(elections);
		System.out.println(jSonString); 
		model.addAttribute("jsonElections",jSonString);
		return "adminElection";
	}

}
