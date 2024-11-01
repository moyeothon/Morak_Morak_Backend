package com.morak.morak.chat.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.morak.morak.chat.dto.ChatMessage;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final JsonParser jsonParser = new JsonParser(objectMapper);

	@Test
	void testToChatDto() {
		String json = "{\"type\":\"TALK\", \"roomId\":1, \"sender\":\"user1\", \"message\":\"Hello!\"}";

		ChatMessage chatDto = jsonParser.toChatMessage(json);

		assertEquals("TALK", chatDto.type().name());
		assertEquals("1", chatDto.roomId());
		assertEquals("user1", chatDto.sender());
		assertEquals("Hello!", chatDto.message());
	}
}
