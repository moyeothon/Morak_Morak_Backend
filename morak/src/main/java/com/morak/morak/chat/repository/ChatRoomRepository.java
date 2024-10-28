package com.morak.morak.chat.repository;

import com.morak.morak.chat.dto.ChatRoom;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ChatRoomRepository {

	private Map<String, ChatRoom> chatRoomMap;

	@PostConstruct
	private void init() {
		chatRoomMap = new LinkedHashMap<>();
	}

	public List<ChatRoom> findAllRoom() {
		List<ChatRoom> chatRooms = new ArrayList<>(chatRoomMap.values());
		Collections.reverse(chatRooms);
		return chatRooms;
	}

	public ChatRoom findRoomById(String id) {
		return chatRoomMap.get(id);
	}

	public ChatRoom createChatRoom(String name) {
		ChatRoom chatRoom = ChatRoom.create(name);
		chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
		return chatRoom;
	}
}