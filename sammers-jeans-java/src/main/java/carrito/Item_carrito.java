package carrito;

import producto.Producto;

/**
 * Clase que representa un item individual dentro del carrito de compras
 * Contiene un producto y la cantidad seleccionada
 */
public class Item_carrito {
    private Producto producto;
    private int cantidad;
    private String tallaSeleccionada;
    private String colorSeleccionado;

    /**
     * Constructor básico
     * @param producto El producto a agregar
     * @param cantidad Cantidad del producto
     */
    public Item_carrito(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.tallaSeleccionada = null;
        this.colorSeleccionado = null;
    }

    /**
     * Constructor completo con talla y color
     * @param producto El producto a agregar
     * @param cantidad Cantidad del producto
     * @param tallaSeleccionada Talla seleccionada
     * @param colorSeleccionado Color seleccionado
     */
    public Item_carrito(Producto producto, int cantidad, String tallaSeleccionada, String colorSeleccionado) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.tallaSeleccionada = tallaSeleccionada;
        this.colorSeleccionado = colorSeleccionado;
    }

    // ==================== MÉTODOS DE NEGOCIO ====================

    /**
     * Calcula el total del item (precio * cantidad)
     * @return Total del item
     */
    public double calcularTotal() {
        return producto.getPrecio() * cantidad;
    }

    /**
     * Aumenta la cantidad del item
     * @param cantidad Cantidad a agregar
     */
    public void aumentarCantidad(int cantidad) {
        if (cantidad > 0) {
            this.cantidad += cantidad;
        }
    }

    /**
     * Disminuye la cantidad del item
     * @param cantidad Cantidad a reducir
     * @return true si se pudo reducir, false si la cantidad resultante sería menor a 1
     */
    public boolean disminuirCantidad(int cantidad) {
        if (this.cantidad - cantidad >= 1) {
            this.cantidad -= cantidad;
            return true;
        }
        return false;
    }

    /**
     * Verifica si hay suficiente stock para la cantidad seleccionada
     * @return true si hay stock suficiente
     */
    public boolean verificarStockDisponible() {
        return producto.getStock() >= cantidad;
    }

    // ==================== GETTERS ====================

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getTallaSeleccionada() {
        return tallaSeleccionada;
    }

    public String getColorSeleccionado() {
        return colorSeleccionado;
    }

    // ==================== SETTERS ====================

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void setCantidad(int cantidad) {
        if (cantidad > 0) {
            this.cantidad = cantidad;
        }
    }

    public void setTallaSeleccionada(String tallaSeleccionada) {
        this.tallaSeleccionada = tallaSeleccionada;
    }

    public void setColorSeleccionado(String colorSeleccionado) {
        this.colorSeleccionado = colorSeleccionado;
    }

    // ==================== OTROS MÉTODOS ====================

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(producto.getNombre());

        if (tallaSeleccionada != null) {
            sb.append(" (Talla: ").append(tallaSeleccionada).append(")");
        }

        if (colorSeleccionado != null) {
            sb.append(" (Color: ").append(colorSeleccionado).append(")");
        }

        sb.append(String.format(" x%d = $%.2f", cantidad, calcularTotal()));

        return sb.toString();
    }

    /**
     * Representación detallada del item
     * @return String con información completa
     */
    public String toStringDetallado() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("─".repeat(50)).append("\n");
        sb.append(String.format("Producto: %s\n", producto.getNombre()));
        sb.append(String.format("Precio unitario: $%.2f\n", producto.getPrecio()));
        sb.append(String.format("Cantidad: %d\n", cantidad));

        if (tallaSeleccionada != null) {
            sb.append(String.format("Talla: %s\n", tallaSeleccionada));
        }

        if (colorSeleccionado != null) {
            sb.append(String.format("Color: %s\n", colorSeleccionado));
        }

        sb.append(String.format("Subtotal: $%.2f\n", calcularTotal()));
        sb.append("─".repeat(50));

        return sb.toString();
    }
}