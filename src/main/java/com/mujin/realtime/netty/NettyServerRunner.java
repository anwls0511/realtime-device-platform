package com.mujin.realtime.netty;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NettyServerRunner implements CommandLineRunner {

    private final NettyServer nettyServer;

    @Override
    public void run(String... args) {

        // Spring Boot 실행 시 Netty 서버 시작
        new Thread(nettyServer::start).start();
    }
}