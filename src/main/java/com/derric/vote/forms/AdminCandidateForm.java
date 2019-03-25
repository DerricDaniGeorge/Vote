package com.derric.vote.forms;

import javax.servlet.http.Part;

public class AdminCandidateForm {
	private String votersId;
	private String firstName;
	private String lastName;
	private String party;
	private String state;
	private String constituency;
	private String electionName;
	private String electionId;
	private String electionStartDate;
	private String electionEndDate;
	public String getElectionId() {
		return electionId;
	}

	public void setElectionId(String electionId) {
		this.electionId = electionId;
	}

	public String getElectionStartDate() {
		return electionStartDate;
	}

	public void setElectionStartDate(String electionStartDate) {
		this.electionStartDate = electionStartDate;
	}

	public String getElectionEndDate() {
		return electionEndDate;
	}

	public void setElectionEndDate(String electionEndDate) {
		this.electionEndDate = electionEndDate;
	}

	public String getElectionName() {
		return electionName;
	}

	public void setElectionName(String electionName) {
		this.electionName = electionName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getConstituency() {
		return constituency;
	}

	public void setConstituency(String constituency) {
		this.constituency = constituency;
	}

	private int age;
	private Part profilePhoto;


	public Part getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(Part profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public Part getSymbol() {
		return symbol;
	}

	public void setSymbol(Part symbol) {
		this.symbol = symbol;
	}

	private Part symbol;

	public String getVotersId() {
		return votersId;
	}

	public void setVotersId(String votersId) {
		this.votersId = votersId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		this.party = party;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
