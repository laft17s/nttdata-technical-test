# 🚀 Guía de Inicio Rápido - Laft Banking

**Autor:** Luis Arcángel Farro Terán  
**Fecha:** 2025-12-06

---

## Inicio en 1 Comando

```bash
cd nttdata-technical-test
./start.sh
```

Espera 3-5 minutos y ¡listo! 🎉

---

## Endpoints Actualizados (Inglés)

### Client Service (Puerto 8081)
- `GET /api/clients` - Listar clientes
- `GET /api/clients/{clientId}` - Obtener cliente
- `POST /api/clients` - Crear cliente
- `PUT /api/clients/{clientId}` - Actualizar cliente
- `DELETE /api/clients/{clientId}` - Eliminar cliente

### Account Service (Puerto 8082)
- `GET /api/accounts` - Listar cuentas
- `GET /api/accounts/{accountNumber}` - Obtener cuenta
- `POST /api/accounts` - Crear cuenta
- `GET /api/transactions` - Listar transacciones
- `POST /api/transactions` - Crear transacción
- `GET /api/reports?fecha=YYYY-MM-DD,YYYY-MM-DD&cliente={clientId}` - Generar reporte

---

## Ejemplos de JSON (Actualizado)

### Crear Cliente
```json
{
  "name": "Jose Lema",
  "gender": "M",
  "age": 35,
  "identification": "1234567890",
  "address": "Otavalo sn y principal",
  "phone": "098254785",
  "password": "1234",
  "status": "ACTIVO"
}
```

### Crear Cuenta
```json
{
  "accountNumber": "478758",
  "accountType": "AHORRO",
  "initialBalance": 2000.00,
  "status": "ACTIVO",
  "clientId": "550e8400-e29b-41d4-a716-446655440001"
}
```

### Crear Transacción (Depósito)
```json
{
  "accountNumber": "478758",
  "transactionType": "DEPOSITO",
  "amount": 600.00
}
```

### Crear Transacción (Retiro)
```json
{
  "accountNumber": "478758",
  "transactionType": "RETIRO",
  "amount": 575.00
}
```

---

## Verificación Rápida

```bash
# Client Service
curl http://localhost:8081/api/clients

# Account Service  
curl http://localhost:8082/api/accounts

# Kafka UI
open http://localhost:8090

# Kafka Broker (Externo)
# localhost:9094

# pgAdmin
open http://localhost:5050
```

---

## Comandos Útiles

```bash
# Ver logs
docker-compose logs -f client-service
docker-compose logs -f account-service

# Reiniciar un servicio
docker-compose restart client-service

# Detener todo
docker-compose down

# Detener y limpiar
docker-compose down -v
```

---

## Importar en Insomnia/Postman

1. Abrir Insomnia o Postman
2. Import → `insomnia/Laft-Banking-API.json`
3. Los endpoints ya están actualizados al inglés

---

## Troubleshooting

### Puerto ocupado
```bash
lsof -i :8081
lsof -i :8082
kill -9 <PID>
```

### Servicio no responde
```bash
docker-compose logs <service-name>
docker-compose restart <service-name>
```

### Limpiar y empezar de cero
```bash
docker-compose down -v
./start.sh
```

---

## ✅ Checklist

- [ ] Docker Desktop corriendo
- [ ] Ejecutar `./start.sh`
- [ ] Esperar 3-5 minutos
- [ ] Verificar endpoints con curl
- [ ] Importar colección en Insomnia
- [ ] ¡Probar!

---

**¡Listo para probar! 🚀**
