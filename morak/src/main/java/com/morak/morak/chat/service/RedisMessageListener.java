package com.morak.morak.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class RedisMessageListener {
	private static final Map<Long, ChannelTopic> TOPICS = new HashMap<>();
	private final RedisMessageListenerContainer redisMessageListenerContainer;
	private final RedisSubscriber redisSubscriber;

	public void enterChatRoom(String debateRoomId){
		Long i = Long.parseLong(debateRoomId);
		ChannelTopic topic = getTopic(debateRoomId);

		if (topic == null){
			topic = new ChannelTopic(String.valueOf(debateRoomId));
			redisMessageListenerContainer.addMessageListener(redisSubscriber, topic);
			TOPICS.put(i, topic);
		}
	}

	public void deleteChatRoom(String debateRoomId) {
		Long i = Long.parseLong(debateRoomId);

		redisMessageListenerContainer.removeMessageListener(redisSubscriber, getTopic(debateRoomId));
		TOPICS.remove(i);
	}

	public ChannelTopic getTopic(String debateRoomId){
		Long i = Long.parseLong(debateRoomId);

		return TOPICS.get(i);
	}
}
