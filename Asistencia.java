/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Yonatan
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 * Clase Asistencia
 * Representa el registro de asistencia de un estudiante en una fecha determinada.
 */
public class Asistencia {
    private String nombreEstudiante;
    private LocalDate fecha;
    private String estado; // Ej: Presente, Ausente, Justificado

    public Asistencia(String nombreEstudiante, LocalDate fecha, String estado) {
        this.nombreEstudiante = nombreEstudiante;
        this.fecha = fecha;
        this.estado = estado;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void mostrarInformacion() {
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    System.out.println("Estudiante: " + nombreEstudiante);
    System.out.println("Fecha: " + fecha.format(formato));
    System.out.println("Estado: " + estado);
}

}
