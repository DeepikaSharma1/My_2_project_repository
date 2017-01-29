package com.niit.collude.backend.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.niit.collude.backend.model.User;

@Repository("userDao")
@EnableTransactionManagement
@Transactional
public class UserDaoImpl implements UserDao {
	
	@Autowired
	SessionFactory sessionFactory;
	
	public void create(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(user);
	}

	public void update(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(user);
	}

	public User getUserById(long userId) {
		String hql = "from User where userId=" + userId;
		Session session = sessionFactory.getCurrentSession();
		User user = null;
		try {
			user = (User) session.createQuery(hql).getSingleResult();
		} catch (NoResultException e) {
			System.err.println("Error Getting User with Id: " + userId);
			System.err.println("Error Message: " + e.getMessage());
		}
		return user;
	}

	public User getUserByUsername(String username) {
		String hql = "from User where username='" + username + "'";
		Session session = sessionFactory.getCurrentSession();
		User user = null;
		try {
			user = (User) session.createQuery(hql).getSingleResult();
		} catch (NoResultException e) {
			System.err.println("Error Getting User with username: " + username);
			System.err.println("Error Message: " + e.getMessage());
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		String hql = "from User";
		Session session = sessionFactory.getCurrentSession();
		List<User> users = null;
		try {
			users = session.createQuery(hql).getResultList();
		} catch (NoResultException e) {
			System.err.println("Error Getting Registered Users!");
			System.err.println("Error Message: " + e.getMessage());
		}
		return users;
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUsersExceptLoggedIn(long loggedInUserId) {
		String hql = "from User where userId<>" + loggedInUserId;
		Session session = sessionFactory.getCurrentSession();
		List<User> users = null;
		try {
			users = session.createQuery(hql).getResultList();
		} catch (NoResultException e) {
			System.err.println("Error Getting Registered Users Except Logged In!");
			System.err.println("Error Message: " + e.getMessage());
		}
		return users;
	}

	public boolean isExistingUser(User aUser) {
		User user = getUserByUsername(aUser.getUsername());
		if (user != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean authenticate(String username, String password) {
		String hql = "from User where username='" + username + "' and password='" + password + "'";
		Session session = sessionFactory.getCurrentSession();
		User user = (User) session.createQuery(hql).getSingleResult();
		
		if (user != null) {
			return true;
		} else {
			return false;
		}
	}

}
