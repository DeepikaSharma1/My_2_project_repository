package com.niit.collude.backend.dao;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.niit.collude.backend.model.UserProfile;

@Repository("userProfileDao")
@EnableTransactionManagement
@Transactional
public class UserProfileDaoImpl implements UserProfileDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	UserDao userDao;
	
	public void create(UserProfile userProfile) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(userProfile);
	}

	public void update(UserProfile userProfile) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(userProfile);
	}

	public void remove(UserProfile userProfile) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(userProfile);
	}

	public UserProfile getUserProfileByUserId(long userId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Profile where userId=" + userId;
		UserProfile profile = null;
		try {
			profile = (UserProfile) session.createQuery(hql).getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace(System.err);
			System.err.println("Error Getting Profile of UserId: " + userId + "\nMessage: " + e.getMessage());
		}
		return profile;
	}

	public UserProfile getUserProfileById(long userProfileId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Profile where profileId=" + userProfileId;
		UserProfile profile = null;
		try {
			profile = (UserProfile) session.createQuery(hql).getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace(System.err);
			System.err.println("Error Getting Profile of userProfileId: " + userProfileId + "\nMessage: " + e.getMessage());
		}
		return profile;
	}

}
