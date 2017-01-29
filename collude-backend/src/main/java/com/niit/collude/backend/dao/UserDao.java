package com.niit.collude.backend.dao;

import java.util.List;

import com.niit.collude.backend.model.User;

public interface UserDao {

	void create(User user);
	
	void update(User user);
	
	User getUserById(long userId);
	
	User getUserByUsername(String username);
	
	List<User> getAllUsers();
	
	List<User> getAllUsersExceptLoggedIn(long loggedInUserId);
	
	boolean isExistingUser(User aUser);
	
	boolean authenticate(String username, String password);
	
}
