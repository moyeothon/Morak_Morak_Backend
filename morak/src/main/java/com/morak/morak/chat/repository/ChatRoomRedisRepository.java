package com.morak.morak.chat.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morak.morak.chat.dto.ChatRoomResponse;
import jakarta.annotation.Resource;
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

	@Resource(name = "redisTemplate")
	private HashOperations<String, String, ChatRoomResponse> opsHashChatRoom;

	private String getChatRoomKey(Long userId) {
		// redis에 채팅방 리스트 저장 시 사용될 key
		return userId + CHAT_ROOM_KEY;
	}

//	public void setChatRoomList(String userId, String roomId, List<ChatRoomResponse> list) {
//		opsHashChatRoom = redisTemplate.opsForValue();
//		opsHashChatRoom.set(get, list);
//	}
//	public void initChatRoomList(Long userId, List<ChatRoomResponse> list) {
//		// 채팅방 리스트 초기화
//		if (redisTemplate.hasKey(getChatRoomKey(userId))) {
//			redisTemplate.delete(getChatRoomKey(userId));
//		}
//
//		opsHashChatRoom = redisTemplate.opsForHash();
//		for (ChatRoomResponse chatRoomListGetRes : list) {
//			setChatRoom(userId, chatRoomListGetRes.getChatRoomNumber(), chatRoomListGetRes);
//		}
//	}

	public List<ChatRoomResponse> getChatRoomList(Long userId) {
		// 채팅방 리스트 조회
		return objectMapper.convertValue(opsHashChatRoom.values(getChatRoomKey(userId)), new TypeReference<>() {});
	}

}