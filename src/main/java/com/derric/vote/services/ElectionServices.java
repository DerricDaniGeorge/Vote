package com.derric.vote.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

import com.derric.vote.beans.Election;
import com.derric.vote.beans.ElectionDetail;
import com.derric.vote.dao.IElectionDBService;

public class ElectionServices {
	@Autowired
	private IElectionDBService electionDBService;
	
	public boolean isCurrentlyRunningElection(Election election) {
		boolean isRunning=false;
		String runningStatus=(String)election.getDetail(ElectionDetail.ACTIVE_STATUS);
		if(runningStatus.equals("RUNNING")) {
			isRunning=true;
		}else {
			isRunning=false;
		}
		return isRunning;
	}
	
	public List<Election> getElectionsNotStartedAndCurrentlyRunning() {
		List<Election> elections=electionDBService.getNotStartedElections();
		Stream<Election> filter=elections.stream().filter(each->!isCurrentlyRunningElection(each));
		List<Election> filteredElections=filter.collect(Collectors.toList());
		return filteredElections;
	}
}
