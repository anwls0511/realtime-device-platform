package com.mujin.realtime.device;

import lombok.Builder;
import lombok.Data;

// 장비 상태 정보
@Data
@Builder
public class DeviceStatus {

    // 장비 고유 ID
    private String deviceId;

    // 현재 온도
    private double temperature;

    // 현재 습도
    private double humidity;

    // 데이터 수집 시간
    private long timestamp;
}