package com.morak.morak.chat.controller;

import com.morak.morak.chat.dto.ChatMessage;
import com.morak.morak.chat.service.ChatService;
import com.morak.morak.chat.service.MongoService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final MongoService chatMongoService;
    private final ChatService chatService;

    /**
     * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
     */
    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        ChatMessage chatMessage = chatMongoService.saveMessage(message);
        chatService.sendMessage(chatMessage); //RedisPublisher 호출
    }
}
