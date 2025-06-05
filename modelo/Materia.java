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
 * Clase Materia
 * Representa una asignatura del sistema SIGE.
 * Cada materia tiene un identificador único y un nombre descriptivo.
 */
public class Materia {

    // Atributos privados
    private int idMateria;           // Identificador único de la materia
    private String nombreMateria;    // Nombre de la materia (ej: Matemática, Historia)

    /*
     * Constructor
     * Inicializa la materia con un ID y un nombre.
     */
    public Materia(int idMateria, String nombreMateria) {
        this.idMateria = idMateria;
        this.nombreMateria = nombreMateria;
    }

    // Métodos getter

    /*
     * Devuelve el ID de la materia.
     */
    public int getIdMateria() {
        return idMateria;
    }

    /*
     * Devuelve el nombre de la materia.
     */
    public String getNombreMateria() {
        return nombreMateria;
    }

    // Métodos setter (opcional, pero útil para futuras ediciones)

    /**
     * Permite modificar el ID de la materia.
     */
    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    /**
     * Permite modificar el nombre de la materia.
     */
    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    /*
     * Método mostrarInformacion()
     * Imprime por consola los datos básicos de la materia.
     */
    public void mostrarInformacion() {
        System.out.println("ID: " + idMateria + " - Materia: " + nombreMateria);
    }

    /*
     * Método toString()
     * Devuelve una cadena con todos los datos de la materia.
     */
    @Override
    public String toString() {
        return "Materia{" +
                "idMateria=" + idMateria +
                ", nombreMateria='" + nombreMateria + '\'' +
                '}';
    }
}
