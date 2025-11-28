package catalogo;

import producto.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Clase que representa el catálogo de productos
 * Muestra productos disponibles y permite búsquedas y filtros
 */
public class Catalogo {
    private Map<Integer, Producto> productos;

    /**
     * Constructor que recibe el inventario de productos
     * @param inventario Mapa de productos del inventario
     */
    public Catalogo(Map<Integer, Producto> inventario) {
        this.productos = inventario;
    }

    // ==================== MOSTRAR CATÁLOGO ====================

    /**
     * Muestra todos los productos disponibles con stock
     */
    public void mostrarCatalogo() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("           CATÁLOGO DE PRODUCTOS");
        System.out.println("=".repeat(60));

        boolean hayProductos = false;

        for (Map.Entry<Integer, Producto> entry : productos.entrySet()) {
            Producto producto = entry.getValue();

            if (producto.isDisponible() && producto.getStock() > 0) {
                hayProductos = true;
                System.out.println("ID: " + entry.getKey());
                System.out.println("Nombre: " + producto.getNombre());
                System.out.printf("Precio: $%.2f%n", producto.getPrecio());
                System.out.println("Stock: " + producto.getStock());
                System.out.println("Categoría: " + producto.getCategoria());
                System.out.println("Tallas disponibles: " + producto.getTallas());
                System.out.println("Colores disponibles: " + producto.getColores());
                System.out.println("-".repeat(60));
            }
        }

        if (!hayProductos) {
            System.out.println("No hay productos disponibles en el catálogo.");
        }

        System.out.println("=".repeat(60) + "\n");
    }

    /**
     * Muestra el catálogo de forma resumida (tabla)
     */
    public void mostrarCatalogoResumido() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("                    CATÁLOGO DE PRODUCTOS");
        System.out.println("=".repeat(80));
        System.out.printf("%-5s %-30s %-12s %-10s %-15s%n",
                "ID", "NOMBRE", "PRECIO", "STOCK", "CATEGORÍA");
        System.out.println("-".repeat(80));

        boolean hayProductos = false;

        for (Map.Entry<Integer, Producto> entry : productos.entrySet()) {
            Producto producto = entry.getValue();

            if (producto.isDisponible() && producto.getStock() > 0) {
                hayProductos = true;
                System.out.printf("%-5d %-30s $%-11.2f %-10d %-15s%n",
                        entry.getKey(),
                        producto.getNombre(),
                        producto.getPrecio(),
                        producto.getStock(),
                        producto.getCategoria());
            }
        }

        if (!hayProductos) {
            System.out.println("No hay productos disponibles en el catálogo.");
        }

        System.out.println("=".repeat(80) + "\n");
    }

    // ==================== BUSCAR PRODUCTOS ====================

    /**
     * Busca productos por nombre (coincidencia parcial)
     * @param nombre Nombre o parte del nombre a buscar
     * @return Lista de productos encontrados
     */
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> resultados = new ArrayList<>();

        for (Producto producto : productos.values()) {
            if (producto.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultados.add(producto);
            }
        }

        if (resultados.isEmpty()) {
            System.out.println("No se encontraron productos con ese nombre.");
            return resultados;
        }

        System.out.println("\nResultados de búsqueda:");
        for (Producto prod : resultados) {
            System.out.printf("- %s ($%.2f) - ID %d%n",
                    prod.getNombre(),
                    prod.getPrecio(),
                    prod.getIdProducto());
        }

        return resultados;
    }

    /**
     * Obtiene un producto específico por su ID
     * @param idProducto ID del producto
     * @return Producto encontrado o null
     */
    public Producto obtenerProducto(int idProducto) {
        if (productos.containsKey(idProducto)) {
            return productos.get(idProducto);
        } else {
            System.out.println("El producto no existe en el catálogo.");
            return null;
        }
    }

    /**
     * Busca productos por categoría
     * @param categoria Categoría a buscar
     * @return Lista de productos de esa categoría
     */
    public List<Producto> buscarPorCategoria(String categoria) {
        List<Producto> resultados = new ArrayList<>();

        for (Producto producto : productos.values()) {
            if (producto.getCategoria().equalsIgnoreCase(categoria) &&
                    producto.getStock() > 0) {
                resultados.add(producto);
            }
        }

        if (resultados.isEmpty()) {
            System.out.println("No se encontraron productos en esa categoría.");
            return resultados;
        }

        System.out.println("\nProductos en categoría: " + categoria);
        for (Producto prod : resultados) {
            System.out.printf("- %s ($%.2f) - ID %d%n",
                    prod.getNombre(),
                    prod.getPrecio(),
                    prod.getIdProducto());
        }

        return resultados;
    }

    // ==================== FILTRAR PRODUCTOS ====================

    /**
     * Filtra productos por rango de precio
     * @param minimo Precio mínimo
     * @param maximo Precio máximo
     * @return Lista de productos en ese rango
     */
    public List<Producto> filtrarPorPrecio(double minimo, double maximo) {
        List<Producto> resultados = new ArrayList<>();

        for (Producto producto : productos.values()) {
            if (producto.getPrecio() >= minimo &&
                    producto.getPrecio() <= maximo &&
                    producto.getStock() > 0) {
                resultados.add(producto);
            }
        }

        if (resultados.isEmpty()) {
            System.out.println("No se encontraron productos en ese rango de precio.");
            return resultados;
        }

        System.out.printf("\nProductos entre $%.2f y $%.2f:%n", minimo, maximo);
        for (Producto prod : resultados) {
            System.out.printf("- %s ($%.2f) - ID %d%n",
                    prod.getNombre(),
                    prod.getPrecio(),
                    prod.getIdProducto());
        }

        return resultados;
    }

    /**
     * Filtra prendas principales (jeans, pantalones, joggers, bermudas)
     * @return Lista de prendas principales disponibles
     */
    public List<Producto> filtrarPrendasPrincipales() {
        String[] claves = {"jean", "pantalon", "jogger", "bermuda"};
        List<Producto> resultados = new ArrayList<>();

        for (Producto producto : productos.values()) {
            String nombreLower = producto.getNombre().toLowerCase();

            for (String clave : claves) {
                if (nombreLower.contains(clave) && producto.getStock() > 0) {
                    resultados.add(producto);
                    break; // Evitar duplicados
                }
            }
        }

        if (resultados.isEmpty()) {
            System.out.println("No se encontraron prendas principales disponibles.");
            return resultados;
        }

        System.out.println("\nPrendas principales disponibles:");
        for (Producto prod : resultados) {
            System.out.printf("- %s ($%.2f) - ID %d%n",
                    prod.getNombre(),
                    prod.getPrecio(),
                    prod.getIdProducto());
        }

        return resultados;
    }

    /**
     * Filtra productos que tienen una talla específica
     * @param talla Talla a buscar
     * @return Lista de productos con esa talla
     */
    public List<Producto> filtrarPorTalla(String talla) {
        List<Producto> resultados = new ArrayList<>();

        for (Producto producto : productos.values()) {
            if (producto.getTallas().contains(talla) &&
                    producto.getStock() > 0) {
                resultados.add(producto);
            }
        }

        if (resultados.isEmpty()) {
            System.out.println("No se encontraron productos con talla: " + talla);
            return resultados;
        }

        System.out.println("\nProductos con talla " + talla + ":");
        for (Producto prod : resultados) {
            System.out.printf("- %s ($%.2f) - ID %d%n",
                    prod.getNombre(),
                    prod.getPrecio(),
                    prod.getIdProducto());
        }

        return resultados;
    }

    /**
     * Filtra productos que tienen un color específico
     * @param color Color a buscar
     * @return Lista de productos con ese color
     */
    public List<Producto> filtrarPorColor(String color) {
        List<Producto> resultados = new ArrayList<>();

        for (Producto producto : productos.values()) {
            if (producto.getColores().contains(color) &&
                    producto.getStock() > 0) {
                resultados.add(producto);
            }
        }

        if (resultados.isEmpty()) {
            System.out.println("No se encontraron productos con color: " + color);
            return resultados;
        }

        System.out.println("\nProductos en color " + color + ":");
        for (Producto prod : resultados) {
            System.out.printf("- %s ($%.2f) - ID %d%n",
                    prod.getNombre(),
                    prod.getPrecio(),
                    prod.getIdProducto());
        }

        return resultados;
    }

    // ==================== PRODUCTOS DESTACADOS ====================

    /**
     * Muestra los productos más baratos
     * @param cantidad Cantidad de productos a mostrar
     * @return Lista de productos más baratos
     */
    public List<Producto> obtenerMasBaratos(int cantidad) {
        List<Producto> disponibles = new ArrayList<>();

        for (Producto producto : productos.values()) {
            if (producto.isDisponible() && producto.getStock() > 0) {
                disponibles.add(producto);
            }
        }

        disponibles.sort((p1, p2) -> Double.compare(p1.getPrecio(), p2.getPrecio()));

        int limite = Math.min(cantidad, disponibles.size());
        List<Producto> resultado = disponibles.subList(0, limite);

        System.out.println("\nProductos más económicos:");
        for (Producto prod : resultado) {
            System.out.printf("- %s ($%.2f) - ID %d%n",
                    prod.getNombre(),
                    prod.getPrecio(),
                    prod.getIdProducto());
        }

        return resultado;
    }

    /**
     * Muestra productos con mejor calificación
     * @param cantidad Cantidad de productos a mostrar
     * @return Lista de productos mejor calificados
     */
    public List<Producto> obtenerMejorCalificados(int cantidad) {
        List<Producto> disponibles = new ArrayList<>();

        for (Producto producto : productos.values()) {
            if (producto.isDisponible() &&
                    producto.getStock() > 0 &&
                    !producto.getResenas().isEmpty()) {
                disponibles.add(producto);
            }
        }

        disponibles.sort((p1, p2) ->
                Double.compare(p2.obtenerPromedioCalificacion(),
                        p1.obtenerPromedioCalificacion()));

        int limite = Math.min(cantidad, disponibles.size());
        List<Producto> resultado = disponibles.subList(0, limite);

        System.out.println("\nProductos mejor calificados:");
        for (Producto prod : resultado) {
            System.out.printf("- %s (★%.1f) - $%.2f - ID %d%n",
                    prod.getNombre(),
                    prod.obtenerPromedioCalificacion(),
                    prod.getPrecio(),
                    prod.getIdProducto());
        }

        return resultado;
    }

    // ==================== MÉTODOS AUXILIARES ====================

    /**
     * Cuenta cuántos productos disponibles hay
     * @return Número de productos con stock
     */
    public int contarProductosDisponibles() {
        int contador = 0;
        for (Producto producto : productos.values()) {
            if (producto.isDisponible() && producto.getStock() > 0) {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Obtiene todas las categorías disponibles
     * @return Lista de categorías únicas
     */
    public List<String> obtenerCategorias() {
        List<String> categorias = new ArrayList<>();

        for (Producto producto : productos.values()) {
            String categoria = producto.getCategoria();
            if (!categorias.contains(categoria)) {
                categorias.add(categoria);
            }
        }

        return categorias;
    }

    // ==================== GETTERS ====================

    public Map<Integer, Producto> getProductos() {
        return productos;
    }

    public void setProductos(Map<Integer, Producto> productos) {
        this.productos = productos;
    }
}