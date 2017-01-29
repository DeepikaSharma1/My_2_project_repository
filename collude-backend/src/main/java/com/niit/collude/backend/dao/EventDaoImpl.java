package com.niit.collude.backend.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.niit.collude.backend.model.Event;

@Repository("eventDao")
@EnableTransactionManagement
@Transactional
public class EventDaoImpl implements EventDao {

	@Autowired
	SessionFactory sessionFactory;
	
	public void create(Event event) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(event);
	}

	public void update(Event event) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(event);
	}

	public void remove(Event event) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(event);
	}

	public Event getEventById(long eventId) {
		String hql = "from Event where eventId=" + eventId;
		Session session = sessionFactory.getCurrentSession();
		Event event = null;
		try {
			event = (Event) session.createQuery(hql).getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		return event;
	}

	@SuppressWarnings("unchecked")
	public List<Event> getAllEvents() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Event";
		List<Event> events = session.createQuery(hql).getResultList();
		return events;
	}

}
