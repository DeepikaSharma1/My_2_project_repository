package com.niit.collude.backend.dao;

import java.util.List;

import com.niit.collude.backend.model.Job;
import com.niit.collude.backend.model.JobsApplied;

public interface JobDao {

	void create(Job job);
	
	void update(Job job);
	
	void remove(Job job);
	
	Job getJobById(long jobId);
	
	List<Job> getAllJobs();
	
	List<JobsApplied> getJobsApplied(long userId);
	
	void saveJobApplied(JobsApplied jobsApplied);
	
}
