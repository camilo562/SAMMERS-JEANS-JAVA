package carrito;

import producto.Producto;
import usuarios.Usuario;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que representa el carrito de compras de un usuario
 * Maneja la adición, eliminación y cálculo de productos
 */
public class Carrito {
    private Usuario usuario;
    private Map<Integer, Item_carrito> items; // Clave: id_producto, Valor: Item_carrito

    /**
     * Constructor con usuario
     * @param usuario Usuario dueño del carrito
     */
    public Carrito(Usuario usuario) {
        this.usuario = usuario;
        this.items = new HashMap<>();
    }

    /**
     * Constructor sin usuario (carrito anónimo)
     */
    public Carrito() {
        this(null);
    }

    // ==================== AGREGAR PRODUCTO ====================

    /**
     * Agrega un producto al carrito
     * @param producto Producto a agregar
     * @param cantidad Cantidad a agregar (por defecto 1)
     * @return true si se agregó exitosamente, false si no hay stock suficiente
     */
    public boolean agregarProducto(Producto producto, int cantidad) {
        int idProducto = producto.getIdProducto();

        // Si el producto ya está en el carrito
        if (items.containsKey(idProducto)) {
            Item_carrito itemExistente = items.get(idProducto);
            int cantidadActual = itemExistente.getCantidad();
            int nuevaCantidad = cantidadActual + cantidad;

            // Validar si hay suficiente stock para la nueva cantidad
            if (nuevaCantidad > producto.getStock() + cantidadActual) {
                int stockDisponible = producto.getStock();
                System.out.println("No hay suficiente stock para " + producto.getNombre() +
                        ". Stock disponible: " + producto.getStock());
                System.out.println("Ya tienes " + cantidadActual + " en el carrito. " +
                        "Solo puedes agregar " + stockDisponible + " más.");
                System.out.println("Stock disponible total: " + stockDisponible);
                return false;
            }

            // Actualizar cantidad y restar stock
            itemExistente.setCantidad(nuevaCantidad);
            producto.reducirStock(cantidad);
            System.out.println("Cantidad actualizada: " + nuevaCantidad +
                    " unidades de " + producto.getNombre() + " en el carrito.");
            return true;

        } else {
            // Producto nuevo - Validar stock
            if (producto.getStock() < cantidad) {
                System.out.println("No hay suficiente stock para " + producto.getNombre() +
                        ". Stock disponible: " + producto.getStock() + " unidades.");
                return false;
            }

            // Agregar nuevo ítem
            Item_carrito nuevoItem = new Item_carrito(producto, cantidad);
            items.put(idProducto, nuevoItem);

            // Restar stock
            producto.reducirStock(cantidad);
            System.out.println("Se agregó " + producto.getNombre() + " x " + cantidad + " al carrito.");
            return true;
        }
    }

    /**
     * Agrega un producto al carrito con cantidad por defecto (1)
     * @param producto Producto a agregar
     * @return true si se agregó exitosamente
     */
    public boolean agregarProducto(Producto producto) {
        return agregarProducto(producto, 1);
    }

    /**
     * Agrega un producto con talla y color específicos
     * @param producto Producto a agregar
     * @param cantidad Cantidad
     * @param talla Talla seleccionada
     * @param color Color seleccionado
     * @return true si se agregó exitosamente
     */
    public boolean agregarProducto(Producto producto, int cantidad, String talla, String color) {
        int idProducto = producto.getIdProducto();

        if (items.containsKey(idProducto)) {
            // Si ya existe, actualizar cantidad
            return agregarProducto(producto, cantidad);
        } else {
            // Validar stock
            if (producto.getStock() < cantidad) {
                System.out.println("No hay suficiente stock para " + producto.getNombre() +
                        ". Stock disponible: " + producto.getStock() + " unidades.");
                return false;
            }

            // Crear nuevo item con talla y color
            Item_carrito nuevoItem = new Item_carrito(producto, cantidad, talla, color);
            items.put(idProducto, nuevoItem);
            producto.reducirStock(cantidad);

            System.out.println("Se agregó " + producto.getNombre() +
                    " (Talla: " + talla + ", Color: " + color +
                    ") x " + cantidad + " al carrito.");
            return true;
        }
    }

    // ==================== ELIMINAR PRODUCTO ====================

    /**
     * Elimina un producto del carrito
     * @param idProducto ID del producto a eliminar
     * @return true si se eliminó, false si no estaba en el carrito
     */
    public boolean eliminarProducto(int idProducto) {
        if (items.containsKey(idProducto)) {
            Item_carrito itemEliminado = items.remove(idProducto);

            // Devolver el stock al producto
            Producto producto = itemEliminado.getProducto();
            producto.aumentarStock(itemEliminado.getCantidad());

            System.out.println("Producto con ID " + idProducto + " eliminado del carrito");
            return true;
        } else {
            System.out.println("Ese producto no está en el carrito.");
            return false;
        }
    }

    /**
     * Actualiza la cantidad de un producto en el carrito
     * @param idProducto ID del producto
     * @param nuevaCantidad Nueva cantidad
     * @return true si se actualizó correctamente
     */
    public boolean actualizarCantidad(int idProducto, int nuevaCantidad) {
        if (!items.containsKey(idProducto)) {
            System.out.println("Ese producto no está en el carrito.");
            return false;
        }

        if (nuevaCantidad <= 0) {
            return eliminarProducto(idProducto);
        }

        Item_carrito item = items.get(idProducto);
        Producto producto = item.getProducto();
        int cantidadActual = item.getCantidad();
        int diferencia = nuevaCantidad - cantidadActual;

        if (diferencia > 0) {
            // Aumentar cantidad
            if (producto.getStock() < diferencia) {
                System.out.println("No hay suficiente stock. Stock disponible: " + producto.getStock());
                return false;
            }
            producto.reducirStock(diferencia);
        } else if (diferencia < 0) {
            // Disminuir cantidad
            producto.aumentarStock(Math.abs(diferencia));
        }

        item.setCantidad(nuevaCantidad);
        System.out.println("Cantidad actualizada a " + nuevaCantidad);
        return true;
    }

    // ==================== CALCULAR MONTO ====================

    /**
     * Calcula el monto total del carrito
     * @return Total a pagar
     */
    public double calcularMonto() {
        double total = 0.0;
        for (Item_carrito item : items.values()) {
            total += item.calcularTotal();
        }
        return total;
    }

    // ==================== MOSTRAR CARRITO ====================

    /**
     * Muestra el contenido del carrito
     */
    public void mostrarCarrito() {
        if (items.isEmpty()) {
            System.out.println("Tu carrito está vacío.");
            return;
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("                    TU CARRITO");
        System.out.println("=".repeat(60));

        for (Map.Entry<Integer, Item_carrito> entry : items.entrySet()) {
            System.out.printf("ID %d: %s%n", entry.getKey(), entry.getValue());
        }

        System.out.println("=".repeat(60));
        System.out.printf("TOTAL: $%.2f%n", calcularMonto());
        System.out.println("=".repeat(60));
    }

    /**
     * Vacía completamente el carrito y devuelve el stock
     */
    public void vaciarCarrito() {
        for (Item_carrito item : items.values()) {
            Producto producto = item.getProducto();
            producto.aumentarStock(item.getCantidad());
        }
        items.clear();
        System.out.println("Carrito vaciado.");
    }

    /**
     * Verifica si el carrito tiene productos
     * @return true si está vacío
     */
    public boolean estaVacio() {
        return items.isEmpty();
    }

    /**
     * Obtiene la cantidad total de items en el carrito
     * @return Número total de items diferentes
     */
    public int obtenerCantidadItems() {
        return items.size();
    }

    /**
     * Obtiene la cantidad total de productos (suma de todas las cantidades)
     * @return Cantidad total de productos
     */
    public int obtenerCantidadTotalProductos() {
        int total = 0;
        for (Item_carrito item : items.values()) {
            total += item.getCantidad();
        }
        return total;
    }

    // ==================== GETTERS Y SETTERS ====================

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Map<Integer, Item_carrito> getItems() {
        return items;
    }

    /**
     * Obtiene un item específico del carrito
     * @param idProducto ID del producto
     * @return Item o null si no existe
     */
    public Item_carrito obtenerItem(int idProducto) {
        return items.get(idProducto);
    }
}