<p align="center">
  <a href="./README.md">
    <img src="https://img.shields.io/badge/lang-Español-red.svg" alt="Spanish Version">
  </a>
</p>

<div align="center" style="background-color: #d90429; padding: 20px; border-radius: 0px; width: full; margin: 0 auto;">
  <img src="./rsrc/laft-logo.svg" width="200" alt="Laft Logo" />
</div>

<br/>

# Nttdata Technical Test - Microservices Architecture

Banking system based on microservices with **Spring Boot 3.4.0**, Spring WebFlux, Kafka, and PostgreSQL.

## 📋 Table of Contents

- [Architecture](#architecture)
- [Technologies](#technologies)
- [Design Patterns](#design-patterns)
- [Quick Start](#quick-start)
- [API Endpoints & Swagger](#api-endpoints--swagger)
- [Docker Swarm](#docker-swarm)
- [Features](#features)
- [Testing & Quality](#testing--quality)

---

## 🏗️ Architecture

```
nttdata-technical-test/
├── common-lib/              # DTOs, shared constants
├── shared-repositories/     # Shared JPA Entities and Repositories
├── client-service/          # Microservice: Client/Person (Port 8081)
├── account-service/         # Microservice: Account/Transactions (Port 8082)
├── bank-mgnt-composite/     # Microservice: Composite GraphQL/WebClient (Port 8083)
├── BaseDatos.sql           # Database initialization script
├── docker-compose.yml      # Standard orchestration
├── docker-stack.yml        # Orchestration for Swarm
├── start.sh                # Automated startup script
└── clean.sh                # Total cleanup script
```

### Microservices (Reactive Stack)

All services use **Spring WebFlux** for non-blocking operations, maintaining compatibility with **JPA** through the use of dedicated Schedulers (`boundedElastic`).

---

## 💻 Technologies

- **Spring Boot**: 3.4.0
- **Java**: 17
- **Spring WebFlux**: Reactive programming
- **Database**: PostgreSQL 16
- **Message Broker**: Apache Kafka
- **Documentation**: SpringDoc OpenAPI (Swagger)
- **Quality**: Pitest (Mutation Testing)

---

## 🚀 Quick Start

The easiest way to start the project is by using the included interactive script.

### 1. Prepare the environment
Ensure you have Docker installed and running.

### 2. Run the Startup Script
```bash
chmod +x start.sh clean.sh
./start.sh
```
The script will allow you to choose between:
- **Option 1**: Docker Compose (Local development with visible logs).
- **Option 2**: Docker Swarm (Deployment with replicas and high availability).

### 3. Total Cleanup
Should you wish to remove containers, networks, volumes, and local NTTData images:
```bash
./clean.sh
```

---

## 📡 API Endpoints & Swagger

Each service exposes its own interactive documentation under the `/api/swagger-ui.html` prefix.

### Direct Access URLs (Local)
- **Client Service**: [http://localhost:8081/api/swagger-ui.html](http://localhost:8081/api/swagger-ui.html)
- **Account Service**: [http://localhost:8082/api/swagger-ui.html](http://localhost:8082/api/swagger-ui.html)
- **Composite Service**: [http://localhost:8083/api/swagger-ui.html](http://localhost:8083/api/swagger-ui.html)

### GraphQL (Composite)
- **GraphiQL IDE**: [http://localhost:8083/api/graphiql](http://localhost:8083/api/graphiql)
- **Endpoint**: `POST /api/graphql`

---

## 🐝 Docker Swarm

For a more advanced orchestration environment, you can deploy the full stack on a Swarm cluster:

```bash
# Initialize Swarm if not already enabled
docker swarm init

# Deploy the stack
docker stack deploy -c docker-stack.yml nttdata

# Verify services
docker stack services nttdata
```

---

## 🎨 Design Patterns

- **Fluent Builder**: Use of `@Builder(toBuilder=true)` for safe updates and immutability.
- **Strategy Pattern**: Dynamic processing of `DEPOSITO` and `RETIRO`.
- **Factory Pattern**: Centralized object creation in `ClientFactory`.
- **Composite Pattern**: Aggregation of microservices in the `bank-mgnt-composite` module.

---

## 🧪 Testing & Quality

### Unit and Reactive Testing
Implemented with **JUnit 5**, **Mockito**, and `StepVerifier` to validate `Mono` and `Flux` flows.

#### Run all project tests:
```bash
./gradlew test
```

#### Run tests for a specific module:
```bash
./gradlew :client-service:test
./gradlew :account-service:test
./gradlew :bank-mgnt-composite:test
```

### Mutation Testing (Pitest)
To guarantee test effectiveness, **Pitest** has been integrated. It generates "mutants" in the code to verify that tests correctly detect logical changes.

#### Run Mutation Testing:
```bash
./gradlew pitest
```

#### Coverage and Mutation Reports:
Reports are generated in HTML format in the following paths within each module:
- **Unit Tests**: `[module]/build/reports/tests/test/index.html`
- **Mutation Testing**: `[module]/build/reports/pitest/index.html`

---

## ⚙️ Use Cases (Data Preload)
The `BaseDatos.sql` file automatically preloads the test cases from the requirements:
1. Clients: Jose Lema, Marianela Montalvo, Juan Osorio.
2. Associated accounts and account types.
3. The system is ready to receive the first transaction via API.

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
