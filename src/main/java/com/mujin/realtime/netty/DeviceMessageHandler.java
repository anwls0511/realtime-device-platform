package com.mujin.realtime.netty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mujin.realtime.device.DeviceStatus;
import com.mujin.realtime.device.DeviceStatusService;
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

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        String message =
                (String) msg;

        System.out.println("수신 메시지 : " + message);

        // JSON -> 객체 변환
        DeviceStatus status = objectMapper.readValue(message, DeviceStatus.class);

        // Redis 저장
        deviceStatusService.save(status);

        // 장비 응답
        ctx.writeAndFlush(ctx.alloc().buffer().writeBytes("OK\n".getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

        cause.printStackTrace();
        ctx.close();
    }
}