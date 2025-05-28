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
 * Clase Estudiante
 * Representa a un estudiante del sistema SIGE.
 * Hereda de Usuario e incluye atributos propios del estudiante.
 */
public class Estudiante extends Usuario {

    // Atributos específicos del estudiante
    private String dni;
    private String fechaNacimiento;
    private String curso;

    /**
     * Constructor de la clase Estudiante.
     * Inicializa los atributos heredados y propios.
     *
     * @param idUsuario ID del usuario (heredado)
     * @param nombre Nombre del usuario (heredado)
     * @param apellido Apellido del usuario (heredado)
     * @param email Email del usuario (heredado)
     * @param rol Rol asignado (debe ser "Estudiante")
     * @param dni Documento del estudiante
     * @param fechaNacimiento Fecha de nacimiento del estudiante
     * @param curso Curso al que pertenece el estudiante
     */
    public Estudiante(int idUsuario, String nombre, String apellido, String email, String rol,
                      String dni, String fechaNacimiento, String curso) {
        super(idUsuario, nombre, apellido, email, rol); // Llama al constructor de Usuario
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.curso = curso;
    }

    // Getters y setters

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    /**
     * Método sobrescrito para mostrar la información del estudiante.
     * Este método demuestra polimorfismo.
     */
    @Override
    public void mostrarInformacion() {
        // Llamamos primero a la versión del método de Usuario
        super.mostrarInformacion();
        // Y agregamos la información adicional de Estudiante
        System.out.println("DNI: " + dni);
        System.out.println("Fecha de nacimiento: " + fechaNacimiento);
        System.out.println("Curso: " + curso);
    }
}
