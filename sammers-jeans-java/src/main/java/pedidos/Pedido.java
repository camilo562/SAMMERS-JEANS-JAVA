package pedidos;

import usuarios.Usuario;
import carrito.Carrito;
import carrito.Item_carrito;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que representa un pedido realizado por un usuario
 * Contiene información del cliente, productos y estado del pedido
 */
public class Pedido {
    private static int contadorPedidos = 1; // Contador estático para generar IDs

    private int idPedido;
    private String nombreUsuario;
    private String correoUsuario;
    private Map<Integer, Item_carrito> items;
    private double montoTotal;
    private String estado;
    private String fecha;
    private String direccionEnvio;

    /**
     * Constructor de Pedido
     * @param usuario Usuario que realiza el pedido
     * @param carrito Carrito con los productos
     * @param direccionEnvio Dirección de envío
     */
    public Pedido(Usuario usuario, Carrito carrito, String direccionEnvio) {
        this.idPedido = contadorPedidos++;
        this.nombreUsuario = usuario.getNombre();
        this.correoUsuario = usuario.getCorreo();

        // Copiar items del carrito (crear nuevo HashMap con los mismos items)
        this.items = new HashMap<>(carrito.getItems());

        this.montoTotal = carrito.calcularMonto();
        this.estado = "Pendiente";

        // Formato de fecha: "YYYY-MM-DD HH:MM:SS"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.fecha = LocalDateTime.now().format(formatter);

        this.direccionEnvio = direccionEnvio;
    }

    // ==================== MÉTODOS DE NEGOCIO ====================

    /**
     * Registra el pedido (para ser usado en una lista global)
     * @return true si se registró exitosamente
     */
    public boolean registrarPedido() {
        try {
            System.out.println("\nPedido #" + idPedido + " registrado exitosamente");
            System.out.println("Fecha: " + fecha);
            System.out.printf("Total: $%.2f%n", montoTotal);
            System.out.println("Dirección de envío: " + direccionEnvio);
            return true;
        } catch (Exception e) {
            System.out.println("Error al registrar el pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Cancela el pedido y devuelve el stock a los productos
     * @return true si se canceló exitosamente
     */
    public boolean cancelarPedido() {
        if ("Cancelado".equals(estado)) {
            System.out.println("Este pedido ya está cancelado.");
            return false;
        }

        if ("Enviado".equals(estado) || "Entregado".equals(estado)) {
            System.out.println("No se puede cancelar un pedido con estado '" + estado + "'.");
            return false;
        }

        // Devolver el stock a los productos
        for (Map.Entry<Integer, Item_carrito> entry : items.entrySet()) {
            Item_carrito item = entry.getValue();
            item.getProducto().aumentarStock(item.getCantidad());
            System.out.println("Stock devuelto: " + item.getProducto().getNombre() +
                    " +" + item.getCantidad());
        }

        estado = "Cancelado";
        System.out.println("Pedido #" + idPedido + " cancelado exitosamente.");
        return true;
    }

    /**
     * Cambia el estado del pedido
     * @param nuevoEstado Nuevo estado del pedido
     * @return true si se cambió exitosamente
     */
    public boolean cambiarEstado(String nuevoEstado) {
        String[] estadosValidos = {"Pendiente", "Confirmado", "Enviado", "Entregado", "Cancelado"};

        boolean esValido = false;
        for (String estadoValido : estadosValidos) {
            if (estadoValido.equals(nuevoEstado)) {
                esValido = true;
                break;
            }
        }

        if (!esValido) {
            System.out.println("Estado inválido. Estados válidos: Pendiente, Confirmado, Enviado, Entregado, Cancelado");
            return false;
        }

        estado = nuevoEstado;
        System.out.println("Estado del pedido #" + idPedido + " actualizado a: " + nuevoEstado);
        return true;
    }

    /**
     * Muestra el detalle completo del pedido
     */
    public void mostrarDetalle() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("PEDIDO #" + idPedido);
        System.out.println("=".repeat(60));
        System.out.println("Cliente: " + nombreUsuario);
        System.out.println("Correo: " + correoUsuario);
        System.out.println("Fecha: " + fecha);
        System.out.println("Estado: " + estado);
        System.out.println("Dirección de envío: " + direccionEnvio);
        System.out.println("-".repeat(60));
        System.out.println("PRODUCTOS:");

        for (Map.Entry<Integer, Item_carrito> entry : items.entrySet()) {
            Item_carrito item = entry.getValue();
            System.out.printf("  • %s x%d - $%.2f%n",
                    item.getProducto().getNombre(),
                    item.getCantidad(),
                    item.calcularTotal());
        }

        System.out.println("-".repeat(60));
        System.out.printf("TOTAL: $%.2f%n", montoTotal);
        System.out.println("=".repeat(60));
    }

    // ==================== GETTERS ====================

    public int getIdPedido() {
        return idPedido;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public Map<Integer, Item_carrito> getItems() {
        return items;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public String getEstado() {
        return estado;
    }

    public String getFecha() {
        return fecha;
    }

    public String getDireccionEnvio() {
        return direccionEnvio;
    }

    // ==================== SETTERS ====================

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setDireccionEnvio(String direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }

    // ==================== OTROS MÉTODOS ====================

    /**
     * Resetea el contador de pedidos (útil para pruebas)
     */
    public static void resetearContador() {
        contadorPedidos = 1;
    }

    /**
     * Obtiene el siguiente ID de pedido sin incrementar
     * @return Próximo ID disponible
     */
    public static int obtenerProximoId() {
        return contadorPedidos;
    }

    @Override
    public String toString() {
        return String.format("Pedido #%d - %s - $%.2f - %s",
                idPedido, nombreUsuario, montoTotal, estado);
    }
}