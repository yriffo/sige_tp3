/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Yonatan
 */

/*
 * Clase Calificacion
 * Representa una calificación asignada a un estudiante en una actividad específica.
 * Incluye información sobre el estudiante, la materia, la actividad y la nota obtenida.
 */
public class Calificacion {

    // Atributos privados que representan los datos de una calificación
    private String nombreEstudiante;   // Nombre completo del estudiante
    private String nombreMateria;      // Nombre de la materia evaluada
    private String nombreActividad;    // Nombre de la actividad evaluada
    private int nota;                  // Nota numérica asignada (entre 1 y 10)

    /**
     * Constructor
     * Inicializa una calificación con los datos básicos del sistema
     */
    public Calificacion(String nombreEstudiante, String nombreMateria, String nombreActividad, int nota) {
        this.nombreEstudiante = nombreEstudiante;
        this.nombreMateria = nombreMateria;
        this.nombreActividad = nombreActividad;
        this.nota = nota;
    }

    // Métodos Gett

    /*
     * Devuelve el nombre del estudiante al que pertenece la calificación.
     */
    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    /*
     * Devuelve el nombre de la materia en la que fue evaluado el estudiante.
     */
    public String getNombreMateria() {
        return nombreMateria;
    }

    /**
     * Devuelve el nombre de la actividad específica que fue calificada.
     */
    public String getNombreActividad() {
        return nombreActividad;
    }

    /**
     * Devuelve la nota asignada al estudiante en esa actividad.
     */
    public int getNota() {
        return nota;
    }

    // Métodos Set 

    /**
     * Permite modificar el nombre del estudiante (uso poco frecuente en la práctica).
     */
    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    /**
     * Permite modificar el nombre de la materia asociada a la calificación.
     */
    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    /**
     * Permite modificar el nombre de la actividad evaluada.
     */
    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    /**
     * Permite modificar la nota numérica asignada a la actividad.
     */
    public void setNota(int nota) {
    if (nota < 1 || nota > 10) {
        throw new IllegalArgumentException("La nota debe estar entre 1 y 10.");
    }
    this.nota = nota;
    }

    /**
     * Devuelve una representación textual de la calificación.
     */
    @Override
    public String toString() {
         return "Estudiante: " + nombreEstudiante +
           ", Materia: " + nombreMateria +
           ", Actividad: " + nombreActividad +
           ", Nota: " + nota;
    }
}