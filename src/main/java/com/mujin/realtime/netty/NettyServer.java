package com.mujin.realtime.netty;

import com.mujin.realtime.netty.decoder.DeviceStatusDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class NettyServer {

    // 장비 메시지 처리 핸들러
    private final DeviceMessageHandler deviceMessageHandler;

    private final DeviceStatusDecoder deviceStatusDecoder;

    // TCP 수신 포트
    @Value("${netty.port}")
    private int port;

    public void start() {

        // 연결 수락 담당
        EventLoopGroup bossGroup =
                new NioEventLoopGroup(1);

        // 데이터 처리 담당
        EventLoopGroup workerGroup =
                new NioEventLoopGroup();

        try {

            ServerBootstrap bootstrap =
                    new ServerBootstrap();

            bootstrap.group(
                            bossGroup,
                            workerGroup
                    )
                    .channel(
                            NioServerSocketChannel.class
                    )
                    .childHandler(
                            new ChannelInitializer<SocketChannel>() {

                                @Override
                                protected void initChannel(
                                        SocketChannel ch
                                ) {

                                    // 수신 데이터 처리 Handler 등록
                                    ch.pipeline()
                                            .addLast(
                                                    new LineBasedFrameDecoder(
                                                            1024
                                                    )
                                            )
                                            .addLast(
                                                    new StringDecoder(
                                                            StandardCharsets.UTF_8
                                                    )
                                            )
                                            .addLast(
                                                    deviceStatusDecoder
                                            )

                                            // 변환된 장비 상태 데이터 처리
                                            .addLast(
                                                    deviceMessageHandler
                                            );
                                }
                            });

            ChannelFuture future =
                    bootstrap.bind(port)
                            .sync();

            System.out.println(
                    "Netty TCP Server Started : "
                            + port
            );

            future.channel()
                    .closeFuture()
                    .sync();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Netty 서버 실행 실패",
                    e
            );

        } finally {

            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}