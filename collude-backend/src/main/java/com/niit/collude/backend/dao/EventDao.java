package com.niit.collude.backend.dao;

import java.util.List;

import com.niit.collude.backend.model.Event;

public interface EventDao {

	void create(Event event);
	
	void update(Event event);
	
	void remove(Event event);
	
	Event getEventById(long eventId);
	
	List<Event> getAllEvents();
	
}
