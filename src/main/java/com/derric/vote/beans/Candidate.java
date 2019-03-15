package com.derric.vote.beans;

import java.util.HashMap;
import java.util.Map;


public class Candidate {
	private String votersId;
	Map<String,Object> candidateDetails;
	
	public Candidate() {
		if(candidateDetails==null) {
			candidateDetails=new HashMap<>();
		}
	}
	
	public String getVotersId() {
		return votersId;
	}

	public void setVotersId(String votersId) {
		this.votersId = votersId;
	}

	public Map<String, Object> getCandidateDetails() {
		return candidateDetails;
	}
	public void setCandidateDetails(Map<String, Object> candidateDetails) {
		this.candidateDetails = candidateDetails;
	}
	public Object getDetail(CandidateDetail detail) {
		return candidateDetails.get(detail.toString());
	}
	public void setDetail(CandidateDetail detail,Object object) {
		candidateDetails.put(detail.toString(), object);
	}
}
