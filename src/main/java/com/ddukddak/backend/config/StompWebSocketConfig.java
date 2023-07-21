package com.ddukddak.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

//@Component
@Configuration
@EnableWebSocketMessageBroker
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /*
    * /ws -> WebSock이 웹소켓 핸드셰이크 커넥션 생성 경로
    * /test 경로로 시작하는 STOMP 메세지의 "destination" 헤더는 @Controller의 @MessageMapping 메세지로 라우팅.
    * 내장된 메세지 브로커 -> 1.Client에게 Subscriptions, Broadcasting 기능 제공.
    *                   2./topic, /queue로 시작하는 "destination" 헤더를 가진 메세지를 브로커로 라우팅한다.
    * */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp/chat")
                .setAllowedOrigins("/*")
                .withSockJS();
        // setAllow -> http://localhost:8080 이 도메인만 허락할지, 다 열어줄지 고민...
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub"); // broker : sub하는 client에게 메세지 전달.
        config.setApplicationDestinationPrefixes("/pub"); // client -> SEND
    }


}
