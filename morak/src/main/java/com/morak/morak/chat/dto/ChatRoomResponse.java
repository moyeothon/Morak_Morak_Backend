package com.morak.morak.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomResponse {
    private String chatRoomNumber; // 채팅방 고유 번호
    private String roomName;       // 채팅방 이름
    private int participantCount;  // 채팅방 참여자 수
}
