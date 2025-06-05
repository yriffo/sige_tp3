/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Yonatan
 */

import java.time.LocalDate;


/*
 * Clase Reunion
 * Representa una reunión institucional o pedagógica registrada en el sistema SIGE.
 * Cada reunión está asociada a un estudiante o grupo (curso), incluye fecha, motivo
 * y participantes, y puede ser consultada por asesores, preceptores y directivos.
 */
    public class Reunion {

        // --- Atributos clave ---
        private String nombreEstudiante;  // Nombre completo del estudiante (si es individual)
        private String curso;             // Curso al que pertenece la reunión (1° A, 2° B, etc.)
        private String motivo;            // Motivo o título de la reunión (Ej: "Seguimiento académico")
        private String participantes;     // Personas involucradas (Ej: preceptor, familia, asesor)
        private LocalDate fecha;          // Fecha en la que se realizó la reunión

        /**
         * Constructor completo
         * Permite registrar una reunión con todos los datos requeridos.
         */
        public Reunion(String nombreEstudiante, String curso, String motivo, String participantes, LocalDate fecha) {
            this.nombreEstudiante = nombreEstudiante;
            this.curso = curso;
            this.motivo = motivo;
            this.participantes = participantes;
            this.fecha = fecha;
        }

        // --- Getters (acceso a datos) ---

        public String getNombreEstudiante() {
            return nombreEstudiante;
        }

        public String getCurso() {
            return curso;
        }

        public String getMotivo() {
            return motivo;
        }

        public String getParticipantes() {
            return participantes;
        }

        public LocalDate getFecha() {
            return fecha;
        }

        // --- Setters (modificación de datos) ---

        public void setNombreEstudiante(String nombreEstudiante) {
            this.nombreEstudiante = nombreEstudiante;
        }

        public void setCurso(String curso) {
            this.curso = curso;
        }

        public void setMotivo(String motivo) {
            this.motivo = motivo;
        }

        public void setParticipantes(String participantes) {
            this.participantes = participantes;
        }

        public void setFecha(LocalDate fecha) {
            this.fecha = fecha;
        }

        /**
         * Representación textual para mostrar en listados o bitácora
         */
        @Override
        public String toString() {
            return "Reunión [" + fecha + "] - " + motivo + "\n"
                 + "Estudiante: " + nombreEstudiante + " - Curso: " + curso + "\n"
                 + "Participantes: " + participantes;
        }
    }
