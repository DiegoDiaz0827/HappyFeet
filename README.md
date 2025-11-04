🐾 Sistema de Gestión Integral para la Veterinaria "Happy Feet"

📘 Descripción General

El Sistema de Gestión Integral para la Veterinaria Happy Feet es una aplicación desarrollada en Java 17 con conexión a MySQL que centraliza todas las operaciones de la clínica veterinaria: 
desde la gestión de pacientes hasta la facturación y los programas especiales como adopciones y jornadas de vacunación.
Este sistema surge ante la necesidad de modernizar la administración de la clínica, reemplazando procesos manuales 
(fichas en papel, hojas de cálculo y agendas físicas) por una plataforma digital eficiente, confiable y fácil de usar.


⚙️ TECNOLOGIAS UTILIZADAS

Lenguaje: Java SE 17
Base de Datos: MySQL 
Conexión: JDBC
Gestor de Dependencias: Maven
Paradigma: Programación Orientada a Objetos (POO) con principios SOLID
Arquitectura: MVC (Modelo – Vista – Controlador)
Patrones de Diseño: Singleton, DAO, Factory, Observer, Strategy
Logs y Excepciones: Manejo de errores con registro en archivo .log
Programación Funcional: Uso de lambdas y API Stream



🧠 Módulos y Funcionalidades

1. Gestión de Pacientes

Registro completo de mascotas (nombre, especie, raza, alergias, historial médico).
Registro de dueños (datos personales, contacto de emergencia).
Asociación uno a uno entre dueño y mascota.
Transferencia de propiedad de mascotas.

2. Servicios Médicos y Citas
Agenda de citas con estados (“Programada”, “Finalizada”, “Cancelada”).
Registro de consultas médicas con diagnóstico, tratamientos y recetas.
Control automático de inventario al usar medicamentos o insumos.
Registro de procedimientos quirúrgicos y su seguimiento postoperatorio.

3. Inventario y Farmacia

Control en tiempo real del stock de medicamentos, vacunas e insumos.
Alertas de stock bajo y vencimiento de productos.
Registro de proveedores y movimientos de inventario.
Bloqueo automático del uso de productos vencidos.

4. Facturación y Reportes

Generación de facturas detalladas en texto plano.
Registro de ventas y servicios prestados.

Reportes gerenciales:

Servicios más solicitados
Estado del inventario
Desempeño del personal
Facturación por período

5. Actividades Especiales

Días de Adopción: registro de mascotas disponibles y contrato de adopción.
Jornadas de Vacunación: registro masivo de animales vacunados.

Club de Mascotas Frecuentes: sistema de puntos y beneficios para clientes leales.

🧱 Modelo de Base de Datos

La base de datos está compuesta por múltiples módulos interrelacionados:
Tablas principales: duenos, mascotas, citas, consultas_medicas, inventario, facturas.
Relaciones uno a muchos y muchos a muchos a través de tablas intermedias.
Restricciones de integridad referencial y validaciones (CHECK, FK, UNIQUE).
📊 Se recomienda revisar el archivo /database/schema.sql para consultar la estructura completa.


🚀 Instrucciones de Instalación y Ejecución
🔧 Requisitos Previos

Java JDK 17
MySQL Server 8.x
Maven 3.x
IDE recomendado: IntelliJ IDEA / VS Code / Netbeans
🐱‍💻 Pasos de Instalación

Clonar el repositorio:

git clone https://github.com/DiegoDiaz0827/HappyFeet.git
cd Happyfeet

Configurar la base de datos:
Crear una base de datos en MySQL:
CREATE DATABASE happyfeet;
Editar el archivo de configuración (por ejemplo: /src/main/resources/db.properties):

db.url=jdbc:mysql://localhost:3306/happyfeet
db.user=root
db.password=tu_contraseña


Ejecutar los scripts SQL:
En el orden:

mysql -u root -p happyfeet< database/schema.sql
mysql -u root -p happyfeet < database/data.sql


Compilar y ejecutar el proyecto:

mvn clean compile
mvn exec:java -Dexec.mainClass="com.happyfeet.Main"


🧭 GUIA DE USO

Menú Principal: Permite acceder a los módulos del sistema.
Gestión de Pacientes: Registrar dueños y mascotas.
Citas y Consultas: Programar, consultar y eliminar citas.
Inventario: Agregar productos, verificar stock y movimientos.
Facturación: Generar facturas automáticas después de cada servicio.
Reportes: Consultar estadísticas y rendimiento general.
Actividades Especiales: Gestionar adopciones, campañas y beneficios del club.
