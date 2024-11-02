package com.morak.morak.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.morak.morak.chat.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class RedisSubscriber implements MessageListener {

	private final SimpMessagingTemplate messagingTemplate;
	private final ObjectMapper objectMapper;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		try {
			ChatMessage chatMessage = objectMapper.readValue(message.getBody(), ChatMessage.class);
			String destination = "/sub/chatroom/" + chatMessage.roomId();
			messagingTemplate.convertAndSend(destination, chatMessage);

			log.info("Received and forwarded message from Redis on topic {}: {}", new String(message.getChannel()), chatMessage);
		} catch (Exception e) {
			log.error("Error processing message from Redis: {}", e.getMessage(), e);
		}
	}
}
