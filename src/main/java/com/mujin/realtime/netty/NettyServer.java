package com.mujin.realtime.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NettyServer {

    // 장비 메시지 처리 핸들러
    private final DeviceMessageHandler deviceMessageHandler;

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