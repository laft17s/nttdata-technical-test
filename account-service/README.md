<p align="center">
  <a href="./README_EN.md">
    <img src="https://img.shields.io/badge/lang-English-blue.svg" alt="English Version">
  </a>
</p>

<div align="center" style="background-color: #d90429; padding: 20px; border-radius: 0px; width: full; margin: 0 auto;">
  <img src="../rsrc/laft-logo.svg" width="200" alt="Laft Logo" />
</div>

<br/>

# Account Service

Microservicio encargado de la gestión de cuentas bancarias y movimientos/transacciones. Implementa lógica de validación de saldos y tipos de transacción.

## 🚀 Inicio Rápido

### Requisitos
- **Java 17**
- **PostgreSQL** y **Kafka** activos.

### Ejecución Local
Desde la raíz del proyecto:
```bash
./gradlew :account-service:bootRun
```

### Docker
```bash
docker build -t account-service -f account-service/Dockerfile .
docker run -p 8082:8082 account-service
```

## 📡 Información del Servicio
- **Puerto:** 8082
- **Endpoints:**
  - Cuentas: `/api/accounts`
  - Movimientos: `/api/transactions`
- **Swagger UI:** [http://localhost:8082/api/swagger-ui.html](http://localhost:8082/api/swagger-ui.html)

## 🎨 Patrones Aplicados
- **Strategy Pattern:** Para procesar los diferentes tipos de movimientos (Depósitos y Retiros).
- **Factory Pattern:** Para la selección dinámica de la estrategia de transacción.
- **Fluent Builder:** Para la inmutabilidad de las entidades.

---

## 👥 Autor

<div align="center">
  <img src="https://avatars.githubusercontent.com/u/57549850?v=4" width="100" style="border-radius: 50%;" alt="Luis Arcángel Farro Terán" />
  <br />
  <strong>Luis Arcángel Farro Terán (LAFT)</strong>
  <br />
  <a href="https://github.com/laft17s">@laft17s</a>
</div>

---
License: UNLICENSED
