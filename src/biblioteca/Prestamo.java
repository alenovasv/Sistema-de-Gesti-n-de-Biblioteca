package biblioteca;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Clase que representa un préstamo de libro.
 *
 * Contiene:
 * - Libro prestado
 * - Usuario que lo recibe
 * - Fecha de inicio
 * - Fecha límite de devolución
 * - Estado (activo o devuelto)
 *
 * Esto nos permite manejar control de vencimientos.
 */
public class Prestamo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Libro libro;
    private Usuario usuario;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private boolean devuelto;

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Constructor con plazo personalizado
    public Prestamo(Libro libro, Usuario usuario, int diasPlazo) {
        this.libro = libro;
        this.usuario = usuario;
        this.fechaPrestamo = LocalDate.now();
        this.fechaDevolucion = this.fechaPrestamo.plusDays(diasPlazo);
        this.devuelto = false;
    }

    // Constructor con plazo de 14 días por defecto
    public Prestamo(Libro libro, Usuario usuario) {
        this(libro, usuario, 14);
    }

    // Getters
    public Libro getLibro() { return libro; }
    public Usuario getUsuario() { return usuario; }
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public boolean isDevuelto() { return devuelto; }

    // Operaciones
    public void marcarDevuelto() { this.devuelto = true; }
    public boolean estaVencido() {
        return !devuelto && LocalDate.now().isAfter(fechaDevolucion);
    }
    public long diasRestantes() {
        return java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), fechaDevolucion);
    }

    @Override
    public String toString() {
        String estado = devuelto ? "DEVUELTO ✅" : (estaVencido() ? "VENCIDO ❌" : "EN PRÉSTAMO ⏳");
        return String.format(
                "%s - Usuario: %s (ID: %s) | Prestado: %s | Dev: %s | Estado: %s",
                libro.getTitulo(),
                usuario.getNombre(),
                usuario.getId(),
                fechaPrestamo.format(FORMAT),
                fechaDevolucion.format(FORMAT),
                estado
        );
    }
}
