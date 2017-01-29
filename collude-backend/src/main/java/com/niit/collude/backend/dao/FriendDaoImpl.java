package com.niit.collude.backend.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.niit.collude.backend.model.Friend;

@Repository("friendDao")
@EnableTransactionManagement
@Transactional
public class FriendDaoImpl implements FriendDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	UserDao userDao;
	
	public void create(Friend friend) {
		Session session = sessionFactory.getCurrentSession();
		
		Friend friend2 = new Friend();
		friend2.setUser(friend.getFriend());
		friend2.setFriend(friend.getUser());
		friend2.setOnline(false);
		friend2.setStatus("NEW_REQUEST");
		
		session.saveOrUpdate(friend);
		session.saveOrUpdate(friend2);
	}

	public void update(Friend friend) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(friend);
	}

	public void remove(Friend friend) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(friend);
	}

	public Friend getFriend(long userId, long friendId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Friend where userId=" + userId + " and friendId=" + friendId;
		Friend friend = null;
		try {
			friend = (Friend) session.createQuery(hql).getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		return friend;
	}

	@SuppressWarnings("unchecked")
	public List<Friend> getFriendsOfUser(long userId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Friend where friendId=" + userId;
		List<Friend> friends = session.createQuery(hql).getResultList();
		return friends;
	}

	@SuppressWarnings("unchecked")
	public List<Friend> getFriendRequestOfUser(long userId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Friend where friendId=" + userId/* + " and status='NEW_REQUEST'"*/;
		List<Friend> friends = session.createQuery(hql).getResultList();
		return friends;
	}

	public void setOnline(long userId) {
		// TODO Auto-generated method stub
		
	}

	public void setOffline(long userId) {
		// TODO Auto-generated method stub
		
	}

}
