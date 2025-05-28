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
 */
public class Materia {
    // Atributos privados
    private int idMateria;
    private String nombreMateria;

    // Constructor
    public Materia(int idMateria, String nombreMateria) {
        this.idMateria = idMateria;
        this.nombreMateria = nombreMateria;
    }

    // Getters
    public int getIdMateria() {
        return idMateria;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    // Método para mostrar información
    public void mostrarInformacion() {
        System.out.println("ID: " + idMateria + " - Materia: " + nombreMateria);
    }
}

