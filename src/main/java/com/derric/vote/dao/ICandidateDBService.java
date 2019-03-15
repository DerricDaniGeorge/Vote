package com.derric.vote.dao;

import com.derric.vote.beans.Candidate;

public interface ICandidateDBService {
	public void insertCandidate(Candidate candidate);
	public String getCandidateVotersId(Candidate candidate) ;
}
