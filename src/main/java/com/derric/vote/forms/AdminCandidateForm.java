package com.derric.vote.forms;

import javax.servlet.http.Part;

public class AdminCandidateForm {
	private String votersId;
	private String firstName;
	private String lastName;
	private String party;

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
