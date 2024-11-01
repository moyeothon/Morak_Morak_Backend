package com.morak.morak.chat.controller;

import com.morak.morak.chat.dto.ChatMessage;
import com.morak.morak.chat.service.RedisPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class MessageController {
	private final RedisPublisher redisService;

//	@MessageMapping("/chat/rooms")
//	public void chat(ChatMessage message){
//		redisService.chat(message);
//	}
}
