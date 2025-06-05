package modelo;

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
}
