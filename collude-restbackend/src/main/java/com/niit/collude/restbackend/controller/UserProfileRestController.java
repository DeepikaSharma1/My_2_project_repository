package com.niit.collude.restbackend.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collude.backend.dao.UserDao;
import com.niit.collude.backend.dao.UserProfileDao;
import com.niit.collude.backend.model.UserProfile;

@RestController
public class UserProfileRestController {
	@Autowired
	UserProfileDao profileDao;
	
	@Autowired
	UserDao userDao;
	
	@GetMapping(value = "/profile/")
	public ResponseEntity<UserProfile> getProfile(HttpSession session) {
		long userId = (Long) session.getAttribute("loggedInUserId");
		UserProfile profile = profileDao.getUserProfileByUserId(userId);
		if (profile == null) {
			return new ResponseEntity<UserProfile>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<UserProfile>(profile, HttpStatus.OK);
	}
	
	@PostMapping(value = "/profile/")
	public ResponseEntity<Void> createProfile(@RequestBody UserProfile profile, HttpSession session) {
		long loggedInUserId = (Long) session.getAttribute("loggedInUserId");
		profile.setUser(userDao.getUserById(loggedInUserId));
		profileDao.create(profile);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@PutMapping(value = "/profile/{profileId}")
	public ResponseEntity<UserProfile> updateProfile(@PathVariable("profileId") long profileId, @RequestBody UserProfile profile) {
		UserProfile p = profileDao.getUserProfileById(profileId);
		
		p.setCity(profile.getCity());
		p.setFirstName(profile.getFirstName());
		p.setLastName(profile.getLastName());
		p.setGender(profile.getGender());
		p.setImage(profile.getImage());
		p.setMobile(profile.getMobile());
		p.setUser(profile.getUser());
		
		profileDao.update(p);
		
		return new ResponseEntity<UserProfile>(p, HttpStatus.OK);
	}
}
