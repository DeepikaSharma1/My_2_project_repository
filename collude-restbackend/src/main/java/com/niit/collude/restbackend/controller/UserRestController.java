package com.niit.collude.restbackend.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collude.backend.dao.UserDao;
import com.niit.collude.backend.model.User;

@RestController
public class UserRestController {

	@Autowired
	UserDao userDao;
	
	@GetMapping(value = "/user/all/")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userDao.getAllUsers();
		if (users.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@GetMapping(value = "/user/others/")
	public ResponseEntity<List<User>> getAllUsersExceptLoggedIn(HttpSession session) {
		long loggedInUserId = (Long) session.getAttribute("loggedInUserId");
		List<User> users = userDao.getAllUsersExceptLoggedIn(loggedInUserId);
		if (users.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@GetMapping(value = "/user/id/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUserById(@PathVariable("userId") long userId) {
		User user = userDao.getUserById(userId);
		if (user != null) {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "/user/username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUserById(@PathVariable("username") String username) {
		User user = userDao.getUserByUsername(username);
		if (user != null) {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(value = "/user/")
	public ResponseEntity<Void> createUser(@RequestBody User user) {
		boolean userExists = userDao.isExistingUser(user);
		if (userExists) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		
		user.setEnabled(false);
		user.setOnline(false);
		
		userDao.create(user);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/user/{userId}")
	public ResponseEntity<User> updateUser(@PathVariable("userId") long userId, @RequestBody User euser) {
		User user = userDao.getUserById(userId);
		if (user != null) {
			user.setUsername(euser.getUsername());
			user.setPassword(euser.getPassword());
			user.setEmail(euser.getEmail());
			user.setRole(euser.getRole());
			user.setEnabled(euser.isEnabled());
			
			userDao.update(user);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping(value = "/user/{userId}")
	public ResponseEntity<User> removeUser(@PathVariable("userId") long userId) {
		User user = userDao.getUserById(userId);
		if (user != null) {
			user.setEnabled(false);
			userDao.update(user);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(value = "/user/auth/")
	public ResponseEntity<User> authenticate(@RequestBody User user, HttpSession session) {
		boolean result = userDao.authenticate(user.getUsername(), user.getPassword());
		if (result) {
			User authUser = userDao.getUserByUsername(user.getUsername());
			authUser.setOnline(true);
			userDao.update(authUser);
			session.setAttribute("loggedInUser", authUser);
			session.setAttribute("loggedInUserId", authUser.getUserId());
			
			return new ResponseEntity<User>(authUser, HttpStatus.OK);
		}
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "/user/logout/")
	public ResponseEntity<User> logout(HttpSession session) {
		long loggedInUserId = (Long) session.getAttribute("loggedInUserId");
		User user = userDao.getUserById(loggedInUserId);
		
		if (user != null) {
			user.setOnline(false);
			userDao.update(user);
			session.removeAttribute("loggedInUser");
			session.removeAttribute("loggedInUserId");
			session.invalidate();
			return new ResponseEntity<User>(HttpStatus.OK);
		}
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
}
