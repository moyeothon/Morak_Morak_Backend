// RedisSubscriber.java
package com.morak.morak.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.morak.morak.chat.dto.ChatMessage;
import com.morak.morak.chat.dto.ChatMessageSub;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber {

	private final ObjectMapper objectMapper;
	private final SimpMessageSendingOperations messagingTemplate;

	// Redis에서 수신한 메시지를 WebSocket을 통해 채팅방에 전달
	public void sendMessage(String publishMessage) {
		try {
			ChatMessage chatMessage = objectMapper.readValue(publishMessage, ChatMessageSub.class).chatMessage();
			log.info("Redis Subscriber - Received chat message: {}", chatMessage.message());

			messagingTemplate.convertAndSend("/sub/chat/room/" + chatMessage.roomId(), chatMessage);
		} catch (Exception e) {
			log.error("Exception while processing Redis message: {}", e.getMessage());
		}
	}
}
