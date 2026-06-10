package com.mujin.realtime.device;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    // 전체 장비 상태 조회 또는 상태값 기준 조회
    @GetMapping("/status")
    public List<DeviceStatus> getDeviceStatuses(
            @RequestParam(required = false) String status
    ) {
        if (status == null || status.isBlank()) {
            return deviceStatusService.findAll();
        }

        return deviceStatusService.findByStatus(status);
    }
}