package com.derric.vote.forms;

import java.time.LocalDate;

public class AdminElectionForm {

private String electionName;
public String getElectionName() {
	return electionName;
}
public void setElectionName(String electionName) {
	this.electionName = electionName;
}
public LocalDate getStartDate() {
	return startDate;
}
public void setStartDate(LocalDate startDate) {
	this.startDate = startDate;
}
public LocalDate getEndDate() {
	return endDate;
}
public void setEndDate(LocalDate endDate) {
	this.endDate = endDate;
}
private LocalDate startDate;
private LocalDate endDate;

}
