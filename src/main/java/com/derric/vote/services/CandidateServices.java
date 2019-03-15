package com.derric.vote.services;

import java.nio.ByteBuffer;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;

import com.datastax.driver.core.utils.Bytes;
import com.derric.vote.beans.Candidate;
import com.derric.vote.beans.CandidateDetail;
import com.derric.vote.beans.User;
import com.derric.vote.dao.ICandidateDBService;
import com.derric.vote.forms.AdminCandidateForm;

public class CandidateServices {
	
	@Autowired
	private ICandidateDBService candidateDBService;
/*	
	public String getImageBytes(){
		ByteBuffer bImage=candidateDBService.getImage();
		String encoded= Base64.getEncoder().encodeToString(bImage.array());
		return encoded;
	} */
	public void addCandidate(AdminCandidateForm candidateForm,User user) {
		Candidate candidate=new Candidate();
		candidate.setVotersId(candidateForm.getVotersId());
		candidate.setDetail(CandidateDetail.FIRST_NAME,candidateForm.getFirstName());
		candidate.setDetail(CandidateDetail.LAST_NAME,candidateForm.getLastName());
		candidate.setDetail(CandidateDetail.PARTY, candidateForm.getParty());
		candidate.setDetail(CandidateDetail.AGE, candidateForm.getAge());

	}
}
