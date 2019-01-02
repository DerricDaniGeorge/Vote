package com.derric.vote.dao;

import com.derric.vote.beans.User;

public interface UserDAO {
	public int insert(User user);
	public int update(User user);
	public User findUserByVotersId(String votersId);
	public String getVotersId(User user);
}
