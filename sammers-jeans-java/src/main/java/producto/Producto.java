package producto;

import usuarios.Usuario;
import java.util.ArrayList;
import java.util.List;


public class Producto {
    private int idProducto;
    private String nombre;
    private double precio;
    private int stock;
    private String categoria;
    private List<String> tallas;
    private List<String> colores;
    private boolean disponible;
    private List<Resena> resenas;


    public Producto(int idProducto, String nombre, double precio, int stock,
                    String categoria, List<String> tallas, List<String> colores, boolean disponible) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
        this.tallas = (tallas != null) ? tallas : new ArrayList<>();
        this.colores = (colores != null) ? colores : new ArrayList<>();
        this.disponible = disponible;
        this.resenas = new ArrayList<>();
    }




    public Producto(int idProducto, String nombre, double precio, int stock, String categoria) {
        this(idProducto, nombre, precio, stock, categoria, null, null, true);
    }




    public String verificarEstado() {
        return disponible ? "Disponible" : "No disponible";
    }



    public void agregarResena(Usuario usuario, String texto, int calificacion) {
        if (calificacion < 1 || calificacion > 5) {
            System.out.println("La calificación debe estar entre 1 y 5");
            return;
        }

        Resena nuevaResena = new Resena(usuario.getNombre(), texto, calificacion);
        resenas.add(nuevaResena);
        System.out.println("Reseña agregada exitosamente");
    }



    public void mostrarResenas() {
        if (resenas.isEmpty()) {
            System.out.println("No hay reseñas disponibles");
            return;
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("RESEÑAS PARA: " + nombre);
        System.out.println("=".repeat(60));

        for (Resena resena : resenas) {
            System.out.println(resena);
        }

        System.out.println("=".repeat(60));
    }




    public double obtenerPromedioCalificacion() {
        if (resenas.isEmpty()) {
            return 0.0;
        }

        int suma = 0;
        for (Resena resena : resenas) {
            suma += resena.getCalificacion();
        }

        return (double) suma / resenas.size();
    }



    public boolean reducirStock(int cantidad) {
        if (stock >= cantidad) {
            stock -= cantidad;
            if (stock == 0) {
                disponible = false;
            }
            return true;
        }
        return false;
    }



    public void aumentarStock(int cantidad) {
        stock += cantidad;
        if (stock > 0) {
            disponible = true;
        }
    }



    public int getIdProducto() {
        return idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public String getCategoria() {
        return categoria;
    }

    public List<String> getTallas() {
        return tallas;
    }

    public List<String> getColores() {
        return colores;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public List<Resena> getResenas() {
        return resenas;
    }



    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setTallas(List<String> tallas) {
        this.tallas = tallas;
    }

    public void setColores(List<String> colores) {
        this.colores = colores;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }



    @Override
    public String toString() {
        return String.format("Producto(%d, %s, $%.2f, stock=%d, Categoria=%s)",
                idProducto, nombre, precio, stock, categoria);
    }

    // ==================== CLASE INTERNA: RESEÑA ====================


    public static class Resena {
        private String nombreUsuario;
        private String texto;
        private int calificacion;

        public Resena(String nombreUsuario, String texto, int calificacion) {
            this.nombreUsuario = nombreUsuario;
            this.texto = texto;
            this.calificacion = calificacion;
        }

        public String getNombreUsuario() {
            return nombreUsuario;
        }

        public String getTexto() {
            return texto;
        }

        public int getCalificacion() {
            return calificacion;
        }

        @Override
        public String toString() {
            String estrellas = "★".repeat(calificacion) + "☆".repeat(5 - calificacion);
            return String.format("Usuario: %s | %s | %s\n\"%s\"",
                    nombreUsuario, estrellas, calificacion + "/5", texto);
        }
    }
}