package modelo;

import java.util.List;
import java.util.Scanner;
import static controlador.SIGE.*;  // Importamos métodos del controlador

/**
 * Clase Asesor que extiende de Usuario.
 * Representa a un usuario con rol específico de Asesor pedagógico.
 * Encapsula el comportamiento vinculado a este rol.
 */
public class Asesor extends Usuario {

    /**
     * Constructor del Asesor.
     * Usa el constructor de Usuario e impone el rol "Asesor".
     *
     * @param idUsuario ID único del usuario.
     * @param nombre Nombre del asesor.
     * @param apellido Apellido del asesor.
     * @param email Correo electrónico del asesor.
     * @param contrasenia Contraseña del asesor.
     */
    public Asesor(int idUsuario, String nombre, String apellido, String email, String contrasenia) {
        super(idUsuario, nombre, apellido, email, contrasenia, List.of("Asesor"));
    }

    /**
     * Muestra por consola el rol principal.
     * Útil para feedback inmediato al iniciar sesión.
     */
    @Override
    public void mostrarRolPrincipal() {
        System.out.println("Rol principal: Asesor pedagógico");
    }

    /**
     * Muestra el menú correspondiente al rol Asesor pedagógico.
     * Desde este menú puede consultar fichas, bitácoras, registrar reuniones y buscar estudiantes.
     */
    
    public void mostrarMenu(Scanner scanner) {
        int opcion;
        do {
            System.out.println("\n--- Menú Asesor Pedagógico ---");
            System.out.println("1. Ver ficha del estudiante");
            System.out.println("2. Ver bitácora del estudiante");
            System.out.println("3. Registrar reunión");
            System.out.println("4. Buscar estudiante");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpia el buffer

                switch (opcion) {
                    case 1 -> verFichaEstudiante(scanner);
                    case 2 -> verBitacoraEstudiante(scanner);
                    case 3 -> registrarReunion(scanner);
                    case 4 -> buscarEstudiante(scanner);
                    case 5 -> System.out.println("Saliendo del menú Asesor...");
                    default -> System.out.println("Opción inválida. Intente nuevamente.");
                }

            } catch (Exception e) {
                System.out.println("Entrada inválida. Ingrese un número.");
                scanner.nextLine(); // limpiar error
                opcion = 0;
            }

        } while (opcion != 5);
    }
}

