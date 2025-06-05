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
 * Clase Curso
 * Representa un curso escolar en el sistema SIGE.
 * Cada curso tiene un ID, un nombre (como "2°B") y un año asociado (por ejemplo, 2).
 */

public class Curso {

    private int idCurso;            // Identificador único del curso
    private String nombreCurso;     // Ejemplo: "4°B", "1°A"
    private int anio;               // Año calendario del cursado (ejemplo: 2025)

    /**
     * Constructor completo
     */
    public Curso(int idCurso, String nombreCurso, int anio) {
        this.idCurso = idCurso;
        this.nombreCurso = nombreCurso;
        this.anio = anio;
    }

    // Getters

    public int getIdCurso() {
        return idCurso;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public int getAnio() {
        return anio;
    }

    // Setters

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    /**
     * Muestra información resumida del curso
     */
    public void mostrarInformacion() {
        System.out.println("Curso: " + nombreCurso + " (Año: " + anio + ")");
    }

    @Override
    public String toString() {
        return "Curso{" +
                "idCurso=" + idCurso +
                ", nombreCurso='" + nombreCurso + '\'' +
                ", anio=" + anio +
                '}';
    }
}


