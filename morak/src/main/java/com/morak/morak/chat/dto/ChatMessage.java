package com.morak.morak.chat.dto;

public record ChatMessage (
		MessageType type,
		String roomId,
		String sender,
		String message,
		String time,
		Long userCount
) {
}