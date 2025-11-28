package pagos;

import pedidos.Pedido;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Clase para gestionar todos los pagos del sistema
 * Funciona como base de datos de pagos
 */
public class Gestion_pagos {
    private List<Pago> listaPagos;

    /**
     * Constructor que inicializa la lista de pagos
     */
    public Gestion_pagos() {
        this.listaPagos = new ArrayList<>();
    }

    // ==================== REGISTRAR PAGO ====================

    /**
     * Registra un nuevo pago en el sistema
     * @param metodoPago Método de pago a usar
     * @param monto Monto a pagar
     * @param pedido Pedido asociado (puede ser null)
     * @return Pago creado o null si falla
     */
    public Pago registrarPago(String metodoPago, double monto, Pedido pedido) {
        Pago nuevoPago = new Pago(monto, metodoPago, pedido);

        if (nuevoPago.procesarPago()) {
            listaPagos.add(nuevoPago);

            if (pedido != null) {
                System.out.println("El ID del Pago es #" + nuevoPago.getIdPago() +
                        " registrado para pedido #" + pedido.getIdPedido());
            } else {
                System.out.println("El ID del Pago es #" + nuevoPago.getIdPago() + " registrado");
            }

            return nuevoPago;
        } else {
            System.out.println(" Error al registrar el pago.");
            return null;
        }
    }

    /**
     * Registra un pago sin pedido asociado
     * @param metodoPago Método de pago
     * @param monto Monto a pagar
     * @return Pago creado o null si falla
     */
    public Pago registrarPago(String metodoPago, double monto) {
        return registrarPago(metodoPago, monto, null);
    }

    // ==================== OBTENER PAGOS ====================

    /**
     * Obtiene el historial completo de pagos
     * @return Lista de todos los pagos
     */
    public List<Pago> obtenerHistorialPagos() {
        return new ArrayList<>(listaPagos);
    }

    /**
     * Obtiene un pago por su ID
     * @param idPago ID del pago
     * @return Pago encontrado o null
     */
    public Pago obtenerPagoPorId(int idPago) {
        for (Pago pago : listaPagos) {
            if (pago.getIdPago() == idPago) {
                return pago;
            }
        }
        return null;
    }

    /**
     * Obtiene todos los pagos asociados a un pedido
     * @param idPedido ID del pedido
     * @return Lista de pagos del pedido
     */
    public List<Pago> obtenerPagosPorPedido(int idPedido) {
        return listaPagos.stream()
                .filter(p -> p.getIdPedido() != null && p.getIdPedido() == idPedido)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene pagos por estado
     * @param estado Estado del pago (Pendiente, Completado, Fallido, Cancelado)
     * @return Lista de pagos con ese estado
     */
    public List<Pago> obtenerPagosPorEstado(String estado) {
        return listaPagos.stream()
                .filter(p -> p.getEstado().equalsIgnoreCase(estado))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene pagos por método de pago
     * @param metodoPago Método de pago
     * @return Lista de pagos con ese método
     */
    public List<Pago> obtenerPagosPorMetodo(String metodoPago) {
        return listaPagos.stream()
                .filter(p -> p.getMetodoPago().equalsIgnoreCase(metodoPago))
                .collect(Collectors.toList());
    }

    // ==================== LISTAR PAGOS ====================

    /**
     * Lista todos los pagos registrados
     */
    public void listarPagos() {
        if (listaPagos.isEmpty()) {
            System.out.println("\nNo hay pagos registrados.");
            return;
        }

        System.out.println("\n" + "=".repeat(90));
        System.out.println("HISTORIAL DE PAGOS");
        System.out.println("=".repeat(90));
        System.out.printf("%-6s %-20s %-12s %-15s %-12s %-8s%n",
                "ID", "FECHA", "MONTO", "MÉTODO", "ESTADO", "PEDIDO");
        System.out.println("-".repeat(90));

        for (Pago pago : listaPagos) {
            String pedidoStr = (pago.getIdPedido() != null) ? "#" + pago.getIdPedido() : "N/A";
            System.out.printf("%-6d %-20s $%-11.2f %-15s %-12s %-8s%n",
                    pago.getIdPago(),
                    pago.getFecha(),
                    pago.getMonto(),
                    pago.getMetodoPago(),
                    pago.getEstado(),
                    pedidoStr);
        }

        System.out.println("=".repeat(90));
    }

    /**
     * Lista pagos por estado específico
     * @param estado Estado a filtrar
     */
    public void listarPagosPorEstado(String estado) {
        List<Pago> pagos = obtenerPagosPorEstado(estado);

        if (pagos.isEmpty()) {
            System.out.println("\nNo hay pagos con estado: " + estado);
            return;
        }

        System.out.println("\n" + "=".repeat(90));
        System.out.println("PAGOS CON ESTADO: " + estado.toUpperCase());
        System.out.println("=".repeat(90));
        System.out.printf("%-6s %-20s %-12s %-15s %-8s%n",
                "ID", "FECHA", "MONTO", "MÉTODO", "PEDIDO");
        System.out.println("-".repeat(90));

        for (Pago pago : pagos) {
            String pedidoStr = (pago.getIdPedido() != null) ? "#" + pago.getIdPedido() : "N/A";
            System.out.printf("%-6d %-20s $%-11.2f %-15s %-8s%n",
                    pago.getIdPago(),
                    pago.getFecha(),
                    pago.getMonto(),
                    pago.getMetodoPago(),
                    pedidoStr);
        }

        System.out.println("=".repeat(90));
    }

    /**
     * Muestra los métodos de pago disponibles
     */
    public void mostrarMetodosPago() {
        Pago.mostrarMetodosPago();
    }

    // ==================== GESTIÓN DE PAGOS ====================

    /**
     * Cancela un pago por su ID
     * @param idPago ID del pago a cancelar
     * @return true si se canceló exitosamente
     */
    public boolean cancelarPago(int idPago) {
        Pago pago = obtenerPagoPorId(idPago);

        if (pago == null) {
            System.out.println("Pago no encontrado.");
            return false;
        }

        return pago.cancelarPago();
    }

    /**
     * Reintenta procesar un pago fallido
     * @param idPago ID del pago a reintentar
     * @return true si se procesó exitosamente
     */
    public boolean reintentarPago(int idPago) {
        Pago pago = obtenerPagoPorId(idPago);

        if (pago == null) {
            System.out.println("Pago no encontrado.");
            return false;
        }

        return pago.reintentarPago();
    }

    /**
     * Muestra el detalle de un pago específico
     * @param idPago ID del pago
     */
    public void mostrarDetallePago(int idPago) {
        Pago pago = obtenerPagoPorId(idPago);

        if (pago == null) {
            System.out.println("Pago no encontrado.");
            return;
        }

        pago.mostrarDetalle();
    }

    // ==================== ESTADÍSTICAS ====================

    /**
     * Calcula el total de pagos completados
     * @return Total de dinero de pagos completados
     */
    public double calcularTotalPagosCompletados() {
        return listaPagos.stream()
                .filter(p -> "Completado".equals(p.getEstado()))
                .mapToDouble(Pago::getMonto)
                .sum();
    }

    /**
     * Cuenta la cantidad de pagos
     * @return Número total de pagos
     */
    public int contarPagos() {
        return listaPagos.size();
    }

    /**
     * Cuenta pagos por estado
     * @param estado Estado a contar
     * @return Cantidad de pagos con ese estado
     */
    public int contarPagosPorEstado(String estado) {
        return obtenerPagosPorEstado(estado).size();
    }

    /**
     * Obtiene el método de pago más usado
     * @return Método de pago más popular
     */
    public String obtenerMetodoMasUsado() {
        if (listaPagos.isEmpty()) {
            return "N/A";
        }

        return listaPagos.stream()
                .collect(Collectors.groupingBy(Pago::getMetodoPago, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }

    /**
     * Calcula la tasa de éxito de pagos
     * @return Porcentaje de pagos completados
     */
    public double calcularTasaExito() {
        if (listaPagos.isEmpty()) {
            return 0.0;
        }

        long completados = listaPagos.stream()
                .filter(p -> "Completado".equals(p.getEstado()))
                .count();

        return (completados * 100.0) / listaPagos.size();
    }

    /**
     * Muestra estadísticas generales de pagos
     */
    public void mostrarEstadisticas() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ESTADÍSTICAS DE PAGOS");
        System.out.println("=".repeat(60));
        System.out.println("Total de pagos: " + contarPagos());
        System.out.println("Pagos completados: " + contarPagosPorEstado("Completado"));
        System.out.println("Pagos pendientes: " + contarPagosPorEstado("Pendiente"));
        System.out.println("Pagos fallidos: " + contarPagosPorEstado("Fallido"));
        System.out.println("Pagos cancelados: " + contarPagosPorEstado("Cancelado"));
        System.out.printf("Total recaudado: $%.2f%n", calcularTotalPagosCompletados());
        System.out.printf("Tasa de éxito: %.1f%%%n", calcularTasaExito());
        System.out.println("Método más usado: " + obtenerMetodoMasUsado());
        System.out.println("=".repeat(60));
    }

    // ==================== GETTERS ====================

    public List<Pago> getListaPagos() {
        return listaPagos;
    }

    /**
     * Verifica si hay pagos registrados
     * @return true si la lista está vacía
     */
    public boolean estaVacia() {
        return listaPagos.isEmpty();
    }
}