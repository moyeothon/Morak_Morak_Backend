package com.morak.morak.chat.service;

import com.morak.morak.chat.dto.ChatMessage;
import com.morak.morak.chat.repository.ChatRoomRedisRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class ChatService {

    private final MongoService chatMongoService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic topic;

    public ChatMessage sendMessage(ChatMessage message) {
        ChatMessage savedMessage = chatMongoService.saveMessage(message);
        redisTemplate.convertAndSend(topic.getTopic(), message);

        return savedMessage;
    }

    // 채팅방 ID로 메시지 검색
    public List<ChatMessage> getMessagesByRoomId(String roomId) {
        return chatMongoService.findMessagesByRoomId(roomId);
    }
}