package com.derric.vote.dao;

import com.derric.vote.beans.User;

public interface IUserDBService {
	public int insert(User user);
	public int update(User user);
	public User findUserByVotersId(String votersId);
	public String getVotersId(User user);
	public String getEmailAddress(User user);
	public String getUserPassword(User user);
}
