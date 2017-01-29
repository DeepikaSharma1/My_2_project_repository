package com.niit.collude.backend.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.niit.collude.backend.model.Blog;

@Repository("blogDao")
@EnableTransactionManagement
@Transactional
public class BlogDaoImpl implements BlogDao {

	@Autowired
	SessionFactory sessionFactory;
	
	public void create(Blog blog) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(blog);
	}

	public void update(Blog blog) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(blog);
	}
	
	public void remove(Blog blog) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(blog);
	}

	public Blog getBlogById(long blogId) {
		String hql = "from Blog where blogId=" + blogId;
		Session session = sessionFactory.getCurrentSession();
		Blog blog = null;
		try {
			blog = (Blog) session.createQuery(hql).getSingleResult();
		} catch (NoResultException e) {
			System.err.println("Error Getting Blog with Id: " + blogId);
			System.err.println("Error Message: " + e.getMessage());
		}
		return blog;
	}

	@SuppressWarnings("unchecked")
	public List<Blog> getAllBlogs() {
		Session session = sessionFactory.getCurrentSession();
		List<Blog> blogs = session.createQuery("from Blog").getResultList();
		return blogs;
	}

	@SuppressWarnings("unchecked")
	public List<Blog> getApprovedBlogs() {
		Session session = sessionFactory.getCurrentSession();
		List<Blog> blogs = session.createQuery("from Blog where status='APPROVED'").getResultList();
		return blogs;
	}

}
