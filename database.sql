DROP DATABASE IF EXISTS happy_feet_veterinaria;

CREATE DATABASE happy_feet_veterinaria;

USE happy_feet_veterinaria;


CREATE TABLE especies (

  id INT AUTO_INCREMENT PRIMARY KEY,

  nombre VARCHAR(100) UNIQUE NOT NULL,

  descripcion TEXT,

  INDEX idx_nombre (nombre)

);


CREATE TABLE razas (

  id INT AUTO_INCREMENT PRIMARY KEY,

  especie_id INT NOT NULL,

  nombre VARCHAR(100) NOT NULL,

  caracteristicas TEXT,

  FOREIGN KEY (especie_id) REFERENCES especies(id) ON DELETE RESTRICT,

  UNIQUE KEY uk_raza_especie (especie_id, nombre),

  INDEX idx_especie (especie_id)

);


CREATE TABLE producto_tipos (

  id INT AUTO_INCREMENT PRIMARY KEY,

  nombre VARCHAR(100) UNIQUE NOT NULL,

  descripcion TEXT,

  INDEX idx_nombre (nombre)

);


CREATE TABLE evento_tipos (

  id INT AUTO_INCREMENT PRIMARY KEY,

  nombre VARCHAR(100) UNIQUE NOT NULL,

  descripcion TEXT,

  INDEX idx_nombre (nombre)

);


CREATE TABLE cita_estados (

  id INT AUTO_INCREMENT PRIMARY KEY,

  nombre VARCHAR(50) UNIQUE NOT NULL,

  descripcion TEXT,

  INDEX idx_nombre (nombre)

);



-- =========== MÓDULO 1: GESTIÓN DE PACIENTES ===========


CREATE TABLE duenos (

  id INT AUTO_INCREMENT PRIMARY KEY,

  nombre_completo VARCHAR(255) NOT NULL,

  documento_identidad VARCHAR(20) UNIQUE NOT NULL,

  direccion VARCHAR(255),

  telefono VARCHAR(20),

  email VARCHAR(100) UNIQUE NOT NULL,

  contacto_emergencia VARCHAR(255),

  fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,

  activo BOOLEAN DEFAULT TRUE,

  INDEX idx_documento (documento_identidad),

  INDEX idx_email (email),

  INDEX idx_activo (activo)

);


CREATE TABLE mascotas (

  id INT AUTO_INCREMENT PRIMARY KEY,

  dueno_id INT NOT NULL,

  nombre VARCHAR(100) NOT NULL,

  raza_id INT NOT NULL,

  fecha_nacimiento DATE,

  sexo ENUM('Macho', 'Hembra') NOT NULL,

  peso_actual DECIMAL(5,2),

  microchip VARCHAR(50) UNIQUE,

  tatuaje VARCHAR(50),

  url_foto VARCHAR(255),

  alergias TEXT,

  condiciones_preexistentes TEXT,

  fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,

  activo BOOLEAN DEFAULT TRUE,

  FOREIGN KEY (dueno_id) REFERENCES duenos(id) ON DELETE RESTRICT,

  FOREIGN KEY (raza_id) REFERENCES razas(id) ON DELETE RESTRICT,

  INDEX idx_dueno (dueno_id),

  INDEX idx_nombre (nombre),

  INDEX idx_microchip (microchip),

  INDEX idx_activo (activo)

);



-- =========== MÓDULO 2: SERVICIOS MÉDICOS Y PERSONAL ===========


CREATE TABLE veterinarios (

  id INT AUTO_INCREMENT PRIMARY KEY,

  nombre_completo VARCHAR(255) NOT NULL,

  documento_identidad VARCHAR(20) UNIQUE NOT NULL,

  licencia_profesional VARCHAR(50) UNIQUE NOT NULL,

  especialidad VARCHAR(100),

  telefono VARCHAR(20),

  email VARCHAR(100) UNIQUE,

  fecha_contratacion DATE,

  activo BOOLEAN DEFAULT TRUE,

  INDEX idx_documento (documento_identidad),

  INDEX idx_licencia (licencia_profesional),

  INDEX idx_activo (activo)

);


CREATE TABLE citas (

  id INT AUTO_INCREMENT PRIMARY KEY,

  mascota_id INT NOT NULL,

  veterinario_id INT,

  fecha_hora DATETIME NOT NULL,

  motivo VARCHAR(500),

  estado_id INT NOT NULL,

  observaciones TEXT,

  fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,

  FOREIGN KEY (mascota_id) REFERENCES mascotas(id) ON DELETE CASCADE,

  FOREIGN KEY (veterinario_id) REFERENCES veterinarios(id) ON DELETE SET NULL,

  FOREIGN KEY (estado_id) REFERENCES cita_estados(id) ON DELETE RESTRICT,

  INDEX idx_fecha (fecha_hora),

  INDEX idx_mascota (mascota_id),

  INDEX idx_veterinario (veterinario_id),

  INDEX idx_estado (estado_id)

);


CREATE TABLE consultas_medicas (

  id INT AUTO_INCREMENT PRIMARY KEY,

  mascota_id INT NOT NULL,

  veterinario_id INT NOT NULL,

  cita_id INT,

  fecha_hora DATETIME NOT NULL,

  motivo VARCHAR(500) NOT NULL,

  sintomas TEXT,

  diagnostico TEXT,

  recomendaciones TEXT,

  observaciones TEXT,

  peso_registrado DECIMAL(5,2),

  temperatura DECIMAL(4,2),

  FOREIGN KEY (mascota_id) REFERENCES mascotas(id) ON DELETE CASCADE,

  FOREIGN KEY (veterinario_id) REFERENCES veterinarios(id) ON DELETE RESTRICT,

  FOREIGN KEY (cita_id) REFERENCES citas(id) ON DELETE SET NULL,

  INDEX idx_mascota (mascota_id),

  INDEX idx_veterinario (veterinario_id),

  INDEX idx_fecha (fecha_hora)

);


CREATE TABLE procedimientos_especiales (

  id INT AUTO_INCREMENT PRIMARY KEY,

  mascota_id INT NOT NULL,

  veterinario_id INT NOT NULL,

  tipo_procedimiento VARCHAR(100) NOT NULL,

  nombre_procedimiento VARCHAR(255) NOT NULL,

  fecha_hora DATETIME NOT NULL,

  duracion_estimada_minutos INT,

  informacion_preoperatoria TEXT,

  detalle_procedimiento TEXT NOT NULL,

  complicaciones TEXT,

  seguimiento_postoperatorio TEXT,

  proximo_control DATE,

  estado ENUM('Programado', 'En Proceso', 'Finalizado', 'Cancelado') NOT NULL DEFAULT 'Programado',

  costo_procedimiento DECIMAL(10, 2),

  FOREIGN KEY (mascota_id) REFERENCES mascotas(id) ON DELETE CASCADE,

  FOREIGN KEY (veterinario_id) REFERENCES veterinarios(id) ON DELETE RESTRICT,

  INDEX idx_mascota (mascota_id),

  INDEX idx_veterinario (veterinario_id),

  INDEX idx_fecha (fecha_hora),

  INDEX idx_estado (estado)

);


CREATE TABLE historial_medico (

  id INT AUTO_INCREMENT PRIMARY KEY,

  mascota_id INT NOT NULL,

  fecha_evento DATE NOT NULL,

  evento_tipo_id INT NOT NULL,

  descripcion TEXT NOT NULL,

  diagnostico TEXT,

  tratamiento_recomendado TEXT,

  veterinario_id INT,

  consulta_id INT,

  procedimiento_id INT,

  FOREIGN KEY (mascota_id) REFERENCES mascotas(id) ON DELETE CASCADE,

  FOREIGN KEY (evento_tipo_id) REFERENCES evento_tipos(id) ON DELETE RESTRICT,

  FOREIGN KEY (veterinario_id) REFERENCES veterinarios(id) ON DELETE SET NULL,

  FOREIGN KEY (consulta_id) REFERENCES consultas_medicas(id) ON DELETE SET NULL,

  FOREIGN KEY (procedimiento_id) REFERENCES procedimientos_especiales(id) ON DELETE SET NULL,

  INDEX idx_mascota (mascota_id),

  INDEX idx_fecha (fecha_evento),

  INDEX idx_tipo (evento_tipo_id)

);



-- =========== MÓDULO 3: INVENTARIO Y FARMACIA ===========


CREATE TABLE proveedores (

  id INT AUTO_INCREMENT PRIMARY KEY,

  nombre_empresa VARCHAR(255) NOT NULL,

  contacto VARCHAR(255),

  telefono VARCHAR(20),

  email VARCHAR(100),

  direccion VARCHAR(255),

  sitio_web VARCHAR(255),

  activo BOOLEAN DEFAULT TRUE,

  fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,

  INDEX idx_nombre (nombre_empresa),

  INDEX idx_activo (activo)

);


CREATE TABLE inventario (

  id INT AUTO_INCREMENT PRIMARY KEY,

  nombre_producto VARCHAR(255) NOT NULL,

  producto_tipo_id INT NOT NULL,

  descripcion TEXT,

  fabricante VARCHAR(100),

  proveedor_id INT,

  lote VARCHAR(50),

  cantidad_stock INT NOT NULL DEFAULT 0,

  stock_minimo INT NOT NULL DEFAULT 0,

  unidad_medida VARCHAR(50) DEFAULT 'unidad',

  fecha_vencimiento DATE,

  precio_compra DECIMAL(10, 2),

  precio_venta DECIMAL(10, 2) NOT NULL,

  requiere_receta BOOLEAN DEFAULT FALSE,

  activo BOOLEAN DEFAULT TRUE,

  fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,

  FOREIGN KEY (producto_tipo_id) REFERENCES producto_tipos(id) ON DELETE RESTRICT,

  FOREIGN KEY (proveedor_id) REFERENCES proveedores(id) ON DELETE SET NULL,

  INDEX idx_nombre (nombre_producto),

  INDEX idx_tipo (producto_tipo_id),

  INDEX idx_stock (cantidad_stock),

  INDEX idx_vencimiento (fecha_vencimiento),

  INDEX idx_proveedor (proveedor_id),

  INDEX idx_activo (activo),

  CONSTRAINT chk_stock_positivo CHECK (cantidad_stock >= 0),

  CONSTRAINT chk_stock_minimo CHECK (stock_minimo >= 0)

);


CREATE TABLE prescripciones (

  id INT AUTO_INCREMENT PRIMARY KEY,

  consulta_id INT,

  procedimiento_id INT,

  producto_id INT NOT NULL,

  cantidad INT NOT NULL,

  dosis VARCHAR(100),

  frecuencia VARCHAR(100),

  duracion_dias INT,

  instrucciones TEXT,

  fecha_prescripcion DATETIME DEFAULT CURRENT_TIMESTAMP,

  FOREIGN KEY (consulta_id) REFERENCES consultas_medicas(id) ON DELETE CASCADE,

  FOREIGN KEY (procedimiento_id) REFERENCES procedimientos_especiales(id) ON DELETE CASCADE,

  FOREIGN KEY (producto_id) REFERENCES inventario(id) ON DELETE RESTRICT,

  INDEX idx_consulta (consulta_id),

  INDEX idx_procedimiento (procedimiento_id),

  INDEX idx_producto (producto_id),

  INDEX idx_fecha (fecha_prescripcion),

  CONSTRAINT chk_cantidad_positiva CHECK (cantidad > 0),

  CONSTRAINT chk_tiene_referencia CHECK (consulta_id IS NOT NULL OR procedimiento_id IS NOT NULL)

);


CREATE TABLE insumos_procedimientos (

  id INT AUTO_INCREMENT PRIMARY KEY,

  procedimiento_id INT NOT NULL,

  producto_id INT NOT NULL,

  cantidad_usada INT NOT NULL,

  observaciones VARCHAR(255),

  FOREIGN KEY (procedimiento_id) REFERENCES procedimientos_especiales(id) ON DELETE CASCADE,

  FOREIGN KEY (producto_id) REFERENCES inventario(id) ON DELETE RESTRICT,

  INDEX idx_procedimiento (procedimiento_id),

  INDEX idx_producto (producto_id),

  CONSTRAINT chk_cantidad_usada_positiva CHECK (cantidad_usada > 0)

);


CREATE TABLE movimientos_inventario (

  id INT AUTO_INCREMENT PRIMARY KEY,

  producto_id INT NOT NULL,

  tipo_movimiento ENUM('Entrada', 'Salida', 'Ajuste', 'Vencimiento') NOT NULL,

  cantidad INT NOT NULL,

  stock_anterior INT NOT NULL,

  stock_nuevo INT NOT NULL,

  motivo VARCHAR(255),

  referencia_consulta_id INT,

  referencia_procedimiento_id INT,

  usuario VARCHAR(100),

  fecha_movimiento DATETIME DEFAULT CURRENT_TIMESTAMP,

  FOREIGN KEY (producto_id) REFERENCES inventario(id) ON DELETE CASCADE,

  FOREIGN KEY (referencia_consulta_id) REFERENCES consultas_medicas(id) ON DELETE SET NULL,

  FOREIGN KEY (referencia_procedimiento_id) REFERENCES procedimientos_especiales(id) ON DELETE SET NULL,

  INDEX idx_producto (producto_id),

  INDEX idx_fecha (fecha_movimiento),

  INDEX idx_tipo (tipo_movimiento)

);



-- =========== MÓDULO 4: FACTURACIÓN Y REPORTES ===========


CREATE TABLE servicios (

  id INT AUTO_INCREMENT PRIMARY KEY,

  nombre VARCHAR(255) NOT NULL,

  descripcion TEXT,

  categoria VARCHAR(100),

  precio_base DECIMAL(10, 2) NOT NULL,

  duracion_estimada_minutos INT,

  activo BOOLEAN DEFAULT TRUE,

  INDEX idx_nombre (nombre),

  INDEX idx_categoria (categoria),

  INDEX idx_activo (activo),

  CONSTRAINT chk_precio_positivo CHECK (precio_base >= 0)

);


CREATE TABLE facturas (

  id INT AUTO_INCREMENT PRIMARY KEY,

  dueno_id INT NOT NULL,

  numero_factura VARCHAR(50) UNIQUE NOT NULL,

  fecha_emision DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

  subtotal DECIMAL(10, 2) NOT NULL,

  impuesto DECIMAL(10, 2) NOT NULL DEFAULT 0,

  descuento DECIMAL(10, 2) NOT NULL DEFAULT 0,

  total DECIMAL(10, 2) NOT NULL,

  metodo_pago ENUM('Efectivo', 'Tarjeta', 'Transferencia', 'Mixto'),

  estado ENUM('Pendiente', 'Pagada', 'Anulada') DEFAULT 'Pendiente',

  observaciones TEXT,

  FOREIGN KEY (dueno_id) REFERENCES duenos(id) ON DELETE RESTRICT,

  INDEX idx_dueno (dueno_id),

  INDEX idx_fecha (fecha_emision),

  INDEX idx_numero (numero_factura),

  INDEX idx_estado (estado),

  CONSTRAINT chk_subtotal_positivo CHECK (subtotal >= 0),

  CONSTRAINT chk_total_positivo CHECK (total >= 0)

);


CREATE TABLE items_factura (

  id INT AUTO_INCREMENT PRIMARY KEY,

  factura_id INT NOT NULL,

  tipo_item ENUM('Producto', 'Servicio') NOT NULL,

  producto_id INT,

  servicio_id INT,

  servicio_descripcion VARCHAR(255),

  cantidad INT NOT NULL,

  precio_unitario DECIMAL(10, 2) NOT NULL,

  subtotal DECIMAL(10, 2) NOT NULL,

  FOREIGN KEY (factura_id) REFERENCES facturas(id) ON DELETE CASCADE,

  FOREIGN KEY (producto_id) REFERENCES inventario(id) ON DELETE RESTRICT,

  FOREIGN KEY (servicio_id) REFERENCES servicios(id) ON DELETE RESTRICT,

  INDEX idx_factura (factura_id),

  INDEX idx_producto (producto_id),

  INDEX idx_servicio (servicio_id),

  CONSTRAINT chk_cantidad_item_positiva CHECK (cantidad > 0),

  CONSTRAINT chk_precio_unitario_positivo CHECK (precio_unitario >= 0),

  CONSTRAINT chk_tipo_item_valido CHECK (

    (tipo_item = 'Producto' AND producto_id IS NOT NULL AND servicio_id IS NULL) OR

    (tipo_item = 'Servicio' AND servicio_id IS NOT NULL AND producto_id IS NULL)

  )

);



-- =========== MÓDULO 5: ACTIVIDADES ESPECIALES ===========


-- --------- Días de Adopción ---------

CREATE TABLE mascotas_adopcion (

  id INT AUTO_INCREMENT PRIMARY KEY,

  mascota_id INT NOT NULL,

  fecha_ingreso DATE NOT NULL,

  motivo_ingreso TEXT,

  estado ENUM('Disponible', 'En Proceso', 'Adoptada', 'Retirada') NOT NULL DEFAULT 'Disponible',

  historia TEXT,

  temperamento VARCHAR(255),

  necesidades_especiales TEXT,

  foto_adicional_url VARCHAR(255),

  FOREIGN KEY (mascota_id) REFERENCES mascotas(id) ON DELETE CASCADE,

  INDEX idx_mascota (mascota_id),

  INDEX idx_estado (estado),

  INDEX idx_fecha_ingreso (fecha_ingreso)

);


CREATE TABLE adopciones (

  id INT AUTO_INCREMENT PRIMARY KEY,

  mascota_adopcion_id INT NOT NULL,

  dueno_id INT NOT NULL,

  fecha_adopcion DATE NOT NULL,

  contrato_texto TEXT,

  condiciones_especiales TEXT,

  seguimiento_requerido BOOLEAN DEFAULT TRUE,

  fecha_primer_seguimiento DATE,

  FOREIGN KEY (mascota_adopcion_id) REFERENCES mascotas_adopcion(id) ON DELETE RESTRICT,

  FOREIGN KEY (dueno_id) REFERENCES duenos(id) ON DELETE RESTRICT,

  INDEX idx_mascota_adopcion (mascota_adopcion_id),

  INDEX idx_dueno (dueno_id),

  INDEX idx_fecha (fecha_adopcion)

);


-- --------- Jornadas de Vacunación ---------

CREATE TABLE jornadas_vacunacion (

  id INT AUTO_INCREMENT PRIMARY KEY,

  nombre VARCHAR(255) NOT NULL,

  fecha DATE NOT NULL,

  hora_inicio TIME,

  hora_fin TIME,

  ubicacion VARCHAR(255),

  descripcion TEXT,

  capacidad_maxima INT,

  estado ENUM('Planificada', 'En Curso', 'Finalizada', 'Cancelada') DEFAULT 'Planificada',

  INDEX idx_fecha (fecha),

  INDEX idx_estado (estado)

);


CREATE TABLE registro_jornada_vacunacion (

  id INT AUTO_INCREMENT PRIMARY KEY,

  jornada_id INT NOT NULL,

  mascota_id INT NOT NULL,

  dueno_id INT NOT NULL,

  vacuna_id INT NOT NULL,

  veterinario_id INT,

  fecha_hora DATETIME NOT NULL,

  lote_vacuna VARCHAR(50),

  proxima_dosis DATE,

  observaciones TEXT,

  FOREIGN KEY (jornada_id) REFERENCES jornadas_vacunacion(id) ON DELETE CASCADE,

  FOREIGN KEY (mascota_id) REFERENCES mascotas(id) ON DELETE CASCADE,

  FOREIGN KEY (dueno_id) REFERENCES duenos(id) ON DELETE CASCADE,

  FOREIGN KEY (vacuna_id) REFERENCES inventario(id) ON DELETE RESTRICT,

  FOREIGN KEY (veterinario_id) REFERENCES veterinarios(id) ON DELETE SET NULL,

  INDEX idx_jornada (jornada_id),

  INDEX idx_mascota (mascota_id),

  INDEX idx_dueno (dueno_id),

  INDEX idx_fecha (fecha_hora)

);


-- --------- Club de Mascotas Frecuentes ---------

CREATE TABLE club_mascotas (

  id INT AUTO_INCREMENT PRIMARY KEY,

  dueno_id INT NOT NULL UNIQUE,

  puntos_acumulados INT DEFAULT 0,

  puntos_canjeados INT DEFAULT 0,

  puntos_disponibles INT DEFAULT 0,

  nivel VARCHAR(50) DEFAULT 'Bronce',

  fecha_inscripcion DATE NOT NULL,

  fecha_ultima_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  activo BOOLEAN DEFAULT TRUE,

  FOREIGN KEY (dueno_id) REFERENCES duenos(id) ON DELETE CASCADE,

  INDEX idx_dueno (dueno_id),

  INDEX idx_nivel (nivel),

  INDEX idx_puntos (puntos_disponibles),

  INDEX idx_activo (activo),

  CONSTRAINT chk_puntos_no_negativos CHECK (puntos_acumulados >= 0 AND puntos_canjeados >= 0 AND puntos_disponibles >= 0)

);


CREATE TABLE transacciones_puntos (

  id INT AUTO_INCREMENT PRIMARY KEY,

  club_mascotas_id INT NOT NULL,

  factura_id INT,

  puntos INT NOT NULL,

  tipo ENUM('Ganados', 'Canjeados', 'Expirados', 'Ajuste') NOT NULL,

  fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

  descripcion VARCHAR(255),

  saldo_anterior INT NOT NULL,

  saldo_nuevo INT NOT NULL,

  FOREIGN KEY (club_mascotas_id) REFERENCES club_mascotas(id) ON DELETE CASCADE,

  FOREIGN KEY (factura_id) REFERENCES facturas(id) ON DELETE SET NULL,

  INDEX idx_club (club_mascotas_id),

  INDEX idx_factura (factura_id),

  INDEX idx_fecha (fecha),

  INDEX idx_tipo (tipo)

);


CREATE TABLE beneficios_club (

  id INT AUTO_INCREMENT PRIMARY KEY,

  nombre VARCHAR(255) NOT NULL,

  descripcion TEXT,

  nivel_requerido VARCHAR(50) NOT NULL,

  puntos_necesarios INT NOT NULL,

  tipo_beneficio ENUM('Descuento', 'Servicio Gratis', 'Producto Gratis', 'Puntos Extra') NOT NULL,

  valor_beneficio DECIMAL(10, 2),

  activo BOOLEAN DEFAULT TRUE,

  INDEX idx_nivel (nivel_requerido),

  INDEX idx_puntos (puntos_necesarios),

  INDEX idx_activo (activo),

  CONSTRAINT chk_puntos_beneficio_positivo CHECK (puntos_necesarios > 0)

);


CREATE TABLE canjes_beneficios (

  id INT AUTO_INCREMENT PRIMARY KEY,

  club_mascotas_id INT NOT NULL,

  beneficio_id INT NOT NULL,

  fecha_canje DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

  puntos_canjeados INT NOT NULL,

  estado ENUM('Pendiente', 'Aplicado', 'Expirado') DEFAULT 'Pendiente',

  fecha_expiracion DATE,

  factura_id INT,

  FOREIGN KEY (club_mascotas_id) REFERENCES club_mascotas(id) ON DELETE CASCADE,

  FOREIGN KEY (beneficio_id) REFERENCES beneficios_club(id) ON DELETE RESTRICT,

  FOREIGN KEY (factura_id) REFERENCES facturas(id) ON DELETE SET NULL,

  INDEX idx_club (club_mascotas_id),

  INDEX idx_beneficio (beneficio_id),

  INDEX idx_fecha (fecha_canje),

  INDEX idx_estado (estado)

);



-- =========== DATOS INICIALES (SOLO LOOKUP TABLES) ===========

-- Estos son datos básicos necesarios para que el sistema funcione.

-- El estudiante debe poblar las demás tablas con datos de prueba.


-- Especies

INSERT INTO especies (nombre, descripcion) VALUES

('Perro', 'Canis lupus familiaris'),

('Gato', 'Felis catus'),

('Ave', 'Aves domésticas'),

('Roedor', 'Pequeños mamíferos roedores'),

('Reptil', 'Reptiles domésticos'),

('Conejo', 'Oryctolagus cuniculus');


-- Razas (ejemplos básicos)

INSERT INTO razas (especie_id, nombre, caracteristicas) VALUES

(1, 'Labrador Retriever', 'Tamaño grande, amigable, energético'),

(1, 'Golden Retriever', 'Tamaño grande, inteligente, leal'),

(1, 'Pastor Alemán', 'Tamaño grande, inteligente, protector'),

(1, 'Bulldog Francés', 'Tamaño pequeño, cariñoso, juguetón'),

(1, 'Chihuahua', 'Tamaño muy pequeño, alerta, valiente'),

(1, 'Poodle', 'Inteligente, hipoalergénico'),

(1, 'Mestizo', 'Cruza de razas'),

(2, 'Persa', 'Pelo largo, tranquilo'),

(2, 'Siamés', 'Vocal, social, activo'),

(2, 'Maine Coon', 'Grande, peludo, amigable'),

(2, 'Bengalí', 'Activo, manchas de leopardo'),

(2, 'Mestizo', 'Cruza de razas'),

(3, 'Canario', 'Pequeño, cantante'),

(3, 'Periquito', 'Pequeño, social'),

(3, 'Loro', 'Inteligente, longevo'),

(4, 'Hámster Sirio', 'Pequeño, nocturno'),

(4, 'Cobayo', 'Mediano, social'),

(5, 'Iguana Verde', 'Herbívoro, grande'),

(5, 'Tortuga', 'Lenta, longeva'),

(6, 'Conejo Enano', 'Pequeño, dócil'),

(6, 'Conejo Gigante', 'Grande, tranquilo');


-- Tipos de productos

INSERT INTO producto_tipos (nombre, descripcion) VALUES

('Medicamento', 'Fármacos y medicinas veterinarias'),

('Vacuna', 'Vacunas y biológicos'),

('Insumo Médico', 'Material médico y quirúrgico'),

('Alimento', 'Alimento para mascotas'),

('Accesorio', 'Accesorios y productos de cuidado');


-- Tipos de eventos médicos

INSERT INTO evento_tipos (nombre, descripcion) VALUES

('Vacunación', 'Aplicación de vacunas'),

('Consulta General', 'Consulta veterinaria general'),

('Cirugía', 'Procedimiento quirúrgico'),

('Desparasitación', 'Tratamiento antiparasitario'),

('Control de Peso', 'Seguimiento de peso'),

('Examen de Sangre', 'Análisis de laboratorio'),

('Radiografía', 'Estudio de imagen'),

('Emergencia', 'Atención de emergencia'),

('Limpieza Dental', 'Profilaxis dental');


-- Estados de citas

INSERT INTO cita_estados (nombre, descripcion) VALUES

('Programada', 'Cita agendada pendiente'),

('Confirmada', 'Cita confirmada por el cliente'),

('En Proceso', 'Mascota siendo atendida'),

('Finalizada', 'Cita completada'),

('Cancelada', 'Cita cancelada'),

('No Asistió', 'Cliente no se presentó');


-- Trigger 

CREATE TRIGGER tr_restar_stock_prescripcion
AFTER INSERT ON prescripciones
FOR EACH ROW
BEGIN
    -- Actualiza el stock del producto usado en la prescripción
    UPDATE inventario
    SET cantidad_stock = cantidad_stock - NEW.cantidad
    WHERE id = NEW.producto_id;

     registrar el movimiento en la tabla de movimientos del inventario
    INSERT INTO movimientos_inventario (
        producto_id,
        tipo_movimiento,
        cantidad,
        stock_anterior,
        stock_nuevo,
        motivo,
        referencia_consulta_id
    )
    VALUES (
        NEW.producto_id,
        'Salida',
        NEW.cantidad,
        (SELECT cantidad_stock + NEW.cantidad FROM inventario WHERE id = NEW.producto_id),
        (SELECT cantidad_stock FROM inventario WHERE id = NEW.producto_id),
        'Prescripción médica',
        NEW.consulta_id
    );
END


-- insercion de datos

INSERT INTO duenos (nombre_completo, documento_identidad, direccion, telefono, email, contacto_emergencia, activo) VALUES
('Juan Pérez', '1011123456', 'Calle Luna 123', '3101234567', 'juan.perez@email.com', 'María Pérez (3207654321)', TRUE),
('Ana López', '1011234567', 'Av. Sol 456', '3112345678', 'ana.lopez@email.com', 'Carlos López (3001122334)', TRUE),
('Pedro Ramírez', '1011345678', 'Calle Estrella ', '3123456789', 'pedro.ramirez@email.com', 'Laura Ramírez (3159876543)', TRUE),
('Sofía Torres', '1011456789', 'Transversal Planeta 101', '3134567890', 'sofia.torres@email.com', 'Javier Torres (3045678901)', TRUE),
('Manuel Castro', '1011567890', 'Diagonal Cometa 202', '3145678901', 'manuel.castro@email.com', 'Elena Castro (3012345678)', TRUE),
('Valeria Gómez', '1011678901', 'la pastora', '3156789012', 'valeria.gomez@email.com', 'Ricardo Gómez (3058765432)', TRUE),
('Andrés Múñoz', '1011789012', 'Calle 534', '3167890123', 'andres.munoz@email.com', 'Patricia Múñoz (3023456789)', TRUE),
('Paula Herrera', '1011890123', 'Carrera federañ', '3178901234', 'paula.herrera@email.com', 'Felipe Herrera (3069876543)', TRUE),
('Ricardo Vargas', '1011901234', ' Vía Láctea ', '3189012345', 'ricardo.vargas@email.com', 'Susana Vargas (3034567890)', TRUE),
('Elena Soto', '1011012345', 'Diagonal donpancho', '3190123456', 'elena.soto@email.com', 'Oscar Soto (3078765432)', TRUE);

INSERT INTO veterinarios (nombre_completo, documento_identidad, licencia_profesional, especialidad, telefono, email, fecha_contratacion, activo) VALUES
('Dr. Mario Rodríguez', '2011123456', 'LIC-VET-001', 'Cirugía General', '3201112233', 'mario.rodriguez@vet.com', '2018-01-15', TRUE),
('Dra. Laura Díaz', '2011234567', 'LIC-VET-002', 'Medicina Interna', '3212223344', 'laura.diaz@vet.com', '2019-05-20', TRUE),
('Dr. Camilo Rivas', '2011345678', 'LIC-VET-003', 'Dermatología', '3223334455', 'camilo.rivas@vet.com', '2020-09-10', TRUE),
('Dra. Natalia Pérez', '2011456789', 'LIC-VET-004', 'Odontología', '3234445566', 'natalia.perez@vet.com', '2021-03-01', TRUE),
('Dr. Jorge López', '2011567890', 'LIC-VET-005', 'Cardiología', '3245556677', 'jorge.lopez@vet.com', '2022-11-25', TRUE),
('Dra. Carolina Vargas', '2011678901', 'LIC-VET-006', 'Oftalmología', '3256667788', 'carolina.vargas@vet.com', '2023-07-07', TRUE),
('Dr. Roberto Gómez', '2011789012', 'LIC-VET-007', 'Diagnóstico por Imagen', '3267778899', 'roberto.gomez@vet.com', '2017-02-14', TRUE),
('Dra. Silvia Castro', '2011890123', 'LIC-VET-008', 'Fisioterapia', '3278889900', 'silvia.castro@vet.com', '2024-01-05', TRUE),
('Dr. Daniel Torres', '2011901234', 'LIC-VET-009', 'Emergencias', '3289990011', 'daniel.torres@vet.com', '2025-8-30', TRUE),
('Dra. Elisa Soto', '2011012345', 'LIC-VET-010', 'Nutrición', '3290001122', 'elisa.soto@vet.com', '2023-04-18', TRUE);

INSERT INTO proveedores (nombre_empresa, contacto, telefono, email, direccion, sitio_web, activo) VALUES
('FarmacoVet S.A.', 'Alejandro Giraldo', '6011112233', 'contacto@farmacovet.com', 'Calle 10 # 5-20', 'www.farmacovet.com', TRUE),
('Insumos Pet Ltda.', 'Marta Lucía Díaz', '6012223344', 'ventas@insumospet.com', 'Av. 20 # 10-30', 'www.insumospet.com', TRUE),
('Medicamentos Animales SAS', 'Rodrigo Pérez', '6013334455', 'pedidos@medicanimal.com', 'Carrera 5 # 8-15', 'www.medicanimal.com', TRUE),
('Distribuidora Quirúrgica', 'Sandra Restrepo', '6014445566', 'info@disquirurgica.com', 'Transversal 1 # 3-50', 'www.disquirurgica.com', TRUE),
('Pet Supplies Global', 'Michael Johnson', '6015556677', 'global@petsupplies.com', 'Diagonal 9 # 12-25', 'www.petsupplies.com', TRUE),
('Vacunas & Biológicos', 'Jenny Acosta', '6016667788', 'vacunas@bio.com', 'Avenida 45 # 10-10', 'www.vacunasbio.com', TRUE),
('Equipamiento Veterinario', 'Fabián Torres', '6017778899', 'equipo@veteq.com', 'Calle 2 # 2-22', 'www.veteq.com', TRUE),
('Nutrición Integral Canina', 'Diana Soto', '6018889900', 'diana.soto@nic.com', 'Carrera 7 # 15-30', 'www.nic.com', TRUE),
('Limpieza Profesional Pet', 'Héctor Gómez', '6019990011', 'limpieza@propet.com', 'Calle 30 # 3-33', 'www.propet.com', TRUE),
('Dermocosmética Animal', 'Laura Vélez', '6010001122', 'dermocos@animal.com', 'Av. Suba # 100-50', 'www.dermocosanimal.com', TRUE);

INSERT INTO servicios (nombre, descripcion, categoria, precio_base, duracion_estimada_minutos, activo) VALUES
('Consulta General', 'Revisión de rutina y chequeo general de salud.', 'Consulta', 45.00, 30, TRUE),
('Vacunación', 'Administración de vacuna (excluye costo de la vacuna).', 'Prevención', 20.00, 15, TRUE),
('Desparasitación', 'Administración de antiparasitario (excluye costo del producto).', 'Prevención', 15.00, 15, TRUE),
('Baño y Peluquería', 'Servicio completo de estética y aseo.', 'Estética', 35.00, 90, TRUE),
('Cirugía Menor', 'Procedimientos quirúrgicos ambulatorios (suturas, remoción de quistes).', 'Cirugía', 150.00, 60, TRUE),
('Análisis de Sangre Completo', 'Toma de muestra y análisis de laboratorio.', 'Diagnóstico', 60.00, 15, TRUE),
('Ecocardiograma', 'Estudio de ultrasonido del corazón.', 'Cardiología', 90.00, 45, TRUE),
('Limpieza Dental', 'Procedimiento de higiene oral bajo sedación.', 'Odontología', 120.00, 90, TRUE),
('Hospitalización Diaria', 'Cuidado y monitoreo por un día (24h).', 'Cuidados Intensivos', 70.00, 1440, TRUE),
('Radiografía', 'Toma e interpretación de una radiografía.', 'Diagnóstico', 40.00, 20, TRUE);

INSERT INTO jornadas_vacunacion (nombre, fecha, hora_inicio, hora_fin, ubicacion, descripcion, capacidad_maxima, estado) VALUES
('Jornada Canina Rabia', '2025-06-15', '09:00:00', '12:00:00', 'Parque Central', 'Campaña gratuita de vacunación antirrábica para perros.', 100, 'Planificada'),
('Jornada Felina Anual', '2025-07-20', '10:00:00', '13:00:00', 'Clínica Veterinaria Principal', 'Vacunación triple felina a bajo costo.', 50, 'Planificada'),
('Jornada Desparasitación', '2025-05-10', '14:00:00', '17:00:00', 'Centro Comunal', 'Jornada de desparasitación interna y externa.', 80, 'Finalizada'),
('Jornada Canina Refuerzo', '2025-08-05', '08:30:00', '11:30:00', 'Parque de la Paz', 'Refuerzo de parvovirus y moquillo.', 90, 'Planificada'),
('Jornada Mascota Nueva', '2025-04-01', '10:00:00', '12:00:00', 'Clínica Sede Norte', 'Primeras vacunas para cachorros y gatitos.', 40, 'Finalizada'),
('Día de Microchip', '2025-09-01', '09:00:00', '14:00:00', 'Clínica Veterinaria Principal', 'Implantación de microchip a tarifa reducida.', 60, 'Planificada'),
('Rabia Extraclínica', '2025-10-12', '10:00:00', '12:00:00', 'Barrio Las Flores', 'Vacunación antirrábica en zona rural.', 70, 'Planificada'),
('Control de Garrapatas', '2025-03-05', '15:00:00', '18:00:00', 'Tienda Pet Shop', 'Aplicación de tratamientos antipulgas/garrapatas.', 55, 'Finalizada'),
('Vacunación Aves y Exóticos', '2025-11-03', '11:00:00', '13:00:00', 'Clínica Sede Centro', 'Vacunas y chequeo para mascotas no convencionales.', 30, 'Planificada'),
('Revisión Post-Adopción', '2025-02-14', '09:00:00', '11:00:00', 'Refugio Animal', 'Chequeo médico para mascotas recién adoptadas.', 25, 'Finalizada');

INSERT INTO beneficios_club (nombre, descripcion, nivel_requerido, puntos_necesarios, tipo_beneficio, valor_beneficio, activo) VALUES
('Descuento 10% Consulta', '10% de descuento en la próxima Consulta General.', 'Bronce', 500, 'Descuento', 0.10, TRUE),
('Baño Gratis', 'Un servicio de Baño y Peluquería totalmente gratis.', 'Plata', 2000, 'Servicio Gratis', 35.00, TRUE),
('Producto de Farmacia $10', 'Cupón de $10.00 para usar en cualquier producto de farmacia.', 'Bronce', 1000, 'Descuento', 10.00, TRUE),
('Doble de Puntos', 'Gana el doble de puntos en tu próxima factura (máximo 500 puntos extra).', 'Oro', 5000, 'Puntos Extra', 500.00, TRUE),
('Vacuna Rabia Gratis', 'Vacuna de la Rabia sin costo (incluye aplicación).', 'Plata', 1500, 'Servicio Gratis', 40.00, TRUE),
('Descuento 20% Cirugía', '20% de descuento en cualquier Procedimiento Quirúrgico Especial.', 'Oro', 8000, 'Descuento', 0.20, TRUE),
('Muestra Comida Premium', 'Una bolsa de muestra de comida premium de 1kg.', 'Bronce', 300, 'Producto Gratis', 5.00, TRUE),
('check Anual Gratis', 'Consulta General y Análisis de Sangre completos sin costo.', 'Diamante', 15000, 'Servicio Gratis', 105.00, TRUE),
('Bono $50', 'Bono de $50.00 para usar en cualquier servicio o producto.', 'Oro', 6000, 'Descuento', 50.00, TRUE),
('Baño Gratis', 'Prioridad para agendar citas de emergencia.', 'Plata', 0, 'Servicio Gratis', 0.00, TRUE);

INSERT INTO mascotas (dueno_id, nombre, raza_id, fecha_nacimiento, sexo, peso_actual, microchip, alergias, condiciones_preexistentes, activo) VALUES
(1, 'Max', 1, '2025-05-10', 'Macho', 30.50, '0011', 'Polen', 'Ninguna', TRUE),
(2, 'Luna', 2, '2025-01-25', 'Hembra', 4.10, '002', 'Ninguna', 'Asma Felina', TRUE),
(3, 'Rocky', 3, '2023-11-03', 'Macho', 38.90, '0033', 'Carne de res', 'Displasia de cadera leve', TRUE),
(4, 'Miau', 4, '2025-08-12', 'Hembra', 3.50, '0044', 'Ninguna', 'Ninguna', TRUE),
(5, 'Toby', 5, '2025-03-01', 'Macho', 12.20, '0055', 'Pollo', 'Ninguna', TRUE),
(6, 'Spot', 6, '2015-07-19', 'Macho', 25.40, '0066', 'Ninguna', 'Sordera parcial', TRUE),
(7, 'Nala', 7, '2025-02-29', 'Hembra', 6.80, '0077', 'Ninguna', 'Obesidad', TRUE),
(8, 'Coco', 8, '2024-10-05', 'Hembra', 5.90, '0088', 'Ninguna', 'Ninguna', TRUE),
(9, 'Zeus', 9, '2025-04-17', 'Macho', 35.00, '0099', 'Pulgas', 'Problemas en piel', TRUE),
(10, 'Kira', 10, '2025-01-01', 'Hembra', 4.50, '0100', 'Ninguna', 'Ninguna', TRUE);

INSERT INTO inventario (nombre_producto, producto_tipo_id, descripcion, fabricante, proveedor_id, lote, cantidad_stock, stock_minimo, unidad_medida, fecha_vencimiento, precio_compra, precio_venta, requiere_receta, activo) VALUES
('Vacuna Antirrábica Canina', 1, 'Vacuna para la Rabia, dosis única.', 'VetPharma', 6, 'RAB202506', 50, 10, 'dosis', '2026-12-31', 5.00, 15.00, FALSE, TRUE),
('Amoxicilina 250mg', 2, 'Antibiótico de amplio espectro.', 'PharmaAnimal', 3, 'AMO202409', 100, 20, 'cápsula', '2026-11-30', 0.50, 2.50, TRUE, TRUE),
('Sutura Absorbible 2-0', 4, 'Sutura para procedimientos quirúrgicos.', 'SurgiVet', 4, 'SUT202601', 30, 5, 'unidad', '2026-06-30', 3.00, 8.00, FALSE, TRUE),
('Meloxicam 1mg/ml', 5, 'Analgésico y antiinflamatorio para perros.', 'MediPet', 3, 'MEL202503', 75, 15, 'ml', '2026-05-30', 2.00, 5.00, TRUE, TRUE),
('Comida Renal Canina', 6, 'Dieta especial para problemas renales.', 'NutriPet', 8, 'REN202410', 20, 5, 'bolsa (3kg)', '2026-12-31', 15.00, 30.00, FALSE, TRUE),
('Vacuna Triple Felina', 1, 'Protege contra Rinotraqueitis, Calicivirus y Panleucopenia.', 'GatoSalud', 6, 'FEL202508', 45, 8, 'dosis', '2026-10-31', 6.50, 18.00, FALSE, TRUE),
('Jeringa 5ml', 3, 'Jeringa desechable para inyecciones.', 'MediStock', 4, 'JER202701', 500, 50, 'unidad', '2026-01-01', 0.10, 0.50, FALSE, TRUE),
('Ivermectina 1%', 8, 'Antiparasitario de amplio espectro.', 'ParaStop', 3, 'IVER202411', 60, 10, 'ml', '2024-12-15', 3.50, 7.50, TRUE, TRUE),
('Vitamina B12', 9, 'Suplemento vitamínico inyectable.', 'VitaMax', 9, 'VITB202507', 80, 15, 'ampolla', '2026-09-01', 1.00, 4.00, FALSE, TRUE),
('Propofol 10mg/ml', 10, 'Anestésico intravenoso.', 'AnesSafe', 4, 'PROPO202412', 15, 3, 'ml', '2026-01-31', 8.00, 20.00, TRUE, TRUE);

INSERT INTO citas (mascota_id, veterinario_id, fecha_hora, motivo, estado_id, observaciones) VALUES
(1, 1, '2025-04-05 10:00:00', 'Chequeo anual y vacuna', 4, 'Mascota sana. Vacuna aplicada.'),
(2, 2, '2025-04-05 11:30:00', 'Tos persistente', 3, 'Pendiente de radiografía.'),
(3, 3, '2025-04-06 09:00:00', 'Revisión dermatológica', 1, 'Primera visita por picazón.'),
(4, 2, '2025-04-06 14:00:00', 'Control de peso', 2, 'Dieta especial en curso.'),
(5, 4, '2025-04-07 16:00:00', 'Limpieza dental', 1, 'Requiere sedación.'),
(6, 1, '2025-04-08 10:30:00', 'Consulta por herida en pata', 4, 'Suturas aplicadas.'),
(7, 5, '2025-04-09 11:00:00', 'Control cardiológico', 1, 'Chequeo de soplos.'),
(8, 6, '2025-04-10 15:00:00', 'Revisión ocular', 1, 'Lagrimeo constante.'),
(9, 7, '2025-04-11 09:30:00', 'Seguimiento post-operatorio', 4, 'Todo en orden. Retiro de puntos.'),
(10, 8, '2025-04-12 12:00:00', 'Fisioterapia', 1, 'Sesión inicial.');

INSERT INTO consultas_medicas (mascota_id, veterinario_id, cita_id, fecha_hora, motivo, sintomas, diagnostico, recomendaciones, peso_registrado, temperatura) VALUES
(1, 1, 1, '2025-04-05 10:15:00', 'Chequeo anual', 'Ninguno, control de rutina.', 'Mascota sana. Condición corporal ideal.', 'Continuar con alimentación balanceada. Próximo control en un año.', 30.50, 38.5),
(6, 1, 6, '2025-04-08 10:45:00', 'Herida en pata', 'Laceración profunda en almohadilla, sangrado leve.', 'Herida abierta por objeto punzante.', 'Sutura, antibiótico oral por 7 días. Control en 5 días.', 25.30, 38.8),
(9, 7, 9, '2025-04-11 09:45:00', 'Retiro de puntos', 'Recuperación de cirugía previa sin complicaciones.', 'Recuperación exitosa.', 'Mantener zona seca. Dieta normal.', 35.00, 38.6),
(2, 2, 2, '2025-04-05 11:45:00', 'Tos persistente', 'Tos seca, especialmente de noche.', 'Sospecha de asma felina. Pendiente de placa.', 'Iniciar tratamiento con broncodilatador. Realizar radiografía ASAP.', 4.10, 39.2),
(5, 4, NULL, '2025-03-20 12:00:00', 'Mal aliento', 'Halitosis, sarro visible.', 'Gingivitis y enfermedad periodontal.', 'Programar limpieza dental profunda.', 12.20, 38.4),
(4, 2, 4, '2025-04-06 14:15:00', 'Control de peso', 'Pérdida de 50g desde el último control.', 'Evolución favorable de peso.', 'Continuar con el régimen alimenticio. Próximo control en 3 meses.', 3.45, 38.3),
(3, 3, 3, '2025-04-06 09:15:00', 'Picazón', 'Rascado constante, enrojecimiento en abdomen y orejas.', 'Dermatitis alérgica a alimentos (sospecha).', 'Dieta de eliminación. Antihistamínico por 10 días.', 38.90, 38.9),
(7, 5, 7, '2025-04-09 11:15:00', 'Chequeo cardíaco', 'Control de soplos previamente diagnosticados.', 'Soplo Grado II/VI, estable.', 'Mantener medicación. Ecocardiograma en 6 meses.', 6.80, 38.7),
(8, 6, 8, '2025-04-10 15:15:00', 'Lagrimeo', 'Ojos llorosos y un poco rojos.', 'Conjuntivitis leve.', 'Gotas oftálmicas por 5 días. Control solo si no mejora.', 5.90, 38.5),
(10, 8, 10, '2025-04-12 12:15:00', 'Sesión de fisioterapia', 'Rehabilitación post-fractura (antigua).', 'Inicio de protocolo de rehabilitación.', 'Realizar ejercicios en casa. Próxima sesión en una semana.', 4.50, 38.4);

INSERT INTO procedimientos_especiales (mascota_id, veterinario_id, tipo_procedimiento, nombre_procedimiento, fecha_hora, duracion_estimada_minutos, detalle_procedimiento, seguimiento_postoperatorio, estado, costo_procedimiento) VALUES
(6, 1, 'Sutura', 'Reparación de Laceración', '2025-04-08 11:00:00', 30, 'Limpieza y sutura con hilo 2-0 absorbible de herida en pata.', 'Retiro de puntos en 10 días. Antibiótico.', 'Finalizado', 80.00),
(5, 4, 'Odontología', 'Limpieza y Pulido Dental', '2025-04-15 10:00:00', 90, 'Detartraje supragingival, pulido y flúor. Extracción de un incisivo con periodontitis avanzada.', 'Dieta blanda por 3 días. Control oral en 6 meses.', 'Programado', 150.00),
(9, 1, 'Cirugía', 'Corrección de Entropión', '2025-03-25 14:00:00', 120, 'Cirugía correctiva de entropión bilateral en párpados inferiores.', 'Uso de collar isabelino. Gotas oftálmicas por 10 días.', 'Finalizado', 250.00),
(3, 7, 'Diagnóstico', 'Radiografía de Cadera', '2025-04-07 10:00:00', 20, 'Toma de placa radiográfica para evaluación de displasia.', 'Pendiente de resultado.', 'Finalizado', 40.00),
(2, 2, 'Diagnóstico', 'Ecocardiograma', '2025-04-18 16:00:00', 45, 'Estudio de ultrasonido para evaluar función cardíaca.', 'Programado por soplo.', 'Programado', 90.00),
(1, 3, 'Dermatología', 'Biopsia de Piel', '2025-05-02 09:30:00', 60, 'Toma de muestra de piel para análisis histopatológico por nódulo.', 'Puntos de sutura. Esperar 1 semana por resultados.', 'Programado', 110.00),
(4, 6, 'Oftalmología', 'Test de Schirmer', '2025-04-10 16:00:00', 10, 'Prueba para medir producción de lágrimas.', 'Resultado normal (15mm/min).', 'Finalizado', 15.00),
(7, 8, 'Fisioterapia', 'Rehabilitación Acuática', '2025-04-20 11:00:00', 45, 'Primera sesión de terapia en agua para reducción de peso y fortalecimiento.', 'Dos sesiones semanales.', 'Programado', 55.00),
(10, 9, 'Emergencia', 'Estabilización y Fluidoterapia', '2025-03-10 20:00:00', 180, 'Atención de emergencia por deshidratación y diarrea severa. Terapia con fluidos IV.', 'Dieta blanda. Control en 24h.', 'Finalizado', 95.00),
(8, 4, 'Odontología', 'Extracción de Muela', '2025-05-10 13:00:00', 70, 'Extracción de premolar con fractura y absceso.', 'Antibiótico post-extracción. Dieta blanda.', 'Programado', 130.00);

INSERT INTO historial_medico (mascota_id, fecha_evento, evento_tipo_id, descripcion, diagnostico, tratamiento_recomendado, veterinario_id, consulta_id, procedimiento_id) VALUES
(1, '2025-04-05', 1, 'Vacunación anual: Rabia', 'Prevención', 'Próxima dosis en 1 año.', 1, 1, NULL),
(6, '2025-04-08', 4, 'Reparación de herida con sutura.', 'Laceración', 'Antibiótico y curación diaria.', 1, 6, 1),
(9, '2025-03-25', 4, 'Cirugía de Entropión bilateral.', 'Entropión', 'Gotas oftálmicas.', 1, NULL, 3),
(2, '2025-04-05', 3, 'Consulta por tos. Sospecha de asma.', 'Sospecha de Asma Felina', 'Broncodilatador. Radiografía pendiente.', 2, 4, NULL),
(5, '2025-03-20', 6, 'Examen de rutina con hallazgo de sarro.', 'Enfermedad Periodontal', 'Programar limpieza dental.', 4, 5, NULL),
(3, '2025-04-06', 3, 'Dermatitis alérgica (sospecha).', 'Dermatitis Alérgica', 'Dieta de eliminación y antihistamínico.', 3, 7, NULL),
(3, '2025-04-07', 8, 'Radiografía de cadera realizada.', 'Displasia Leve (Confirmada)', 'Control de peso y suplementos articulares.', 7, NULL, 4),
(10, '2025-03-10', 9, 'Emergencia por deshidratación y diarrea.', 'Gastroenteritis', 'Fluidoterapia y probióticos.', 9, NULL, 9),
(8, '2025-04-10', 6, 'Examen ocular por lagrimeo.', 'Conjuntivitis Leve', 'Gotas oftálmicas por 5 días.', 6, 9, 7),
(7, '2025-04-09', 3, 'Chequeo cardiológico de rutina.', 'Soplo Grado II/VI', 'Continuar medicación cardíaca.', 5, 8, NULL);

INSERT INTO facturas (dueno_id, numero_factura, fecha_emision, subtotal, impuesto, descuento, total, metodo_pago, estado, observaciones) VALUES
(1, 'FAC-2025-0001', '2025-04-05 10:20:00', 60.00, 9.60, 0.00, 69.60, 'Tarjeta', 'Pagada', 'Consulta y Vacuna Antirrábica.'),
(6, 'FAC-2025-0002', '2025-04-08 11:30:00', 110.00, 17.60, 0.00, 127.60, 'Efectivo', 'Pagada', 'Procedimiento de sutura y antibiótico.'),
(9, 'FAC-2025-0003', '2025-03-25 16:00:00', 250.00, 40.00, 0.00, 290.00, 'Transferencia', 'Pagada', 'Cirugía de Entropión.'),
(2, 'FAC-2025-0004', '2025-04-05 12:00:00', 45.00, 7.20, 0.00, 52.20, 'Tarjeta', 'Pagada', 'Consulta y medicamento.'),
(3, 'FAC-2025-0005', '2025-04-06 10:00:00', 85.00, 13.60, 0.00, 98.60, 'Pendiente', 'Pendiente', 'Consulta, radiografía y medicamento.'),
(5, 'FAC-2025-0006', '2025-04-07 16:30:00', 45.00, 7.20, 0.00, 52.20, 'Efectivo', 'Pagada', 'Consulta inicial por mal aliento.'),
(7, 'FAC-2025-0007', '2025-04-09 11:45:00', 45.00, 7.20, 0.00, 52.20, 'Tarjeta', 'Pagada', 'Control cardiológico.'),
(10, 'FAC-2025-0008', '2025-03-10 21:00:00', 105.00, 16.80, 0.00, 121.80, 'Efectivo', 'Pagada', 'Emergencia y fluidoterapia.'),
(4, 'FAC-2025-0009', '2025-04-06 14:30:00', 45.00, 7.20, 0.00, 52.20, 'Tarjeta', 'Pagada', 'Consulta de control de peso.'),
(8, 'FAC-2025-0010', '2025-04-10 15:30:00', 60.00, 9.60, 0.00, 69.60, 'Efectivo', 'Pagada', 'Consulta y Test de Schirmer.');

INSERT INTO club_mascotas (dueno_id, puntos_acumulados, puntos_canjeados, puntos_disponibles, nivel, fecha_inscripcion) VALUES
(1, 100, 0, 100, 'Bronce', '2024-10-01'),
(2, 80, 0, 80, 'Bronce', '2024-10-15'),
(3, 0, 0, 0, 'Bronce', '2025-01-01'),
(4, 50, 0, 50, 'Bronce', '2024-11-20'),
(5, 0, 0, 0, 'Bronce', '2025-03-01'),
(6, 150, 0, 150, 'Bronce', '2024-09-01'),
(7, 45, 0, 45, 'Bronce', '2025-04-01'),
(8, 60, 0, 60, 'Bronce', '2025-04-05'),
(9, 300, 0, 300, 'Bronce', '2024-12-10'),
(10, 120, 0, 120, 'Bronce', '2024-08-01');

INSERT INTO items_factura (factura_id, tipo_item, producto_id, servicio_id, servicio_descripcion, cantidad, precio_unitario, subtotal) VALUES
(1, 'Servicio', NULL, 1, 'Consulta General', 1, 45.00, 45.00),
(1, 'Producto', 1, NULL, NULL, 1, 15.00, 15.00), -- Vacuna Antirrábica
(2, 'Servicio', NULL, 5, 'Cirugía Menor (Sutura)', 1, 80.00, 80.00),
(2, 'Producto', 2, NULL, NULL, 10, 2.50, 25.00), -- Amoxicilina
(2, 'Producto', 3, NULL, NULL, 1, 5.00, 5.00), -- Sutura
(3, 'Servicio', NULL, 5, 'Cirugía Corrección de Entropión', 1, 250.00, 250.00),
(4, 'Servicio', NULL, 1, 'Consulta General', 1, 45.00, 45.00),
(5, 'Servicio', NULL, 1, 'Consulta General', 1, 45.00, 45.00),
(5, 'Servicio', NULL, 10, 'Radiografía', 1, 40.00, 40.00),
(6, 'Servicio', NULL, 1, 'Consulta General', 1, 45.00, 45.00);

INSERT INTO prescripciones (consulta_id, procedimiento_id, producto_id, cantidad, dosis, frecuencia, duracion_dias, instrucciones) VALUES
(2, NULL, 4, 1, '1 ml', 'Cada 12 horas', 5, 'Dar con comida, si es necesario, por tos.'), 
(6, NULL, 2, 7, '250mg (1 cápsula)', 'Cada 12 horas', 7, 'Completar el ciclo, incluso si mejora.'), 
(NULL, 1, 2, 7, '250mg (1 cápsula)', 'Cada 12 horas', 7, 'Para prevenir infección en la herida.'),
(7, NULL, 4, 1, '0.5 ml', 'Cada 24 horas', 10, 'Antihistamínico para picazón, monitorear sedación.'),
(8, NULL, 8, 1, '0.5 ml', 'Dosis Única', 1, 'Antiparasitario de amplio espectro.'),
(9, NULL, 9, 1, '1 ampolla', 'Cada 24 horas', 5, 'Suplemento para mejorar la condición corporal.'), 
(1, NULL, 1, 1, '1 dosis', 'Dosis Única', 1, 'Vacuna antirrábica, anotada en carné.'),
(NULL, 3, 2, 10, '1 cápsula', 'Cada 8 horas', 10, 'Para prevenir infección post-cirugía.'),
(4, NULL, 5, 1, '200g', 'Cada 24 horas', 90, 'Dieta de mantenimiento renal.'), 
(10, NULL, 8, 1, '1 ml', 'Dosis Única', 1, 'Desparasitación preventiva.');

INSERT INTO insumos_procedimientos (procedimiento_id, producto_id, cantidad_usada, observaciones) VALUES
(1, 3, 1, 'Sutura 2-0 utilizada.'),
(1, 7, 1, 'Jeringa de 5ml para anestesia local.'),
(3, 10, 2, '2 ml de Propofol para la inducción.'),
(3, 3, 1, 'Sutura 3-0 para párpados.'),
(3, 7, 2, 'Jeringas para anestesia y medicación intraoperatoria.'),
(9, 7, 5, '5 jeringas para la fluidoterapia y medicamentos IV.'),
(9, 2, 3, 'Medicamento IV de emergencia usado.'),
(4, 7, 1, 'Jeringa para sedación leve para la placa.'),
(2, 10, 3, '3 ml de Propofol para el mantenimiento de la anestesia.'),
(2, 7, 1, 'Jeringa para aplicación de anestesia local.');

INSERT INTO transacciones_puntos (club_mascotas_id, factura_id, puntos, tipo, descripcion, saldo_anterior, saldo_nuevo) VALUES
(1, 1, 70, 'Ganados', 'Puntos por Factura FAC-2025-0001', 100, 170),
(6, 2, 130, 'Ganados', 'Puntos por Factura FAC-2025-0002', 150, 280),
(9, 3, 290, 'Ganados', 'Puntos por Factura FAC-2025-0003', 300, 590),
(2, 4, 50, 'Ganados', 'Puntos por Factura FAC-2025-0004', 80, 130),
(4, 9, 50, 'Ganados', 'Puntos por Factura FAC-2025-0009', 50, 100),
(8, 10, 70, 'Ganados', 'Puntos por Factura FAC-2025-0010', 60, 130),
(7, 7, 50, 'Ganados', 'Puntos por Factura FAC-2025-0007', 45, 95),
(10, 8, 120, 'Ganados', 'Puntos por Factura FAC-2025-0008', 120, 240),
(9, NULL, 200, 'Ajuste', 'Ajuste de puntos por promoción especial.', 590, 790),
(1, NULL, 50, 'Expirados', 'Puntos de promoción de bienvenida expirados.', 170, 120);

INSERT INTO mascotas_adopcion (mascota_id, fecha_ingreso, motivo_ingreso, estado, historia, temperamento, necesidades_especiales) VALUES
(1, '2025-01-01', 'Fue rescatado de la calle, requiere un hogar.', 'Disponible', 'Max es un perro muy juguetón y cariñoso, ideal para familias con niños.', 'Juguetón, amigable', 'Requiere paseos diarios de alta intensidad.'),
(2, '2025-01-15', 'Dueño anterior se mudó y no pudo llevarla.', 'Adoptada', 'Luna es una gata tranquila y reservada, prefiere la compañía de adultos.', 'Tranquila, reservada', 'Dieta especial por asma felina.'),
(3, '2025-02-01', 'Abandono por condición médica.', 'Disponible', 'Rocky necesita un dueño responsable que maneje su displasia.', 'Leal, protector', 'Medicación diaria y ejercicio controlado.'),
(4, '2025-02-10', 'Rescate de camada.', 'En Proceso', 'Miau es muy curiosa y le gusta explorar. Ideal para interiores.', 'Curiosa, activa', 'Ninguna.'),
(5, '2025-03-01', 'Rescate animal.', 'Disponible', 'Toby es un cachorro enérgico que necesita entrenamiento.', 'Enérgico, travieso', 'Entrenamiento de obediencia.'),
(7, '2025-03-15', 'Problemas de salud del dueño.', 'Disponible', 'Nala es una gata que necesita una dieta estricta para bajar de peso.', 'Perezosa, cariñosa', 'Dieta de control de peso.'),
(8, '2025-04-01', 'Encontrada en la calle.', 'Disponible', 'Coco es una perra muy dócil y fácil de entrenar.', 'Dócil, obediente', 'Ninguna.'),
(10, '2025-04-10', 'Problemas de espacio en hogar de paso.', 'Disponible', 'Kira es una gatita joven y sociable.', 'Sociable, juguetona', 'Ninguna.'),
(6, '2025-04-15', 'Entrega voluntaria.', 'Retirada', 'Spot fue retirado por el dueño original tras un cambio de circunstancias.', 'Tranquilo, mayor', 'Revisiones periódicas de audición.'),
(9, '2025-04-20', 'Maltrato en hogar anterior.', 'Disponible', 'Zeus necesita mucha paciencia y amor para superar sus traumas.', 'Tímido, desconfiado', 'Terapias de comportamiento.');

INSERT INTO adopciones (mascota_adopcion_id, dueno_id, fecha_adopcion, contrato_texto, condiciones_especiales, seguimiento_requerido, fecha_primer_seguimiento) VALUES
(2, 1, '2025-03-01', 'El adoptante se compromete a brindarle el cuidado veterinario necesario.', 'Mantener dieta especial por asma.', TRUE, '2025-04-01'),
(4, 3, '2025-04-25', 'El adoptante se compromete a mantenerla en interiores.', 'Castración obligatoria al cumplir 6 meses.', TRUE, '2025-05-25'),
(6, 2, '2025-05-01', 'Contrato de adopción estándar.', 'Ninguna.', TRUE, '2025-06-01'),
(1, 4, '2025-05-10', 'El adoptante debe garantizar dos paseos diarios.', 'Seguimiento por alergias.', TRUE, '2025-06-10'),
(3, 5, '2025-05-15', 'Contrato especial por condición médica.', 'Control de displasia cada 6 meses.', TRUE, '2025-06-15'),
(5, 7, '2025-05-20', 'El adoptante debe asistir a un curso de adiestramiento.', 'Entrenamiento obligatorio.', TRUE, '2025-06-20'),
(7, 8, '2025-06-01', 'El adoptante se compromete a seguir la dieta de control de peso.', 'Controles de peso mensuales.', TRUE, '2025-07-01'),
(8, 9, '2025-06-10', 'Adopción por pareja de adultos mayores.', 'Ninguna.', TRUE, '2025-07-10'),
(10, 10, '2025-06-15', 'Contrato de adopción estándar.', 'Ninguna.', TRUE, '2025-07-15'),
(9, 6, '2025-06-20', 'El adoptante debe proporcionar un ambiente tranquilo.', 'Terapia de comportamiento.', TRUE, '2025-07-20');


INSERT INTO movimientos_inventario (producto_id, tipo_movimiento, cantidad, stock_anterior, stock_nuevo, motivo, referencia_consulta_id, referencia_procedimiento_id, usuario) VALUES
(1, 'Salida', 1, 50, 49, 'Aplicación de Vacuna', 1, NULL, 'Dr. Mario Rodríguez'),
(2, 'Salida', 10, 100, 90, 'Venta en Consulta', 6, NULL, 'Dra. Laura Díaz'),
(3, 'Salida', 1, 30, 29, 'Uso en Procedimiento', NULL, 1, 'Dr. Mario Rodríguez'),
(10, 'Salida', 2, 15, 13, 'Uso en Procedimiento', NULL, 3, 'Dr. Mario Rodríguez'),
(7, 'Salida', 1, 500, 499, 'Uso en Consulta para inyección', 1, NULL, 'Dr. Mario Rodríguez'),
(5, 'Entrada', 10, 20, 30, 'Reabastecimiento de stock', NULL, NULL, 'Jefe de Inventario'),
(8, 'Salida', 1, 60, 59, 'Venta en Consulta', 8, NULL, 'Dr. Roberto Gómez'),
(4, 'Salida', 1, 75, 74, 'Uso en Consulta', 2, NULL, 'Dra. Laura Díaz'),
(1, 'Entrada', 20, 49, 69, 'Compra a proveedor Vacunas & Biológicos', NULL, NULL, 'Jefe de Compras'),
(9, 'Salida', 1, 80, 79, 'Uso en Consulta', 9, NULL, 'Dra. Elisa Soto');

INSERT INTO registro_jornada_vacunacion (jornada_id, mascota_id, dueno_id, vacuna_id, veterinario_id, fecha_hora, lote_vacuna, proxima_dosis) VALUES
(3, 3, 3, 8, 3, '2025-05-10 14:15:00', 'IVER202411', '2025-11-10'),
(5, 5, 5, 1, 4, '2025-04-01 10:30:00', 'RAB202506', '2026-04-01'),
(5, 10, 10, 6, 8, '2025-04-01 11:00:00', 'FEL202508', '2026-04-01'),
(3, 7, 7, 8, 8, '2025-05-10 15:00:00', 'IVER202411', '2025-11-10'),
(2, 2, 2, 6, 2, '2025-07-20 10:00:00', 'FEL202508', '2026-07-20'),
(1, 1, 1, 1, 1, '2025-06-15 09:30:00', 'RAB202506', '2026-06-15'),
(1, 6, 6, 1, 9, '2025-06-15 10:00:00', 'RAB202506', '2026-06-15'),
(4, 9, 9, 1, 1, '2025-08-05 08:30:00', 'RAB202506', '2026-08-05'),
(4, 4, 4, 6, 2, '2025-08-05 09:00:00', 'FEL202508', '2026-08-05'),
(7, 8, 8, 1, 1, '2025-10-12 10:15:00', 'RAB202506', '2026-10-12');

INSERT INTO canjes_beneficios (club_mascotas_id, beneficio_id, puntos_canjeados, estado, factura_id) VALUES
(9, 1, 500, 'Aplicado', 3),
(1, 3, 1000, 'Pendiente', NULL),
(6, 4, 5000, 'Expirado', NULL),
(2, 5, 1500, 'Aplicado', 4),
(10, 1, 500, 'Aplicado', 8),
(3, 7, 300, 'Pendiente', NULL),
(4, 1, 500, 'Aplicado', 9),
(8, 3, 1000, 'Pendiente', NULL),
(7, 5, 1500, 'Expirado', NULL),
(5, 7, 300, 'Pendiente', NULL);


