<p align="center">
  <a href="./README.md">
    <img src="https://img.shields.io/badge/lang-Español-red.svg" alt="Spanish Version">
  </a>
</p>

<div align="center" style="background-color: #d90429; padding: 20px; border-radius: 0px; width: full; margin: 0 auto;">
  <img src="../rsrc/laft-logo.svg" width="200" alt="Laft Logo" />
</div>

<br/>

# Account Service

Microservice responsible for managing bank accounts and transactions/movements. It implements balance validation logic and transaction types.

## 🚀 Quick Start

### Requirements
- **Java 17**
- **PostgreSQL** and **Kafka** active.

### Local Execution
From the project root:
```bash
./gradlew :account-service:bootRun
```

### Docker
```bash
docker build -t account-service -f account-service/Dockerfile .
docker run -p 8082:8082 account-service
```

## 📡 Service Information
- **Port:** 8082
- **Endpoints:**
  - Accounts: `/api/accounts`
  - Transactions: `/api/transactions`
- **Swagger UI:** [http://localhost:8082/api/swagger-ui.html](http://localhost:8082/api/swagger-ui.html)

## 🎨 Design Patterns Applied
- **Strategy Pattern:** To process the different types of movements (Deposits and Withdrawals).
- **Factory Pattern:** For dynamic selection of the transaction strategy.
- **Fluent Builder:** For entity immutability.

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
