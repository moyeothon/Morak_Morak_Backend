package com.morak.morak.chat.dto;

import java.time.LocalDateTime;

public record ChatMessage(
		String roomId,
		Long userId,
		String username,
		String content,
		LocalDateTime timestamp
) {
	public ChatMessage {
		timestamp = (timestamp == null) ? LocalDateTime.now() : timestamp;
	}
}
