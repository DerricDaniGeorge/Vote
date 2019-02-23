package com.derric.vote.dao;

import java.util.List;

import com.derric.vote.beans.Election;

public interface IElectionDBService {
	public List<Election> getNotStartedElections();
}
