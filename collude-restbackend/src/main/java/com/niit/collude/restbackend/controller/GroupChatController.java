package com.niit.collude.restbackend.controller;

import java.util.Date;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.niit.collude.backend.model.GroupMessage;
import com.niit.collude.backend.model.Message;

@Controller
public class GroupChatController {
	@MessageMapping("/groupChat") 	// Defines where messages will be sent
	@SendTo("/topic/message") 		// Defines where messages will be routed to
	public GroupMessage sendMessage(Message message) {
		return new GroupMessage(message, new Date());
	}
}
