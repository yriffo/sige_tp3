package modelo;

import java.util.Scanner;
import controlador.SIGE;
import java.util.InputMismatchException;
import java.util.List;


/**
 * Clase Directivo que extiende de Usuario.
 * Representa a un usuario con el rol específico de Directivo.
 * Esta clase demuestra herencia y permite encapsular el rol "Directivo"
 * de forma predeterminada al crear una instancia.
 */
public class Directivo extends Usuario {

    /**
     * Constructor de la clase Directivo.
     * Llama al constructor de la clase base (Usuario) y
     * establece automáticamente el rol como "Directivo".
     *
     * @param idUsuario     Identificador único del usuario
     * @param nombre        Nombre del directivo
     * @param apellido      Apellido del directivo
     * @param email         Correo electrónico institucional
     * @param contrasenia   Contraseña para autenticación
     */
    public Directivo(int idUsuario, String nombre, String apellido, String email, String contrasenia) {
        super(idUsuario, nombre, apellido, email, contrasenia, List.of("Directivo"));
    }

    /**
     * Método que imprime en consola el rol principal del usuario.
     * En este caso, "Directivo".
     */
    public void mostrarRolPrincipal() {
        System.out.println("Rol principal: Directivo");
    }
    
        /*
         * Menú de opciones específicas para el rol Directivo.
         * El directivo puede acceder a toda la información del sistema,
         * incluyendo asistencias, calificaciones, reuniones y bitácoras completas.
         */
        public static void mostrarMenu(Scanner scanner) {
            int opcion;
            do {
                System.out.println("\n--- Menú Directivo ---");
                System.out.println("1. Ver todas las asistencias");
                System.out.println("2. Ver calificaciones por curso");
                System.out.println("3. Ver reuniones registradas");
                System.out.println("4. Ver bitácora de un estudiante");
                System.out.println("5. Ver ficha completa de un estudiante");
                System.out.println("6. Salir");
                System.out.print("Seleccione una opción: ");

                try {
                    opcion = scanner.nextInt();
                    scanner.nextLine(); // Limpia el buffer

                    switch (opcion) {
                        case 1 -> SIGE.verTodasLasAsistencias(scanner);
                        case 2 -> SIGE.verCalificacionesPorCursoParaPreceptor(scanner); 
                        case 3 -> SIGE.verTodasLasReuniones(); // Usa método ya definido
                        case 4 -> SIGE.verBitacoraEstudiante(scanner); // Usa método ya definido
                        case 5 -> SIGE.verFichaEstudiante(scanner); // Usa método ya definido
                        case 6 -> System.out.println("Saliendo del menú Directivo...");
                        default -> System.out.println("Opción inválida. Intente nuevamente.");
                    }

                } catch (InputMismatchException e) {
                    System.out.println("Entrada no válida. Intente nuevamente.");
                    scanner.nextLine(); // Limpia error
                    opcion = 0;
                }

            } while (opcion != 6);
        }

}
