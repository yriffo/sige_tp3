package modelo;

import java.util.List;
import java.util.Scanner;
import controlador.SIGE;

/**
 * Clase Preceptor que extiende de Usuario.
 * Representa a un usuario con rol específico de Preceptor.
 * Encapsula el comportamiento asociado a este rol.
 */
public class Preceptor extends Usuario {

    /**
     * Constructor del Preceptor.
     * Utiliza el constructor de Usuario y asigna automáticamente el rol "Preceptor".
     *
     * @param idUsuario ID numérico único del usuario.
     * @param nombre Nombre del preceptor.
     * @param apellido Apellido del preceptor.
     * @param email Correo electrónico del preceptor.
     * @param contrasenia Contraseña del preceptor.
     */
    public Preceptor(int idUsuario, String nombre, String apellido, String email, String contrasenia) {
        super(idUsuario, nombre, apellido, email, contrasenia, List.of("Preceptor"));
    }

    /**
     * Muestra el rol principal del usuario (sobrescribe el método abstracto).
     */
    @Override
    public void mostrarRolPrincipal() {
        System.out.println("Rol principal: Preceptor");
    }

    /**
     * Muestra el menú correspondiente al rol Preceptor.
     * Ejecuta acciones específicas para gestionar asistencia, reuniones, y ver información académica.
     */
    
    public static void mostrarMenu(Scanner scanner) {
        int opcion;
        do {
            System.out.println("\n--- Menú Preceptor ---");
            System.out.println("1. Tomar asistencia de un curso");
            System.out.println("2. Ver asistencias");
            System.out.println("3. Ver asistencias por curso");
            System.out.println("4. Editar asistencia");
            System.out.println("5. Ver calificaciones por curso");
            System.out.println("6. Registrar reunión");
            System.out.println("7. Ver reuniones");
            System.out.println("8. Ver reuniones por estudiante y curso");
            System.out.println("9. Ver bitácora de un estudiante");
            System.out.println("10. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // limpiar buffer

                switch (opcion) {
                    case 1 -> SIGE.registrarAsistenciaPreceptor(scanner);
                    case 2 -> SIGE.verTodasLasAsistencias(scanner);
                    case 3 -> SIGE.verAsistenciasPorCursoYMateria(scanner);
                    case 4 -> SIGE.editarAsistencia(scanner);
                    case 5 -> SIGE.verCalificacionesPorCursoParaPreceptor(scanner);
                    case 6 -> SIGE.registrarReunion(scanner);
                    case 7 -> SIGE.verTodasLasReuniones();
                    case 8 -> SIGE.verReunionesPorEstudianteYCurso(scanner);
                    case 9 -> SIGE.verBitacoraEstudiante(scanner);
                    case 10 -> System.out.println("Saliendo del menú Preceptor...");
                    default -> System.out.println("Opción inválida.");
                }

            } catch (Exception e) {
                System.out.println("Entrada no válida. Intente nuevamente.");
                scanner.nextLine(); // limpia el error
                opcion = 0;
            }

        } while (opcion != 10);
    }
}
