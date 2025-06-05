/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;
import modelo.Estudiante;
import modelo.Evento;

/**
 *
 * @author Yonatan
 */


/*
 * Clase  para registrar eventos en la bitácora de los estudiantes.
 * Permite un acceso centralizado desde cualquier parte del sistema SIGE.
 */
public class BitacoraEventos {

    private static int contadorEventos = 1; // ID autoincremental local (consola)

    /**
     * Crea y registra un evento en la bitácora del estudiante indicado.
     *
     * @param estudiante El estudiante al que se asocia el evento
     * @param descripcion Descripción textual del evento
     * @param tipo Tipo de evento: "Asistencia", "Calificación", "Reunión", etc.
     */
    public static void registrarEvento(Estudiante estudiante, String descripcion, String tipoEvento) {
        Evento evento = new Evento(
            contadorEventos++, // ID único por sesión
            descripcion,
            tipoEvento,
            estudiante.getIdEstudiante(),
            estudiante.getNombre()
        );
        estudiante.agregarEvento(evento);
        System.out.println("Evento registrado para el estudiante " + estudiante.getNombre() + ":");
        System.out.println("    " + evento);
    }
}

