/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo; // Este archivo pertenece al paquete "modelo", que agrupa las clases base del sistema
import java.util.List;
/**
 *
 * @author Yonatan
 */

/**
 * Clase Usuario
 * Representa a un usuario general del sistema SIGE (puede ser docente, preceptor, etc.)
 * Cada usuario tiene un identificador, nombre, apellido, correo electrónico, contraseña y rol asignado.
 */
public class Usuario {

    // Atributos privados (encapsulamiento) para proteger los datos
    private int idUsuario;           // Identificador único del usuario
    private String nombre;           // Nombre del usuario
    private String apellido;         // Apellido del usuario
    private String email;            // Correo electrónico institucional
    private String contrasenia;      // Contraseña del usuario (en esta versión se simula como texto plano)
    // Lista de roles que puede tener el usuario (Docente, Preceptor, Asesor, Directivo, etc.)
    private List<String> roles;

    /**
     * Constructor completo de la clase Usuario
     * Permite inicializar un objeto Usuario con todos sus atributos
     */
    public Usuario(int idUsuario, String nombre, String apellido, String email, String contrasenia, List<String> roles) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.contrasenia = contrasenia;
        this.roles = roles;
    }

    // Métodos getter para acceder a los atributos

    public int getIdUsuario() {
        return idUsuario;
    }
    public String getNombre() {
        return nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public String getEmail() {
        return email;
    }
    public String getContrasenia() {
        return contrasenia;
    }
    // Getter de roles
    public List<String> getRoles() {
    return roles;
    }
    
    // Métodos setter para modificar los atributos
    /**
     * Devuelve la contraseña del usuario
     * En este prototipo es visible, pero en versiones futuras se oculta.
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    } 
    public void setEmail(String email) {
        this.email = email;
    }  
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    public void setRol(List<String> roles) {
    this.roles = roles;
    }

    /*
     * Método mostrarInformacion()
     * Permite imprimir información básica del usuario.
     * Aplica encapsulamiento.
     */
    public void mostrarInformacion() {
    System.out.println("Nombre completo: " + nombre + " " + apellido);
    System.out.println("Rol del usuario: " + roles);
    }   

    /**
     * Método toString()
     * Devuelve una cadena con todos los datos del usuario
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", rol='" + roles + '\'' +
                '}';
    }
    
    public void mostrarRolPrincipal() {
    System.out.println("Rol principal: Usuario general con múltiples roles.");
    }

}
