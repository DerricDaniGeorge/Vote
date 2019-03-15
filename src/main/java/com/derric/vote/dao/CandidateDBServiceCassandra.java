package com.derric.vote.dao;

import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.util.UUID;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.NoHostAvailableException;
import com.derric.vote.beans.Candidate;
import com.derric.vote.beans.CandidateDetail;
import com.derric.vote.beans.Election;
import com.derric.vote.beans.ElectionDetail;
import com.derric.vote.beans.User;
import com.derric.vote.constants.CqlConstants;
import com.derric.vote.constants.QueryConstants;

public class CandidateDBServiceCassandra implements ICandidateDBService {

	public CandidateDBServiceCassandra(Session session) {
		this.session = session;
	}

	private Session session;

	@Override
	public void insertCandidate(Candidate candidate) {
		try {
			PreparedStatement ps = session.prepare(CqlConstants.ADD_CANDIDATE);
			BoundStatement bs = ps.bind(candidate.getVotersId(),candidate.getDetail(CandidateDetail.FIRST_NAME),
					candidate.getDetail(CandidateDetail.LAST_NAME), candidate.getDetail(CandidateDetail.PARTY),
					candidate.getDetail(CandidateDetail.AGE),
					(ByteBuffer) candidate.getDetail(CandidateDetail.PROFILE_PHOTO),
					(ByteBuffer) candidate.getDetail(CandidateDetail.ELECTION_SYMBOL),
					candidate.getDetail(CandidateDetail.PROFILE_PHOTO_LENGTH),
					candidate.getDetail(CandidateDetail.SYMBOL_IMG_LENGTH));
			session.execute(bs);
		} catch (NoHostAvailableException nhae) {
			throw nhae;
		} catch (Exception e) {
			throw e;
		}
	}
	public String getCandidateVotersId(Candidate candidate) {
		String votersId=null;
		try {
			PreparedStatement ps = session.prepare(QueryConstants.GET_CANDIDATE_VOTERSID_BY_VOTERSID);
			BoundStatement bs=ps.bind(candidate.getVotersId());
			ResultSet rs=session.execute(bs);
			Row row=rs.one();
			if(row!=null) {
				votersId=row.getString("voters_id");
			}
			System.out.println("votersid-->"+votersId);
		}catch(Exception e) {
			throw e;
		}
		return votersId;
	}


}
