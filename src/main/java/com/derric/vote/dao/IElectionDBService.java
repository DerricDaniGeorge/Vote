package com.derric.vote.dao;

import java.util.List;

import com.derric.vote.beans.Election;
import com.derric.vote.beans.User;

public interface IElectionDBService {
	public List<Election> getNotStartedElections();
	public void insertElection(Election election,User user);
	public void updateElection(Election newElection,Election oldElection,User user);
	public void deleteElection(Election election);
	public List<String> getAllElectionNames();
}
