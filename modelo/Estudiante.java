/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
/**
 *
 * @author Yonatan
 */


/*
 * Clase Estudiante
 * Representa a un estudiante dentro del sistema SIGE.
 */
public class Estudiante {

    // Atributos principales
    private int idEstudiante;// Identificador único
    private String nombre;// Nombre del estudiante
    private String apellido;// Apellido del estudiante
    private String dni;// Documento Nacional de Identidad
    private LocalDate fechaNacimiento;// Fecha de nacimiento
    private String curso;// Curso asignado (ej: "3° B - Informática")
    private String direccion;// Domicilio del estudiante
    private String telefono; // Teléfono de contacto
    private String contactoFamiliar;// Nombre de madre/padre/tutor
    private String email;// Email de contacto 
    private List<Evento> bitacora; // Lista de eventos asociados al estudiante


    // Constructor completo
    public Estudiante(int idEstudiante, String nombre, String apellido, String dni, LocalDate fechaNacimiento,
                      String curso, String direccion, String telefono, String contactoFamiliar, String email) {
        this.idEstudiante = idEstudiante;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.curso = curso;
        this.direccion = direccion;
        this.telefono = telefono;
        this.contactoFamiliar = contactoFamiliar;
        this.email = email;
        this.bitacora = new ArrayList<>();

    }

    // Getters
    public int getIdEstudiante() {
        return idEstudiante;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getDni() {
        return dni;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getCurso() {
        return curso;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getContactoFamiliar() {
        return contactoFamiliar;
    }

    public String getEmail() {
        return email;
    }

    // Setters
    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setContactoFamiliar(String contactoFamiliar) {
        this.contactoFamiliar = contactoFamiliar;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Muestra un resumen informativo del estudiante
     */
    public void mostrarInformacion() {
        System.out.println("Estudiante: " + nombre + " " + apellido + " - Curso: " + curso);
        System.out.println("DNI: " + dni + " - Fecha de nacimiento: " + fechaNacimiento);
        System.out.println("Dirección: " + direccion + " - Teléfono: " + telefono);
        System.out.println("Contacto familiar: " + contactoFamiliar + " - Email: " + email);
    }
    
    public void agregarEvento(Evento evento) {
    bitacora.add(evento);
    }

    public List<Evento> getBitacora() {
        return bitacora;
    }

    public void mostrarBitacora() {
        if (bitacora.isEmpty()) {
            System.out.println("Sin eventos registrados para este estudiante.");
        } else {
            System.out.println("Bitácora de eventos del estudiante " + nombre + ":");

            // Ordenar por fechaHora descendente (más recientes primero)
            bitacora.sort(Comparator.comparing(Evento::getFechaHora).reversed());

            for (Evento e : bitacora) {
                System.out.println("  " + e);
            }
        }
    }


    @Override
    public String toString() {
        return "Estudiante{" +
                "idEstudiante=" + idEstudiante +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", curso='" + curso + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", contactoFamiliar='" + contactoFamiliar + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
