package com.niit.collude.backend.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.niit.collude.backend.model.Job;
import com.niit.collude.backend.model.JobsApplied;

@Repository("jobDao")
@EnableTransactionManagement
@Transactional
public class JobDaoImpl implements JobDao {

	@Autowired
	SessionFactory sessionFactory;
	
	public void create(Job job) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(job);
	}

	public void update(Job job) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(job);
	}

	public void remove(Job job) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(job);
	}

	public Job getJobById(long jobId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Job where jobId=" + jobId;
		Job job = null;
		try {
			job = (Job) session.createQuery(hql).getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		return job;
	}

	@SuppressWarnings("unchecked")
	public List<Job> getAllJobs() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Job";
		List<Job> jobs = session.createQuery(hql).getResultList();
		return jobs;
	}

	@SuppressWarnings("unchecked")
	public List<JobsApplied> getJobsApplied(long userId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from JobsApplied where userId=" + userId;
		List<JobsApplied> jobsApplied = session.createQuery(hql).getResultList();
		return jobsApplied;
	}

	public void saveJobApplied(JobsApplied jobsApplied) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(jobsApplied);
	}

}
