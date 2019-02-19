package com.derric.vote.dao;

import java.time.LocalDate;

import org.springframework.dao.DataAccessException;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.derric.vote.beans.User;
import com.derric.vote.beans.UserDetail;
import com.derric.vote.constants.ISqlConstants;

public class UserDBServiceCassandra implements IUserDBService {

	public UserDBServiceCassandra(Session session) {
		this.session = session;
	}

	private Session session;

	@Override
	public void insertUser(User user) {
		LocalDate date=(LocalDate) user.getDetail(UserDetail.DATE_OF_BIRTH);
		com.datastax.driver.core.LocalDate cassandraDate=com.datastax.driver.core.LocalDate.fromMillisSinceEpoch(date.toEpochDay());
		PreparedStatement ps = session.prepare(ISqlConstants.ADD_USER_APP_USER);
		BoundStatement bs = ps.bind(user.getVotersId(), user.getDetail(UserDetail.FIRST_NAME),
				user.getDetail(UserDetail.MIDDLE_NAME), user.getDetail(UserDetail.LAST_NAME),
				user.getDetail(UserDetail.GENDER), cassandraDate,
				user.getDetail(UserDetail.EMAIL), user.getPassword(),"VOTER");
		session.executeAsync(bs);
		session.executeAsync(insertUser_user_by_email(user));
	}

	public BoundStatement insertUser_user_by_email(User user) {
		PreparedStatement ps=session.prepare(ISqlConstants.ADD_USER_USER_BY_EMAIL);
		BoundStatement bs=ps.bind(user.getVotersId(),user.getDetail(UserDetail.FIRST_NAME),user.getDetail(UserDetail.MIDDLE_NAME),
				user.getDetail(UserDetail.LAST_NAME),user.getDetail(UserDetail.EMAIL));
		return bs;
	}

	@Override
	public int update(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public User findUserByVotersId(String votersId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getVotersId(User user) {
		String votersId=null;
		try {
			PreparedStatement ps = session.prepare(ISqlConstants.GET_VOTERSID_BY_VOTERSID);
			BoundStatement bs=ps.bind(user.getVotersId());
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

	@Override
	public String getEmailAddress(User user) {
		String email=null;
		try {
		PreparedStatement ps = session.prepare(ISqlConstants.GET_EMAILID_BY_EMAILID);
		BoundStatement bs=ps.bind(user.getDetail(UserDetail.EMAIL));
		//ResultSetFuture future=session.executeAsync(bs);
		ResultSet rs=session.execute(bs);
		Row row=rs.one();
		if(row!=null) {
			email=row.getString("email");
		}
		System.out.println("email-->"+email);	
		}catch(Exception e) {
			throw e;
		}
		return email;
	}

	@Override
	public String getUserPassword(User user) {
		String password=null;
		System.out.println("voters_id-->"+user.getVotersId());
		try {
		PreparedStatement ps = session.prepare(ISqlConstants.GET_PASSWORD_BY_VOTERSID);
		BoundStatement bs=ps.bind(user.getVotersId());
		//ResultSetFuture future=session.executeAsync(bs);
		ResultSet rs=session.execute(bs);
		Row row=rs.one();
		if(row!=null) {
			password=row.getString("password");
		}
		System.out.println("password-->"+password);	
		}catch(Exception e) {
			throw e;
		}
		return password;

	}

}
