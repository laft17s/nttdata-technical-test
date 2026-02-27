# Configuración de Archivos de Aplicación

Este proyecto utiliza archivos `.dist` como plantillas versionadas para la configuración.

## Archivos de Configuración

### Client Service
- `client-service/src/main/resources/application.yml.dist` - Plantilla versionada
- `client-service/src/main/resources/application.yml` - Tu configuración local (ignorada por git)

### Account Service
- `account-service/src/main/resources/application.yml.dist` - Plantilla versionada
- `account-service/src/main/resources/application.yml` - Tu configuración local (ignorada por git)

## Primer Uso

Si es la primera vez que clonas el proyecto:

```bash
# Client Service
cp client-service/src/main/resources/application.yml.dist client-service/src/main/resources/application.yml

# Account Service
cp account-service/src/main/resources/application.yml.dist account-service/src/main/resources/application.yml
```

## Configuración

Los archivos `application.yml` contienen dos perfiles:

### Perfil por defecto (desarrollo local)
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/nttdata_bank
    username: postgres
    password: postgres
  kafka:
    bootstrap-servers: localhost:9093
```

### Perfil Docker
```yaml
spring:
  config:
    activate:
      on-profile: docker
  datasource:
    url: jdbc:postgresql://postgres:5432/nttdata_bank
  kafka:
    bootstrap-servers: kafka:9092
```

## Uso

### Desarrollo Local
```bash
# Levantar solo infraestructura
docker-compose up -d postgres kafka zookeeper

# Ejecutar microservicios localmente
./gradlew :client-service:bootRun
./gradlew :account-service:bootRun
```

### Docker Completo
```bash
# Usa automáticamente el perfil 'docker'
./start.sh
```

## Personalización

Puedes modificar tu `application.yml` local sin afectar el repositorio. Los cambios en `.dist` deben ser versionados.

## Autor
Luis Arcángel Farro Terán
