package com.derric.vote.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.derric.vote.beans.Constituency;
import com.derric.vote.beans.Election;
import com.derric.vote.beans.Party;
import com.derric.vote.beans.State;
import com.derric.vote.beans.User;

public interface IElectionDBService {
	public List<Election> getNotStartedElections();
	public void insertElection(Election election,User user);
	public void updateElection(Election newElection,Election oldElection,User user);
	public void deleteElection(Election election);
	public List<String> getAllElectionNames();
	public List<String> getAllStateNames();
	public void insertState(State state,User user);
	public List<String> getAllLokSabhaConstituenciesName();
	public void insertConstituency(Constituency constituency,User user);
	public Date getElectionCreationTime(Election election);
	public void deleteState(State state);
	public void deleteLoksabhaConstituency(Constituency constituency);
	public void updateLoksabhaConstituencies(State state, List<Constituency> constituencyList, User user);
	public void insertNewLoksabhaStateMapping(State state, List<Constituency> constituencyList, User user);
	public String getStateOfLokSabhaMapping(State state);
	public Set<String> getStateMappedConstituencies(State state);
	public void insertParty(Party party, User user);
	public void deleteParty(Party party);
	public List<String> getAllPartyNames();
}
