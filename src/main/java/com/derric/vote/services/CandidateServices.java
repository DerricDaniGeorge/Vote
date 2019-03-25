package com.derric.vote.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;

import com.datastax.driver.core.utils.Bytes;
import com.derric.vote.beans.Candidate;
import com.derric.vote.beans.CandidateDetail;
import com.derric.vote.beans.Election;
import com.derric.vote.beans.ElectionDetail;
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
	public void addCandidate(AdminCandidateForm candidateForm,User user,Part profileImage,Part symbolImage) throws IOException {
		Candidate candidate=new Candidate();
		candidate.setVotersId(candidateForm.getVotersId());
		candidate.setDetail(CandidateDetail.FIRST_NAME,candidateForm.getFirstName().trim());
		candidate.setDetail(CandidateDetail.LAST_NAME,candidateForm.getLastName().trim());
		candidate.setDetail(CandidateDetail.PARTY, candidateForm.getParty().trim());
		candidate.setDetail(CandidateDetail.AGE, candidateForm.getAge());
		candidate.setDetail(CandidateDetail.PROFILE_PHOTO,profileImage);
		candidate.setDetail(CandidateDetail.SYMBOL, symbolImage);
		candidate.setDetail(CandidateDetail.PROFILE_PHOTO_LENGTH, profileImage.getSize());
		candidate.setDetail(CandidateDetail.SYMBOL_IMG_LENGTH, symbolImage.getSize());
		candidate.setDetail(CandidateDetail.STATE, candidateForm.getState());
		Election election =new Election();
		election.setElectionName(candidateForm.getElectionName());
		election.setDetail(ElectionDetail.ELECTION_ID,candidateForm.getElectionId());
		election.setDetail(ElectionDetail.START_DATE, candidateForm.getElectionStartDate());
		election.setDetail(ElectionDetail.END_DATE, candidateForm.getElectionEndDate());
		try {
		candidateDBService.insertCandidate(candidate,election,user);
		}catch(IOException ioe) {
			throw ioe;
		}
	}
	public List<Candidate> getAllCandidates(){
		for(Candidate c:candidateDBService.getAllCandidates()) {
			System.out.println("candidate votersid:"+c.getVotersId());
		}
		return candidateDBService.getAllCandidates();
	}
}
