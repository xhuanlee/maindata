package com.kd.maindata.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Created by xhuanlee on 2017/3/19.
 */
@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        System.out.println("register websocket");
        webSocketHandlerRegistry.addHandler(systemWebSocketHandler(), "/websocket/server").addInterceptors(new WebSocketHandshakeInterceptor());
        webSocketHandlerRegistry.addHandler(systemWebSocketHandler(), "/websocket/server/sockjs").addInterceptors(new WebSocketHandshakeInterceptor()).withSockJS();
    }


    public WebSocketHandler systemWebSocketHandler() {
        return new SystemWebSocketHandler();
    }
}
