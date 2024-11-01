package com.morak.morak.chat.service;

import com.morak.morak.chat.dto.ChatMessage;
import com.morak.morak.chat.repository.ChatMessageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MongoService {
    private final ChatMessageRepository chatMessageRepository;

    public ChatMessage saveMessage(ChatMessage message) {
        return chatMessageRepository.save(message);
    }

    public List<ChatMessage> findMessagesByRoomId(String roomId) {
        return chatMessageRepository.findByRoomId(roomId);
    }
}
