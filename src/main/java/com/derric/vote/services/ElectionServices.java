package com.derric.vote.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

import com.derric.vote.beans.Election;
import com.derric.vote.beans.ElectionDetail;
import com.derric.vote.beans.User;
import com.derric.vote.beans.VoteConstants;
import com.derric.vote.dao.IElectionDBService;
import com.derric.vote.forms.AdminElectionForm;

/**
 * @author Leo
 *
 */
public class ElectionServices {
	@Autowired
	private IElectionDBService electionDBService;
	
	public boolean isCurrentlyRunningElection(Election election) {
		boolean isRunning=false;
		String runningStatus=(String)election.getDetail(ElectionDetail.ACTIVE_STATUS);
		if(runningStatus.equals(VoteConstants.RUNNING.toString())) {
			isRunning=true;
		}else {
			isRunning=false;
		}
		return isRunning;
	}
	
	public List<Election> getElectionsNotStartedAndCurrentlyRunning() {
		List<Election> elections=electionDBService.getNotStartedElections();
		Stream<Election> filter=elections.stream().filter(each->!isCurrentlyRunningElection(each));
		return filter.collect(Collectors.toList());
	}
	public boolean isElectionAlreadyExists(Election election) {
		List<String> electionNames=electionDBService.getAllElectionNames();
		return electionNames.contains(election.getElectionName())?true:false;
	}
	public boolean addElection(AdminElectionForm electionForm,User user) {
		boolean isElectionAdded=false;
		Election election=new Election();
		election.setElectionName(electionForm.getElectionName());
		election.setDetail(ElectionDetail.START_DATE,electionForm.getStartDate());
		election.setDetail(ElectionDetail.END_DATE,electionForm.getEndDate());
		election.setDetail(ElectionDetail.YEAR,electionForm.getStartDate().getYear());
		election.setDetail(ElectionDetail.ACTIVE_STATUS,VoteConstants.DEACTIVE);
		if(!isElectionAlreadyExists(election)) {
		electionDBService.insertElection(election,user);
		isElectionAdded=true;
		}else {
			isElectionAdded=false;
		}
		return isElectionAdded;
	}
	public void updateElection(AdminElectionForm electionForm,User user,String oldElectionID,String oldStartDate,String oldEndDate) {
		Election newElection=new Election();
		newElection.setElectionName(electionForm.getElectionName());
		newElection.setDetail(ElectionDetail.START_DATE,electionForm.getStartDate());
		newElection.setDetail(ElectionDetail.END_DATE,electionForm.getEndDate());
		newElection.setDetail(ElectionDetail.YEAR,electionForm.getStartDate().getYear());
		Election oldElection=new Election();
		oldElection.setDetail(ElectionDetail.ELECTION_ID,oldElectionID);
		LocalDate oldStartDateDate=LocalDate.parse(oldStartDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		oldElection.setDetail(ElectionDetail.START_DATE,oldStartDateDate);
		LocalDate oldEndDateDate=LocalDate.parse(oldEndDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		oldElection.setDetail(ElectionDetail.END_DATE,oldEndDateDate);
		oldElection.setDetail(ElectionDetail.YEAR,((LocalDate)oldElection.getDetail(ElectionDetail.START_DATE)).getYear());
		electionDBService.updateElection(newElection, oldElection,user);
		
	}
	public void deleteElection(String electionId,String startDate,String endDate) {
		Election election=new Election();
		election.setDetail(ElectionDetail.ELECTION_ID,electionId);
		LocalDate startDateDate=LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		election.setDetail(ElectionDetail.START_DATE,startDateDate);
		LocalDate endDateDate=LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		election.setDetail(ElectionDetail.END_DATE,endDateDate);
		election.setDetail(ElectionDetail.YEAR,((LocalDate)election.getDetail(ElectionDetail.START_DATE)).getYear());
		electionDBService.deleteElection(election);
	}

}
