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

    // --- Atributos privados ---
    private int idActividad;                // Identificador único de la actividad
    private String nombreActividad;         // Título de la actividad
    private String materia;                 // Materia asociada a la actividad
    private String curso;                   // Curso correspondiente
    private String tipoCalificacion;        // "Numérica" o "Conceptual"
    private LocalDate fechaInicio;          // Fecha de inicio o asignación
    private LocalDate fechaEntrega;         // Fecha de entrega de la actividad
    private String observacion;             // Observación opcional
    private Docente docente;                // Docente responsable de la actividad

    /**
     * Constructor completo de la clase Actividad.
     * Se agregan todos los parámetros, incluyendo el docente responsable.
     */
    public Actividad(int idActividad, String nombreActividad, String materia, String curso,
                     String tipoCalificacion, LocalDate fechaInicio, LocalDate fechaEntrega,
                     String observacion, Docente docente) {
        this.idActividad = idActividad;
        this.nombreActividad = nombreActividad;
        this.materia = materia;
        this.curso = curso;
        this.tipoCalificacion = tipoCalificacion;
        this.fechaInicio = fechaInicio;
        this.fechaEntrega = fechaEntrega;
        this.observacion = observacion;
        this.docente = docente;  // 💡 Se guarda el docente asociado a la actividad
    }

    // --- Métodos Getters ---

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

    /**
     * Devuelve el docente responsable de esta actividad.
     * Esto es esencial para vincular la actividad con su autor y filtrar por docente.
     */
    public Docente getDocente() {
        return docente;
    }

    /**
     * Representación de texto legible para consola o depuración.
     */
    @Override
    public String toString() {
        return "Actividad: " + nombreActividad +
               " | Materia: " + materia +
               " | Curso: " + curso +
               " | Tipo: " + tipoCalificacion +
               " | Inicio: " + fechaInicio +
               " | Entrega: " + fechaEntrega +
               " | Observación: " + observacion +
               " | Docente: " + docente.getNombreCompleto(); // Se muestra el docente también
    }
}

