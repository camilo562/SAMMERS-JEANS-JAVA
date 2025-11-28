package inventario;

import producto.Producto;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase para gestionar el inventario de productos
 * Incluye productos predefinidos y operaciones CRUD
 */
public class Gestion_inventario {
    private Map<Integer, Producto> inventario;

    /**
     * Constructor que inicializa el inventario con productos predefinidos
     */
    public Gestion_inventario() {
        this.inventario = new HashMap<>();
        inicializarInventario();
    }

    /**
     * Inicializa el inventario con productos predefinidos
     */
    private void inicializarInventario() {
        // Producto 1: Jean clásico caballero
        inventario.put(1, new Producto(
                1,
                "jean clasico",
                80000,
                50,
                "Caballero",
                Arrays.asList("32", "34", "36"),
                Arrays.asList("azul", "negro"),
                true
        ));

        // Producto 2: Pantalón cargo caballero
        inventario.put(2, new Producto(
                2,
                "pantalon cargo",
                90000,
                30,
                "Caballero",
                Arrays.asList("34", "36"),
                Arrays.asList("verde", "beige"),
                true
        ));

        // Producto 3: Pantalón cargo dama
        inventario.put(3, new Producto(
                3,
                "pantalon cargo",
                90000,
                30,
                "Dama",
                Arrays.asList("34", "36"),
                Arrays.asList("verde", "beige"),
                true
        ));

        // Producto 4: Jogger deportivo caballero
        inventario.put(4, new Producto(
                4,
                "jogger deportivo",
                70000,
                20,
                "Caballero",
                Arrays.asList("30", "32", "34"),
                Arrays.asList("gris", "negro"),
                true
        ));

        // Producto 5: Jogger deportivo dama
        inventario.put(5, new Producto(
                5,
                "jogger deportivo",
                70000,
                20,
                "Dama",
                Arrays.asList("30", "32", "34"),
                Arrays.asList("gris", "negro"),
                true
        ));

        // Producto 6: Bermudas caballero
        inventario.put(6, new Producto(
                6,
                "bermudas",
                60000,
                15,
                "caballero",
                Arrays.asList("28", "30", "32"),
                Arrays.asList("azul", "caqui"),
                true
        ));
    }

    // ==================== BUSCAR PRODUCTOS ====================

    /**
     * Busca un producto por nombre exacto (para reseñas)
     * @param nombreProducto Nombre del producto a buscar
     * @return Producto encontrado o null
     */
    public Producto buscarPorNombre(String nombreProducto) {
        String nombreBusqueda = nombreProducto.toLowerCase();

        for (Producto producto : inventario.values()) {
            if (producto.getNombre().toLowerCase().equals(nombreBusqueda)) {
                return producto;
            }
        }

        return null;
    }

    /**
     * Busca un producto por ID
     * @param idProducto ID del producto
     * @return Producto encontrado o null
     */
    public Producto buscarPorId(int idProducto) {
        return inventario.get(idProducto);
    }

    // ==================== AGREGAR PRODUCTO ====================

    /**
     * Agrega un nuevo producto al inventario
     * @param producto Producto a agregar
     * @return true si se agregó exitosamente
     */
    public boolean agregarProducto(Producto producto) {
        if (inventario.containsKey(producto.getIdProducto())) {
            System.out.println("El producto ya existe en el inventario.");
            return false;
        }

        inventario.put(producto.getIdProducto(), producto);
        System.out.println("Producto " + producto.getNombre() + " agregado al inventario con éxito.");
        return true;
    }

    // ==================== ELIMINAR PRODUCTO ====================

    /**
     * Elimina un producto del inventario
     * @param productoId ID del producto a eliminar
     * @return true si se eliminó exitosamente
     */
    public boolean eliminarProducto(int productoId) {
        if (!inventario.containsKey(productoId)) {
            System.out.println("El producto no existe en el inventario.");
            return false;
        }

        String nombre = inventario.get(productoId).getNombre();
        inventario.remove(productoId);
        System.out.println("El producto " + nombre + " con ID " + productoId +
                " fue eliminado del inventario.");
        return true;
    }

    // ==================== ACTUALIZAR STOCK ====================

    /**
     * Actualiza el stock de un producto
     * @param productoId ID del producto
     * @param nuevaCantidad Nueva cantidad de stock
     * @return true si se actualizó exitosamente
     */
    public boolean actualizarStock(int productoId, int nuevaCantidad) {
        if (!inventario.containsKey(productoId)) {
            System.out.println("El producto no existe en el inventario.");
            return false;
        }

        Producto producto = inventario.get(productoId);
        producto.setStock(nuevaCantidad);

        // Actualizar disponibilidad según el stock
        if (nuevaCantidad > 0) {
            producto.setDisponible(true);
        } else {
            producto.setDisponible(false);
        }

        System.out.println("Stock de " + producto.getNombre() + " actualizado a " + nuevaCantidad + ".");
        return true;
    }

    /**
     * Aumenta el stock de un producto
     * @param productoId ID del producto
     * @param cantidad Cantidad a aumentar
     * @return true si se actualizó exitosamente
     */
    public boolean aumentarStock(int productoId, int cantidad) {
        if (!inventario.containsKey(productoId)) {
            System.out.println("El producto no existe en el inventario.");
            return false;
        }

        Producto producto = inventario.get(productoId);
        int nuevoStock = producto.getStock() + cantidad;
        return actualizarStock(productoId, nuevoStock);
    }

    /**
     * Reduce el stock de un producto
     * @param productoId ID del producto
     * @param cantidad Cantidad a reducir
     * @return true si se actualizó exitosamente
     */
    public boolean reducirStock(int productoId, int cantidad) {
        if (!inventario.containsKey(productoId)) {
            System.out.println("El producto no existe en el inventario.");
            return false;
        }

        Producto producto = inventario.get(productoId);
        int nuevoStock = producto.getStock() - cantidad;

        if (nuevoStock < 0) {
            System.out.println("No se puede reducir el stock por debajo de 0.");
            return false;
        }

        return actualizarStock(productoId, nuevoStock);
    }

    // ==================== MOSTRAR INVENTARIO ====================

    /**
     * Muestra todo el inventario en formato tabla
     */
    public void mostrarInventario() {
        if (inventario.isEmpty()) {
            System.out.println("El inventario está vacío");
            return;
        }

        System.out.println("\n" + "=".repeat(130));
        System.out.printf("%-5s %-25s %-20s %-15s %-20s %-20s %-20s%n",
                "ID", "NOMBRE", "PRECIO", "STOCK", "CATEGORIA", "TALLAS", "COLORES");
        System.out.println("=".repeat(130));

        for (Map.Entry<Integer, Producto> entry : inventario.entrySet()) {
            Producto producto = entry.getValue();

            if (producto.getStock() > 0) {
                System.out.printf("%-5d %-25s $%-19.0f %-15d %-20s %-20s %-20s%n",
                        entry.getKey(),
                        producto.getNombre(),
                        producto.getPrecio(),
                        producto.getStock(),
                        producto.getCategoria(),
                        producto.getTallas().toString(),
                        producto.getColores().toString());
            }
        }

        System.out.println("=".repeat(130));
        System.out.println("Total de productos: " + inventario.size());
    }

    /**
     * Muestra productos con stock bajo (menor a un umbral)
     * @param umbral Cantidad mínima de stock
     */
    public void mostrarStockBajo(int umbral) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("PRODUCTOS CON STOCK BAJO (Menos de " + umbral + " unidades)");
        System.out.println("=".repeat(80));
        System.out.printf("%-5s %-30s %-15s %-15s%n", "ID", "NOMBRE", "STOCK", "CATEGORÍA");
        System.out.println("-".repeat(80));

        boolean hayStockBajo = false;

        for (Map.Entry<Integer, Producto> entry : inventario.entrySet()) {
            Producto producto = entry.getValue();

            if (producto.getStock() < umbral && producto.getStock() > 0) {
                hayStockBajo = true;
                System.out.printf("%-5d %-30s %-15d %-15s%n",
                        entry.getKey(),
                        producto.getNombre(),
                        producto.getStock(),
                        producto.getCategoria());
            }
        }

        if (!hayStockBajo) {
            System.out.println("No hay productos con stock bajo.");
        }

        System.out.println("=".repeat(80));
    }

    /**
     * Muestra productos sin stock
     */
    public void mostrarSinStock() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("PRODUCTOS SIN STOCK");
        System.out.println("=".repeat(80));
        System.out.printf("%-5s %-30s %-15s%n", "ID", "NOMBRE", "CATEGORÍA");
        System.out.println("-".repeat(80));

        boolean haySinStock = false;

        for (Map.Entry<Integer, Producto> entry : inventario.entrySet()) {
            Producto producto = entry.getValue();

            if (producto.getStock() == 0) {
                haySinStock = true;
                System.out.printf("%-5d %-30s %-15s%n",
                        entry.getKey(),
                        producto.getNombre(),
                        producto.getCategoria());
            }
        }

        if (!haySinStock) {
            System.out.println("Todos los productos tienen stock.");
        }

        System.out.println("=".repeat(80));
    }

    // ==================== ACTUALIZAR PRODUCTO ====================

    /**
     * Actualiza el precio de un producto
     * @param productoId ID del producto
     * @param nuevoPrecio Nuevo precio
     * @return true si se actualizó exitosamente
     */
    public boolean actualizarPrecio(int productoId, double nuevoPrecio) {
        if (!inventario.containsKey(productoId)) {
            System.out.println("El producto no existe en el inventario.");
            return false;
        }

        if (nuevoPrecio <= 0) {
            System.out.println("El precio debe ser mayor a 0.");
            return false;
        }

        Producto producto = inventario.get(productoId);
        double precioAnterior = producto.getPrecio();
        producto.setPrecio(nuevoPrecio);

        System.out.printf("Precio de %s actualizado de $%.2f a $%.2f%n",
                producto.getNombre(), precioAnterior, nuevoPrecio);
        return true;
    }

    // ==================== ESTADÍSTICAS ====================

    /**
     * Calcula el valor total del inventario
     * @return Valor total (precio * stock)
     */
    public double calcularValorTotalInventario() {
        double total = 0.0;

        for (Producto producto : inventario.values()) {
            total += producto.getPrecio() * producto.getStock();
        }

        return total;
    }

    /**
     * Cuenta cuántos productos hay en total
     * @return Número de productos diferentes
     */
    public int contarProductos() {
        return inventario.size();
    }

    /**
     * Cuenta el total de unidades en stock
     * @return Suma de todos los stocks
     */
    public int contarUnidadesTotales() {
        int total = 0;

        for (Producto producto : inventario.values()) {
            total += producto.getStock();
        }

        return total;
    }

    // ==================== GETTERS Y SETTERS ====================

    /**
     * Obtiene el mapa del inventario
     * @return Inventario completo
     */
    public Map<Integer, Producto> getInventario() {
        return inventario;
    }

    /**
     * Verifica si el inventario está vacío
     * @return true si está vacío
     */
    public boolean estaVacio() {
        return inventario.isEmpty();
    }

    /**
     * Verifica si un producto existe en el inventario
     * @param idProducto ID del producto
     * @return true si existe
     */
    public boolean existeProducto(int idProducto) {
        return inventario.containsKey(idProducto);
    }
}