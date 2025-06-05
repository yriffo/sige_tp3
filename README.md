# SIGE - Sistema Integral de Gestión Escolar

Este proyecto es un **prototipo funcional en Java** del sistema SIGE, desarrollado como parte del **Trabajo Práctico 3 (TP3)** de la materia *Seminario de Práctica* de la Licenciatura en Informática.

El sistema simula un entorno escolar donde distintos usuarios pueden acceder según su **rol institucional** (Docente, Preceptor, Asesor Pedagógico, Directivo) y operar sobre un conjunto de datos académicos.

---

## Objetivo del proyecto

Diseñar e implementar un prototipo en Java que permita simular las principales funcionalidades de gestión escolar, aplicando correctamente los **pilares de la Programación Orientada a Objetos (POO)**, estructuras de control, manejo de excepciones y organización modular del código.

---

##  Tecnologías utilizadas

- Java SE 17+
- NetBeans IDE
- Consola de texto (interfaz básica)
- Estructuras de datos en memoria (listas)
- Simulación de persistencia de datos
- Java Collections
- Manejo de excepciones personalizadas

---

##  Funcionalidades implementadas

- **Inicio de sesión** por email y contraseña.
- Acceso diferenciado por **rol institucional**:
  - **Docente**: registrar asistencia, cargar calificaciones, gestionar actividades.
  - **Preceptor**: gestionar asistencia completa, registrar reuniones, ver calificaciones y bitácora.
  - **Asesor**: ver fichas de estudiantes, registrar reuniones y consultar bitácoras.
  - **Directivo**: acceso completo a todos los registros y fichas.
- Registro y edición de **asistencia**.
- Carga y consulta de **calificaciones**.
- Gestión de **actividades** (nombre, fechas, tipo de calificación, observaciones).
- Registro de **reuniones pedagógicas**.
- Visualización de **bitácora** cronológica de eventos por estudiante.
- Uso de **constructores, getters, setters** y clases hijas para representar los distintos roles.

---

##  Organización del código

- `modelo/`: contiene las clases principales (`Usuario`, `Docente`, `Estudiante`, `Asistencia`, `Actividad`, etc.)
- `controlador/SIGE.java`: clase principal con el menú de navegación por rol.
- `excepciones/`: definición de excepción personalizada para asistencias inválidas.
- `BitacoraEventos`: clase auxiliar para registrar eventos cronológicos.

---

##  Cómo ejecutar

1. Clonar este repositorio o descargar como ZIP.
2. Abrir el proyecto en NetBeans.
3. Ejecutar la clase principal: `SIGE.java`.
4. Iniciar sesión con alguno de los usuarios simulados (ver lista en el código).

---

##  Usuarios simulados

| Usuario              | Email               | Contraseña | Rol(es)                    |
|----------------------|---------------------|------------|----------------------------|
| María González       | maria@mail.com      | doc123     | Docente                    |
| Carlos Pérez         | carlos@mail.com     | pre123     | Preceptor                  |
| Laura Ramírez        | laura@mail.com      | ase123     | Asesor                     |
| Ana Martínez         | ana@mail.com        | dir123     | Directivo                  |
| Pedro Lopez          | pedro@mail.com      | multi123   | Docente, Preceptor         |
| Marcela Torres       | marcela@mail.com    | trirol123  | Docente, Asesor, Preceptor |
| Federico Juarez      | fede@mail.com       | dirpre123  | Preceptor, Directivo       |
| Gisella Ibarra       | gisella@mail.com    | todos123   | Todos los roles            |

---

##  Proyección hacia el TP4

En la siguiente etapa del proyecto se prevé:
- Implementar **persistencia real** de datos utilizando MySQL y JDBC.
- Reemplazar estructuras en memoria por operaciones en base de datos.
- Migrar desde consola a una **interfaz gráfica** (Swing o JavaFX).
- Incorporar validaciones adicionales, manejo de duplicados y registros más complejos.

---

##  Licencia

Uso académico exclusivamente. Proyecto realizado para la **Licenciatura en Informática - Seminario de Práctica**

