package com.mujin.realtime.netty.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mujin.realtime.device.DeviceStatus;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DeviceStatusDecoder extends MessageToMessageDecoder<String> {

    private final ObjectMapper objectMapper;

    @Override
    protected void decode(
            ChannelHandlerContext ctx,
            String message,
            List<Object> out
    ) throws Exception {

        // JSON 문자열을 장비 상태 객체로 변환
        DeviceStatus deviceStatus =
                objectMapper.readValue(
                        message,
                        DeviceStatus.class
                );

        // 다음 Handler로 DeviceStatus 객체 전달
        out.add(deviceStatus);
    }
}