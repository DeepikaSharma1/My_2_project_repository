package com.niit.collude.backend.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "C_JOBS_APPLIED", schema = "collude")
public class JobsApplied {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long jobsAppliedId;
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	@ManyToOne
	@JoinColumn(name = "jobId")
	private Job job;
	private Date dateApplied;
	private String status;

	public long getJobsAppliedId() {
		return jobsAppliedId;
	}

	public void setJobsAppliedId(long jobsAppliedId) {
		this.jobsAppliedId = jobsAppliedId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public Date getDateApplied() {
		return dateApplied;
	}

	public void setDateApplied(Date dateApplied) {
		this.dateApplied = dateApplied;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
