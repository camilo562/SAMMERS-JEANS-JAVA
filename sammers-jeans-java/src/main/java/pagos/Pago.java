package pagos;

import pedidos.Pedido;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * Clase que representa un pago realizado en el sistema
 * Soporta múltiples métodos de pago y validaciones
 */
public class Pago {
    // Métodos de pago válidos
    public static final List<String> METODOS_VALIDOS = Arrays.asList("nequi", "bancolombia", "daviplata");

    // Contador estático para generar IDs
    private static int contadorPagos = 100;

    private int idPago;
    private double monto;
    private String metodoPago;
    private String estado;
    private String fecha;
    private Integer idPedido;
    private Pedido pedido;

    /**
     * Constructor completo de Pago
     * @param monto Monto a pagar
     * @param metodoPago Método de pago (nequi, bancolombia, daviplata)
     * @param pedido Pedido asociado (puede ser null)
     */
    public Pago(double monto, String metodoPago, Pedido pedido) {
        this.idPago = ++contadorPagos;
        this.monto = monto;
        this.metodoPago = metodoPago.toLowerCase();
        this.estado = "Pendiente";

        // Formato de fecha: "YYYY-MM-DD HH:MM:SS"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.fecha = LocalDateTime.now().format(formatter);

        this.pedido = pedido;
        this.idPedido = (pedido != null) ? pedido.getIdPedido() : null;
    }

    /**
     * Constructor sin pedido
     * @param monto Monto a pagar
     * @param metodoPago Método de pago
     */
    public Pago(double monto, String metodoPago) {
        this(monto, metodoPago, null);
    }

    // ==================== VALIDACIÓN ====================

    /**
     * Valida el pago antes de procesarlo
     * @return true si el pago es válido
     */
    public boolean validarPago() {
        // Validar método de pago
        if (!METODOS_VALIDOS.contains(metodoPago)) {
            System.out.println("Método de pago inválido: " + metodoPago);
            System.out.println("   Métodos válidos: " + String.join(" - ", METODOS_VALIDOS));
            return false;
        }

        // Validar monto
        if (monto <= 0) {
            System.out.println("Monto inválido: $" + monto);
            return false;
        }

        // Validar que el monto coincida con el pedido
        if (pedido != null) {
            if (Math.abs(monto - pedido.getMontoTotal()) > 0.01) { // Comparación con tolerancia
                System.out.printf(" Error: El monto del pago ($%.2f) no coincide%n", monto);
                System.out.printf("   con el total del pedido ($%.2f)%n", pedido.getMontoTotal());
                return false;
            }
        }

        return true;
    }

    // ==================== PROCESAR PAGO ====================

    /**
     * Procesa el pago si es válido
     * @return true si el pago se procesó exitosamente
     */
    public boolean procesarPago() {
        if (!validarPago()) {
            estado = "Fallido";
            return false;
        }

        estado = "Completado";
        System.out.printf(" Pago de $%.2f procesado exitosamente mediante %s.%n",
                monto, metodoPago.toUpperCase());
        return true;
    }

    /**
     * Cancela un pago (solo si está pendiente)
     * @return true si se canceló exitosamente
     */
    public boolean cancelarPago() {
        if ("Completado".equals(estado)) {
            System.out.println("No se puede cancelar un pago completado.");
            return false;
        }

        if ("Cancelado".equals(estado)) {
            System.out.println("El pago ya está cancelado.");
            return false;
        }

        estado = "Cancelado";
        System.out.println("Pago #" + idPago + " cancelado exitosamente.");
        return true;
    }

    /**
     * Reintenta procesar un pago fallido
     * @return true si se procesó exitosamente
     */
    public boolean reintentarPago() {
        if ("Completado".equals(estado)) {
            System.out.println("El pago ya fue completado.");
            return false;
        }

        System.out.println("Reintentando pago #" + idPago + "...");
        return procesarPago();
    }

    // ==================== GETTERS ====================

    public int getIdPago() {
        return idPago;
    }

    public double getMonto() {
        return monto;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public String getEstado() {
        return estado;
    }

    public String getFecha() {
        return fecha;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public static List<String> getMetodosValidos() {
        return METODOS_VALIDOS;
    }

    // ==================== SETTERS ====================

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // ==================== MÉTODOS ESTÁTICOS ====================

    /**
     * Resetea el contador de pagos (útil para pruebas)
     */
    public static void resetearContador() {
        contadorPagos = 100;
    }

    /**
     * Muestra los métodos de pago disponibles
     */
    public static void mostrarMetodosPago() {
        System.out.println("\n Métodos de pago disponibles:");
        int contador = 1;
        for (String metodo : METODOS_VALIDOS) {
            System.out.println("  " + contador + ". " + metodo.toUpperCase());
            contador++;
        }
    }

    /**
     * Verifica si un método de pago es válido
     * @param metodo Método a verificar
     * @return true si es válido
     */
    public static boolean esMetodoValido(String metodo) {
        return METODOS_VALIDOS.contains(metodo.toLowerCase());
    }

    // ==================== OTROS MÉTODOS ====================

    @Override
    public String toString() {
        String pedidoInfo = (idPedido != null) ? " - Pedido #" + idPedido : "";
        return String.format("Pago #%d - $%.2f - %s - %s%s",
                idPago, monto, metodoPago, estado, pedidoInfo);
    }

    /**
     * Muestra detalle completo del pago
     */
    public void mostrarDetalle() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("DETALLE DEL PAGO #" + idPago);
        System.out.println("=".repeat(60));
        System.out.printf("Monto: $%.2f%n", monto);
        System.out.println("Método de pago: " + metodoPago.toUpperCase());
        System.out.println("Estado: " + estado);
        System.out.println("Fecha: " + fecha);

        if (idPedido != null) {
            System.out.println("Pedido asociado: #" + idPedido);
        } else {
            System.out.println("Pedido asociado: N/A");
        }

        System.out.println("=".repeat(60));
    }
}