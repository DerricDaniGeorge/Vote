package com.derric.vote.dao;

import java.io.IOException;
import java.util.List;

import com.derric.vote.beans.Candidate;
import com.derric.vote.beans.User;

public interface ICandidateDBService {
	public void insertCandidate(Candidate candidate,User user) throws IOException;
	public String getCandidateVotersId(Candidate candidate) ;
	public List<Candidate> getAllCandidates();
}
