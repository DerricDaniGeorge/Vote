package com.derric.vote.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.derric.vote.beans.User;
import com.derric.vote.beans.UserDetail;
import com.derric.vote.constants.ISqlConstants;

public class UserDBService /* implements IUserDBService */ {

	private JdbcTemplate jdbcTemplate;

	public UserDBService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public int insert_user_by_email(User user) {
		int user_by_email_insertedCount = 0;
		try {
			user_by_email_insertedCount = jdbcTemplate.update(ISqlConstants.ADD_USER_USER_BY_EMAIL,
					new Object[] { user.getVotersId(), user.getDetail(UserDetail.FIRST_NAME),
							user.getDetail(UserDetail.MIDDLE_NAME), user.getDetail(UserDetail.LAST_NAME),
							user.getDetail(UserDetail.GENDER), user.getDetail(UserDetail.DATE_OF_BIRTH),
							user.getDetail(UserDetail.EMAIL), user.getPassword() });
			System.out.println(user_by_email_insertedCount + "row(s) inserted into user_by_email table");
		} catch (DataAccessException dae) {
			throw dae;
		} catch (Exception e) {
			throw e;
		}
		return user_by_email_insertedCount;
	}

	public int insertUser(final User user) {
		int insertedCount = 0;
		int app_user_insertedCount = 0;
		int user_by_email_insertedCount = 0;
		try {
			app_user_insertedCount = jdbcTemplate.update(ISqlConstants.ADD_USER_APP_USER,
					new Object[] { user.getVotersId(), user.getDetail(UserDetail.FIRST_NAME),
							user.getDetail(UserDetail.MIDDLE_NAME), user.getDetail(UserDetail.LAST_NAME),
							user.getDetail(UserDetail.GENDER), user.getDetail(UserDetail.DATE_OF_BIRTH),
							user.getDetail(UserDetail.EMAIL), user.getPassword() });
			System.out.println(insertedCount + "row(s) inserted into app_user table");
			user_by_email_insertedCount = insert_user_by_email(user);
		} catch (DataAccessException dae) {
			throw dae;
		} catch (Exception e) {
			throw e;
		}
		return app_user_insertedCount + user_by_email_insertedCount;

	}

	public int update(User user) {
		// TODO Auto-generated method stub-
		return 0;
	}

	public User findUserByVotersId(String votersId) {
		return null;
	}

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
			throw dae;
		} catch (Exception e) {
			throw e;
		}
	}

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
			throw dae;
		} catch (Exception e) {
			throw e;
		}
	}

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
			throw dae;
		} catch (Exception e) {
			throw e;
		}
	}

}
