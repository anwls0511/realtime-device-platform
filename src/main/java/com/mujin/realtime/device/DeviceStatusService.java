package com.mujin.realtime.device;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DeviceStatusService {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    // 최신 장비 상태 저장
    public void save(DeviceStatus status) {
        try {
            String key = "device:status:" + status.getDeviceId();
            String value = objectMapper.writeValueAsString(status);

            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            throw new RuntimeException("장비 상태 저장 실패", e);
        }
    }

    // 장비 상태 조회
    public String findByDeviceId(String deviceId) {
        return redisTemplate.opsForValue().get("device:status:" + deviceId);
    }

    // 전체 장비 상태 조회
    public List<String> findAll() {
        Set<String> keys = redisTemplate.keys("device:status:*");

        if (keys == null || keys.isEmpty()) {
            return List.of();
        }

        List<String> deviceStatuses = new ArrayList<>();

        for (String key : keys) {
            String value = redisTemplate.opsForValue().get(key);

            if (value != null) {
                deviceStatuses.add(value);
            }
        }

        return deviceStatuses;
    }
}