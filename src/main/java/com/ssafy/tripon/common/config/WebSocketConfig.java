package com.ssafy.tripon.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${spring.rabbitmq.host}")
    private String brokerHost;

    @Value("${spring.rabbitmq.port}")
    private int brokerPort;

    @Value("${spring.rabbitmq.username}")
    private String brokerUsername;

    @Value("${spring.rabbitmq.password}")
    private String brokerPassword;

    @Value("${spring.rabbitmq.virtual-host:/}")
    private String virtualHost;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableStompBrokerRelay("/topic", "/queue", "/exchange")
                .setRelayHost(brokerHost)
                .setRelayPort(brokerPort)
                .setClientLogin(brokerUsername)
                .setClientPasscode(brokerPassword)
                .setSystemLogin(brokerUsername)       // 시스템 계정 (관리용)
                .setSystemPasscode(brokerPassword)
                .setVirtualHost(virtualHost)        // RabbitMQ Virtual Host
                .setSystemHeartbeatSendInterval(10_000)   // 10초마다 heartbeat
                .setSystemHeartbeatReceiveInterval(10_000);

        // 클라이언트가 서버로 보낼 prefix
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-plan")
                .setAllowedOriginPatterns("http://localhost:5173")
                .withSockJS();
    }
}

