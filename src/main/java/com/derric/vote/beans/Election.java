package com.derric.vote.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Election implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4624483534691075568L;
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
