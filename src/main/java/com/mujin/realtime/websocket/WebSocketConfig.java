package com.mujin.realtime.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final DeviceStatusWebSocketHandler deviceStatusWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(
            WebSocketHandlerRegistry registry
    ) {

        // 장비 상태 실시간 수신 WebSocket 엔드포인트
        registry.addHandler(
                        deviceStatusWebSocketHandler,
                        "/ws/devices/status"
                )
                .setAllowedOrigins("*");
    }
}