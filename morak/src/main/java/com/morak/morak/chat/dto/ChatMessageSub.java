package com.morak.morak.chat.dto;

import java.util.List;

public record ChatMessageSub(
		Long userId,
		Long partnerId,
		ChatMessage chatMessage,
		List<ChatRoomResponse> list,
		List<ChatRoomResponse> partnerList
) {
}
