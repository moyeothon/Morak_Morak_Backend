package com.morak.morak.chat.config;

import com.morak.morak.chat.service.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@RequiredArgsConstructor
@Configuration
public class RedisConfig {

	// yml 파일 redis 설정 불러오기
	private final RedisProperties redisProperties;
	/**
	 * 단일 Topic 사용을 위한 Bean 설정
	 */
	@Bean
	public ChannelTopic channelTopic() {
		return new ChannelTopic("chatroom");
	}

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
	}

	/**
	 * redis 에 발행(publish)된 메시지 처리를 위한 리스너 설정
	 */
	@Bean
	public RedisMessageListenerContainer redisMessageListener (
			MessageListenerAdapter listenerAdapterChatMessage,
			ChannelTopic channelTopic
	) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(redisConnectionFactory());
		container.addMessageListener(listenerAdapterChatMessage, channelTopic);
		return container;
	}

	/** 실제 메시지를 처리하는 subscriber 설정 추가*/
	@Bean
	public MessageListenerAdapter listenerAdapterChatMessage(RedisSubscriber subscriber) {
		return new MessageListenerAdapter(subscriber, "sendMessage");
	}

	@Bean
	public RedisMessageListenerContainer redisMessageListenerRoomList (
			MessageListenerAdapter listenerAdapterChatRoomList,
			ChannelTopic channelTopic
	) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(redisConnectionFactory());
		container.addMessageListener(listenerAdapterChatRoomList, channelTopic);
		return container;
	}

	/** 실제 메시지 방을 처리하는 subscriber 설정 추가*/
	@Bean
	public MessageListenerAdapter listenerAdapterChatRoomList(RedisSubscriber subscriber) {
		return new MessageListenerAdapter(subscriber, "sendRoomList");
	}


}