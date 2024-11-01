package com.morak.morak.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class RedisMessageListener {

	private final RedisMessageListenerContainer redisMessageListenerContainer;
	private final RedisSubscriber redisSubscriber;

	// 채팅방별 토픽을 관리하는 맵
	private static final Map<String, ChannelTopic> TOPICS = new ConcurrentHashMap<>();

	// 특정 채팅방에 대한 구독 시작
	public void subscribeToRoom(String roomId) {
		// 채팅방 ID별로 토픽을 생성하고, 이미 존재하면 해당 토픽 사용
		ChannelTopic topic = TOPICS.computeIfAbsent(roomId, id -> {
			ChannelTopic newTopic = new ChannelTopic(id);
			redisMessageListenerContainer.addMessageListener((MessageListener) redisSubscriber, newTopic);
			return newTopic;
		});
	}

	// 특정 채팅방에 대한 구독 해제
	public void unsubscribeFromRoom(String roomId) {
		ChannelTopic topic = TOPICS.remove(roomId);
		if (topic != null) {
			redisMessageListenerContainer.removeMessageListener((MessageListener) redisSubscriber, topic);
		}
	}
}
