package com.mujin.realtime.simulator;

import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class DeviceSimulator {

    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 9000;

    private static final String[] DEVICE_IDS = {
            "D001",
            "D002",
            "D003",
            "D004",
            "D005"
    };

    public static void main(String[] args) throws Exception {

        Random random = new Random();

        // 테스트 장비가 Netty TCP 서버에 연결
        try (
                Socket socket = new Socket(SERVER_IP, SERVER_PORT);
                OutputStream outputStream = socket.getOutputStream()
        ) {

            System.out.println("장비 시뮬레이터 연결 성공");

            while (true) {

                for (String deviceId : DEVICE_IDS) {

                    // 장비별 더미 상태 데이터 생성
                    String message = String.format(
                            "{\"deviceId\":\"%s\",\"temperature\":%.1f,\"humidity\":%.1f,\"timestamp\":%d}",
                            deviceId,
                            20 + random.nextDouble() * 10,
                            40 + random.nextDouble() * 20,
                            System.currentTimeMillis()
                    );

                    outputStream.write(
                            message.getBytes(StandardCharsets.UTF_8)
                    );
                    outputStream.flush();

                    System.out.println("전송 메시지 : " + message);

                    Thread.sleep(1000);
                }
            }
        }
    }
}