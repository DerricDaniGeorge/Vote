package com.derric.vote.dao;

import java.io.IOException;
import java.util.List;

import com.derric.vote.beans.Candidate;
import com.derric.vote.beans.Election;
import com.derric.vote.beans.User;

public interface ICandidateDBService {
	public String getCandidateVotersId(Candidate candidate) ;
	public List<Candidate> getAllCandidates();
	public void insertCandidate(Candidate candidate, Election election, User user) throws IOException;
}
