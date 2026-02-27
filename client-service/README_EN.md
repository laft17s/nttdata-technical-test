<p align="center">
  <a href="./README.md">
    <img src="https://img.shields.io/badge/lang-Español-red.svg" alt="Spanish Version">
  </a>
</p>

<div align="center" style="background-color: #d90429; padding: 20px; border-radius: 0px; width: full; margin: 0 auto;">
  <img src="../rsrc/laft-logo.svg" width="200" alt="Laft Logo" />
</div>

<br/>

# Client Service

Microservice responsible for managing people and clients. It uses **Spring WebFlux** to provide a reactive, non-blocking interface.

## 🚀 Quick Start

### Requirements
- **Java 17**
- **PostgreSQL** and **Kafka** active (you can use the `docker-compose.yml` in the root to start only the dependencies).

### Local Execution
From the project root:
```bash
./gradlew :client-service:bootRun
```

### Docker
```bash
docker build -t client-service -f client-service/Dockerfile .
docker run -p 8081:8081 client-service
```

## 📡 Service Information
- **Port:** 8081
- **Base Path:** `/api/clients`
- **Swagger UI:** [http://localhost:8081/api/swagger-ui.html](http://localhost:8081/api/swagger-ui.html)

## 🛠️ Technologies
- Spring Boot 3.4.0
- Spring WebFlux
- JPA + Hibernate (Reactive Scheduler)
- Kafka Producer (Client events)
- Bean Validation (DTO validation)

---

## 👥 Author

<div align="center">
  <img src="https://avatars.githubusercontent.com/u/57549850?v=4" width="100" style="border-radius: 50%;" alt="Luis Arcángel Farro Terán" />
  <br />
  <strong>Luis Arcángel Farro Terán (LAFT)</strong>
  <br />
  <a href="https://github.com/laft17s">@laft17s</a>
</div>

---
License: UNLICENSED
