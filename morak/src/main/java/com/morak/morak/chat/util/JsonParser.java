package com.morak.morak.chat.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morak.morak.chat.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JsonParser {
	private final ObjectMapper objectMapper;

	public String getType(String json) {
		try {
			JsonNode jsonNode = objectMapper.readTree(json);
			return jsonNode.get("type").asText();
		} catch (IOException e) {
			return null;
		}
	}

	public ChatMessage toChatMessage(String chatMessage) {
		try {
			return objectMapper.readValue(chatMessage, ChatMessage.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
