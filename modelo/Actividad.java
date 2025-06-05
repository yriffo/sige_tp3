/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.time.LocalDate;
/**
 *
 * @author Yonatan
 */
  /**
 * Clase Actividad
 * Representa una actividad o evento académico del sistema SIGE, como una evaluación o tarea.
 * Puede ser calificada por el docente según un tipo de calificación (conceptual o numérica).
 * Cada actividad pertenece a un curso y materia, y tiene una fecha de inicio y una fecha de entrega.
 * También permite incluir una observación opcional.
 */
public class Actividad {

    // Atributos privados (según el diseño de la base de datos)
    private int idActividad;                // Identificador único de la actividad (no usado en esta versión pero contemplado)
    private String nombreActividad;         // Nombre o título de la actividad (ej: "TP3 - Java", "Parcial")
    private String materia;                 // Nombre de la materia a la que pertenece
    private String curso;                   // Curso asignado (ej: "2° A")
    private String tipoCalificacion;        // Tipo de calificación: "Numérica" o "Conceptual"
    private LocalDate fechaInicio;          // Fecha en que se asigna o comienza la actividad
    private LocalDate fechaEntrega;         // Fecha límite de entrega de la actividad
    private String observacion;             // Observación opcional asociada a la actividad

    /**
     * Constructor completo para inicializar una actividad.
     * Este constructor incluye todos los atributos relevantes definidos en el modelo de datos.
     */
    public Actividad(int idActividad,String nombreActividad, String materia, String curso, String tipoCalificacion, LocalDate fechaInicio, LocalDate fechaEntrega,String observacion) {
        this.idActividad = idActividad;
        this.nombreActividad = nombreActividad;
        this.materia = materia;
        this.curso = curso;
        this.tipoCalificacion = tipoCalificacion;
        this.fechaInicio = fechaInicio;
        this.fechaEntrega = fechaEntrega;
        this.observacion = observacion;
    }

    // Getters para acceder a los atributos
    
    public int getIdActividad() {
        return idActividad;
    }
    
    public String getNombreActividad() {
        return nombreActividad;
    }

    public String getMateria() {
        return materia;
    }

    public String getCurso() {
        return curso;
    }

    public String getTipoCalificacion() {
        return tipoCalificacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    // Método toString() para mostrar información clara de la actividad
    @Override
    public String toString() {
        return "Actividad: " + nombreActividad +
               " | Materia: " + materia +
               " | Curso: " + curso +
               " | Tipo: " + tipoCalificacion +
               " | Inicio: " + fechaInicio +
               " | Entrega: " + fechaEntrega +
               " | Observación: " + observacion;
    }
}
