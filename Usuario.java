/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;// Este archivo pertenece al paquete "modelo", que agrupa las clases base del sistema


/**
 *
 * @author Yonatan
 */

/**
 * Clase Usuario
 * Representa a un usuario general del sistema SIGE (puede ser docente, preceptor, etc.)
 */
public class Usuario {

    // Atributos privados (encapsulamiento) para proteger los datos
    private int idUsuario;           // Identificador único del usuario
    private String nombre;           // Nombre del usuario
    private String apellido;         // Apellido del usuario
    private String email;            // Correo electrónico
    private String rol;              // Rol asignado (docente, preceptor, etc.)

    /**
     * Constructor de la clase Usuario
     * Permite inicializar un objeto Usuario con todos sus atributos
     */
    public Usuario(int idUsuario, String nombre, String apellido, String email, String rol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.rol = rol;
    }

    // Métodos getter y setter para acceder y modificar los atributos de forma controlada

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * Método mostrarInformacion()
     * Permite imprimir información básica del usuario (aplica polimorfismo si es sobreescrito en una subclase)
     */
    public void mostrarInformacion() {
        System.out.println("Usuario: " + nombre + " " + apellido + " (" + rol + ")");
    }

    /**
     * Método toString()
     * Devuelve una cadena con todos los datos del usuario (para depuración o impresión)
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }
}

