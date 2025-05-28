/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author Yonatan
 */

/**
 * Excepción personalizada que se lanza cuando se ingresa un estado de asistencia no válido.
 */
public class EstadoAsistenciaInvalidoException extends Exception {
    
    /**
     * Constructor que recibe un mensaje personalizado.
     * @param mensaje El mensaje de error a mostrar cuando se lanza la excepción.
     */
    public EstadoAsistenciaInvalidoException(String mensaje) {
        super(mensaje);
    }
}