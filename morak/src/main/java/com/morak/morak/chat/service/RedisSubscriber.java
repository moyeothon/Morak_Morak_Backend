package com.morak.morak.chat.service;

import com.morak.morak.chat.dto.ChatMessage;
import com.morak.morak.chat.util.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import static com.morak.morak.chat.dto.MessageType.TALK;

@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {
	private final SimpMessagingTemplate messagingTemplate;
	private final RedisTemplate<String, Object> redisTemplate;
	private final JsonParser jsonParser;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		System.out.println("Received message: " + message); // 로그 추가
		String type = jsonParser.getType(redisTemplate.getStringSerializer().deserialize(message.getBody()));

		if (type.equals(TALK.name())) {
			ChatMessage chatMessage = jsonParser.toChatMessage(redisTemplate.getStringSerializer().deserialize(message.getBody()));
			System.out.println("JSON Message: " + chatMessage); // JSON 메시지 로그
			messagingTemplate.convertAndSend("/sub/chat/rooms/" +  chatMessage.roomId(), chatMessage);
		}
	}
}
