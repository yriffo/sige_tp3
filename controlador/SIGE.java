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
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

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
            cargarDatosPrueba(); // Carga los datos simulados antes del inicio de sesión

                       
            // --- Inicio del flujo de autenticación ---
            System.out.println("\nInicio de sesión en SIGE");
            System.out.print("Correo electrónico: ");
            String emailIngresado = scanner.nextLine();
            System.out.print("Contraseña: ");
            String contraseniaIngresada = scanner.nextLine();

            // Búsqueda del usuario por email y contraseña
            Usuario usuarioActual = usuariosSimulados.stream().filter(u -> u.getEmail().equalsIgnoreCase(emailIngresado)&& u.getContrasenia().equals(contraseniaIngresada)).findFirst().orElse(null);

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
                    case "Docente" -> {
                        if (usuarioActual instanceof Docente) {
                            Docente docente = (Docente) usuarioActual; // cast explícito
                            Docente.mostrarMenu(docente, scanner);}
                    }
                    case "Preceptor" -> Preceptor.mostrarMenu(scanner);
                    case "Asesor" -> Asesor.mostrarMenu(scanner);
                    case "Directivo" -> Directivo.mostrarMenu(scanner);
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
                                    if (usuarioActual instanceof Docente) {
                                        Docente docente = (Docente) usuarioActual; // casteo usuarioActual a Docente
                                        System.out.println(count + ". Menú Docente");
                                        acciones.add(() -> Docente.mostrarMenu(docente, scanner));
                                        count++;
                                    }
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
                                    acciones.add(() -> Directivo.mostrarMenu(scanner));
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
        * Método para registrar asistencia por parte del docente.
        * El docente debe seleccionar uno de sus cursos y luego una materia dentro de ese curso.
        * Se mostrará la lista de estudiantes y se podrá asignar un estado (P/A/R).
        * Si hay diferencias con la asistencia tomada por el preceptor, se modifica y se registra en la bitácora.
        */
       public static void registrarAsistenciaDocente(Docente docente, Scanner scanner) {
           System.out.println("\n--- Registro de asistencia por Docente ---");

           // Obtener los cursos únicos donde el docente tiene actividades asignadas
           List<String> cursosDelDocente = actividadesSimuladas.stream()
               .filter(a -> a.getDocente().equals(docente)).map(Actividad::getCurso).distinct().collect(Collectors.toList());

           // Validación si el docente no tiene cursos asignados
           if (cursosDelDocente.isEmpty()) {
               System.out.println("No tiene cursos asignados para registrar asistencia.");
               return;
           }

           // Mostrar cursos disponibles
           System.out.println("Seleccione un curso:");
           for (int i = 0; i < cursosDelDocente.size(); i++) {
               System.out.println((i + 1) + ". " + cursosDelDocente.get(i));
           }
           int opcionCurso = scanner.nextInt();
           scanner.nextLine(); // limpiar buffer
           String cursoSeleccionado = cursosDelDocente.get(opcionCurso - 1);

           // Obtener materias que el docente dicta en ese curso
           List<String> materiasDelDocente = actividadesSimuladas.stream()
               .filter(a -> a.getDocente().equals(docente) && a.getCurso().equals(cursoSeleccionado))
               .map(Actividad::getMateria)
               .distinct()
               .collect(Collectors.toList());

           // Validación si no hay materias en ese curso
           if (materiasDelDocente.isEmpty()) {
               System.out.println("No tiene materias asignadas en ese curso.");
               return;
           }

           // Mostrar materias y seleccionar una
           System.out.println("Seleccione una materia:");
           for (int i = 0; i < materiasDelDocente.size(); i++) {
               System.out.println((i + 1) + ". " + materiasDelDocente.get(i));
           }
           int opcionMateria = scanner.nextInt();
           scanner.nextLine(); // limpiar buffer
           String materiaSeleccionada = materiasDelDocente.get(opcionMateria - 1);

           // Ingresar la fecha del registro
           System.out.print("Ingrese la fecha (dd/MM/yyyy): ");
           String fechaStr = scanner.nextLine();
           LocalDate fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

           // Obtener estudiantes pertenecientes al curso seleccionado
           List<Estudiante> estudiantesDelCurso = estudiantesSimulados.stream()
               .filter(e -> e.getCurso().equalsIgnoreCase(cursoSeleccionado))
               .collect(Collectors.toList());

           // Iterar por cada estudiante para registrar asistencia
           for (Estudiante estudiante : estudiantesDelCurso) {
               System.out.print("Estudiante: " + estudiante.getNombreCompleto() + " - Estado (Presente/Ausente/Retirado): ");
               String estado = scanner.nextLine().toUpperCase();

               try {
                   // Validar que el estado ingresado sea correcto
                   validarEstadoAsistencia(estado);

                   // Verificar si ya existe una asistencia previa del preceptor para ese estudiante en esa fecha
                   Asistencia asistenciaPrevia = asistencias.stream()
                       .filter(a -> a.getNombreEstudiante().equals(estudiante) && a.getFecha().equals(fecha)).findFirst().orElse(null);

                   // Si ya existe, se evalúa si hay diferencia de estado
                   if (asistenciaPrevia != null && !asistenciaPrevia.getEstado().equals(estado)) {
                       asistenciaPrevia.setEstado(estado); // se modifica el estado

                       // Registrar evento indicando que fue modificada por el docente
                       BitacoraEventos.registrarEvento(estudiante,
                           "Docente " + docente.getNombreCompleto() +
                           " modificó la asistencia previa tomada por el preceptor para la materia " +
                           materiaSeleccionada + " en el curso " + cursoSeleccionado +
                           ". Nuevo estado: " + estado, "Asistencia");
                   } else {
                       // Si no hay asistencia previa, se crea una nueva
                       Asistencia nueva = new Asistencia(estudiante.getNombreCompleto(),materiaSeleccionada,fecha, estado);
                       asistencias.add(nueva);

                       // Registrar evento si el estado no es presente
                       if (estado.equals("A") || estado.equals("R")) {
                           BitacoraEventos.registrarEvento(estudiante,"Asistencia registrada por docente " + docente.getNombreCompleto() +" en la materia " + materiaSeleccionada + " - Estado: " + estado,"Asistencia"); // tipo del evento
                        }    
                   }
             
                } catch (EstadoAsistenciaInvalidoException e) {
                   System.out.println("Estado inválido. Use solo P (Presente), A (Ausente) o R (Retirado).");
               }
           }

           System.out.println("Registro de asistencia finalizado.");
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
        
         /**
        * Permite al docente o preceptor editar el estado de una asistencia previamente registrada.
        * Muestra todas las asistencias ordenadas por fecha descendente.
        * Permite elegir una asistencia y luego seleccionar al estudiante para modificar su estado.
        * Registra un evento en la bitácora del estudiante indicando el cambio.
        */
       public static void editarAsistencia(Scanner scanner) {
           if (asistencias.isEmpty()) {
               System.out.println("No hay asistencias registradas.");
               return;
           }

           // Ordena asistencias por fecha (más reciente primero)
           List<Asistencia> asistenciasOrdenadas = new ArrayList<>(asistencias);
           asistenciasOrdenadas.sort((a1, a2) -> a2.getFecha().compareTo(a1.getFecha()));

           // Mostrar lista de asistencias con índice
           System.out.println("\n--- Asistencias registradas ---");
           for (int i = 0; i < asistenciasOrdenadas.size(); i++) {
               Asistencia a = asistenciasOrdenadas.get(i);
               System.out.println((i + 1) + ". Fecha: " + a.getFecha() +
                       ", Materia: " + a.getNombreMateria() +
                       ", Estudiante: " + a.getNombreEstudiante() +
                       ", Estado actual: " + a.getEstado());
           }

           // Selección de asistencia
           System.out.print("Seleccione el número de la asistencia a editar: ");
           int seleccion = scanner.nextInt();
           scanner.nextLine(); // Limpia el buffer

           if (seleccion < 1 || seleccion > asistenciasOrdenadas.size()) {
               System.out.println("Selección inválida.");
               return;
           }

           // Obtiene la asistencia seleccionada
           Asistencia asistenciaSeleccionada = asistenciasOrdenadas.get(seleccion - 1);

           System.out.println("Editar asistencia de:");
           System.out.println("Estudiante: " + asistenciaSeleccionada.getNombreEstudiante());
           System.out.println("Materia: " + asistenciaSeleccionada.getNombreMateria());
           System.out.println("Fecha: " + asistenciaSeleccionada.getFecha());
           System.out.println("Estado actual: " + asistenciaSeleccionada.getEstado());

           // Solicita nuevo estado
           System.out.print("Ingrese el nuevo estado (Presente / Ausente / Retirado): ");
           String nuevoEstado = scanner.nextLine();

           try {
               validarEstadoAsistencia(nuevoEstado); // Verifica que el estado sea válido

               // Actualiza la asistencia
               asistenciaSeleccionada.setEstado(nuevoEstado);
               System.out.println("Asistencia actualizada correctamente.");

               // Registra evento en bitácora del estudiante correspondiente
               String nombreEstudiante = asistenciaSeleccionada.getNombreEstudiante();
               for (Estudiante estudiante : estudiantesSimulados) {
                   String nombreCompleto = estudiante.getNombre() + " " + estudiante.getApellido();
                   if (nombreCompleto.equalsIgnoreCase(nombreEstudiante)) {
                       BitacoraEventos.registrarEvento(
                               estudiante,
                               "Asistencia modificada manualmente a: " + nuevoEstado +
                               " para la materia " + asistenciaSeleccionada.getNombreMateria(),
                               "Edición de Asistencia"
                       );
                       break;
                   }
               }

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
        * Permite al docente registrar una nueva actividad.
        * Solicita nombre, materia, curso, tipo, fechas y observación.
        * Luego guarda la actividad en la lista simulada.
        */
       public static void registrarActividad(Docente docente, Scanner scanner) {
           System.out.println("\n--- Registro de nueva actividad ---");

           // Asignar ID automáticamente según tamaño de lista
           int nuevoId = actividadesSimuladas.size() + 1;

           // Ingreso de datos
           System.out.print("Ingrese nombre de la actividad: ");
           String nombre = scanner.nextLine();

           System.out.print("Ingrese nombre de la materia: ");
           String materia = scanner.nextLine();

           System.out.print("Ingrese curso: ");
           String curso = scanner.nextLine();

           System.out.print("Ingrese tipo de calificación (Numérica o Conceptual): ");
           String tipoCalificacion = scanner.nextLine();

           System.out.print("Ingrese fecha de inicio (dd/MM/yyyy): ");
           String fechaInicioTexto = scanner.nextLine();

           System.out.print("Ingrese fecha de entrega (dd/MM/yyyy): ");
           String fechaEntregaTexto = scanner.nextLine();

           System.out.print("Ingrese observación (opcional): ");
           String observacion = scanner.nextLine();

           try {
               // Conversión de fechas
               DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
               LocalDate fechaInicio = LocalDate.parse(fechaInicioTexto, formatter);
               LocalDate fechaEntrega = LocalDate.parse(fechaEntregaTexto, formatter);

               // Crear objeto actividad
               Actividad act = new Actividad(
                   nuevoId,
                   nombre,
                   materia,
                   curso,
                   tipoCalificacion,
                   fechaInicio,
                   fechaEntrega,
                   observacion,
                   docente // Asignación del docente actual
               );

               // Guardar en la lista simulada
               actividadesSimuladas.add(act);

               System.out.println("✔ Actividad registrada correctamente.\n");
           } catch (Exception e) {
               System.out.println("❌ Error en el formato de fecha. Use el formato dd/MM/yyyy.");
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
        * Permite registrar o modificar calificaciones para una actividad específica, filtrada por curso y materia.
        * Las calificaciones pueden ser numéricas o conceptuales según la configuración de la actividad.
        * También se actualiza la bitácora del estudiante con un evento.
        */
       public static void registrarCalificacion(Docente docente) {
           Scanner scanner = new Scanner(System.in);

           // Paso 1: Solicitar curso y materia
           System.out.print("Ingrese el curso (ej: 1A): ");
           String curso = scanner.nextLine();

           System.out.print("Ingrese la materia: ");
           String materia = scanner.nextLine();

           // Paso 2: Buscar actividades asociadas a ese curso y materia
           List<Actividad> actividadesFiltradas = actividadesSimuladas.stream()
               .filter(a -> a.getCurso().equalsIgnoreCase(curso) && a.getMateria().equalsIgnoreCase(materia))
               .toList();

           if (actividadesFiltradas.isEmpty()) {
               System.out.println("No se encontraron actividades para ese curso y materia.");
               System.out.print("¿Desea registrar una nueva actividad? (S/N): ");
               String respuesta = scanner.nextLine();
               if (respuesta.equalsIgnoreCase("S")) {
                   registrarActividad(docente,scanner);
               }
               return;
           }

           // Paso 3: Selección de actividad
           System.out.println("\n--- Actividades disponibles ---");
           for (int i = 0; i < actividadesFiltradas.size(); i++) {
               Actividad a = actividadesFiltradas.get(i);
               System.out.println((i + 1) + ". " + a.getNombreActividad() + " | Tipo: " + a.getTipoCalificacion());
           }

           System.out.print("Seleccione una actividad por número: ");
           int seleccion = scanner.nextInt();
           scanner.nextLine(); // limpia buffer

           if (seleccion < 1 || seleccion > actividadesFiltradas.size()) {
               System.out.println("Selección inválida.");
               return;
           }

           Actividad actividadSeleccionada = actividadesFiltradas.get(seleccion - 1);
           String tipoCalificacion = actividadSeleccionada.getTipoCalificacion();

           // Paso 4: Filtrar estudiantes del curso
           List<Estudiante> estudiantesDelCurso = estudiantesSimulados.stream()
               .filter(e -> e.getCurso().equalsIgnoreCase(curso))
               .toList();

           if (estudiantesDelCurso.isEmpty()) {
               System.out.println("No hay estudiantes cargados en el curso " + curso);
               return;
           }

           // Paso 5: Recorrer estudiantes y registrar o modificar calificación
           for (Estudiante estudiante : estudiantesDelCurso) {
               String nombreCompleto = estudiante.getNombre() + " " + estudiante.getApellido();

               // Verificar si ya existe una calificación previa
               Calificacion existente = calificaciones.stream()
                   .filter(c -> c.getNombreEstudiante().equalsIgnoreCase(nombreCompleto)
                             && c.getNombreMateria().equalsIgnoreCase(materia)
                             && c.getNombreActividad().equalsIgnoreCase(actividadSeleccionada.getNombreActividad()))
                   .findFirst()
                   .orElse(null);

               String notaNueva = "";

               // Mostrar calificación existente (si hay)
               if (existente != null) {
                   System.out.println("Ya existe una calificación para " + nombreCompleto + ": " + existente.getNota());
                   System.out.print("¿Desea modificarla? (S/N): ");
                   String modificar = scanner.nextLine();

                   if (!modificar.equalsIgnoreCase("S")) {
                       continue; // Saltea a siguiente estudiante
                   }
               }

               // Pedir nota según el tipo
               if (tipoCalificacion.equalsIgnoreCase("Numerica")) {
                   int nota;
                   do {
                       System.out.print("Ingrese nota numérica (1-10) para " + nombreCompleto + ": ");
                       nota = scanner.nextInt();
                   } while (nota < 1 || nota > 10);
                   scanner.nextLine(); // limpia buffer
                   notaNueva = String.valueOf(nota);
               } else {
                   System.out.print("Ingrese nota conceptual para " + nombreCompleto + ": ");
                   notaNueva = scanner.nextLine();
               }

               // Si existe, modificarla; si no, crear nueva
               if (existente != null) {
                   existente.setNota(notaNueva);
                   System.out.println("Calificación modificada para " + nombreCompleto);
               } else {
                   calificaciones.add(new Calificacion(nombreCompleto, materia, actividadSeleccionada.getNombreActividad(), notaNueva));
                   System.out.println("Calificación registrada para " + nombreCompleto);
               }

               // Registrar evento en bitácora
               BitacoraEventos.registrarEvento(
                   estudiante,
                   "Calificación actualizada: " + notaNueva + " en " + actividadSeleccionada.getNombreActividad(),
                   "Calificación"
               );
           }

           System.out.println("Proceso de calificación finalizado.");
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
                        case 2 -> verTodasLasAsistencias(scanner);
                        case 3 -> verAsistenciasPorCursoYMateria(scanner);
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
        * Muestra un listado de asistencias agrupadas por fecha, curso y materia.
        * Luego permite seleccionar una para ver el detalle de los estudiantes.
        */
       public static void verTodasLasAsistencias(Scanner scanner) {
           if (asistencias.isEmpty()) {
               System.out.println("No hay asistencias registradas en el sistema.");
               return;
           }

           // Obtener combinaciones únicas de Fecha + Curso + Materia
           List<String> combinacionesUnicas = new ArrayList<>();
           for (Asistencia a : asistencias) {
               String curso = obtenerCursoPorNombreEstudiante(a.getNombreEstudiante());
               String clave = a.getFecha() + " | " + curso + " | " + a.getNombreMateria();
               if (!combinacionesUnicas.contains(clave)) {
                   combinacionesUnicas.add(clave);
               }
           }

           // Ordenar por fecha descendente
           combinacionesUnicas.sort((c1, c2) -> {
               LocalDate f1 = LocalDate.parse(c1.split(" \\| ")[0]);
               LocalDate f2 = LocalDate.parse(c2.split(" \\| ")[0]);
               return f2.compareTo(f1);
           });

           // Mostrar combinaciones
           System.out.println("\nAsistencias registradas:");
           for (int i = 0; i < combinacionesUnicas.size(); i++) {
               System.out.println((i + 1) + ". " + combinacionesUnicas.get(i));
           }

           // Selección
           System.out.print("Seleccione un número para ver detalles: ");
           int seleccion = scanner.nextInt();
           scanner.nextLine();

           if (seleccion < 1 || seleccion > combinacionesUnicas.size()) {
               System.out.println("Selección inválida.");
               return;
           }

           // Parsear clave
           String[] partes = combinacionesUnicas.get(seleccion - 1).split(" \\| ");
           LocalDate fechaSeleccionada = LocalDate.parse(partes[0].trim());
           String cursoSeleccionado = partes[1].trim();
           String materiaSeleccionada = partes[2].trim();

           // Mostrar asistencias de esa combinación
           System.out.println("\nAsistencias para " + materiaSeleccionada + " - " + cursoSeleccionado + " - " + fechaSeleccionada + ":");
           for (Asistencia a : asistencias) {
               String curso = obtenerCursoPorNombreEstudiante(a.getNombreEstudiante());
               if (a.getFecha().equals(fechaSeleccionada)
                       && a.getNombreMateria().equalsIgnoreCase(materiaSeleccionada)
                       && curso.equalsIgnoreCase(cursoSeleccionado)) {
                   System.out.println("🧑 Estudiante: " + a.getNombreEstudiante() + " | Estado: " + a.getEstado());
               }
           }
       }

       /**
        * Método auxiliar que busca el curso de un estudiante dado su nombre completo.
        */
       private static String obtenerCursoPorNombreEstudiante(String nombreCompleto) {
           for (Estudiante e : estudiantesSimulados) {
               if ((e.getNombre() + " " + e.getApellido()).equalsIgnoreCase(nombreCompleto)) {
                   return e.getCurso();
               }
           }
           return "";
       }

        
          /**
        * Método verCalificacionesPorCurso
        * Permite al directivo ver calificaciones por curso, materia y actividad.
        * El usuario selecciona un curso, luego una materia, luego una actividad.
        * Finalmente, se muestran las calificaciones de todos los estudiantes.
        */
        public static void verCalificacionesPorCursoParaPreceptor(Scanner scanner) {
            
// Paso 1: Obtener lista única de cursos de estudiantes
            Set<String> cursosUnicos = estudiantesSimulados.stream()
                .map(Estudiante::getCurso)
                .collect(Collectors.toSet());

            if (cursosUnicos.isEmpty()) {
                System.out.println("No hay cursos disponibles.");
                return;
            }

            // Mostrar cursos disponibles
            List<String> cursos = new ArrayList<>(cursosUnicos);
            System.out.println("\nCursos disponibles:");
            for (int i = 0; i < cursos.size(); i++) {
                System.out.println((i + 1) + ". " + cursos.get(i));
            }

            // Elegir curso
            System.out.print("Seleccione un curso por número: ");
            int seleccionCurso = scanner.nextInt();
            scanner.nextLine();
            if (seleccionCurso < 1 || seleccionCurso > cursos.size()) {
                System.out.println("Selección inválida.");
                return;
            }
            String cursoSeleccionado = cursos.get(seleccionCurso - 1);

            // Paso 2: Obtener materias del curso a partir de actividades
            Set<String> materiasUnicas = actividadesSimuladas.stream()
                .filter(a -> a.getCurso().equalsIgnoreCase(cursoSeleccionado))
                .map(Actividad::getMateria)
                .collect(Collectors.toSet());

            if (materiasUnicas.isEmpty()) {
                System.out.println("No hay materias con actividades registradas para ese curso.");
                return;
            }

            // Mostrar materias disponibles
            List<String> materias = new ArrayList<>(materiasUnicas);
            System.out.println("\nMaterias disponibles para " + cursoSeleccionado + ":");
            for (int i = 0; i < materias.size(); i++) {
                System.out.println((i + 1) + ". " + materias.get(i));
            }

            // Elegir materia
            System.out.print("Seleccione una materia por número: ");
            int seleccionMateria = scanner.nextInt();
            scanner.nextLine();
            if (seleccionMateria < 1 || seleccionMateria > materias.size()) {
                System.out.println("Selección inválida.");
                return;
            }
            String materiaSeleccionada = materias.get(seleccionMateria - 1);

            // Paso 3: Mostrar actividades de esa materia en ese curso
            List<Actividad> actividadesFiltradas = actividadesSimuladas.stream()
                .filter(a -> a.getCurso().equalsIgnoreCase(cursoSeleccionado)
                          && a.getMateria().equalsIgnoreCase(materiaSeleccionada))
                .sorted(Comparator.comparing(Actividad::getFechaInicio))
                .collect(Collectors.toList());

            if (actividadesFiltradas.isEmpty()) {
                System.out.println("No hay actividades para esa materia en ese curso.");
                return;
            }

            System.out.println("\nActividades disponibles:");
            for (int i = 0; i < actividadesFiltradas.size(); i++) {
                Actividad act = actividadesFiltradas.get(i);
                System.out.println((i + 1) + ". " + act.getNombreActividad() + " | Fecha: " + act.getFechaInicio());
            }

            System.out.print("Seleccione una actividad por número: ");
            int seleccionActividad = scanner.nextInt();
            scanner.nextLine();
            if (seleccionActividad < 1 || seleccionActividad > actividadesFiltradas.size()) {
                System.out.println("Selección inválida.");
                return;
            }

            Actividad actividadSeleccionada = actividadesFiltradas.get(seleccionActividad - 1);

            // Paso 4: Mostrar calificaciones de todos los estudiantes en esa actividad
            System.out.println("\nCalificaciones para actividad \"" + actividadSeleccionada.getNombreActividad() + "\":");

            boolean seEncontraron = false;

            for (Estudiante estudiante : estudiantesSimulados) {
                if (!estudiante.getCurso().equalsIgnoreCase(cursoSeleccionado)) continue;

                String nombreCompleto = estudiante.getNombre() + " " + estudiante.getApellido();

                for (Calificacion calificacion : calificaciones) {
                    if (calificacion.getNombreEstudiante().equalsIgnoreCase(nombreCompleto)
                        && calificacion.getNombreMateria().equalsIgnoreCase(materiaSeleccionada)
                        && calificacion.getNombreActividad().equalsIgnoreCase(actividadSeleccionada.getNombreActividad())) {

                        System.out.println("Estudiante: " + nombreCompleto);
                        System.out.println("Nota: " + calificacion.getNota());
                        System.out.println("--------------------------");
                        seEncontraron = true;
                    }
                }
            }

            if (!seEncontraron) {
                System.out.println("No hay calificaciones registradas para esta actividad.");
            }
        }

        
          /**
        * Permite al Preceptor registrar una reunión con un estudiante específico del curso.
        * El usuario elige al estudiante y se registra la reunión con fecha automática.
        */
       public static void registrarReunion(Scanner scanner) {
           System.out.print("Ingrese el curso (ej: 1A): ");
           String curso = scanner.nextLine();

           // Filtrar estudiantes del curso
           List<Estudiante> estudiantesCurso = estudiantesSimulados.stream()
               .filter(e -> e.getCurso().equalsIgnoreCase(curso))
               .toList();

           if (estudiantesCurso.isEmpty()) {
               System.out.println("No hay estudiantes cargados para ese curso.");
               return;
           }

           // Mostrar estudiantes y permitir elegir uno
           System.out.println("\nEstudiantes del curso " + curso + ":");
           for (int i = 0; i < estudiantesCurso.size(); i++) {
               Estudiante e = estudiantesCurso.get(i);
               System.out.println((i + 1) + ". " + e.getNombre() + " " + e.getApellido());
           }

           System.out.print("Seleccione un estudiante por número: ");
           int seleccion = scanner.nextInt();
           scanner.nextLine(); // limpia buffer

           if (seleccion < 1 || seleccion > estudiantesCurso.size()) {
               System.out.println("Selección inválida.");
               return;
           }

           Estudiante estudianteSeleccionado = estudiantesCurso.get(seleccion - 1);

           // Ingreso del motivo y participantes
           System.out.print("Motivo de la reunión: ");
           String motivo = scanner.nextLine();

           System.out.print("Participantes (separados por coma): ");
           String participantes = scanner.nextLine();

           // Fecha automática
           LocalDate fecha = LocalDate.now();

           // Crear y guardar la reunión
           Reunion reunion = new Reunion(
               estudianteSeleccionado.getNombre() + " " + estudianteSeleccionado.getApellido(),
               curso,
               motivo,
               participantes,
               fecha
           );
           reuniones.add(reunion);

           // Registrar en bitácora
           BitacoraEventos.registrarEvento(
               estudianteSeleccionado,
               "Reunión registrada. Motivo: " + motivo,
               "Reunión"
           );

           System.out.println("Reunión registrada correctamente para " + estudianteSeleccionado.getNombre() + " " + estudianteSeleccionado.getApellido() + " en fecha " + fecha + ".");
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
        
            public static void cargarDatosPrueba() {
                // ---------------- USUARIOS ----------------
    // Crear docente1 como variable para usarlo luego en actividad
                Docente docente1 = new Docente(1, "María", "González", "maria@mail.com", "doc123");
                usuariosSimulados.add(docente1); // fue declarado arriba                  
                usuariosSimulados.add(new Preceptor(2, "Carlos", "Pérez", "carlos@mail.com", "pre123"));
                usuariosSimulados.add(new Asesor(3, "Laura", "Ramírez", "laura@mail.com", "ase123"));
                usuariosSimulados.add(new Directivo(4, "Ana", "Martínez", "ana@mail.com", "dir123"));
                usuariosSimulados.add(new Usuario(5, "Pedro", "Lopez", "pedro@mail.com", "multi123", List.of("Docente", "Preceptor")));
                usuariosSimulados.add(new Usuario(6, "Marcela", "Torres", "marcela@mail.com", "trirol123", List.of("Docente", "Asesor", "Preceptor")));
                usuariosSimulados.add(new Usuario(7, "Federico", "Juarez", "fede@mail.com", "dirpre123", List.of("Preceptor", "Directivo")));
                usuariosSimulados.add(new Usuario(8, "Gisella", "Ibarra", "gisella@mail.com", "todos123", List.of("Docente", "Preceptor", "Asesor", "Directivo")));

                // Estudiantes
                estudiantesSimulados.add(new Estudiante(1, "Sofia", "Perez", "45678901", LocalDate.of(2010, 5, 14), "1A", "Calle 123", "2991234567", "María Pérez", "sofiap@gmail.com"));
                estudiantesSimulados.add(new Estudiante(2, "Lucas", "Martinez", "45789123", LocalDate.of(2009, 8, 30), "2B", "Av. San Martín 456", "2997654321", "Carlos Martínez", "lucasm@gmail.com"));
                estudiantesSimulados.add(new Estudiante(3, "Valentina", "Lopez", "45891234", LocalDate.of(2011, 3, 22), "1A", "Belgrano 789", "2999876543", "Laura López", "valenl@gmail.com"));

                // Actividad
                LocalDate fecha = LocalDate.of(2025, 6, 1);
                Actividad actividad = new Actividad(1, "Evaluación Diagnóstica", "Informática", "1A", "Numerica", fecha, fecha.plusDays(5), "Primera evaluación del año",docente1);
                actividadesSimuladas.add(actividad);

                // Asistencia + evento
                Estudiante sofia = estudiantesSimulados.get(0);
                asistencias.add(new Asistencia(sofia.getNombre() + " " + sofia.getApellido(), "Informática", fecha, "Ausente"));
                BitacoraEventos.registrarEvento(sofia, "Asistencia registrada como: Ausente", "Asistencia");

                // Calificación + evento
                calificaciones.add(new Calificacion(sofia.getNombre() + " " + sofia.getApellido(), "Informática", "Evaluación Diagnóstica", "8"));
                BitacoraEventos.registrarEvento(sofia, "Calificación actualizada: 8 en Evaluación Diagnóstica", "Calificación");

                // Mostrar solo una línea de confirmación
                System.out.println(" Datos simulados cargados para pruebas.");
                System.out.println("Estos datos simulados solo se usan en el prototipo de la entrega 3, luego se usara persistenciad de Datos con BD");
            }   



    }