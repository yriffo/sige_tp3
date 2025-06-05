package modelo;

import java.util.List;
import java.util.Scanner;
import static controlador.SIGE.*;  // Importa métodos del controlador

/**
 * Clase Docente que extiende de Usuario.
 * Representa a un usuario con rol específico de Docente.
 * Esta clase permite demostrar herencia y encapsula la lógica
 * para asignar automáticamente el rol "Docente".
 */
public class Docente extends Usuario {

    /**
     * Constructor del Docente.
     * Utiliza el constructor de la clase base Usuario y asigna
     * automáticamente el rol "Docente" en la lista de roles.
     *
     * @param idUsuario ID numérico del usuario.
     * @param nombre Nombre del docente.
     * @param apellido Apellido del docente.
     * @param email Correo electrónico del docente.
     * @param contrasenia Contraseña del docente.
     */
    public Docente(int idUsuario, String nombre, String apellido, String email, String contrasenia) {
        // Se llama al constructor de la clase base (Usuario)
        // y se asigna directamente el rol "Docente"
        super(idUsuario, nombre, apellido, email, contrasenia, List.of("Docente"));
    }

    /**
     * Método sobreescrito para mostrar el rol principal del docente.
     * Forma parte del uso de polimorfismo.
     */
    @Override
    public void mostrarRolPrincipal() {
        System.out.println("Rol principal: Docente");
    }

    /**
     * Muestra el menú específico del rol Docente.
     * Esta versión delega la lógica en métodos de SIGE pero
     * centraliza la interacción desde esta clase (principio de responsabilidad).
     */
    public void mostrarMenu(Scanner scanner) {
        int opcion;
        do {
            System.out.println("\n--- Menú Docente ---");
            System.out.println("1. Registrar o modificar asistencia");
            System.out.println("2. Ver asistencias por curso y materia");
            System.out.println("3. Editar asistencia");
            System.out.println("4. Registrar o modificar calificación");
            System.out.println("5. Ver calificaciones del curso por materia");
            System.out.println("6. Registrar nueva actividad");
            System.out.println("7. Ver actividades registradas");
            System.out.println("8. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpia el buffer del scanner

                switch (opcion) {
                    case 1 -> registrarAsistenciaDocente(scanner);
                    case 2 -> verAsistenciasPorCursoYMateria(scanner);
                    case 3 -> editarAsistencia(scanner);
                    case 4 -> registrarCalificacion();
                    case 5 -> verCalificacionesPorCursoYMateria(scanner);
                    case 6 -> registrarActividad(scanner);
                    case 7 -> verActividadesRegistradas(scanner);
                    case 8 -> System.out.println("Saliendo del menú Docente...");
                    default -> System.out.println("Opción inválida. Intente nuevamente.");
                }

            } catch (Exception e) {
                System.out.println("Entrada no válida. Ingrese un número.");
                scanner.nextLine(); // Limpia el error
                opcion = 0; // Reinicia opción para mantener el bucle
            }

        } while (opcion != 8);
    }
}
