package com.niit.collude.restbackend.controller;

import java.util.Date;
import java.util.List;

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

import com.niit.collude.backend.dao.JobDao;
import com.niit.collude.backend.dao.UserDao;
import com.niit.collude.backend.model.Job;
import com.niit.collude.backend.model.JobsApplied;

@RestController
public class JobRestController {
	
	@Autowired
	JobDao jobDao;
	
	@Autowired
	UserDao userDao;
	
	@GetMapping(value = "/job/")
	public ResponseEntity<List<Job>> getAllJobs() {
		List<Job> jobs = jobDao.getAllJobs();
		if (jobs.isEmpty()) {
			return new ResponseEntity<List<Job>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
	}
	
	@GetMapping(value = "/job/{jobId}")
	public ResponseEntity<Job> getJobById(@PathVariable("jobId") long jobId) {
		Job job = jobDao.getJobById(jobId);
		if (job == null) {
			return new ResponseEntity<Job>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}
	
	@PostMapping(value = "/job/")
	public ResponseEntity<Job> addJob(@RequestBody Job job) {
		job.setPostDate(new Date());
		jobDao.create(job);
		return new ResponseEntity<Job>(job, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/job/{jobId}")
	public ResponseEntity<Job> updateJob(@PathVariable("jobId") long jobId, @RequestBody Job job) {
		Job j = jobDao.getJobById(jobId);
		if (j == null) {
			return new ResponseEntity<Job>(HttpStatus.NOT_FOUND);
		}
		
		j.setTitle(job.getTitle());
		j.setDescription(job.getDescription());
		j.setPostDate(job.getPostDate());
		j.setLocation(job.getLocation());
		j.setQualification(job.getQualification());
		j.setStatus(job.getStatus());
		
		jobDao.update(j);
		return new ResponseEntity<Job>(j, HttpStatus.OK);
	}
	
	@PostMapping(value = "/job/apply/{jobId}")
	public ResponseEntity<Job> applyForJob(@PathVariable("jobId") long jobId, HttpSession session) {
		long loggedInUserId = (Long) session.getAttribute("loggedInUserId");
		JobsApplied applyJob = new JobsApplied();
		applyJob.setJob(jobDao.getJobById(jobId));
		applyJob.setStatus("APPLIED");
		applyJob.setDateApplied(new java.util.Date());
		applyJob.setUser(userDao.getUserById(loggedInUserId));
		
		jobDao.saveJobApplied(applyJob);
		
		return new ResponseEntity<Job>(HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/job/applied/")
	public ResponseEntity<List<JobsApplied>> getJobsApplied(HttpSession session) {
		long loggedInUserId = (Long) session.getAttribute("loggedInUserId");
		List<JobsApplied> jobsApplied = jobDao.getJobsApplied(loggedInUserId);
		if (jobsApplied.isEmpty()) {
			return new ResponseEntity<List<JobsApplied>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<JobsApplied>>(jobsApplied, HttpStatus.OK);
	}
}
