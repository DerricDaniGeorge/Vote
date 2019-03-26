package com.derric.vote.dao;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.NoHostAvailableException;
import com.derric.vote.beans.Constituency;
import com.derric.vote.beans.Election;
import com.derric.vote.beans.ElectionDetail;
import com.derric.vote.beans.Party;
import com.derric.vote.beans.State;
import com.derric.vote.beans.User;
import com.derric.vote.beans.VoteConstants;
import com.derric.vote.constants.CqlConstants;
import com.derric.vote.constants.QueryConstants;

public class ElectionDBServiceCassandra implements IElectionDBService {
	
	private Session session;
	public ElectionDBServiceCassandra(Session session) {
		this.session=session;
	}
	@Override
	public List<Election> getNotStartedElections() {
		List<Election> elections=new ArrayList<>();
		try {
			PreparedStatement ps = session.prepare(CqlConstants.GET_NOT_STARTED_ELECTIONS);
			BoundStatement bs=ps.bind(LocalDate.now().getYear());
			ResultSet results=session.execute(bs);
			for(Row row: results) {
				Election election=new Election();
				election.setDetail(ElectionDetail.ELECTION_ID, row.getUUID("election_id"));
				election.setElectionName(row.getString("election_name"));
				election.setDetail(ElectionDetail.ACTIVE_STATUS, row.getString("activation_status"));
				LocalDate startDate=LocalDate.ofEpochDay(row.getDate("start_date").getDaysSinceEpoch());
				election.setDetail(ElectionDetail.START_DATE,startDate);
				LocalDate endDate=LocalDate.ofEpochDay(row.getDate("end_date").getDaysSinceEpoch());
				election.setDetail(ElectionDetail.END_DATE,endDate);
				election.setDetail(ElectionDetail.YEAR, row.getInt("year"));
				elections.add(election);
			}
		}catch(NoHostAvailableException nhae) {
			throw nhae;
		}
		catch(Exception e) {
			throw e;
		}
		return elections;
	}
	@Override
	public void insertElection(Election election,User user) {
		try {
		LocalDate startDate=(LocalDate) election.getDetail(ElectionDetail.START_DATE);
		com.datastax.driver.core.LocalDate cassandraStartDate=com.datastax.driver.core.LocalDate.fromYearMonthDay(startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfMonth());
		LocalDate endDate=(LocalDate) election.getDetail(ElectionDetail.END_DATE);
		com.datastax.driver.core.LocalDate cassandraEndDate=com.datastax.driver.core.LocalDate.fromYearMonthDay(endDate.getYear(), endDate.getMonthValue(), endDate.getDayOfMonth());
		ZonedDateTime timeNow=ZonedDateTime.now(ZoneId.of("Asia/Calcutta"));
		Date dateForCassandra=Date.from(timeNow.toInstant());
		UUID uuid=UUID.randomUUID();
		System.out.println("votersid:"+user.getVotersId());
		PreparedStatement ps = session.prepare(CqlConstants.ADD_ELECTION_BY_STARTDATE);
		BoundStatement bs = ps.bind(election.getDetail(ElectionDetail.YEAR),cassandraStartDate,uuid,VoteConstants.DEACTIVE.toString(),dateForCassandra,election.getElectionName(),cassandraEndDate,dateForCassandra,user.getVotersId());
		session.executeAsync(bs);
		PreparedStatement ps2 = session.prepare(CqlConstants.ADD_ELECTION_BY_ENDDATE);
		BoundStatement bs2 = ps2.bind(election.getDetail(ElectionDetail.YEAR),cassandraStartDate,uuid,VoteConstants.DEACTIVE.toString(),dateForCassandra,election.getElectionName(),cassandraEndDate,dateForCassandra,user.getVotersId());
		session.executeAsync(bs2);
		}catch(NoHostAvailableException nhae) {
			throw nhae;
		}catch(Exception e) {
			throw e;
		}
		
	}
	@Override
	public List<String> getAllElectionNames(){
		List<String> electionNames=new ArrayList<>();
		try {
			ResultSet results=session.execute(QueryConstants.GET_ALL_ELECTION_NAMES);
			for(Row row:results){
				electionNames.add(row.getString("election_name"));
			}
		}catch(NoHostAvailableException nhae) {
			throw nhae;
		}catch(Exception e) {
			throw e;
		}
		return electionNames;
	}
	@Override
	public void updateElection(Election newElection, Election oldElection,User user) {
		try {
			Date oldElectionCreationTimestamp=getElectionCreationTime(oldElection);
			System.out.println("OldElectionCreationTimestamp: "+oldElectionCreationTimestamp+" Election name: "+oldElection.getElectionName());
			deleteElection(oldElection);
			LocalDate startDate=(LocalDate) newElection.getDetail(ElectionDetail.START_DATE);
			com.datastax.driver.core.LocalDate cassandraStartDate=com.datastax.driver.core.LocalDate.fromYearMonthDay(startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfMonth());
			LocalDate endDate=(LocalDate) newElection.getDetail(ElectionDetail.END_DATE);
			com.datastax.driver.core.LocalDate cassandraEndDate=com.datastax.driver.core.LocalDate.fromYearMonthDay(endDate.getYear(), endDate.getMonthValue(), endDate.getDayOfMonth());
			ZonedDateTime timeNow=ZonedDateTime.now(ZoneId.of("Asia/Calcutta"));
			Date dateForCassandra=Date.from(timeNow.toInstant());
			UUID uuid=UUID.randomUUID();
			System.out.println("votersid:"+user.getVotersId());
			PreparedStatement ps = session.prepare(CqlConstants.ADD_ELECTION_BY_STARTDATE);
			BoundStatement bs = ps.bind(newElection.getDetail(ElectionDetail.YEAR),cassandraStartDate,uuid,VoteConstants.DEACTIVE.toString(),oldElectionCreationTimestamp,newElection.getElectionName(),cassandraEndDate,dateForCassandra,user.getVotersId());
			session.executeAsync(bs);
			PreparedStatement ps2 = session.prepare(CqlConstants.ADD_ELECTION_BY_ENDDATE);
			BoundStatement bs2 = ps2.bind(newElection.getDetail(ElectionDetail.YEAR),cassandraStartDate,uuid,VoteConstants.DEACTIVE.toString(),oldElectionCreationTimestamp,newElection.getElectionName(),cassandraEndDate,dateForCassandra,user.getVotersId());
			session.executeAsync(bs2);
			
		}catch(NoHostAvailableException nhae) {
			throw nhae;
		}catch(Exception e) {
			throw e;
		}
	}
	@Override
	public Date getElectionCreationTime(Election election) {
		Date electionCreationDate=null;
		try {
		LocalDate startDate=(LocalDate) election.getDetail(ElectionDetail.START_DATE);
		com.datastax.driver.core.LocalDate cassandraStartDate=com.datastax.driver.core.LocalDate.fromYearMonthDay(startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfMonth());
		PreparedStatement ps = session.prepare(CqlConstants.GET_ELECTION_CREATION_TIME);
		BoundStatement bs=ps.bind(election.getDetail(ElectionDetail.YEAR),cassandraStartDate,UUID.fromString((String)election.getDetail(ElectionDetail.ELECTION_ID)));
		ResultSet results=session.execute(bs);
		for(Row row:results) {
			electionCreationDate=row.getTimestamp("creation_time");
		}
		}catch(NoHostAvailableException nhae) {
			throw nhae;
		}catch(Exception e) {
			throw e;
		}
		return electionCreationDate;
	}
	@Override
	public void deleteElection(Election election) {
		try {
			LocalDate startDate=(LocalDate) election.getDetail(ElectionDetail.START_DATE);
			com.datastax.driver.core.LocalDate cassandraStartDate=com.datastax.driver.core.LocalDate.fromYearMonthDay(startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfMonth());
			LocalDate endDate=(LocalDate) election.getDetail(ElectionDetail.END_DATE);
			com.datastax.driver.core.LocalDate cassandraEndDate=com.datastax.driver.core.LocalDate.fromYearMonthDay(endDate.getYear(), endDate.getMonthValue(), endDate.getDayOfMonth());
			PreparedStatement ps=session.prepare(CqlConstants.DELETE_ELECTION_ELECTION_BY_STARTDATE);
			BoundStatement bs=ps.bind(election.getDetail(ElectionDetail.YEAR),cassandraStartDate,UUID.fromString((String)election.getDetail(ElectionDetail.ELECTION_ID)));
			session.executeAsync(bs);
			PreparedStatement ps2=session.prepare(CqlConstants.DELETE_ELECTION_ELECTION_BY_ENDDATE);
			BoundStatement bs2=ps2.bind(election.getDetail(ElectionDetail.YEAR),cassandraEndDate,UUID.fromString((String)election.getDetail(ElectionDetail.ELECTION_ID)));
			session.executeAsync(bs2);
		}catch(NoHostAvailableException nhae) {
			throw nhae;
		}catch(Exception e) {
			throw e;
		}
	}
	@Override
	public List<String> getAllStateNames() {
		List<String> stateNames=new ArrayList<>();
		try {
			ResultSet result=session.execute(CqlConstants.GET_ALL_STATES);
			for(Row row:result) {
				stateNames.add(row.getString("state"));
			}
		}catch(NoHostAvailableException nhae) {
			throw nhae;
		}catch(Exception e) {
			throw e;
		}
		return stateNames;
	}
	@Override
	public void insertState(State state, User user) {
		try {
			ZonedDateTime timeNow=ZonedDateTime.now(ZoneId.of("Asia/Calcutta"));
			Date dateForCassandra=Date.from(timeNow.toInstant());
			PreparedStatement ps=session.prepare(CqlConstants.ADD_STATE);
			BoundStatement bs=ps.bind(state.getStateName(),dateForCassandra,user.getVotersId(),dateForCassandra);
			session.execute(bs);
		}catch(NoHostAvailableException nhae) {
			throw nhae;
		}catch(Exception e) {
			throw e;
		}
		
	}
	@Override
	public void deleteState(State state) {
		try {
			PreparedStatement ps=session.prepare(CqlConstants.DELETE_STATE);
			BoundStatement bs=ps.bind(state.getStateName());
			session.execute(bs);
		}catch(NoHostAvailableException nhae) {
			throw nhae;
		}catch(Exception e) {
			throw e;
		}
	}
	@Override
	public List<String> getAllLokSabhaConstituenciesName() {
		List<String> constituencyNames=new ArrayList<>();
		try {
			ResultSet result=session.execute(CqlConstants.GET_ALL_LOKSABHA_CONSTITUECY_NAMES);
			for(Row row:result) {
				constituencyNames.add(row.getString("constituency"));
			}
		}catch(NoHostAvailableException nhae) {
			throw nhae;
		}catch(Exception e) {
			throw e;
		}
		return constituencyNames;
	}
	@Override
	public void insertConstituency(Constituency constituency, User user) {
		try {
			ZonedDateTime timeNow=ZonedDateTime.now(ZoneId.of("Asia/Calcutta"));
			Date dateForCassandra=Date.from(timeNow.toInstant());
			PreparedStatement ps=session.prepare(CqlConstants.ADD_LOKSAHBA_CONSTITUENCY);
			BoundStatement bs=ps.bind(constituency.getConstituencyName(),dateForCassandra,user.getVotersId(),dateForCassandra);
			session.execute(bs);
		}catch(NoHostAvailableException nhae) {
			throw nhae;
		}catch(Exception e) {
			throw e;
		}	
	}
	@Override
	public void deleteLoksabhaConstituency(Constituency constituency) {
		try {
			PreparedStatement ps=session.prepare(CqlConstants.DELETE_LOKSABHA_CONSTITUENCY);
			BoundStatement bs=ps.bind(constituency.getConstituencyName());
			session.execute(bs);
		}catch(NoHostAvailableException nhae) {
			throw nhae;
		}catch(Exception e) {
			throw e;
		}
	}
	@Override
	public void updateLoksabhaConstituencies(State state, List<Constituency> constituencyList, User user) {
		try {
			ZonedDateTime timeNow=ZonedDateTime.now(ZoneId.of("Asia/Calcutta"));
			Date dateForCassandra=Date.from(timeNow.toInstant());
			Set<String> constituencySet=new HashSet<>();
			for(Constituency c: constituencyList) {
				constituencySet.add(c.getConstituencyName());
			}
			PreparedStatement ps=session.prepare(CqlConstants.UPDATE_STATE_LOK_SABHA_MAPPING);
			BoundStatement bs=ps.bind(dateForCassandra,user.getVotersId(),constituencySet,state.getStateName());
			session.execute(bs);
		}catch(NoHostAvailableException nhae) {
			throw nhae;
		}catch(Exception e) {
			throw e;
		}
		
	}
	@Override
	public String getStateOfLokSabhaMapping(State state) {
		String stateName=null;
		try {
			PreparedStatement ps=session.prepare(CqlConstants.GET_STATE_LOK_SABHA_MAPPING);
			BoundStatement bs=ps.bind(state.getStateName());
			ResultSet result=session.execute(bs);
			Row row=result.one();
			if(row!=null) {
				stateName=row.getString("state");
			}
		}catch(NoHostAvailableException nhae) {
			throw nhae;
		}catch(Exception e) {
			throw e;
		}
		return stateName;
	}
	@Override
	public void insertNewLoksabhaStateMapping(State state, List<Constituency> constituencyList, User user) {
		try {
			ZonedDateTime timeNow=ZonedDateTime.now(ZoneId.of("Asia/Calcutta"));
			Date dateForCassandra=Date.from(timeNow.toInstant());
			Set<String> constituencySet=new HashSet<>();
			for(Constituency c: constituencyList) {
				constituencySet.add(c.getConstituencyName());
			}
			PreparedStatement ps=session.prepare(CqlConstants.MAP_LOKSABHA_CONSTITUENCY);
			BoundStatement bs=ps.bind(dateForCassandra,dateForCassandra,user.getVotersId(),state.getStateName(),constituencySet);
			session.execute(bs);
		}catch(NoHostAvailableException nhae) {
			throw nhae;
		}catch(Exception e) {
			throw e;
		}
		
	}
	@Override
	public Set<String> getStateMappedConstituencies(State state) {
		Set<String> consituencySet=new HashSet<>();
		try {
			PreparedStatement ps=session.prepare(CqlConstants.GET_MAPPED_CONSTITUENCIES_STATE);
			BoundStatement bs=ps.bind(state.getStateName());
			ResultSet results=session.execute(bs);
			Row row=results.one();
			if(row!=null) {
				consituencySet=row.getSet("loksabha_constituencies", String.class);
			}
		}catch(NoHostAvailableException nhae) {
			throw nhae;
		}catch(Exception e) {
			throw e;
		}
		return consituencySet;
	}
	@Override
	public List<String> getAllPartyNames() {
		List<String> partyNames=new ArrayList<>();
		try {
			ResultSet result=session.execute(CqlConstants.GET_ALL_PARTIES);
			for(Row row:result) {
				partyNames.add(row.getString("party"));
			}
		}catch(NoHostAvailableException nhae) {
			throw nhae;
		}catch(Exception e) {
			throw e;
		}
		return partyNames;
	}
	@Override
	public void insertParty(Party party, User user) {
		try {
			ZonedDateTime timeNow=ZonedDateTime.now(ZoneId.of("Asia/Calcutta"));
			Date dateForCassandra=Date.from(timeNow.toInstant());
			PreparedStatement ps=session.prepare(CqlConstants.ADD_PARTY);
			BoundStatement bs=ps.bind(party.getPartyName(),dateForCassandra,user.getVotersId(),dateForCassandra);
			session.execute(bs);
		}catch(NoHostAvailableException nhae) {
			throw nhae;
		}catch(Exception e) {
			throw e;
		}
		
	}
	@Override
	public void deleteParty(Party party) {
		try {
			PreparedStatement ps=session.prepare(CqlConstants.DELETE_PARTY);
			BoundStatement bs=ps.bind(party.getPartyName());
			session.execute(bs);
		}catch(NoHostAvailableException nhae) {
			throw nhae;
		}catch(Exception e) {
			throw e;
		}
	}
}

