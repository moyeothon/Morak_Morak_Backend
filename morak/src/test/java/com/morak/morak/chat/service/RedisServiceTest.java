package com.morak.morak.chat.service;

import com.morak.morak.chat.dto.ChatMessage;
import com.morak.morak.chat.dto.MessageType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;

import static org.mockito.Mockito.*;

class RedisServiceTest {
	@Mock
	private RedisTemplate<String, Object> redisTemplate;

	private RedisPublisher redisService;

//	@BeforeEach
//	void setUp() {
//		MockitoAnnotations.openMocks(this);
//		redisService = new RedisPublisher(redisTemplate);
//	}
//
//	@Test
//	void testChat() {
//		ChatMessage chatMessage = new ChatMessage(MessageType.TALK, "1", "user1", "Hello!");
//
//		redisService.chat(chatMessage);
//
//		verify(redisTemplate).convertAndSend("chat:1", chatMessage); // Redis에 발행하는 메시지 검증
//	}
}
