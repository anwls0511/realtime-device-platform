package com.mujin.realtime.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mujin.realtime.device.DeviceStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DeviceStatusWebSocketHandler extends TextWebSocketHandler {

    // 현재 연결된 WebSocket 클라이언트 세션 관리
    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();
    private final ObjectMapper objectMapper;

    public DeviceStatusWebSocketHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {

        // 클라이언트 연결 시 세션 저장
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {

        // 클라이언트 연결 종료 시 세션 제거
        sessions.remove(session);
    }

    public void broadcast(DeviceStatus deviceStatus) {
        try {
            // DeviceStatus 객체를 WebSocket 전송용 JSON 문자열로 변환
            String message =
                    objectMapper.writeValueAsString(deviceStatus);

            // 연결된 모든 클라이언트에게 메시지 전송
            for (WebSocketSession session : sessions) {
                if (session.isOpen()) {
                    session.sendMessage(
                            new TextMessage(message)
                    );
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(
                    "WebSocket 메시지 전송 실패",
                    e
            );
        }
    }

}