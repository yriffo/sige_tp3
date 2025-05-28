/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Yonatan
 */

/**
 * Clase Calificacion
 * Representa la calificación de un estudiante en una actividad.
 */
public class Calificacion {
    private String nombreEstudiante;
    private String nombreActividad;
    private double nota;

    public Calificacion(String nombreEstudiante, String nombreActividad, double nota) {
        this.nombreEstudiante = nombreEstudiante;
        this.nombreActividad = nombreActividad;
        this.nota = nota;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public double getNota() {
        return nota;
    }

    public void mostrarInformacion() {
        System.out.println("Calificacion de " + nombreEstudiante + " - " + nombreActividad + ": " + nota);
    }
}

