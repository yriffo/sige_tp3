package controlador;

// Importaciones de clases del modelo
import modelo.Usuario;
import modelo.Asistencia;
import modelo.Calificacion;
import modelo.Materia;
import modelo.Reunion;
import modelo.Estudiante;
import modelo.Actividad;
import modelo.Docente;
import modelo.Preceptor;
import modelo.Asesor;
import modelo.Directivo;
import excepciones.EstadoAsistenciaInvalidoException;
import controlador.BitacoraEventos;

// Importaciones de utilidades Java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

    /**
     * Clase principal SIGE - Sistema Integral de Gestión Escolar.
     * Simula el funcionamiento general del sistema desde consola,
     * gestionando acciones según el rol del usuario.
     
     */
    public class SIGE { 

        // --- Estructuras de datos simuladas para esta versión del sistema ---
        private static List<Calificacion> calificaciones = new ArrayList<>();
        private static List<Asistencia> asistencias = new ArrayList<>();
        private static List<Reunion> reuniones = new ArrayList<>();
        private static List<Estudiante> estudiantesSimulados = new ArrayList<>();
        private static List<Actividad> actividadesSimuladas = new ArrayList<>();
        private static List<Usuario> usuariosSimulados = new ArrayList<>();


        /**
         * Método principal que inicia el sistema SIGE por consola.
         * Permite ingresar como distintos usuarios simulados y acceder
         * al menú correspondiente según el rol asignado.
         */
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            // --- Carga de usuarios simulados con diferentes roles (herencia + múltiples roles) ---

            // Usuarios con UN solo rol (instancias de subclases específicas)
            usuariosSimulados.add(new Docente(1, "María", "González", "maria@mail.com", "doc123"));
            usuariosSimulados.add(new Preceptor(2, "Carlos", "Pérez", "carlos@mail.com", "pre123"));
            usuariosSimulados.add(new Asesor(3, "Laura", "Ramírez", "laura@mail.com", "ase123"));
            usuariosSimulados.add(new Directivo(4, "Ana", "Martínez", "ana@mail.com", "dir123"));

            // Usuarios con múltiples roles (usando clase Usuario con lista de roles)
            usuariosSimulados.add(new Usuario(5, "Pedro", "Lopez", "pedro@mail.com", "multi123", List.of("Docente", "Preceptor")));
            usuariosSimulados.add(new Usuario(6, "Marcela", "Torres", "marcela@mail.com", "trirol123", List.of("Docente", "Asesor", "Preceptor")));
            usuariosSimulados.add(new Usuario(7, "Federico", "Juarez", "fede@mail.com", "dirpre123", List.of("Preceptor", "Directivo")));
            usuariosSimulados.add(new Usuario(8, "Gisella", "Ibarra", "gisella@mail.com", "todos123", List.of("Docente", "Preceptor", "Asesor", "Directivo")));

            // Carga de estudiantes simulados en memoria
            estudiantesSimulados.add(new Estudiante(1, "Sofia", "Perez", "45678901", LocalDate.of(2010, 5, 14), "1A", "Calle 123", "2991234567", "María Pérez", "sofiap@gmail.com"));
            estudiantesSimulados.add(new Estudiante(2, "Lucas", "Martinez", "45789123", LocalDate.of(2009, 8, 30), "2B", "Av. San Martín 456", "2997654321", "Carlos Martínez", "lucasm@gmail.com"));
            estudiantesSimulados.add(new Estudiante(3, "Valentina", "Lopez", "45891234", LocalDate.of(2011, 3, 22), "1A", "Belgrano 789", "2999876543", "Laura López", "valenl@gmail.com"));

            // --- Inicio del flujo de autenticación ---
            System.out.println("\nInicio de sesión en SIGE");
            System.out.print("Correo electrónico: ");
            String emailIngresado = scanner.nextLine();
            System.out.print("Contraseña: ");
            String contraseniaIngresada = scanner.nextLine();

            // Búsqueda del usuario por email y contraseña
            Usuario usuarioActual = usuariosSimulados.stream()
                    .filter(u -> u.getEmail().equalsIgnoreCase(emailIngresado)
                            && u.getContrasenia().equals(contraseniaIngresada))
                    .findFirst()
                    .orElse(null);

            // Validación de acceso
            if (usuarioActual == null) {
                System.out.println("Datos incorrectos: el correo electrónico o la contraseña no coinciden.");
                return;
            }

            // Mensaje de bienvenida personalizado
            System.out.println("\nBienvenida/o al sistema SIGE, " + usuarioActual.getNombre() + " " + usuarioActual.getApellido());
            System.out.println("Roles disponibles: " + String.join(", ", usuarioActual.getRoles()));
            usuarioActual.mostrarRolPrincipal(); // Esto sí es polimórfico

            



            // Redirección al menú correspondiente según rol. Esto permite que un mismo usuario vea todos los menús que le correspondan, uno después del otro.
            // --- Menú según cantidad de roles ---
            if (usuarioActual.getRoles().size() == 1) {
                String rolUnico = usuarioActual.getRoles().get(0);
                switch (rolUnico) {
                    case "Docente" -> mostrarMenuDocente(scanner);
                    case "Preceptor" -> mostrarMenuPreceptor(scanner);
                    case "Asesor" -> mostrarMenuAsesor(scanner);
                    case "Directivo" -> mostrarMenuDirectivo(scanner);
                    default -> System.out.println("Rol no reconocido.");
                }
            } else {
                // CASO: El usuario tiene más de un rol asignado (ej: Docente y Preceptor)

                    // Se obtiene la lista de roles del usuario
                    List<String> roles = usuarioActual.getRoles();

                    // Variable para guardar la opción elegida del menú general
                    int opcion;

                    // Se inicia un bucle que mostrará el menú general de roles mientras el usuario no elija "Salir"
                    do {
                        System.out.println("\n--- Menú del sistema (usuario con múltiples roles) ---");

                        int count = 1; // Usamos este contador para numerar las opciones del menú dinámicamente
                        List<Runnable> acciones = new ArrayList<>(); // Lista de acciones (funciones) a ejecutar según el rol elegido

                        // Recorremos cada rol que tiene el usuario y agregamos una entrada al menú y su acción asociada
                        for (String rol : roles) {
                            switch (rol) {
                                case "Docente" -> {
                                    // Se muestra la opción "Menú Docente" y se guarda la función correspondiente en la lista
                                    System.out.println(count + ". Menú Docente");
                                    acciones.add(() -> mostrarMenuDocente(scanner));
                                    count++;
                                }
                                case "Preceptor" -> {
                                    System.out.println(count + ". Menú Preceptor");
                                    acciones.add(() -> mostrarMenuPreceptor(scanner));
                                    count++;
                                }
                                case "Asesor" -> {
                                    System.out.println(count + ". Menú Asesor");
                                    acciones.add(() -> mostrarMenuAsesor(scanner));
                                    count++;
                                }
                                case "Directivo" -> {
                                    System.out.println(count + ". Menú Directivo");
                                    acciones.add(() -> mostrarMenuDirectivo(scanner));
                                    count++;
                                }
                            }
                        }

                        // Agregamos la última opción para salir del sistema
                        System.out.println(count + ". Salir");

                        try {
                            // Solicitamos al usuario que elija una opción del menú
                            System.out.print("Seleccione una opción: ");
                            opcion = scanner.nextInt();
                            scanner.nextLine(); // Limpiamos el buffer del scanner

                            // Si eligió una opción válida (dentro del rango de roles disponibles)
                            if (opcion >= 1 && opcion < count) {
                                acciones.get(opcion - 1).run(); // Ejecutamos la acción correspondiente al rol
                            } else if (opcion != count) {
                                // Si la opción es inválida (fuera de rango y distinta de "Salir")
                                System.out.println("Opción inválida. Intente nuevamente.");
                            }

                        } catch (InputMismatchException e) {
                            // En caso de que el usuario ingrese un texto en lugar de un número
                            System.out.println("Entrada inválida. Ingrese un número.");
                            scanner.nextLine(); // Limpiamos el error en el buffer
                            opcion = 0; // Reiniciamos la opción para continuar en el bucle
                        }

                    } while (opcion != roles.size() + 1); // Se repite hasta que elija "Salir"
                }
        }
     /**
     * Menú de opciones específicas para el rol Docente.
     * Incluye funciones de asistencia y calificaciones.
     */
        public static void mostrarMenuDocente(Scanner scanner){
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

                } catch (InputMismatchException e) {
                    System.out.println("Entrada no válida. Ingrese un número.");
                    scanner.nextLine(); // Limpia el error
                    opcion = 0; // Reinicia opción para mantener el bucle
                }

            } while (opcion != 8);
        }
        

    /**
     * Método registrarAsistenciaDocente
     * Permite al docente registrar asistencias de los estudiantes
     * de un curso y materia específicos.
     */
    public static void registrarAsistenciaDocente(Scanner scanner) {
        System.out.print("Ingrese el curso (ej: 1A): ");
        String curso = scanner.nextLine();

        System.out.print("Ingrese la materia: ");
        String materia = scanner.nextLine();

        System.out.print("Ingrese la fecha (dd/MM/yyyy): ");
        String fechaTexto = scanner.nextLine();

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fecha;
        try {
            fecha = LocalDate.parse(fechaTexto, formato);
        } catch (Exception e) {
            System.out.println("Fecha inválida.");
            return;
        }

        // Filtrar estudiantes del curso ingresado
        List<Estudiante> estudiantesCurso = estudiantesSimulados.stream()
            .filter(e -> e.getCurso().equalsIgnoreCase(curso))
            .toList();

        if (estudiantesCurso.isEmpty()) {
            System.out.println("No hay estudiantes cargados para ese curso.");
            return;
        }

        for (Estudiante estudiante : estudiantesCurso) {
            String nombreCompleto = estudiante.getNombre() + " " + estudiante.getApellido();

            System.out.print("Estado para " + nombreCompleto + ": ");
            String estado = scanner.nextLine();

            try {
                validarEstadoAsistencia(estado);

                // Registrar asistencia en la lista
                asistencias.add(new Asistencia(nombreCompleto, materia, fecha, estado));
                System.out.println("Asistencia registrada para " + nombreCompleto);

                // Generar evento en la bitácora
                BitacoraEventos.registrarEvento(
                    estudiante,
                    "Asistencia registrada como: " + estado,
                    "Asistencia"
                );

            } catch (EstadoAsistenciaInvalidoException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

        /**
         * Método verAsistenciasPorCursoYMateria
         * Muestra las asistencias filtradas por curso y materia desde el rol Docente.
         */
        public static void verAsistenciasPorCursoYMateria(Scanner scanner) {
            System.out.print("Ingrese el curso (ej: 1A): ");
            String curso = scanner.nextLine();

            System.out.print("Ingrese la materia: ");
            String materia = scanner.nextLine();

            boolean seEncontraron = false;

            for (Asistencia asistencia : asistencias) {
                for (Estudiante estudiante : estudiantesSimulados) {
                    String nombreCompleto = estudiante.getNombre() + " " + estudiante.getApellido();

                    if (nombreCompleto.equalsIgnoreCase(asistencia.getNombreEstudiante())
                            && estudiante.getCurso().equalsIgnoreCase(curso)
                            && asistencia.getNombreMateria().equalsIgnoreCase(materia)) {

                        System.out.println("Estudiante: " + nombreCompleto);
                        System.out.println("Fecha: " + asistencia.getFecha());
                        System.out.println("Estado: " + asistencia.getEstado());
                        System.out.println("------------------------------");
                        seEncontraron = true;
                    }
                }
            }

            if (!seEncontraron) {
                System.out.println("No se encontraron asistencias para ese curso y materia.");
            }
        }
        
         /*
         * Permite modificar el estado de una asistencia previamente registrada.
         * Se solicita el nombre del estudiante, la materia y la fecha exacta.
         * Luego se reemplaza el estado por uno nuevo validado.
         */
        public static void editarAsistencia(Scanner scanner) {
            System.out.print("Nombre del estudiante: ");
            String estudiante = scanner.nextLine();

            System.out.print("Materia: ");
            String materia = scanner.nextLine();

            System.out.print("Fecha (dd/MM/yyyy): ");
            String fechaTexto = scanner.nextLine();

            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fecha;
            try {
                fecha = LocalDate.parse(fechaTexto, formato);
            } catch (Exception e) {
                System.out.println("Fecha inválida.");
                return;
            }

            // Buscar la asistencia específica en la lista
            Asistencia encontrada = null;
            for (Asistencia a : asistencias) {
                if (a.getNombreEstudiante().equalsIgnoreCase(estudiante) &&
                    a.getNombreMateria().equalsIgnoreCase(materia) &&
                    a.getFecha().equals(fecha)) {
                    encontrada = a;
                    break;
                }
            }

            if (encontrada == null) {
                System.out.println("No se encontró un registro de asistencia con esos datos.");
                return;
            }

            // Solicitar nuevo estado
            System.out.println("Estado actual: " + encontrada.getEstado());
            System.out.print("Nuevo estado (Presente / Ausente / Retirado): ");
            String nuevoEstado = scanner.nextLine();

            try {
                validarEstadoAsistencia(nuevoEstado);
                encontrada.setEstado(nuevoEstado);
                System.out.println("Estado actualizado correctamente.");
            } catch (EstadoAsistenciaInvalidoException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        
        /**
         * Verifica si el estado de asistencia ingresado es válido.
         * Acepta únicamente "Presente", "Ausente" o "Retirado" (ignorando mayúsculas/minúsculas).
         * Si no lo es, lanza una excepción personalizada.
         */
        public static void validarEstadoAsistencia(String estado) throws EstadoAsistenciaInvalidoException {
            if (!(estado.equalsIgnoreCase("Presente") || estado.equalsIgnoreCase("Ausente") ||estado.equalsIgnoreCase("Retirado"))){
                throw new EstadoAsistenciaInvalidoException("Estado inválido. Use Presente, Ausente o Retirado.");
            }
        }
        
                /**
         * Permite registrar una nueva actividad por parte del docente.
         * Se ingresan los datos principales y se almacena en la lista simulada.
         */
        public static void registrarActividad(Scanner scanner) {
            System.out.println("\n--- Registro de nueva actividad ---");

            int nuevoId = actividadesSimuladas.size() + 1;

            System.out.print("Ingrese nombre de la actividad: ");
            String nombre = scanner.nextLine();

            System.out.print("Ingrese materia: ");
            String materia = scanner.nextLine();

            System.out.print("Ingrese curso: ");
            String curso = scanner.nextLine();
            
            System.out.print("Ingrese Tipo Calificacion (Numerica o Conceptual): ");
            String tipoCalificacion = scanner.nextLine();

            System.out.print("Ingrese fecha de inicio (dd/MM/yyyy): ");
            String fechaInicioTexto = scanner.nextLine();

            System.out.print("Ingrese fecha de entrega (dd/MM/yyyy): ");
            String fechaEntregaTexto = scanner.nextLine();

            System.out.print("Ingrese observación (opcional): ");
            String observacion = scanner.nextLine();

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate fechaInicio = LocalDate.parse(fechaInicioTexto, formatter);
                LocalDate fechaEntrega = LocalDate.parse(fechaEntregaTexto, formatter);

                Actividad act = new Actividad(nuevoId, nombre,materia, curso, tipoCalificacion, fechaInicio,fechaEntrega, observacion);
                actividadesSimuladas.add(act);

                System.out.println("Actividad registrada correctamente.");
                System.out.println(act); // Muestra resumen

            } catch (Exception e) {
                System.out.println("Error al procesar fechas. Verifique el formato.");
            }
        }
            
                /**
        * Muestra las actividades filtradas por curso y materia desde el rol Docente.
        * Se solicita al usuario ingresar el curso y la materia para visualizar solo
        * las actividades relacionadas.
        */
       public static void verActividadesRegistradas(Scanner scanner) {   
           // Solicitar filtros
        System.out.print("Ingrese el curso (ej: 1A): ");
        String curso = scanner.nextLine();

        System.out.print("Ingrese la materia: ");
        String materia = scanner.nextLine();

        boolean seEncontraron = false;

        System.out.println("\n--- Actividades registradas para el curso " + curso + " y la materia " + materia + " ---");
        for (Actividad act : actividadesSimuladas) {
            if (act.getCurso().equalsIgnoreCase(curso) && act.getMateria().equalsIgnoreCase(materia)) {
                System.out.println("ID: " + act.getIdActividad());
                System.out.println("Nombre: " + act.getNombreActividad());
                System.out.println("Tipo de Calificación: " + act.getTipoCalificacion());
                System.out.println("Fecha inicio: " + act.getFechaInicio());
                System.out.println("Fecha entrega: " + act.getFechaEntrega());
                System.out.println("Observación: " + act.getObservacion());
                System.out.println("-----------------------------");
                seEncontraron = true;
            }
        }

        if (!seEncontraron) {
            System.out.println("No se encontraron actividades para ese curso y materia.");
        }
    }
      
            
        /**
         * Permite registrar una o más calificaciones para los estudiantes simulados.
         * Se ingresan los datos de la materia, las actividades, y luego se carga la nota para cada estudiante.
         * Cada calificación también se agrega a la bitácora del estudiante.
         */
        public static void registrarCalificacion() {
            Scanner scanner = new Scanner(System.in);
            List<String> estudiantes = List.of("Sofía Pérez", "Lucas Martínez", "Valentina López");

            System.out.print("Ingrese la materia: ");
            String materia = scanner.nextLine();

            System.out.print("Cantidad de actividades: ");
            int actividades = scanner.nextInt();
            scanner.nextLine(); // limpia el buffer

            for (int i = 1; i <= actividades; i++) {
                System.out.print("Nombre de la actividad " + i + ": ");
                String actividad = scanner.nextLine();

                for (String nombreEstudiante : estudiantes) {
                    int nota;
                    do {
                        System.out.print("Nota para " + nombreEstudiante + " (1-10): ");
                        nota = scanner.nextInt();
                    } while (nota < 1 || nota > 10);
                    scanner.nextLine();

                    // Registrar calificación
                    calificaciones.add(new Calificacion(nombreEstudiante, materia, actividad, nota));

                    // Buscar el estudiante real para registrar evento en bitácora
                    for (Estudiante e : estudiantesSimulados) {
                        String nombreCompleto = e.getNombre() + " " + e.getApellido();
                        if (nombreCompleto.equalsIgnoreCase(nombreEstudiante)) {
                            BitacoraEventos.registrarEvento(e, "Calificación registrada: " + nota, "Calificación");
                        }
                    }
                }
            }
        }
        
         /**
         * Muestra las calificaciones filtradas por curso y materia.
         * Solo se listan aquellas donde el estudiante coincida con ambos filtros.
         */
        public static void verCalificacionesPorCursoYMateria(Scanner scanner) {
            System.out.print("Ingrese el curso (ej: 1A): ");
            String curso = scanner.nextLine();

            System.out.print("Ingrese la materia: ");
            String materia = scanner.nextLine();

            boolean seEncontraron = false;

            for (Calificacion calificacion : calificaciones) {
                for (Estudiante estudiante : estudiantesSimulados) {
                    String nombreCompleto = estudiante.getNombre() + " " + estudiante.getApellido();

                    if (nombreCompleto.equalsIgnoreCase(calificacion.getNombreEstudiante()) &&
                        estudiante.getCurso().equalsIgnoreCase(curso) &&
                        calificacion.getNombreMateria().equalsIgnoreCase(materia)) {

                        System.out.println("Estudiante: " + nombreCompleto);
                        System.out.println("Actividad: " + calificacion.getNombreActividad());
                        System.out.println("Nota: " + calificacion.getNota());
                        System.out.println("------------------------------");
                        seEncontraron = true;
                    }
                }
            }

            if (!seEncontraron) {
                System.out.println("No se encontraron calificaciones para ese curso y materia.");
            }
        }
        
        /**
         * Menú de opciones específicas para el rol Preceptor.
         * Incluye funciones de asistencia, reuniones, visualización de calificaciones y bitácora del estudiante.
         */
        public static void mostrarMenuPreceptor(Scanner scanner) {
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
                    scanner.nextLine(); // limpia buffer

                    switch (opcion) {
                        case 1 -> registrarAsistenciaPreceptor(scanner);
                        case 2 -> verTodasLasAsistencias();
                        case 3 -> verAsistenciasPorCurso(scanner);
                        case 4 -> editarAsistencia(scanner);
                        case 5 -> verCalificacionesPorCursoParaPreceptor(scanner);
                        case 6 -> registrarReunion(scanner);
                        case 7 -> verTodasLasReuniones();
                        case 8 -> verReunionesPorEstudianteYCurso(scanner);
                        case 9 -> verBitacoraEstudiante(scanner);
                        case 10 -> System.out.println("Saliendo del menú Preceptor...");
                        default -> System.out.println("Opción inválida.");
                    }

                } catch (InputMismatchException e) {
                    System.out.println("Entrada no válida. Intente nuevamente.");
                    scanner.nextLine(); // limpiar error
                    opcion = 0;
                }

            } while (opcion != 10);
        }
        /**
         * Permite al Preceptor registrar la asistencia de todos los estudiantes de un curso en una fecha específica.
         * Se ingresan los datos de curso, materia y fecha. Luego se pide el estado de cada estudiante.
         * Se registra cada asistencia y se genera un evento en la bitácora del estudiante.
         */
        public static void registrarAsistenciaPreceptor(Scanner scanner) {
            System.out.print("Ingrese el curso (ej: 1A): ");
            String curso = scanner.nextLine();

            System.out.print("Ingrese la materia: ");
            String materia = scanner.nextLine();

            System.out.print("Ingrese la fecha (dd/MM/yyyy): ");
            String fechaTexto = scanner.nextLine();

            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fecha;
            try {
                fecha = LocalDate.parse(fechaTexto, formato);
            } catch (Exception e) {
                System.out.println("Fecha inválida.");
                return;
            }

            // Filtrar estudiantes del curso
            List<Estudiante> estudiantesCurso = estudiantesSimulados.stream()
                .filter(e -> e.getCurso().equalsIgnoreCase(curso))
                .toList();

            if (estudiantesCurso.isEmpty()) {
                System.out.println("No hay estudiantes cargados para ese curso.");
                return;
            }

            for (Estudiante estudiante : estudiantesCurso) {
                String nombreCompleto = estudiante.getNombre() + " " + estudiante.getApellido();

                System.out.print("Estado para " + nombreCompleto + ": ");
                String estado = scanner.nextLine();

                try {
                    validarEstadoAsistencia(estado);

                    // Registrar asistencia
                    asistencias.add(new Asistencia(nombreCompleto, materia, fecha, estado));
                    System.out.println("Asistencia registrada para " + nombreCompleto);

                    // Generar evento
                    BitacoraEventos.registrarEvento(estudiante, "Asistencia registrada como: " + estado, "Asistencia");

                } catch (EstadoAsistenciaInvalidoException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
        /**
         * Muestra todas las asistencias registradas en el sistema, sin aplicar filtros.
         * Cada registro incluye el nombre del estudiante, materia, fecha y estado.
         */
        public static void verTodasLasAsistencias() {
            if (asistencias.isEmpty()) {
                System.out.println("No hay asistencias registradas en el sistema.");
                return;
            }

            System.out.println("\nAsistencias registradas:");
            for (Asistencia a : asistencias) {
                System.out.println("Estudiante: " + a.getNombreEstudiante());
                System.out.println("Materia: " + a.getNombreMateria());
                System.out.println("Fecha: " + a.getFecha());
                System.out.println("Estado: " + a.getEstado());
                System.out.println("------------------------------");
            }
        }
        /**
         * Muestra las asistencias filtradas únicamente por curso, sin tener en cuenta la materia.
         * El Preceptor puede ver todos los registros de asistencia de los estudiantes que pertenecen al curso indicado.
         */
        public static void verAsistenciasPorCurso(Scanner scanner) {
            System.out.print("Ingrese el curso (ej: 1A): ");
            String curso = scanner.nextLine();

            boolean seEncontraron = false;

            for (Asistencia asistencia : asistencias) {
                // Buscar si el estudiante de esa asistencia pertenece al curso ingresado
                for (Estudiante estudiante : estudiantesSimulados) {
                    String nombreCompleto = estudiante.getNombre() + " " + estudiante.getApellido();

                    if (nombreCompleto.equalsIgnoreCase(asistencia.getNombreEstudiante()) &&
                        estudiante.getCurso().equalsIgnoreCase(curso)) {

                        System.out.println("Estudiante: " + nombreCompleto);
                        System.out.println("Materia: " + asistencia.getNombreMateria());
                        System.out.println("Fecha: " + asistencia.getFecha());
                        System.out.println("Estado: " + asistencia.getEstado());
                        System.out.println("------------------------------");
                        seEncontraron = true;
                    }
                }
            }

            if (!seEncontraron) {
                System.out.println("No se encontraron asistencias para ese curso.");
            }
        }
        /**
         * Muestra las calificaciones de todos los estudiantes de un curso determinado.
         * Se muestran todas las actividades registradas, agrupadas por estudiante.
         */
        public static void verCalificacionesPorCursoParaPreceptor(Scanner scanner) {
            System.out.print("Ingrese el curso (ej: 1° A): ");
            String curso = scanner.nextLine();

            boolean seEncontraron = false;

            for (Calificacion calificacion : calificaciones) {
                for (Estudiante estudiante : estudiantesSimulados) {
                    String nombreCompleto = estudiante.getNombre() + " " + estudiante.getApellido();

                    // Verifica si la calificación corresponde a un estudiante del curso
                    if (nombreCompleto.equalsIgnoreCase(calificacion.getNombreEstudiante()) &&
                        estudiante.getCurso().equalsIgnoreCase(curso)) {

                        System.out.println("Estudiante: " + nombreCompleto);
                        System.out.println("Materia: " + calificacion.getNombreMateria());
                        System.out.println("Actividad: " + calificacion.getNombreActividad());
                        System.out.println("Nota: " + calificacion.getNota());
                        System.out.println("------------------------------");
                        seEncontraron = true;
                    }
                }
            }

            if (!seEncontraron) {
                System.out.println("No se encontraron calificaciones para ese curso.");
            }
        }
        /**
         * Permite al Preceptor registrar una reunión con un estudiante o grupo.
         * Se ingresan los datos principales como curso, motivo, participantes y fecha.
         * El registro se guarda en la lista de reuniones y se genera un evento en la bitácora del estudiante.
         */
        public static void registrarReunion(Scanner scanner) {
            System.out.print("Ingrese el curso (ej: 1A): ");
            String curso = scanner.nextLine();

            System.out.print("Motivo de la reunión: ");
            String motivo = scanner.nextLine();

            System.out.print("Participantes de la reunión: ");
            String participantes = scanner.nextLine();

            System.out.print("Ingrese la fecha (dd/MM/yyyy): ");
            String fechaTexto = scanner.nextLine();

            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fecha;
            try {
                fecha = LocalDate.parse(fechaTexto, formato);
            } catch (Exception e) {
                System.out.println("Fecha inválida.");
                return;
            }

            // Verifica si hay estudiantes en ese curso
            boolean seRegistróAlMenosUna = false;

            for (Estudiante estudiante : estudiantesSimulados) {
                if (estudiante.getCurso().equalsIgnoreCase(curso)) {

                    // Crea reunión para ese estudiante
                    Reunion reunion = new Reunion(
                        estudiante.getNombre() + " " + estudiante.getApellido(),
                        curso,
                        motivo,
                        participantes,
                        fecha
                    );

                    reuniones.add(reunion);

                    // Registra en bitácora del estudiante
                    BitacoraEventos.registrarEvento(
                        estudiante,
                        "Reunión registrada. Motivo: " + motivo,
                        "Reunión"
                    );

                    seRegistróAlMenosUna = true;
                }
            }

            if (seRegistróAlMenosUna) {
                System.out.println("Reunión registrada para estudiantes del curso " + curso + ".");
            } else {
                System.out.println("No se encontraron estudiantes en el curso ingresado.");
            }
        }
        /**
         * Muestra todas las reuniones registradas en el sistema.
         * Cada reunión incluye estudiante, curso, motivo, participantes y fecha.
         */
        public static void verTodasLasReuniones() {
            if (reuniones.isEmpty()) {
                System.out.println("No hay reuniones registradas en el sistema.");
                return;
            }

            System.out.println("\nReuniones registradas:");
            for (Reunion r : reuniones) {
                System.out.println("Estudiante: " + r.getNombreEstudiante());
                System.out.println("Curso: " + r.getCurso());
                System.out.println("Motivo: " + r.getMotivo());
                System.out.println("Participantes: " + r.getParticipantes());
                System.out.println("Fecha: " + r.getFecha());
                System.out.println("------------------------------");
            }
        }
        
            /**
            * Muestra las reuniones asociadas a un estudiante en un curso determinado.
            */
           public static void verReunionesPorEstudianteYCurso(Scanner scanner) {
               Estudiante estudiante = buscarEstudiante(scanner);
               if (estudiante == null) return;

               System.out.print("Ingrese el curso del estudiante (ej: 1A): ");
               String curso = scanner.nextLine();

               boolean seEncontraron = false;

               for (Reunion reunion : reuniones) {
                   if (reunion.getNombreEstudiante().equalsIgnoreCase(estudiante.getNombre() + " " + estudiante.getApellido()) &&
                       reunion.getCurso().equalsIgnoreCase(curso)) {

                       System.out.println("\nReunión encontrada:");
                       System.out.println("Estudiante: " + reunion.getNombreEstudiante());
                       System.out.println("Curso: " + reunion.getCurso());
                       System.out.println("Motivo: " + reunion.getMotivo());
                       System.out.println("Participantes: " + reunion.getParticipantes());
                       System.out.println("Fecha: " + reunion.getFecha());
                       System.out.println("------------------------------");
                       seEncontraron = true;
                   }
               }

               if (!seEncontraron) {
                   System.out.println("No se encontraron reuniones para este estudiante en ese curso.");
               }
           }

        
            /**
            * Muestra la bitácora completa de eventos de un estudiante específico.
            */
           public static void verBitacoraEstudiante(Scanner scanner) {
               Estudiante estudiante = buscarEstudiante(scanner);
               if (estudiante == null) return;

               List<modelo.Evento> eventos = estudiante.getBitacora();

               if (eventos.isEmpty()) {
                   System.out.println("No hay eventos registrados para este estudiante.");
                   return;
               }

               eventos.sort((e1, e2) -> e1.getFechaHora().compareTo(e2.getFechaHora())); // Orden cronológico

               System.out.println("\nBitácora del estudiante " + estudiante.getNombre() + " " + estudiante.getApellido() + ":");
               for (modelo.Evento evento : eventos) {
                   System.out.println(evento);
               }
           }
        
        /**
         * Menú de opciones específicas para el rol Asesor.
         * Permite registrar reuniones, ver bitácora completa de estudiantes, 
         * y acceder a toda la información pedagógica disponible.
         */
        public static void mostrarMenuAsesor(Scanner scanner) {
            int opcion;
            do {
                System.out.println("\n--- Menú Asesor ---");
                System.out.println("1. Ver información de un estudiante");
                System.out.println("2. Ver bitácora del estudiante");
                System.out.println("3. Registrar reunión");
                System.out.println("4. Buscar estudiante");
                System.out.println("5. Salir");
                System.out.print("Seleccione una opción: ");

                try {
                    opcion = scanner.nextInt();
                    scanner.nextLine(); // Limpia buffer

                    switch (opcion) {
                        case 1 -> verFichaEstudiante(scanner);
                        case 2 -> verBitacoraEstudiante(scanner);
                        case 3 -> registrarReunion(scanner);
                        case 4 -> buscarEstudiante(scanner);
                        case 5 -> System.out.println("Saliendo del menú Asesor...");
                        default -> System.out.println("Opción inválida. Intente nuevamente.");
                    }

                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Ingrese un número.");
                    scanner.nextLine();
                    opcion = 0;
                }

            } while (opcion != 5);
        }
        /**
        * Muestra una ficha resumen del estudiante con información académica y de contacto.
        * Incluye nombre, curso, DNI, dirección, teléfono y email del responsable.
        */
       public static void verFichaEstudiante(Scanner scanner) {
           Estudiante estudiante = buscarEstudiante(scanner);
           if (estudiante == null) return;

           // Mostramos directamente la información del estudiante encontrado
           System.out.println("\nFicha del estudiante:");
           System.out.println("Nombre: " + estudiante.getNombre() + " " + estudiante.getApellido());
           System.out.println("DNI: " + estudiante.getDni());
           System.out.println("Curso: " + estudiante.getCurso());
           System.out.println("Dirección: " + estudiante.getDireccion());
           System.out.println("Teléfono: " + estudiante.getTelefono());
           System.out.println("Email del responsable: " + estudiante.getEmail());
       }

        
        /*
         * Menú de opciones específicas para el rol Directivo.
         * El directivo puede acceder a toda la información del sistema,
         * incluyendo asistencias, calificaciones, reuniones y bitácoras completas.
         */
        public static void mostrarMenuDirectivo(Scanner scanner) {
            int opcion;
            do {
                System.out.println("\n--- Menú Directivo ---");
                System.out.println("1. Ver asistencias por curso");
                System.out.println("2. Ver calificaciones por curso");
                System.out.println("3. Ver reuniones registradas");
                System.out.println("4. Ver bitácora de un estudiante");
                System.out.println("5. Ver ficha completa de un estudiante");
                System.out.println("6. Buscar estudiante");
                System.out.println("7. Salir");
                System.out.print("Seleccione una opción: ");

                try {
                    opcion = scanner.nextInt();
                    scanner.nextLine(); // Limpia el buffer

                    switch (opcion) {
                        case 1 -> verAsistenciasPorCurso(scanner);
                        case 2 -> verCalificacionesPorCursoParaPreceptor(scanner); // Usa método ya definido
                        case 3 -> verTodasLasReuniones(); // Usa método ya definido
                        case 4 -> verBitacoraEstudiante(scanner); // Usa método ya definido
                        case 5 -> verFichaEstudiante(scanner); // Usa método ya definido
                        case 6 -> buscarEstudiante(scanner);
                        case 7 -> System.out.println("Saliendo del menú Directivo...");
                        default -> System.out.println("Opción inválida. Intente nuevamente.");
                    }

                } catch (InputMismatchException e) {
                    System.out.println("Entrada no válida. Intente nuevamente.");
                    scanner.nextLine(); // Limpia error
                    opcion = 0;
                }

            } while (opcion != 7);
        }
        
        /**
        * Permite buscar estudiantes.
        * Si se encuentra, muestra su ficha detallada.
        */
        public static Estudiante buscarEstudiante(Scanner scanner) {
            
        System.out.print("Ingrese parte del nombre, apellido o DNI del estudiante: ");
        String entrada = scanner.nextLine().toLowerCase().trim();

        List<Estudiante> coincidencias = estudiantesSimulados.stream()
            .filter(e -> (e.getNombre() + " " + e.getApellido()).toLowerCase().contains(entrada)|| e.getDni().contains(entrada)).toList();

        if (coincidencias.isEmpty()) {
            System.out.println("No se encontró ningún estudiante.");
            return null;
        }

        if (coincidencias.size() == 1) {
            return coincidencias.get(0);
        }

        System.out.println("Se encontraron varios estudiantes:");
        for (int i = 0; i < coincidencias.size(); i++) {
            Estudiante e = coincidencias.get(i);
            System.out.println((i + 1) + ". " + e.getNombre() + " " + e.getApellido() + " - DNI: " + e.getDni());
        }

        int seleccion = -1;
        while (seleccion < 1 || seleccion > coincidencias.size()) {
            System.out.print("Seleccione un número de la lista: ");
            seleccion = scanner.nextInt();
            scanner.nextLine();
        }

        return coincidencias.get(seleccion - 1);
   }


    }