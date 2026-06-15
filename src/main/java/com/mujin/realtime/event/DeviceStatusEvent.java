package com.mujin.realtime.event;

import com.mujin.realtime.device.DeviceStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DeviceStatusEvent {

    private final DeviceStatus deviceStatus;
}