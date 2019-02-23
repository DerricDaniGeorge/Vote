package com.derric.vote.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.derric.vote.beans.Election;
import com.derric.vote.beans.ElectionDetail;
import com.derric.vote.constants.ICqlConstants;

public class ElectionDBServiceCassandra implements IElectionDBService {
	
	private Session session;
	public ElectionDBServiceCassandra(Session session) {
		this.session=session;
	}
	public List<Election> getNotStartedElections() {
		List<Election> elections=new ArrayList<>();
		try {
			PreparedStatement ps = session.prepare(ICqlConstants.GET_NOT_STARTED_ELECTIONS);
			BoundStatement bs=ps.bind(LocalDate.now().getYear());
			ResultSet results=session.execute(bs);
			for(Row row: results) {
				Election election=new Election();
				election.setElectionName(row.getString("election_name"));
				election.setDetail(ElectionDetail.ACTIVE_STATUS, row.getString("activation_status"));
				LocalDate startDate=LocalDate.ofEpochDay(row.getDate("start_date").getDaysSinceEpoch());
				election.setDetail(ElectionDetail.START_DATE,startDate);
				LocalDate endDate=LocalDate.ofEpochDay(row.getDate("end_date").getDaysSinceEpoch());
				election.setDetail(ElectionDetail.END_DATE,endDate);
				election.setDetail(ElectionDetail.YEAR, row.getInt("year"));
				elections.add(election);
			}
		}catch(Exception e) {
			throw e;
		}
		return elections;
	}

	}

