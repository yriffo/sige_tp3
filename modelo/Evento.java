/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import modelo.Estudiante;

/**
 *
 * @author Yonatan
 */

/* Clase que representa un evento en el sistema SIGE, vinculado a un estudiante.
 * Permite registrar, editar y mostrar información de eventos como asistencia, calificación, etc.
 */
    public class Evento {
    private int id;
    private String descripcion;
    private String tipo; // "Asistencia", "Calificación", etc.
    private int idEstudiante;
    private String nombreEstudiante;

    // NUEVO: Fecha y hora automática del evento
    private LocalDateTime fechaHora;

    public Evento(int id, String descripcion, String tipo, int idEstudiante, String nombreEstudiante) {
        this.id = id;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.idEstudiante = idEstudiante;
        this.nombreEstudiante = nombreEstudiante;
        this.fechaHora = LocalDateTime.now(); // Se registra el momento exacto del evento
    }

    // Getters necesarios
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return "[" + fechaHora.format(formatter) + "] (" + tipo + ") " + descripcion;
    }
}