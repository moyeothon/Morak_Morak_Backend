package com.morak.morak.chat.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morak.morak.chat.dto.ChatRoomResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRoomRedisRepository {

	private static final String CHAT_ROOM_KEY = "_CHAT_ROOM_RESPONSE_LIST";
	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper objectMapper;
	private HashOperations<String, String, ChatRoomResponse> opsHashChatRoom;

	// RedisTemplate 초기화 후 HashOperations 설정
	@PostConstruct
	private void init() {
		opsHashChatRoom = redisTemplate.opsForHash();
	}

	// 채팅방 리스트 저장 시 사용될 키 생성
	private String getChatRoomKey(Long userId) {
		return userId + CHAT_ROOM_KEY;
	}

	// 사용자별 채팅방 목록 설정
	public void setChatRoomList(Long userId, List<ChatRoomResponse> list) {
		String key = getChatRoomKey(userId);
		list.forEach(room -> opsHashChatRoom.put(key, room.getChatRoomNumber(), room));
	}

	// 사용자별 채팅방 목록 초기화
	public void initChatRoomList(Long userId, List<ChatRoomResponse> list) {
		String key = getChatRoomKey(userId);
		if (redisTemplate.hasKey(key)) {
			redisTemplate.delete(key);
		}
		setChatRoomList(userId, list); // 초기화 후 목록 설정
	}

	// 사용자별 채팅방 목록 조회
	public List<ChatRoomResponse> getChatRoomList(Long userId) {
		String key = getChatRoomKey(userId);
		return objectMapper.convertValue(opsHashChatRoom.values(key), new TypeReference<List<ChatRoomResponse>>() {});
	}
}
