package controlador;

import modelo.Usuario;
import modelo.Materia;
import modelo.Asistencia;
import modelo.Calificacion;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;


import java.time.LocalDate; // Clase que representa una fecha (sin hora)
import java.time.format.DateTimeFormatter; // Para definir el formato de entrada de la fecha
import java.time.format.DateTimeParseException; // Para capturar errores si la fecha está mal escrita

import excepciones.EstadoAsistenciaInvalidoException;




/**
 * Clase SIGE
 * Representa la clase principal del sistema SIGE.
 * Contiene el menú funcional, permite crear usuarios, registrar materias, asistencias y calificaciones.
 * También incluye manejo de excepciones y estructuras de control.
 */
public class SIGE {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Permite leer entrada desde consola
        Usuario usuarioEjemplo = null;
        ArrayList<Materia> listaMaterias = new ArrayList<>();
        ArrayList<Asistencia> listaAsistencias = new ArrayList<>();
        ArrayList<Calificacion> listaCalificaciones = new ArrayList<>();

        int opcion;

        // Menú principal con opciones del sistema SIGE
        do {
            System.out.println("\n=== MENÚ PRINCIPAL DEL SISTEMA SIGE ===");
            System.out.println("1. Crear usuario");
            System.out.println("2. Mostrar usuario");
            System.out.println("3. Registrar materia");
            System.out.println("4. Mostrar materias");
            System.out.println("5. Registrar asistencia");
            System.out.println("6. Mostrar asistencias");
            System.out.println("7. Registrar calificación");
            System.out.println("8. Mostrar calificaciones");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = scanner.nextInt(); // Captura la opción elegida
                scanner.nextLine(); // Limpia el buffer

                switch (opcion) {
                    case 1:
                        // Crear un nuevo usuario
                        System.out.print("Ingrese ID de usuario: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Ingrese nombre: ");
                        String nombre = scanner.nextLine();

                        System.out.print("Ingrese apellido: ");
                        String apellido = scanner.nextLine();

                        System.out.print("Ingrese email: ");
                        String email = scanner.nextLine();

                        System.out.print("Ingrese rol: ");
                        String rol = scanner.nextLine();

                        usuarioEjemplo = new Usuario(id, nombre, apellido, email, rol);
                        System.out.println("Usuario creado exitosamente.");
                        break;

                    case 2:
                        // Mostrar los datos del usuario
                        if (usuarioEjemplo != null) {
                            usuarioEjemplo.mostrarInformacion();
                        } else {
                            System.out.println("No hay usuario creado.");
                        }
                        break;

                    case 3:
                        // Registrar una nueva materia
                        System.out.print("Ingrese ID de la materia: ");
                        int idMateria = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Ingrese nombre de la materia: ");
                        String nombreMateria = scanner.nextLine();

                        Materia materia = new Materia(idMateria, nombreMateria);
                        listaMaterias.add(materia);
                        System.out.println("Materia registrada correctamente: "+nombreMateria);
                        break;

                    case 4:
                        // Mostrar todas las materias registradas
                        if (listaMaterias.isEmpty()) {
                            System.out.println("No hay materias registradas.");
                        } else {
                            for (Materia m : listaMaterias) {
                                m.mostrarInformacion();
                            }
                        }
                        break;

                    case 5:
                        // Registrar una asistencia
                        try {
                            System.out.print("Ingrese nombre del estudiante: ");
                            String estudiante = scanner.nextLine();

                            System.out.print("Ingrese la fecha (dd/MM/aaaa): ");
                            String fechaTexto = scanner.nextLine();
                            LocalDate fecha;

                            try {
                                fecha = LocalDate.parse(fechaTexto, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                            } catch (DateTimeParseException e) {
                                System.out.println("Formato de fecha inválido. Ingresa dd/mm/aaaa.");
                                break;
                            }

                            System.out.print("Ingrese estado (Presente/Ausente/Retirado/Ausente Justificado): ");
                            String estado = scanner.nextLine();

                            // Validamos el estado ingresado. Si no es válido, lanzamos nuestra excepción personalizada
                            if (!estado.equalsIgnoreCase("Presente") &&
                                !estado.equalsIgnoreCase("Ausente") &&
                                !estado.equalsIgnoreCase("Retirado") &&
                                !estado.equalsIgnoreCase("Ausente Justificado")) {
                                throw new EstadoAsistenciaInvalidoException("Estado de asistencia inválido: " + estado);
                            }

                            // Si no hubo errores, se crea el objeto Asistencia
                            Asistencia asistencia = new Asistencia(estudiante, fecha, estado);
                            listaAsistencias.add(asistencia);
                            System.out.println("Asistencia registrada.");
                        } catch (EstadoAsistenciaInvalidoException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;


                    case 6:
                        // Mostrar asistencias
                        if (listaAsistencias.isEmpty()) {
                            System.out.println("No hay asistencias registradas.");
                        } else {
                            for (Asistencia a : listaAsistencias) {
                                a.mostrarInformacion();
                            }
                        }
                        break;

                    case 7:
                        // Registrar una calificación
                        System.out.print("Ingrese nombre del estudiante: ");
                        String alumno = scanner.nextLine();

                        System.out.print("Ingrese nombre de la actividad: ");
                        String actividad = scanner.nextLine();

                        System.out.print("Ingrese nota (número): ");
                        double nota = scanner.nextDouble();
                        scanner.nextLine();

                        Calificacion calificacion = new Calificacion(alumno, actividad, nota);
                        listaCalificaciones.add(calificacion);
                        System.out.println("Calificación registrada.");
                        break;

                    case 8:
                        // Mostrar calificaciones
                        if (listaCalificaciones.isEmpty()) {
                            System.out.println("No hay calificaciones registradas.");
                        } else {
                            for (Calificacion c : listaCalificaciones) {
                                c.mostrarInformacion();
                            }
                        }
                        break;

                    case 0:
                        System.out.println("Saliendo del sistema...");
                        break;

                    default:
                        System.out.println("Opción inválida.");
                        break;
                }

            } catch (InputMismatchException e) {
                // Maneja errores si el usuario ingresa un valor que no es un número donde se espera uno
                System.out.println("Error: debe ingresar un número válido.");
                scanner.nextLine(); // Limpia el input incorrecto para continuar
                opcion = -1; // Reinicia la opción para repetir el ciclo
            }

        } while (opcion != 0); // Repite mientras no se elija salir

        scanner.close(); // Cierra el lector de consola
    }
}
