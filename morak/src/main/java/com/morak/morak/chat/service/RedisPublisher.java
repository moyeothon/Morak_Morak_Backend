package com.morak.morak.chat.service;

import com.morak.morak.chat.dto.ChatMessageSub;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisPublisher {
	private final ChannelTopic channelTopic;
	private final RedisTemplate redisTemplate;

	public void publish(ChatMessageSub message) {
		redisTemplate.convertAndSend(channelTopic.getTopic(), message);
	}
}