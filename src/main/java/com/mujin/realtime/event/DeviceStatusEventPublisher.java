package com.mujin.realtime.event;

import com.mujin.realtime.device.DeviceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeviceStatusEventPublisher {

    private final ApplicationEventPublisher publisher;

    public void publish(DeviceStatus deviceStatus) {

        publisher.publishEvent(new DeviceStatusEvent(deviceStatus));
    }
}