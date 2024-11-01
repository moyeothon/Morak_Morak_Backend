package com.morak.morak.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomRequest {
    private String roomName;     // 생성할 채팅방 이름
    private String creatorId;    // 채팅방을 생성하는 사용자의 ID
}
