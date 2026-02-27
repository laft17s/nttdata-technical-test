-- =====================================================
-- Base de Datos: Nttdata Technical Test
-- Sistema Bancario - Microservicios
-- =====================================================

-- Base de Datos: Nttdata Technical Test
-- Sistema Bancario - Microservicios
-- =====================================================

-- La base de datos nttdata_bank ya es creada por el contenedor (POSTGRES_DB)

-- =====================================================
-- TABLAS DE CATÁLOGO
-- =====================================================

-- Tabla: gender
CREATE TABLE gender (
    id BIGSERIAL PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    descripcion VARCHAR(50) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

-- Tabla: account_type
CREATE TABLE account_type (
    id BIGSERIAL PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    descripcion VARCHAR(50) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

-- Tabla: status
CREATE TABLE status (
    id BIGSERIAL PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    descripcion VARCHAR(50) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

-- =====================================================
-- TABLAS PRINCIPALES
-- =====================================================

-- Tabla: person (tabla base)
CREATE TABLE person (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    gender_id BIGINT NOT NULL,
    edad INTEGER NOT NULL CHECK (edad >= 0 AND edad <= 150),
    identificacion VARCHAR(20) NOT NULL UNIQUE,
    direccion VARCHAR(200),
    telefono VARCHAR(20),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_person_gender FOREIGN KEY (gender_id) REFERENCES gender(id)
);

-- Tabla: client (hereda de person)
CREATE TABLE client (
    person_id BIGINT PRIMARY KEY,
    client_id VARCHAR(36) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    status_id BIGINT NOT NULL,
    CONSTRAINT fk_client_person FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE,
    CONSTRAINT fk_client_status FOREIGN KEY (status_id) REFERENCES status(id)
);

-- Tabla: account
CREATE TABLE account (
    id BIGSERIAL PRIMARY KEY,
    account_number VARCHAR(20) NOT NULL UNIQUE,
    account_type_id BIGINT NOT NULL,
    initial_balance DECIMAL(15,2) NOT NULL,
    current_balance DECIMAL(15,2) NOT NULL,
    status_id BIGINT NOT NULL,
    client_id VARCHAR(36) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_account_tipo FOREIGN KEY (account_type_id) REFERENCES account_type(id),
    CONSTRAINT fk_account_status FOREIGN KEY (status_id) REFERENCES status(id)
);

-- Tabla: transaction
CREATE TABLE transaction (
    id BIGSERIAL PRIMARY KEY,
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    transaction_type VARCHAR(20) NOT NULL CHECK (transaction_type IN ('DEPOSITO', 'RETIRO')),
    valor DECIMAL(15,2) NOT NULL,
    saldo DECIMAL(15,2) NOT NULL,
    account_id BIGINT NOT NULL,
    CONSTRAINT fk_transaction_account FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE
);

-- =====================================================
-- ÍNDICES PARA OPTIMIZACIÓN
-- =====================================================

CREATE INDEX idx_person_identificacion ON person(identificacion);
CREATE INDEX idx_client_client_id ON client(client_id);
CREATE INDEX idx_client_status ON client(status_id);
CREATE INDEX idx_account_numero ON account(account_number);
CREATE INDEX idx_account_client ON account(client_id);
CREATE INDEX idx_account_status ON account(status_id);
CREATE INDEX idx_transaction_account ON transaction(account_id);
CREATE INDEX idx_transaction_fecha ON transaction(fecha);
CREATE INDEX idx_transaction_account_fecha ON transaction(account_id, fecha);

-- =====================================================
-- DATOS INICIALES - CATÁLOGOS
-- =====================================================

-- Insertar géneros
INSERT INTO gender (codigo, descripcion, activo) VALUES
('M', 'Masculino', TRUE),
('F', 'Femenino', TRUE),
('O', 'Otro', TRUE);

-- Insertar tipos de account
INSERT INTO account_type (codigo, descripcion, activo) VALUES
('AHORRO', 'Cuenta de Ahorro', TRUE),
('CORRIENTE', 'Cuenta Corriente', TRUE);

-- Insertar statuss
INSERT INTO status (codigo, descripcion, activo) VALUES
('ACTIVO', 'Activo', TRUE),
('INACTIVO', 'Inactivo', TRUE);

-- =====================================================
-- DATOS DE PRUEBA - CASOS DE USO DEL PDF
-- =====================================================

-- Caso de Uso 1: Creación de Usuarios

-- Jose Lema
INSERT INTO person (nombre, gender_id, edad, identificacion, direccion, telefono)
VALUES ('Jose Lema', 1, 35, '1234567890', 'Otavalo sn y principal', '098254785');

INSERT INTO client (person_id, client_id, contrasena, status_id)
VALUES (
    (SELECT id FROM person WHERE identificacion = '1234567890'),
    '550e8400-e29b-41d4-a716-446655440001',
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', -- 1234 (BCrypt)
    (SELECT id FROM status WHERE codigo = 'ACTIVO')
);

-- Marianela Montalvo
INSERT INTO person (nombre, gender_id, edad, identificacion, direccion, telefono)
VALUES ('Marianela Montalvo', 2, 28, '0987654321', 'Amazonas y NNUU', '097548965');

INSERT INTO client (person_id, client_id, contrasena, status_id)
VALUES (
    (SELECT id FROM person WHERE identificacion = '0987654321'),
    '550e8400-e29b-41d4-a716-446655440002',
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', -- 5678 (BCrypt)
    (SELECT id FROM status WHERE codigo = 'ACTIVO')
);

-- Juan Osorio
INSERT INTO person (nombre, gender_id, edad, identificacion, direccion, telefono)
VALUES ('Juan Osorio', 1, 42, '1122334455', '13 junio y Equinoccial', '098874587');

INSERT INTO client (person_id, client_id, contrasena, status_id)
VALUES (
    (SELECT id FROM person WHERE identificacion = '1122334455'),
    '550e8400-e29b-41d4-a716-446655440003',
    '$2a$10$DpwmetQQCDnH3KcKSKw.MOXZrFqKPHKPmqKLIJkYlQqKqKqKqKqKq', -- 1245 (BCrypt)
    (SELECT id FROM status WHERE codigo = 'ACTIVO')
);

-- Caso de Uso 2: Creación de Cuentas de Usuario

-- Cuenta de Jose Lema - Ahorro
INSERT INTO account (account_number, account_type_id, initial_balance, current_balance, status_id, client_id)
VALUES (
    '478758',
    (SELECT id FROM account_type WHERE codigo = 'AHORRO'),
    2000.00,
    2000.00,
    (SELECT id FROM status WHERE codigo = 'ACTIVO'),
    '550e8400-e29b-41d4-a716-446655440001'
);

-- Cuenta de Marianela Montalvo - Corriente
INSERT INTO account (account_number, account_type_id, initial_balance, current_balance, status_id, client_id)
VALUES (
    '225487',
    (SELECT id FROM account_type WHERE codigo = 'CORRIENTE'),
    100.00,
    100.00,
    (SELECT id FROM status WHERE codigo = 'ACTIVO'),
    '550e8400-e29b-41d4-a716-446655440002'
);

-- Cuenta de Juan Osorio - Ahorro
INSERT INTO account (account_number, account_type_id, initial_balance, current_balance, status_id, client_id)
VALUES (
    '495878',
    (SELECT id FROM account_type WHERE codigo = 'AHORRO'),
    0.00,
    0.00,
    (SELECT id FROM status WHERE codigo = 'ACTIVO'),
    '550e8400-e29b-41d4-a716-446655440003'
);

-- Cuenta de Marianela Montalvo - Ahorro
INSERT INTO account (account_number, account_type_id, initial_balance, current_balance, status_id, client_id)
VALUES (
    '496825',
    (SELECT id FROM account_type WHERE codigo = 'AHORRO'),
    540.00,
    540.00,
    (SELECT id FROM status WHERE codigo = 'ACTIVO'),
    '550e8400-e29b-41d4-a716-446655440002'
);

-- Caso de Uso 3: Nueva Cuenta Corriente para Jose Lema
INSERT INTO account (account_number, account_type_id, initial_balance, current_balance, status_id, client_id)
VALUES (
    '585545',
    (SELECT id FROM account_type WHERE codigo = 'CORRIENTE'),
    1000.00,
    1000.00,
    (SELECT id FROM status WHERE codigo = 'ACTIVO'),
    '550e8400-e29b-41d4-a716-446655440001'
);

-- =====================================================
-- FUNCIONES Y TRIGGERS
-- =====================================================

-- Función para actualizar updated_at automáticamente
CREATE OR REPLACE FUNCTION update_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Triggers para actualizar updated_at
CREATE TRIGGER trigger_update_person
    BEFORE UPDATE ON person
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at();

CREATE TRIGGER trigger_update_account
    BEFORE UPDATE ON account
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at();

-- =====================================================
-- VISTAS ÚTILES
-- =====================================================

-- Vista: Clientes con información completa
CREATE OR REPLACE VIEW v_clients_completo AS
SELECT 
    c.client_id,
    p.nombre,
    g.descripcion as gender,
    p.edad,
    p.identificacion,
    p.direccion,
    p.telefono,
    e.descripcion as status,
    p.created_at
FROM client c
INNER JOIN person p ON c.person_id = p.id
INNER JOIN gender g ON p.gender_id = g.id
INNER JOIN status e ON c.status_id = e.id;

-- Vista: Cuentas con información completa
CREATE OR REPLACE VIEW v_accounts_completo AS
SELECT 
    cu.account_number,
    tc.descripcion as account_type,
    cu.initial_balance,
    cu.current_balance,
    e.descripcion as status,
    cu.client_id,
    cl.nombre as client_nombre,
    cu.created_at
FROM account cu
INNER JOIN account_type tc ON cu.account_type_id = tc.id
INNER JOIN status e ON cu.status_id = e.id
LEFT JOIN v_clients_completo cl ON cu.client_id = cl.client_id;

-- =====================================================
-- PERMISOS (Opcional - ajustar según necesidad)
-- =====================================================

-- Crear usuario para la aplicación (opcional)
-- CREATE USER nttdata_app WITH PASSWORD 'nttdata_pass_2024';
-- GRANT ALL PRIVILEGES ON DATABASE nttdata_bank TO nttdata_app;
-- GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO nttdata_app;
-- GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO nttdata_app;

-- =====================================================
-- VERIFICACIÓN
-- =====================================================

-- Verificar datos insertados
SELECT 'Géneros:', COUNT(*) FROM gender;
SELECT 'Tipos de Cuenta:', COUNT(*) FROM account_type;
SELECT 'Estados:', COUNT(*) FROM status;
SELECT 'Personas:', COUNT(*) FROM person;
SELECT 'Clientes:', COUNT(*) FROM client;
SELECT 'Cuentas:', COUNT(*) FROM account;
SELECT 'Movimientos:', COUNT(*) FROM transaction;

-- Mostrar clients completos
SELECT * FROM v_clients_completo ORDER BY nombre;

-- Mostrar accounts completas
SELECT * FROM v_accounts_completo ORDER BY account_number;

-- =====================================================
-- FIN DEL SCRIPT
-- =====================================================
