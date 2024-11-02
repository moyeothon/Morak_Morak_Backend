package com.morak.morak.chat.service;

import com.morak.morak.chat.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class ChatService {

    private final MongoService chatMongoService;
    private final RedisPublisher redisPublisher;

    public ChatMessage sendMessage(ChatMessage message) {
        // MongoDB에 메시지 저장
        ChatMessage savedMessage = chatMongoService.saveMessage(message);

        // RedisPublisher를 통해 Redis로 메시지 발행
        redisPublisher.publish(savedMessage);

        log.info("Message sent and saved to MongoDB: {}", savedMessage);
        return savedMessage;
    }
}
