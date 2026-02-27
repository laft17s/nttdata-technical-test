<p align="center">
  <a href="./README.md">
    <img src="https://img.shields.io/badge/lang-Español-red.svg" alt="Spanish Version">
  </a>
</p>

<div align="center" style="background-color: #d90429; padding: 20px; border-radius: 0px; width: full; margin: 0 auto;">
  <img src="../rsrc/laft-logo.svg" width="200" alt="Laft Logo" />
</div>

<br/>

# Bank Management Composite Service

Aggregator service (Orchestrator) that exposes a **GraphQL** interface. It is responsible for communicating the frontend with the Client and Account microservices transparently.

## 🚀 Quick Start

### Requirements
- **Java 17**
- **Client Service** (8081) and **Account Service** (8082) running.

### Local Execution
From the project root:
```bash
./gradlew :bank-mgnt-composite:bootRun
```

### Docker
```bash
docker build -t bank-mgnt-composite -f bank-mgnt-composite/Dockerfile .
docker run -p 8083:8083 bank-mgnt-composite
```

## 📡 Service Information
- **Port:** 8083
- **GraphQL Endpoint:** `/api/graphql`
- **GraphiQL IDE:** [http://localhost:8083/api/graphiql](http://localhost:8083/api/graphiql)
- **Swagger UI:** [http://localhost:8083/api/swagger-ui.html](http://localhost:8083/api/swagger-ui.html)

## 🧩 Functionality (Composite)
This service enables complex mutations (such as `createClientWithAccount`) that interact with multiple microservices in a single GraphQL request, simplifying logic for the client (web/mobile).

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
