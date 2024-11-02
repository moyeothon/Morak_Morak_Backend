package com.morak.morak.chat.controller;

import com.morak.morak.chat.dto.ChatMessage;
import com.morak.morak.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatRoomRepository chatRoomRepository;

    @MessageMapping("chat/message")
    public void message(ChatMessage message) {

        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}