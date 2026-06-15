package com.mujin.realtime.netty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mujin.realtime.device.DeviceStatus;
import com.mujin.realtime.device.DeviceStatusService;
import com.mujin.realtime.event.DeviceStatusEventPublisher;
import com.mujin.realtime.websocket.DeviceStatusWebSocketHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class DeviceMessageHandler extends ChannelInboundHandlerAdapter {

    private final DeviceStatusService deviceStatusService;
    private final ObjectMapper objectMapper;
    private final DeviceStatusWebSocketHandler webSocketHandler;
    private final DeviceStatusEventPublisher deviceStatusEventPublisher;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // DeviceStatusDecoder에서 변환된 장비 상태 객체
        DeviceStatus status = (DeviceStatus) msg;


        deviceStatusEventPublisher.publish(status);
        // 장비 응답
        ctx.writeAndFlush(ctx.alloc().buffer().writeBytes("OK\n".getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

        cause.printStackTrace();
        ctx.close();
    }
}