package com.niit.collude.backend.model;

import java.util.Date;

public class GroupMessage extends Message {
	private Date time;

	public GroupMessage() {
		super();
	}

	public GroupMessage(Message message, Date time) {
		super(message.getId(), message.getMessage(), message.getUsername());
		this.time = time;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
}
