package modelo;

/**
 * Clase Calificacion
 * Representa una calificación asignada a un estudiante en una actividad específica.
 * Soporta calificaciones numéricas o conceptuales (texto).
 */
public class Calificacion {

    private String nombreEstudiante;   // Nombre completo del estudiante
    private String nombreMateria;      // Materia en la que se evaluó
    private String nombreActividad;    // Actividad evaluada
    private String nota;               // Nota asignada (numérica o conceptual)

    /**
     * Constructor que recibe nota como texto (permite usar tanto "10" como "Excelente")
     */
    public Calificacion(String nombreEstudiante, String nombreMateria, String nombreActividad, String nota) {
        this.nombreEstudiante = nombreEstudiante;
        this.nombreMateria = nombreMateria;
        this.nombreActividad = nombreActividad;
        this.nota = nota;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public String getNota() {
        return nota;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "Estudiante: " + nombreEstudiante +
               ", Materia: " + nombreMateria +
               ", Actividad: " + nombreActividad +
               ", Nota: " + nota;
    }
}
