<p align="center">
  <a href="./README_EN.md">
    <img src="https://img.shields.io/badge/lang-English-blue.svg" alt="English Version">
  </a>
</p>

<div align="center" style="background-color: #d90429; padding: 20px; border-radius: 0px; width: full; margin: 0 auto;">
  <img src="../rsrc/laft-logo.svg" width="200" alt="Laft Logo" />
</div>

<br/>

# Bank Management Composite Service

Servicio agregador (Orquestador) que expone una interfaz **GraphQL**. Se encarga de comunicar el frontend con los microservicios de Clientes y Cuentas de forma transparente.

## 🚀 Inicio Rápido

### Requisitos
- **Java 17**
- **Client Service** (8081) y **Account Service** (8082) en ejecución.

### Ejecución Local
Desde la raíz del proyecto:
```bash
./gradlew :bank-mgnt-composite:bootRun
```

### Docker
```bash
docker build -t bank-mgnt-composite -f bank-mgnt-composite/Dockerfile .
docker run -p 8083:8083 bank-mgnt-composite
```

## 📡 Información del Servicio
- **Puerto:** 8083
- **GraphQL Endpoint:** `/api/graphql`
- **GraphiQL IDE:** [http://localhost:8083/api/graphiql](http://localhost:8083/api/graphiql)
- **Swagger UI:** [http://localhost:8083/api/swagger-ui.html](http://localhost:8083/api/swagger-ui.html)

## 🧩 Funcionalidad (Composite)
Este servicio permite realizar mutaciones complejas (como `createClientWithAccount`) que interactúan con múltiples microservicios en una sola petición GraphQL, simplificando la lógica para el cliente cliente (web/mobile).

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
