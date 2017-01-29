package com.niit.collude.backend.dao;

import com.niit.collude.backend.model.UserProfile;

public interface UserProfileDao {

	void create(UserProfile userProfile);
	
	void update(UserProfile userProfile);
	
	void remove(UserProfile userProfile);
	
	UserProfile getUserProfileByUserId(long userId);
	
	UserProfile getUserProfileById(long userProfileId);
	
}
