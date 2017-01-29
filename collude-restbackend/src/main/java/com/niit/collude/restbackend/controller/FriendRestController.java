package com.niit.collude.restbackend.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collude.backend.dao.FriendDao;
import com.niit.collude.backend.dao.UserDao;
import com.niit.collude.backend.model.Friend;

@RestController
public class FriendRestController {

	@Autowired
	UserDao userDao;
	
	@Autowired
	FriendDao friendDao;
	
	@GetMapping(value = "/friend/sendRequest/{toId}")
	public ResponseEntity<Friend> sendFriendRequest(@PathVariable("toId") long friendId, HttpSession session) {
		long loggedInUserId = (Long) session.getAttribute("loggedInUserId");
		Friend request = new Friend();
		
		request.setUser(userDao.getUserById(loggedInUserId));
		request.setFriend(userDao.getUserById(friendId));
		request.setStatus("NEW_REQUEST");
		request.setOnline(false);
		
		friendDao.create(request);
		
		return new ResponseEntity<Friend>(request, HttpStatus.OK);
	}
	
	@GetMapping(value = "/friend/friends/")
	public ResponseEntity<List<Friend>> getMyFriends(HttpSession session) {
		long loggedInUserId = (Long) session.getAttribute("loggedInUserId");
		List<Friend> myFriendRequests = friendDao.getFriendsOfUser(loggedInUserId);
		return new ResponseEntity<List<Friend>>(myFriendRequests, HttpStatus.OK);
	}
		
	@GetMapping(value = "/friend/friendRequests/")
	public ResponseEntity<List<Friend>> getMyNewFriendRequests(HttpSession session) {
		long loggedInUserId = (Long) session.getAttribute("loggedInUserId");
		List<Friend> myFriendRequests = friendDao.getFriendRequestOfUser(loggedInUserId);
		return new ResponseEntity<List<Friend>>(myFriendRequests, HttpStatus.OK);
	}
	
	@GetMapping(value = "/friend/accept/{id}")
	public ResponseEntity<Friend> acceptFriendRequest(@PathVariable("id") long friendId, HttpSession session) {
		long loggedInUserId = (Long) session.getAttribute("loggedInUserId");
		Friend request = friendDao.getFriend(loggedInUserId, friendId);
		Friend request2 = friendDao.getFriend(friendId, loggedInUserId);
		request.setStatus("ACCEPTED");
		request2.setStatus("ACCEPTED");
		friendDao.update(request);
		friendDao.update(request2);
		
		return new ResponseEntity<Friend>(request, HttpStatus.OK);
	}
	
	@GetMapping(value = "/friend/reject/{id}")
	public ResponseEntity<Friend> rejectFriendRequest(@PathVariable("id") long friendId, HttpSession session) {
		long loggedInUserId = (Long) session.getAttribute("loggedInUserId");
		Friend request = friendDao.getFriend(loggedInUserId, friendId);
		Friend request2 = friendDao.getFriend(friendId, loggedInUserId);
		request.setStatus("REJECTED");
		request2.setStatus("REJECTED");
		friendDao.update(request);
		friendDao.update(request2);
		
		return new ResponseEntity<Friend>(request, HttpStatus.OK);
	}
}
