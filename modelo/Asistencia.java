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
 * Incluye información sobre el estudiante, la materia, la fecha y el estado de asistencia.
 */
public class Asistencia {

    // Atributos privados
    private String nombreEstudiante;   // Nombre del estudiante
    private String nombreMateria;      // Materia correspondiente
    private LocalDate fecha;           // Fecha del registro de asistencia
    private String estado;             // Estado: Presente, Ausente o Retirado

    /**
     * Constructor
     * Inicializa un registro de asistencia con los datos principales.
     */
    public Asistencia(String nombreEstudiante, String nombreMateria, LocalDate fecha, String estado) {
        this.nombreEstudiante = nombreEstudiante;
        this.nombreMateria = nombreMateria;
        this.fecha = fecha;
        this.estado = estado;
    }

    //  Métodos Get

    /*
     * Devuelve el nombre del estudiante asociado a este registro de asistencia.
     */
    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    /*
     * Devuelve el nombre de la materia en la que se tomó asistencia.
     */
    public String getNombreMateria() {
        return nombreMateria;
    }

    /*
     * Devuelve la fecha del registro de asistencia.
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /*
     * Devuelve el estado de asistencia: Presente, Ausente o Retirado.
     */
    public String getEstado() {
        return estado;
    }

    // Métodos Set

    /*
     * Permite modificar el nombre del estudiante (poco usual, solo si hay error de carga).
     */
    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    /*
     * Permite modificar el nombre de la materia.
     */
    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    /*
     * Permite modificar la fecha de la asistencia.
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /*
     * Permite modificar el estado de asistencia (Presente, Ausente, Retirado).
     */
    public void setEstado(String estado) {
    if (estado.equalsIgnoreCase("Presente") || estado.equalsIgnoreCase("Ausente") || estado.equalsIgnoreCase("Retirado")) {
        this.estado = estado;
    } else {
        throw new IllegalArgumentException("Estado no válido: " + estado);
    }
    }

    /**
     * Devuelve un resumen textual del objeto Asistencia.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Asistencia{" +
            "estudiante='" + nombreEstudiante + '\'' +
            ", materia='" + nombreMateria + '\'' +
            ", fecha=" + fecha.format(formatter) +
            ", estado='" + estado + '\'' +'}';}
}