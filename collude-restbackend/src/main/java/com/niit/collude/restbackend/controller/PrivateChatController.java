package com.niit.collude.restbackend.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.niit.collude.backend.model.PrivateMessage;

@Controller
public class PrivateChatController {
	@Autowired
	private SimpMessagingTemplate smt;
	
	@MessageMapping("/privateChat")
	private void sendMessage(PrivateMessage privateMessage) {
		privateMessage.setTime(new Date());
		smt.convertAndSend("/queue/message/" + privateMessage.getUsername(), privateMessage);
		smt.convertAndSend("/queue/message/" + privateMessage.getFriendName(), privateMessage);
	}
}
