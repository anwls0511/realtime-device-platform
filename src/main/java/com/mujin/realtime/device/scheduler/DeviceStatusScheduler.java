package com.mujin.realtime.device.scheduler;

import com.mujin.realtime.device.DeviceStatus;
import com.mujin.realtime.device.DeviceStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DeviceStatusScheduler {

    // 30이상 시 OFFLINE
    private static final long OFFLINE_THRESHOLD_MILLIS = 30_000L;

    private final DeviceStatusService deviceStatusService;

    // 10초마다 장비의 마지막 수신 시간을 확인
    @Scheduled(fixedRate = 10_000)
    public void checkOfflineDevices() {
        List<DeviceStatus> deviceStatuses = deviceStatusService.findAll();

        long now = System.currentTimeMillis();

        for (DeviceStatus deviceStatus : deviceStatuses) {
            long lastReceivedTime = deviceStatus.getTimestamp();

            boolean isOffline = now - lastReceivedTime > OFFLINE_THRESHOLD_MILLIS;

            if (isOffline && !"OFFLINE".equals(deviceStatus.getStatus())) {
                deviceStatus.setStatus("OFFLINE");

                deviceStatusService.save(deviceStatus);

                System.out.println("장비 OFFLINE 처리 : " + deviceStatus.getDeviceId());
            }
        }
    }
}