package com.niit.collude.backend.model;

public class Message {

	private long id;
	private String message;
	private String username;

	public Message() {
	}

	public Message(long id, String message, String username) {
		this.id = id;
		this.message = message;
		this.username = username;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
