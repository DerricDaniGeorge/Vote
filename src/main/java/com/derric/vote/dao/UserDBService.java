package com.derric.vote.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.derric.vote.beans.User;
import com.derric.vote.beans.UserDetail;
import com.derric.vote.constants.ISqlConstants;

public class UserDBService implements IUserDBService {

	private JdbcTemplate jdbcTemplate;
	private static final Logger logger = LogManager.getLogger(UserDBService.class);

	public UserDBService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int insert(final User user) {
		int insertedCount = 0;
		try {
			insertedCount = jdbcTemplate.update(ISqlConstants.ADD_USER,
					new Object[] { user.getVotersId(), user.getDetail(UserDetail.FIRST_NAME),
							user.getDetail(UserDetail.MIDDLE_NAME), user.getDetail(UserDetail.LAST_NAME),
							user.getDetail(UserDetail.GENDER), user.getDetail(UserDetail.DATE_OF_BIRTH),
							user.getDetail(UserDetail.EMAIL), user.getPassword() });
			System.out.println(insertedCount + "row(s) inserted");

		} catch (DataAccessException dae) {
			logger.error("Couldn't communicate with database");
			logger.error(dae);
		}
		return insertedCount;

	}

	@Override
	public int update(User user) {
		// TODO Auto-generated method stub-
		return 0;
	}

	@Override
	public User findUserByVotersId(String votersId) {
		return null;
	}

	@Override
	public String getVotersId(User user) {
		try {
			return jdbcTemplate.query(ISqlConstants.GET_VOTERSID_BY_VOTERSID, new Object[] { user.getVotersId() },
					new ResultSetExtractor<String>() {
						@Override
						public String extractData(ResultSet rs) throws SQLException {
							return rs.next() ? rs.getString("voters_id") : null;
						}
					});
		} catch (DataAccessException dae) {
			System.out.println("OOps");
			dae.printStackTrace();
		}
		return null;
	}

	@Override
	public String getEmailAddress(User user) {
		try {
			return jdbcTemplate.query(ISqlConstants.GET_EMAILID_BY_EMAILID,
					new Object[] { user.getDetail(UserDetail.EMAIL) }, new ResultSetExtractor<String>() {
						@Override
						public String extractData(ResultSet rs) throws SQLException {
							return rs.next() ? rs.getString("email") : null;
						}
					});
		} catch (DataAccessException dae) {
			System.out.println("Oops");
			dae.printStackTrace();
		}
		return null;
	}

	@Override
	public String getUserPassword(User user) {
		try {
			return jdbcTemplate.query(ISqlConstants.GET_PASSWORD_BY_VOTERSID, new Object[] { user.getVotersId() },
					new ResultSetExtractor<String>() {
						@Override
						public String extractData(ResultSet rs) throws SQLException {
							return rs.next() ? rs.getString("password") : null;
						}
					});
		} catch (DataAccessException dae) {
			logger.error("Couldn't fetch user's password from database.");
			logger.error(dae);
		}
		return null;
	}

}
