package com.niit.collude.backend.model;

import java.util.Date;

public class PrivateMessage extends Message {
	private String friendName;
	private Date time;

	public PrivateMessage() {
	}

	public PrivateMessage(Message message, String friendName, Date time) {
		super(message.getId(), message.getMessage(), message.getUsername());
		this.friendName = friendName;
		this.time = time;
	}

	public String getFriendName() {
		return friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
