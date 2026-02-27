# 🐳 Guía de Despliegue con Docker

## Inicio Rápido - Un Solo Comando

```bash
cd nttdata-technical-test
docker-compose up --build
```

¡Eso es todo! En 2-3 minutos tendrás todo corriendo.

---

## 📋 Paso a Paso Detallado

### 1. Compilar el Proyecto (Primera vez)

```bash
# Compilar todos los módulos
./gradlew clean build

# Esto creará los JARs necesarios para Docker
```

### 2. Levantar Todos los Servicios

```bash
# Opción A: En primer plano (ver logs en tiempo real)
docker-compose up --build

# Opción B: En segundo plano (modo daemon)
docker-compose up -d --build
```

### 3. Verificar que Todo Esté Corriendo

```bash
# Ver estado de servicios
docker-compose ps

# Salida esperada:
NAME                    STATUS              PORTS
laft-postgres           Up (healthy)        0.0.0.0:5432->5432/tcp
laft-kafka              Up (healthy)        0.0.0.0:9092-9093->9092-9093/tcp
laft-zookeeper          Up (healthy)        0.0.0.0:2181->2181/tcp
laft-client-service     Up                  0.0.0.0:8081->8081/tcp
laft-account-service    Up                  0.0.0.0:8082->8082/tcp
laft-kafka-ui           Up                  0.0.0.0:8090->8080/tcp
laft-pgadmin            Up                  0.0.0.0:5050->80/tcp
```

### 4. Probar los Endpoints

```bash
# Client Service
curl http://localhost:8081/api/clientes

# Account Service
curl http://localhost:8082/api/cuentas
```

---

## 🔧 Comandos Útiles

### Ver Logs

```bash
# Todos los servicios
docker-compose logs -f

# Solo un servicio
docker-compose logs -f client-service
docker-compose logs -f account-service
docker-compose logs -f postgres
docker-compose logs -f kafka

# Últimas 100 líneas
docker-compose logs --tail=100 client-service
```

### Reiniciar Servicios

```bash
# Reiniciar un servicio específico
docker-compose restart client-service
docker-compose restart account-service

# Reiniciar todos
docker-compose restart
```

### Reconstruir Servicios

```bash
# Si haces cambios en el código
docker-compose up -d --build client-service
docker-compose up -d --build account-service

# O reconstruir todo
docker-compose up -d --build
```

### Detener Servicios

```bash
# Detener todos (mantiene datos)
docker-compose down

# Detener y eliminar volúmenes (limpia TODO)
docker-compose down -v

# Detener un servicio específico
docker-compose stop client-service
```

---

## 🗄️ Acceso a Servicios

| Servicio | URL | Credenciales |
|----------|-----|--------------|
| **Client Service** | http://localhost:8081/api | - |
| **Account Service** | http://localhost:8082/api | - |
| **PostgreSQL** | localhost:5432 | postgres/postgres |
| **pgAdmin** | http://localhost:5050 | admin@laft.com/admin (Server "Laft Bank Local" pre-configured) |

> **Nota sobre pgAdmin**: El servidor "Laft Bank Local" debería aparecer automáticamente. Si necesitas conectarte manualmente, usa **Host: `postgres`** (no localhost), Usuario: `postgres`, Password: `postgres`.
| **Kafka UI** | http://localhost:8090 | - |

---

## 🐛 Troubleshooting

### Problema: "Port already in use"

```bash
# Ver qué está usando el puerto
lsof -i :8081
lsof -i :8082
lsof -i :5432

# Matar el proceso
kill -9 <PID>

# O cambiar el puerto en docker-compose.yml
```

### Problema: "Service unhealthy"

```bash
# Ver logs del servicio con problemas
docker-compose logs postgres
docker-compose logs kafka

# Reiniciar el servicio
docker-compose restart postgres
```

### Problema: "Connection refused to database"

```bash
# Esperar a que PostgreSQL esté listo
docker-compose logs postgres | grep "ready to accept connections"

# Verificar health check
docker inspect laft-postgres | grep -A 10 Health
```

### Problema: Microservicio no inicia

```bash
# Ver logs detallados
docker-compose logs -f client-service

# Verificar que la BD esté lista
docker exec -it laft-postgres psql -U postgres -d laft_bank -c "SELECT 1;"

# Reconstruir el servicio
docker-compose up -d --build client-service
```

### Limpiar Todo y Empezar de Cero

```bash
# Detener y eliminar todo
docker-compose down -v

# Eliminar imágenes
docker-compose down --rmi all

# Recompilar
./gradlew clean build

# Levantar de nuevo
docker-compose up --build
```

---

## 📊 Monitoreo

### Ver Uso de Recursos

```bash
# Uso de CPU y memoria
docker stats

# Solo microservicios
docker stats laft-client-service laft-account-service
```

### Inspeccionar Contenedores

```bash
# Ver configuración completa
docker inspect laft-client-service

# Ver variables de entorno
docker inspect laft-client-service | grep -A 20 Env
```

### Ejecutar Comandos Dentro de Contenedores

```bash
# Conectar a PostgreSQL
docker exec -it laft-postgres psql -U postgres -d laft_bank

# Shell en el contenedor
docker exec -it laft-client-service sh

# Ver archivos de log
docker exec -it laft-client-service ls -la /app
```

---

## 🚀 Flujo de Desarrollo

### 1. Hacer Cambios en el Código

```bash
# Editar archivos Java
vim client-service/src/main/java/...
```

### 2. Recompilar

```bash
# Solo el módulo modificado
./gradlew :client-service:build

# O todos
./gradlew build
```

### 3. Reconstruir y Reiniciar

```bash
# Reconstruir imagen y reiniciar
docker-compose up -d --build client-service

# Ver logs
docker-compose logs -f client-service
```

### 4. Probar

```bash
# Probar endpoint
curl http://localhost:8081/api/clientes
```

---

## 📝 Notas Importantes

1. **Primera vez**: El build puede tardar 3-5 minutos
2. **Health checks**: Los microservicios esperan a que PostgreSQL y Kafka estén listos
3. **Datos**: Los datos se mantienen en volúmenes Docker (usa `down -v` para limpiar)
4. **Logs**: Los logs se muestran en tiempo real con `docker-compose logs -f`
5. **Kafka**: Los topics se crean automáticamente cuando se publican eventos

---

## ✅ Checklist de Verificación

- [ ] `docker-compose ps` muestra todos los servicios "Up"
- [ ] `curl http://localhost:8081/api/clientes` devuelve JSON
- [ ] `curl http://localhost:8082/api/cuentas` devuelve JSON
- [ ] http://localhost:8090 muestra Kafka UI
- [ ] http://localhost:5050 muestra pgAdmin
- [ ] Los logs no muestran errores: `docker-compose logs`

Si todos los checks pasan, ¡estás listo para probar con Postman! 🎉
