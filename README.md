# Realtime Device Platform

Spring Boot와 Netty를 활용한 실시간 장비 관제 플랫폼 프로젝트입니다.

장비로부터 TCP 데이터를 수신하고, Redis에 저장한 뒤 API를 통해 조회할 수 있는 구조를 목표로 합니다.

---

## 프로젝트 목표

* Netty 기반 TCP 서버 구축
* Redis를 활용한 실시간 데이터 저장
* 장비 상태 조회 API 제공
* WebSocket 기반 실시간 모니터링
* Docker 환경 구성
* MQTT 연동
* ROS2 연동
* 로봇 관제 플랫폼 확장

---

## 기술 스택

### Backend

* Java 21
* Spring Boot 3.5
* Netty
* Redis

### Build

* Gradle

### Infra

* Docker (예정)

---

## 프로젝트 구조

```text
com.mujin.realtime

├── device
│   ├── DeviceStatus
│   ├── DeviceStatusService
│   └── DeviceController
│
├── netty
│   ├── NettyServer
│   ├── NettyServerRunner
│   └── DeviceMessageHandler
│
└── simulator
    └── DeviceSimulator
```

---

## 현재 아키텍처

```text
Device Simulator

        │

        ▼

Netty TCP Server

        │

        ▼

DeviceMessageHandler

        │

        ▼

Redis

        │

        ▼

REST API
```

---

## 구현 기능

### 장비 상태 조회

* Redis 기반 장비 상태 저장
* 장비 상태 조회 API 제공

### TCP 서버

* Netty 기반 TCP 서버 구현
* 장비 연결 수신
* 장비 데이터 수신 처리

### 장비 시뮬레이터

* TCP Client 역할
* 더미 장비 데이터 생성
* Netty 서버로 데이터 전송

---

## 실행 순서

### Redis 실행

```bash
docker run --name realtime-redis -p 6379:6379 -d redis
```

### Spring Boot 실행

```bash
./gradlew bootRun
```

### Device Simulator 실행

```text
DeviceSimulator.main()
```

---

## API

### 장비 상태 조회

```http
GET /api/devices/{deviceId}/status
```

예시

```http
GET /api/devices/D001/status
```

---

## 개발 로드맵

### 1단계

* [x] Spring Boot 프로젝트 구성
* [x] Redis 연동
* [x] Netty TCP 서버 구성
* [x] 장비 시뮬레이터 구현

### 2단계

* [ ] TCP 전문 프레이밍 처리
* [ ] LengthFieldBasedFrameDecoder 적용
* [ ] 예외 처리 고도화

### 3단계

* [ ] WebSocket 실시간 모니터링

### 4단계

* [ ] Docker 환경 구성
* [ ] Docker Compose 구성

### 5단계

* [ ] MQTT 연동

### 6단계

* [ ] ROS2 연동

### 7단계

* [ ] 로봇 관제 플랫폼 확장

```
```
