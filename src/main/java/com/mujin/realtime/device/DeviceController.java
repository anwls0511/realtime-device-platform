package com.mujin.realtime.device;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceStatusService deviceStatusService;

    // 장비 상태 조회
    @GetMapping("/{deviceId}/status")
    public String getDeviceStatus(@PathVariable String deviceId) {
        return deviceStatusService.findByDeviceId(deviceId);
    }

    // 전체 장비 상태 조회
    @GetMapping("/status")
    public List<DeviceStatus> getDeviceStatuses() {
        return deviceStatusService.findAll();
    }
}