<p align="center">
  <a href="./README_EN.md">
    <img src="https://img.shields.io/badge/lang-English-blue.svg" alt="English Version">
  </a>
</p>

<div align="center" style="background-color: #d90429; padding: 20px; border-radius: 0px; width: full; margin: 0 auto;">
  <img src="./rsrc/laft-logo.svg" width="200" alt="Laft Logo" />
</div>

<br/>

# Nttdata Technical Test - Microservices Architecture

Sistema bancario basado en microservicios con **Spring Boot 3.4.0**, Spring WebFlux, Kafka y PostgreSQL.

## 📋 Tabla de Contenidos

- [Arquitectura](#arquitectura)
- [Tecnologías](#tecnologías)
- [Patrones de Diseño](#patrones-de-diseño)
- [Inicio Rápido](#inicio-rápido)
- [API Endpoints & Swagger](#api-endpoints--swagger)
- [Docker Swarm](#docker-swarm)
- [Funcionalidades](#funcionalidades)
- [Testing & Calidad](#testing--calidad)

---

## 🏗️ Arquitectura

```
nttdata-technical-test/
├── common-lib/              # DTOs, constantes compartidas
├── shared-repositories/     # Entidades y Repositorios JPA compartidos
├── client-service/          # Microservicio: Cliente/Persona (Puerto 8081)
├── account-service/         # Microservicio: Cuenta/Movimientos (Puerto 8082)
├── bank-mgnt-composite/     # Microservicio: Composite GraphQL/WebClient (Puerto 8083)
├── BaseDatos.sql           # Script de inicialización de BD
├── docker-compose.yml      # Orquestación estándar
├── docker-stack.yml        # Orquestación para Swarm
├── start.sh                # Script de inicio automatizado
└── clean.sh                # Script de limpieza total
```

### Microservicios (Stack Reactivo)

Todos los servicios utilizan **Spring WebFlux** para operaciones no bloqueantes, manteniendo compatibilidad con **JPA** mediante el uso de Schedulers dedicados (`boundedElastic`).

---

## 💻 Tecnologías

- **Spring Boot**: 3.4.0
- **Java**: 17
- **Spring WebFlux**: Programación reactiva
- **Base de datos**: PostgreSQL 16
- **Message Broker**: Apache Kafka
- **Documentación**: SpringDoc OpenAPI (Swagger)
- **Calidad**: Pitest (Mutation Testing)

---

## 🚀 Inicio Rápido

La forma más sencilla de iniciar el proyecto es utilizando el script interactivo incluido.

### 1. Preparar el entorno
Asegúrate de tener Docker instalado y corriendo.

### 2. Ejecutar Script de Inicio
```bash
chmod +x start.sh clean.sh
./start.sh
```
El script te permitirá elegir entre:
- **Opción 1**: Docker Compose (Desarrollo local con logs visibles).
- **Opción 2**: Docker Swarm (Despliegue con réplicas y alta disponibilidad).

### 3. Limpieza Total
Si deseas eliminar contenedores, redes, volúmenes e imágenes locales de NTTData:
```bash
./clean.sh
```

---

## 📡 API Endpoints & Swagger

Cada servicio expone su propia documentación interactiva bajo el prefijo `/api/swagger-ui.html`.

### URLs de Acceso Directo (Local)
- **Client Service**: [http://localhost:8081/api/swagger-ui.html](http://localhost:8081/api/swagger-ui.html)
- **Account Service**: [http://localhost:8082/api/swagger-ui.html](http://localhost:8082/api/swagger-ui.html)
- **Composite Service**: [http://localhost:8083/api/swagger-ui.html](http://localhost:8083/api/swagger-ui.html)

### GraphQL (Composite)
- **GraphiQL IDE**: [http://localhost:8083/api/graphiql](http://localhost:8083/api/graphiql)
- **Endpoint**: `POST /api/graphql`

---

## 🐝 Docker Swarm

Para un entorno de orquestación más avanzado, puedes desplegar el stack completo en un cluster Swarm:

```bash
# Inicializar Swarm si no lo está
docker swarm init

# Desplegar el stack
docker stack deploy -c docker-stack.yml nttdata

# Verificar servicios
docker stack services nttdata
```

---

## 🎨 Patrones de Diseño

- **Fluent Builder**: Uso de `@Builder(toBuilder=true)` para inmutabilidad y actualizaciones seguras.
- **Strategy Pattern**: Procesamiento dinámico de `DEPOSITO` y `RETIRO`.
- **Factory Pattern**: Creación centralizada de objetos en `ClientFactory`.
- **Composite Pattern**: Agregación de microservicios en el módulo `bank-mgnt-composite`.

---

## 🧪 Testing & Calidad

### Pruebas Unitarias y Reactivas
Implementadas con **JUnit 5**, **Mockito** y `StepVerifier` para validar los flujos de `Mono` y `Flux`.

#### Ejecutar todos los tests del proyecto:
```bash
./gradlew test
```

#### Ejecutar tests de un módulo específico:
```bash
./gradlew :client-service:test
./gradlew :account-service:test
./gradlew :bank-mgnt-composite:test
```

### Mutation Testing (Pitest)
Para garantizar la efectividad de los tests, se ha integrado **Pitest**. Este genera "mutantes" en el código para verificar que los tests realmente detecten cambios lógicos.

#### Ejecutar Mutation Testing:
```bash
./gradlew pitest
```

#### Reportes de Cobertura y Mutación:
Los reportes se generan en formato HTML en las siguientes rutas dentro de cada módulo:
- **Unit Tests**: `[modulo]/build/reports/tests/test/index.html`
- **Mutation Testing**: `[modulo]/build/reports/pitest/index.html`


---

## � Casos de Uso (Data Preload)
El archivo `BaseDatos.sql` precarga automáticamente los casos de prueba del PDF:
1. Clientes: Jose Lema, Marianela Montalvo, Juan Osorio.
2. Cuentas asociadas y tipos de cuenta.
3. El sistema está listo para recibir el primer movimiento vía API.

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
