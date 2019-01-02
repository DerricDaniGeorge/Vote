package com.derric.vote.dao;

import com.derric.vote.beans.User;
import com.derric.vote.beans.UserDetail;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

public class JdbcUserDaoImpl implements UserDAO {

	private JdbcTemplate jdbcTemplate;

	public JdbcUserDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int insert(final User user) {
		String sql = "INSERT INTO app_user(voters_id,first_name,middle_name,last_name,gender,date_of_birth,email,password) VALUES(?,?,?,?,?,?,?,?)";
		int insertedCount = jdbcTemplate.update(sql,
				new Object[] { user.getVotersId(), user.getDetail(UserDetail.FIRST_NAME),
						user.getDetail(UserDetail.MIDDLE_NAME), user.getDetail(UserDetail.LAST_NAME),
						user.getDetail(UserDetail.GENDER), user.getDetail(UserDetail.DATE_OF_BIRTH),
						user.getDetail(UserDetail.EMAIL), user.getPassword() });
		System.out.println(insertedCount + "row(s) inserted");
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
		String sql = "SELECT voters_id FROM app_user WHERE voters_id=?";
		return jdbcTemplate.query(sql, new Object[] { user.getVotersId() }, new ResultSetExtractor<String>() {
			public String extractData(ResultSet rs) throws SQLException {
				return rs.next() ? rs.getString("voters_id") : null;
			}
		});
	}

}
