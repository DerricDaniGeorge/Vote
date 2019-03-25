package com.derric.vote.dao;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Part;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
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
	public void insertCandidate(Candidate candidate, Election election, User user) throws IOException {
		try {
			Part profileImage = (Part) candidate.getDetail(CandidateDetail.PROFILE_PHOTO);
			InputStream is = profileImage.getInputStream();
			byte[] bytes = new byte[is.available() + 1];
			is.read(bytes);
			ByteBuffer profileImgBuffer = ByteBuffer.wrap(bytes);
			is.close();
			Part symbolImage = (Part) candidate.getDetail(CandidateDetail.SYMBOL);
			InputStream is2 = symbolImage.getInputStream();
			byte[] bytes2 = new byte[is2.available() + 1];
			is2.read(bytes2);
			ByteBuffer symbolImgBuffer = ByteBuffer.wrap(bytes2);
			is2.close();
			ZonedDateTime timeNow = ZonedDateTime.now(ZoneId.of("Asia/Calcutta"));
			Date dateForCassandra = Date.from(timeNow.toInstant());
			PreparedStatement ps = session.prepare(CqlConstants.ADD_CANDIDATE);
			BoundStatement bs = ps.bind(candidate.getVotersId(), candidate.getDetail(CandidateDetail.FIRST_NAME),
					candidate.getDetail(CandidateDetail.LAST_NAME), candidate.getDetail(CandidateDetail.PARTY),
					candidate.getDetail(CandidateDetail.AGE), profileImgBuffer, symbolImgBuffer,
					candidate.getDetail(CandidateDetail.PROFILE_PHOTO_LENGTH),
					candidate.getDetail(CandidateDetail.SYMBOL_IMG_LENGTH), dateForCassandra, dateForCassandra,
					user.getVotersId(), candidate.getDetail(CandidateDetail.STATE),
					candidate.getDetail(CandidateDetail.CONSTITUENCY),UUID.randomUUID(),election.getElectionName(),election.getDetail(ElectionDetail.ELECTION_ID),
					election.getDetail(ElectionDetail.START_DATE),election.getDetail(ElectionDetail.END_DATE));
			session.execute(bs);

		} catch (NoHostAvailableException nhae) {
			throw nhae;
		} catch (IOException ioe) {
			throw ioe;
		} catch (Exception e) {
			throw e;
		}
	}

	public String getCandidateVotersId(Candidate candidate) {
		String votersId = null;
		try {
			PreparedStatement ps = session.prepare(QueryConstants.GET_CANDIDATE_VOTERSID_BY_VOTERSID);
			BoundStatement bs = ps.bind(candidate.getVotersId());
			ResultSet rs = session.execute(bs);
			Row row = rs.one();
			if (row != null) {
				votersId = row.getString("voters_id");
			}
			System.out.println("votersid-->" + votersId);
		} catch (NoHostAvailableException nhae) {
			throw nhae;
		} catch (Exception e) {
			throw e;
		}
		return votersId;
	}

	@Override
	public List<Candidate> getAllCandidates() {
		List<Candidate> candidateList = new ArrayList<>();
		try {
			ResultSet result = session.execute(CqlConstants.GET_ALL_CANDIDATE_DETAILS);
			for (Row row : result) {
				Candidate candidate = new Candidate();
				candidate.setVotersId(row.getString("voters_id"));
				candidate.setDetail(CandidateDetail.FIRST_NAME, row.getString("first_name"));
				candidate.setDetail(CandidateDetail.LAST_NAME, row.getString("last_name"));
				candidate.setDetail(CandidateDetail.AGE, row.getInt("age"));
				candidate.setDetail(CandidateDetail.PARTY, row.getString("party"));
				candidate.setDetail(CandidateDetail.PROFILE_PHOTO, row.getBytes("profile_photo"));
				candidate.setDetail(CandidateDetail.SYMBOL, row.getBytes("election_symbol_img"));
				candidateList.add(candidate);
			}
		} catch (NoHostAvailableException nhae) {
			throw nhae;
		} catch (Exception e) {
			throw e;
		}
		return candidateList;
	}

}
