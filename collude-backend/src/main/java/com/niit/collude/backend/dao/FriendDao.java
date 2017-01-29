package com.niit.collude.backend.dao;

import java.util.List;

import com.niit.collude.backend.model.Friend;

public interface FriendDao {

	void create(Friend friend);
	
	void update(Friend friend);
	
	void remove(Friend friend);
	
	Friend getFriend(long userId, long friendId);
	
	List<Friend> getFriendsOfUser(long userId);
	
	List<Friend> getFriendRequestOfUser(long userId);
	
	void setOnline(long userId);
	
	void setOffline(long userId);
	
}
