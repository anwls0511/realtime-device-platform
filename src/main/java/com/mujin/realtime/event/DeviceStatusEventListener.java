package com.mujin.realtime.event;

import com.mujin.realtime.device.DeviceStatus;
import com.mujin.realtime.device.DeviceStatusService;
import com.mujin.realtime.websocket.DeviceStatusWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeviceStatusEventListener {

    private final DeviceStatusService deviceStatusService;
    private final DeviceStatusWebSocketHandler webSocketHandler;

    @EventListener
    public void handleDeviceStatusEvent(DeviceStatusEvent event) {

        DeviceStatus status = event.getDeviceStatus();

        // Redis 저장
        deviceStatusService.save(status);

        // WebSocket 전파
        webSocketHandler.broadcast(status);
    }
}