package com.morak.morak.chat.repository;

import com.morak.morak.chat.dto.ChatMessage;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findByRoomId(String roomId);
}
