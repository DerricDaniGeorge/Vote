package com.derric.vote.beans;

import java.util.HashMap;
import java.util.Map;

public class Election {
	String electionName;
	Map<String,Object> electionDetails;
	
	public Election() {
		if(electionDetails==null) {
			electionDetails=new HashMap<>();
		}
	}
	public void setElectionDetails(Map<String, Object> electionDetails) {
		this.electionDetails=electionDetails;
	}
	public Object getDetail(ElectionDetail detail) {
		return electionDetails.get(detail.toString());
	}
	public void setDetail(ElectionDetail detail,Object object) {
		electionDetails.put(detail.toString(), object);
	}
	public String getElectionName() {
		return electionName;
	}
	public void setElectionName(String electionName) {
		this.electionName = electionName;
	}
	public Map<String, Object> getElectionDetails() {
		return electionDetails;
	}

}
