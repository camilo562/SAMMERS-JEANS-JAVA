package pedidos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase para gestionar todos los pedidos del sistema
 * Funciona como base de datos de pedidos
 */
public class Gestion_pedidos {
    private List<Pedido> basePedidos;

    /**
     * Constructor que inicializa la lista de pedidos
     */
    public Gestion_pedidos() {
        this.basePedidos = new ArrayList<>();
    }

    // ==================== REGISTRAR PEDIDO ====================

    /**
     * Agrega un pedido a la base de pedidos
     * @param pedido Pedido a agregar
     * @return true si se agregó exitosamente
     */
    public boolean agregarPedido(Pedido pedido) {
        if (pedido.registrarPedido()) {
            basePedidos.add(pedido);
            return true;
        }
        return false;
    }

    // ==================== OBTENER PEDIDOS ====================

    /**
     * Obtiene todos los pedidos de un usuario específico
     * @param correoUsuario Correo del usuario
     * @return Lista de pedidos del usuario
     */
    public List<Pedido> obtenerPedidosPorUsuario(String correoUsuario) {
        return basePedidos.stream()
                .filter(p -> p.getCorreoUsuario().equals(correoUsuario))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un pedido por su ID
     * @param idPedido ID del pedido
     * @return Pedido encontrado o null
     */
    public Pedido obtenerPedidoPorId(int idPedido) {
        for (Pedido pedido : basePedidos) {
            if (pedido.getIdPedido() == idPedido) {
                return pedido;
            }
        }
        return null;
    }

    /**
     * Obtiene todos los pedidos del sistema
     * @return Lista completa de pedidos
     */
    public List<Pedido> obtenerTodosPedidos() {
        return new ArrayList<>(basePedidos);
    }

    /**
     * Obtiene pedidos por estado
     * @param estado Estado del pedido (Pendiente, Confirmado, etc.)
     * @return Lista de pedidos con ese estado
     */
    public List<Pedido> obtenerPedidosPorEstado(String estado) {
        return basePedidos.stream()
                .filter(p -> p.getEstado().equalsIgnoreCase(estado))
                .collect(Collectors.toList());
    }

    // ==================== LISTAR PEDIDOS ====================

    /**
     * Lista todos los pedidos de un usuario
     * @param correoUsuario Correo del usuario
     */
    public void listarPedidosUsuario(String correoUsuario) {
        List<Pedido> pedidos = obtenerPedidosPorUsuario(correoUsuario);

        if (pedidos.isEmpty()) {
            System.out.println("\nNo tienes pedidos registrados.");
            return;
        }

        System.out.println("\n" + "=".repeat(70));
        System.out.println("TUS PEDIDOS");
        System.out.println("=".repeat(70));
        System.out.printf("%-6s %-20s %-15s %-10s%n", "ID", "FECHA", "ESTADO", "TOTAL");
        System.out.println("-".repeat(70));

        for (Pedido pedido : pedidos) {
            System.out.printf("%-6d %-20s %-15s $%-9.2f%n",
                    pedido.getIdPedido(),
                    pedido.getFecha(),
                    pedido.getEstado(),
                    pedido.getMontoTotal());
        }

        System.out.println("=".repeat(70));
    }

    /**
     * Lista todos los pedidos del sistema (para administradores)
     */
    public void listarTodosPedidos() {
        if (basePedidos.isEmpty()) {
            System.out.println("\nNo hay pedidos registrados en el sistema.");
            return;
        }

        System.out.println("\n" + "=".repeat(80));
        System.out.println("TODOS LOS PEDIDOS");
        System.out.println("=".repeat(80));
        System.out.printf("%-6s %-20s %-20s %-15s %-10s%n",
                "ID", "CLIENTE", "FECHA", "ESTADO", "TOTAL");
        System.out.println("-".repeat(80));

        for (Pedido pedido : basePedidos) {
            System.out.printf("%-6d %-20s %-20s %-15s $%-9.2f%n",
                    pedido.getIdPedido(),
                    pedido.getNombreUsuario(),
                    pedido.getFecha(),
                    pedido.getEstado(),
                    pedido.getMontoTotal());
        }

        System.out.println("=".repeat(80));
    }

    /**
     * Lista pedidos por estado específico
     * @param estado Estado a filtrar
     */
    public void listarPedidosPorEstado(String estado) {
        List<Pedido> pedidos = obtenerPedidosPorEstado(estado);

        if (pedidos.isEmpty()) {
            System.out.println("\nNo hay pedidos con estado: " + estado);
            return;
        }

        System.out.println("\n" + "=".repeat(80));
        System.out.println("PEDIDOS CON ESTADO: " + estado.toUpperCase());
        System.out.println("=".repeat(80));
        System.out.printf("%-6s %-20s %-20s %-10s%n",
                "ID", "CLIENTE", "FECHA", "TOTAL");
        System.out.println("-".repeat(80));

        for (Pedido pedido : pedidos) {
            System.out.printf("%-6d %-20s %-20s $%-9.2f%n",
                    pedido.getIdPedido(),
                    pedido.getNombreUsuario(),
                    pedido.getFecha(),
                    pedido.getMontoTotal());
        }

        System.out.println("=".repeat(80));
    }

    // ==================== GESTIÓN DE PEDIDOS ====================

    /**
     * Cancela un pedido por su ID
     * @param idPedido ID del pedido a cancelar
     * @return true si se canceló exitosamente
     */
    public boolean cancelarPedido(int idPedido) {
        Pedido pedido = obtenerPedidoPorId(idPedido);

        if (pedido == null) {
            System.out.println("Pedido no encontrado.");
            return false;
        }

        return pedido.cancelarPedido();
    }

    /**
     * Cambia el estado de un pedido
     * @param idPedido ID del pedido
     * @param nuevoEstado Nuevo estado
     * @return true si se cambió exitosamente
     */
    public boolean cambiarEstadoPedido(int idPedido, String nuevoEstado) {
        Pedido pedido = obtenerPedidoPorId(idPedido);

        if (pedido == null) {
            System.out.println("Pedido no encontrado.");
            return false;
        }

        return pedido.cambiarEstado(nuevoEstado);
    }

    /**
     * Muestra el detalle de un pedido específico
     * @param idPedido ID del pedido
     */
    public void mostrarDetallePedido(int idPedido) {
        Pedido pedido = obtenerPedidoPorId(idPedido);

        if (pedido == null) {
            System.out.println("Pedido no encontrado.");
            return;
        }

        pedido.mostrarDetalle();
    }

    // ==================== ESTADÍSTICAS ====================

    /**
     * Calcula el total de ventas del sistema
     * @return Total vendido
     */
    public double calcularTotalVentas() {
        double total = 0.0;
        for (Pedido pedido : basePedidos) {
            if (!"Cancelado".equals(pedido.getEstado())) {
                total += pedido.getMontoTotal();
            }
        }
        return total;
    }

    /**
     * Cuenta la cantidad de pedidos
     * @return Número total de pedidos
     */
    public int contarPedidos() {
        return basePedidos.size();
    }

    /**
     * Cuenta pedidos por estado
     * @param estado Estado a contar
     * @return Cantidad de pedidos con ese estado
     */
    public int contarPedidosPorEstado(String estado) {
        return obtenerPedidosPorEstado(estado).size();
    }

    // ==================== GETTERS ====================

    public List<Pedido> getBasePedidos() {
        return basePedidos;
    }

    /**
     * Verifica si hay pedidos registrados
     * @return true si la lista está vacía
     */
    public boolean estaVacia() {
        return basePedidos.isEmpty();
    }
}