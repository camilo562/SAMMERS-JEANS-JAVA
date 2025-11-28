package menu_principal;

import usuarios.Usuario;
import usuarios.Gestion_usuarios;
import producto.Producto;
import catalogo.Catalogo;
import carrito.Carrito;
import carrito.Item_carrito;
import pedidos.Pedido;
import pedidos.Gestion_pedidos;
import inventario.Gestion_inventario;
import pagos.Pago;
import pagos.Gestion_pagos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Menú principal del sistema Sammers Jeans
 * Conecta todos los módulos del sistema
 */
public class Menu {
    private Scanner scanner;
    private Gestion_usuarios gestionUsuarios;
    private Gestion_inventario gestionInventario;
    private Catalogo catalogo;
    private Gestion_pedidos gestionPedidos;
    private Gestion_pagos gestionPagos;
    private Usuario usuarioActual;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.gestionUsuarios = new Gestion_usuarios();
        this.gestionInventario = new Gestion_inventario();
        this.catalogo = new Catalogo(gestionInventario.getInventario());
        this.gestionPedidos = new Gestion_pedidos();
        this.gestionPagos = new Gestion_pagos();
        this.usuarioActual = null;
    }

    // ==================== MENÚ PRINCIPAL ====================

    public void iniciar() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("--------------Bienvenido a SAMMERS JEANS--------------------");
            System.out.println("=".repeat(60));
            System.out.println("---Seleccione un rol: ---");
            System.out.println("1. Administrador");
            System.out.println("2. Cliente");
            System.out.println("3. Salir");
            System.out.print("Ingrese el número correspondiente a su rol: ");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer

                switch (opcion) {
                    case 1:
                        menuAdministrador();
                        break;
                    case 2:
                        menuCliente();
                        break;
                    case 3:
                        System.out.println("Saliendo del sistema. Hasta luego!");
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("Error: Debe ingresar un número válido.");
                scanner.nextLine(); // Limpiar buffer
            }
        }

        scanner.close();
    }

    // ==================== MENÚ ADMINISTRADOR ====================

    private void menuAdministrador() {
        System.out.println("\nHas seleccionado el rol de Administrador.");

        boolean accesoConcedido = false;
        int intentos = 0;

        while (intentos < 3 && !accesoConcedido) {
            System.out.print("Ingrese su correo: ");
            String correo = scanner.nextLine().trim();
            System.out.print("Ingrese su contraseña: ");
            String contrasena = scanner.nextLine().trim();

            if (gestionUsuarios.iniciarSesionAdmin(correo, contrasena)) {
                System.out.println("\nAcceso concedido como Administrador.\n");
                usuarioActual = gestionUsuarios.buscarUsuario(correo);
                accesoConcedido = true;
                mostrarPanelAdministrador();
            } else {
                intentos++;
                System.out.println("Credenciales incorrectas. Intento " + intentos + " de 3.");
            }
        }

        if (!accesoConcedido) {
            System.out.println("Máximo de intentos alcanzado. Regresando al menú principal.");
        }

        usuarioActual = null;
    }

    private void mostrarPanelAdministrador() {
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("===== PANEL ADMINISTRADOR =====");
            System.out.println("=".repeat(60));
            System.out.println("1. Gestionar inventario");
            System.out.println("2. Ver todos los pedidos");
            System.out.println("3. Cambiar estado de pedido");
            System.out.println("4. Ver estadísticas de pagos");
            System.out.println("5. Listar usuarios");
            System.out.println("6. Ver productos con stock bajo");
            System.out.println("7. Cerrar sesión");
            System.out.println("=".repeat(60));
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        menuGestionInventario();
                        break;
                    case 2:
                        gestionPedidos.listarTodosPedidos();
                        break;
                    case 3:
                        cambiarEstadoPedido();
                        break;
                    case 4:
                        gestionPagos.mostrarEstadisticas();
                        break;
                    case 5:
                        gestionUsuarios.listarUsuarios();
                        break;
                    case 6:
                        gestionInventario.mostrarStockBajo(10);
                        break;
                    case 7:
                        System.out.println("Cerrando sesión de administrador...");
                        continuar = false;
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Error: Ingrese un número válido.");
                scanner.nextLine();
            }
        }
    }

    private void menuGestionInventario() {
        System.out.println("\n--- Gestión de Inventario ---");
        System.out.println("1. Ver inventario completo");
        System.out.println("2. Agregar producto");
        System.out.println("3. Actualizar stock");
        System.out.println("4. Actualizar precio");
        System.out.println("5. Eliminar producto");
        System.out.print("Seleccione una opción: ");

        try {
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    gestionInventario.mostrarInventario();
                    break;
                case 2:
                    // Agregar producto (implementación simplificada)
                    System.out.println("Función de agregar producto pendiente de implementación completa");
                    break;
                case 3:
                    System.out.print("ID del producto: ");
                    int id = scanner.nextInt();
                    System.out.print("Nuevo stock: ");
                    int nuevoStock = scanner.nextInt();
                    scanner.nextLine();
                    gestionInventario.actualizarStock(id, nuevoStock);
                    break;
                case 4:
                    System.out.print("ID del producto: ");
                    int idPrecio = scanner.nextInt();
                    System.out.print("Nuevo precio: ");
                    double nuevoPrecio = scanner.nextDouble();
                    scanner.nextLine();
                    gestionInventario.actualizarPrecio(idPrecio, nuevoPrecio);
                    break;
                case 5:
                    System.out.print("ID del producto a eliminar: ");
                    int idEliminar = scanner.nextInt();
                    scanner.nextLine();
                    gestionInventario.eliminarProducto(idEliminar);
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } catch (Exception e) {
            System.out.println("Error: Entrada inválida.");
            scanner.nextLine();
        }
    }

    private void cambiarEstadoPedido() {
        System.out.print("ID del pedido: ");
        try {
            int idPedido = scanner.nextInt();
            scanner.nextLine();

            System.out.println("\nEstados disponibles:");
            System.out.println("1. Pendiente");
            System.out.println("2. Confirmado");
            System.out.println("3. Enviado");
            System.out.println("4. Entregado");
            System.out.println("5. Cancelado");
            System.out.print("Nuevo estado (nombre): ");
            String nuevoEstado = scanner.nextLine().trim();

            gestionPedidos.cambiarEstadoPedido(idPedido, nuevoEstado);
        } catch (Exception e) {
            System.out.println("Error: ID inválido.");
            scanner.nextLine();
        }
    }

    // ==================== MENÚ CLIENTE ====================

    private void menuCliente() {
        System.out.println("\nHas seleccionado el rol de Cliente.\n");
        System.out.println("Seleccione una opción:");
        System.out.println("1. Registrarse");
        System.out.println("2. Iniciar sesión");
        System.out.println("3. Continuar como invitado");
        System.out.println("4. Olvidó su contraseña");
        System.out.print("Opción: ");

        String opc = scanner.nextLine().trim();

        switch (opc) {
            case "1":
                registrarCliente();
                break;
            case "2":
                iniciarSesionCliente();
                break;
            case "3":
                modoInvitado();
                break;
            case "4":
                recuperarContrasena();
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    // ==================== REGISTRO CLIENTE ====================

    private void registrarCliente() {
        System.out.println("\n=== Registro de nuevo usuario ===");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Correo: ");
        String correo = scanner.nextLine().trim();
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine().trim();

        int nuevoId = gestionUsuarios.getUsuarios().size() + 1;
        Usuario nuevoUsuario = new Usuario(nuevoId, nombre, correo, contrasena);

        if (gestionUsuarios.registrarUsuario(nuevoUsuario)) {
            System.out.println("Registro exitoso! Ahora puedes iniciar sesión para comprar.");
        } else {
            System.out.println("No se pudo completar el registro.");
        }

        System.out.println("Redirigiendo al menú principal...\n");
    }

    // ==================== INICIO SESIÓN CLIENTE ====================

    private void iniciarSesionCliente() {
        System.out.println("\n=== Iniciar sesión de cliente ===");
        System.out.print("Ingrese su correo: ");
        String correo = scanner.nextLine().trim();
        System.out.print("Ingrese su contraseña: ");
        String contrasena = scanner.nextLine().trim();

        if (gestionUsuarios.iniciarSesionUsuario(correo, contrasena)) {
            usuarioActual = gestionUsuarios.buscarUsuario(correo);
            System.out.println("Acceso concedido como Cliente. Bienvenido " + usuarioActual.getNombre() + ".\n");
            menuClienteLogueado(correo);
        } else {
            System.out.println("Correo o contraseña incorrectos.\n");
        }

        usuarioActual = null;
    }

    // ==================== MENÚ CLIENTE LOGUEADO ====================

    private void menuClienteLogueado(String correo) {
        Carrito carritoUsuario = new Carrito(usuarioActual);
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("===== MENU CLIENTE =====");
            System.out.println("=".repeat(50));
            System.out.println("1. Ver catálogo de productos");
            System.out.println("2. Buscar producto");
            System.out.println("3. Agregar producto al carrito");
            System.out.println("4. Ver carrito");
            System.out.println("5. Crear pedido desde el carrito");
            System.out.println("6. Pagar pedido pendiente");
            System.out.println("7. Dejar reseña del producto");
            System.out.println("8. Ver mis pedidos");
            System.out.println("9. Actualizar mis datos");
            System.out.println("10. Ver reseñas de productos");
            System.out.println("11. Salir");
            System.out.println("=".repeat(50));
            System.out.print("Seleccione una opción -> ");

            String opcionCliente = scanner.nextLine().trim();

            switch (opcionCliente) {
                case "1":
                    catalogo.mostrarCatalogo();
                    break;
                case "2":
                    buscarProducto();
                    break;
                case "3":
                    agregarProductoAlCarrito(carritoUsuario);
                    break;
                case "4":
                    carritoUsuario.mostrarCarrito();
                    break;
                case "5":
                    crearPedido(carritoUsuario, correo);
                    break;
                case "6":
                    pagarPedido(correo);
                    break;
                case "7":
                    dejarResena();
                    break;
                case "8":
                    gestionPedidos.listarPedidosUsuario(correo);
                    break;
                case "9":
                    actualizarDatosUsuario();
                    break;
                case "10":
                    verResenasProducto();
                    break;
                case "11":
                    System.out.println("Cerrando sesión...");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida. Intenta nuevamente.");
            }
        }
    }

    // ==================== FUNCIONES CLIENTE ====================

    private void buscarProducto() {
        System.out.print("Ingresa el nombre del producto a buscar: ");
        String nombre = scanner.nextLine().trim();
        catalogo.buscarPorNombre(nombre);
    }

    private void agregarProductoAlCarrito(Carrito carrito) {
        try {
            System.out.println("\n=== Agregar productos al carrito ===");
            System.out.print("Ingresa el ID del producto -> ");
            int productoId = scanner.nextInt();
            System.out.print("Ingresa la cantidad -> ");
            int cantidad = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            Producto producto = catalogo.obtenerProducto(productoId);
            if (producto != null) {
                carrito.agregarProducto(producto, cantidad);
            } else {
                System.out.println("El producto con ese ID no existe.");
            }
        } catch (Exception e) {
            System.out.println("Error: Debes ingresar números válidos.");
            scanner.nextLine();
        }
    }

    private void crearPedido(Carrito carrito, String correo) {
        if (carrito.estaVacio()) {
            System.out.println("El carrito está vacío. No se puede realizar el pedido.");
            System.out.println("Agrega productos primero (opción 3).");
            return;
        }

        carrito.mostrarCarrito();

        System.out.print("\nAgrega una dirección de envío (Ejemplo: Calle 123, Barrio Atalaya, Cúcuta) -> ");
        String direccion = scanner.nextLine().trim();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("¿Crear pedido con TODOS los productos del carrito?");
        System.out.println("=".repeat(60));
        System.out.print("Confirmar (si/no): ");
        String confirmacion = scanner.nextLine().trim().toLowerCase();

        if (confirmacion.equals("si")) {
            Pedido nuevoPedido = new Pedido(usuarioActual, carrito, direccion);

            if (gestionPedidos.agregarPedido(nuevoPedido)) {
                System.out.println("\nPedido #" + nuevoPedido.getIdPedido() + " creado exitosamente.");
                System.out.printf("Total: $%.2f%n", nuevoPedido.getMontoTotal());
                System.out.println("Dirección de envío: " + direccion);
                System.out.println("-----Debes pagar en la opción 6 para que se complete tu envío.----");
                System.out.println("----------NOTA: SI NO PAGAS TU PEDIDO SERÁ CANCELADO---------");

                carrito.vaciarCarrito();
                System.out.println("El carrito ha sido vaciado.");
            } else {
                System.out.println("No se pudo registrar el pedido.");
            }
        } else {
            System.out.println("Pedido cancelado.");
        }
    }

    private void pagarPedido(String correo) {
        List<Pedido> pedidosUsuario = gestionPedidos.obtenerPedidosPorUsuario(correo);
        List<Pedido> pedidosPendientes = pedidosUsuario.stream()
                .filter(p -> "Pendiente".equals(p.getEstado()))
                .toList();

        if (pedidosPendientes.isEmpty()) {
            System.out.println("No tienes pedidos pendientes de pago.");
            System.out.println("Crea un pedido primero (opción 5).");
            return;
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("PEDIDOS PENDIENTES DE PAGO");
        System.out.println("=".repeat(60));

        for (Pedido p : pedidosPendientes) {
            System.out.println("\nPedido #" + p.getIdPedido());
            System.out.println("  Fecha: " + p.getFecha());
            System.out.printf("  Total: $%.2f%n", p.getMontoTotal());
            System.out.println("  Estado: " + p.getEstado());
            System.out.println("  Dirección: " + p.getDireccionEnvio());
            System.out.println("-".repeat(60));
        }

        System.out.println("=".repeat(60));

        try {
            System.out.print("\nID del pedido a pagar: ");
            int idPed = scanner.nextInt();
            scanner.nextLine();

            Pedido pedidoAPagar = null;
            for (Pedido p : pedidosPendientes) {
                if (p.getIdPedido() == idPed) {
                    pedidoAPagar = p;
                    break;
                }
            }

            if (pedidoAPagar != null) {
                pedidoAPagar.mostrarDetalle();

                System.out.print("\n¿Cuánto deseas pagar? $");
                double montoIngresado = scanner.nextDouble();
                scanner.nextLine();

                if (montoIngresado <= 0) {
                    System.out.println("Error: El monto debe ser mayor a cero.");
                    return;
                }

                if (Math.abs(montoIngresado - pedidoAPagar.getMontoTotal()) < 0.01) {
                    System.out.println("\nMonto exacto. Procederemos con el pago.");
                    procesarPagoFinal(pedidoAPagar, montoIngresado);
                } else if (montoIngresado < pedidoAPagar.getMontoTotal()) {
                    double faltante = pedidoAPagar.getMontoTotal() - montoIngresado;
                    System.out.println("\n" + "=".repeat(60));
                    System.out.println("MONTO INSUFICIENTE");
                    System.out.printf("Te faltan: $%.2f%n", faltante);
                    System.out.println("=".repeat(60));
                } else {
                    double excedente = montoIngresado - pedidoAPagar.getMontoTotal();
                    System.out.println("\n" + "=".repeat(60));
                    System.out.println("MONTO EXCEDENTE");
                    System.out.printf("Su cambio será de: $%.2f%n", excedente);
                    System.out.println("=".repeat(60));
                    System.out.print("\n¿Deseas continuar con el pago exacto? (si/no): ");
                    String conf = scanner.nextLine().trim().toLowerCase();

                    if (conf.equals("si")) {
                        procesarPagoFinal(pedidoAPagar, pedidoAPagar.getMontoTotal());
                    } else {
                        System.out.println("Pago cancelado.");
                    }
                }
            } else {
                System.out.println("Pedido no encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Error: Entrada inválida.");
            scanner.nextLine();
        }
    }

    private void procesarPagoFinal(Pedido pedido, double monto) {
        System.out.println("\nMétodos de pago disponibles:");
        Pago.mostrarMetodosPago();
        System.out.print("\nMétodo de pago: ");
        String metodo = scanner.nextLine().trim().toLowerCase();

        Pago pagoExitoso = gestionPagos.registrarPago(metodo, monto, pedido);

        if (pagoExitoso != null) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("PAGO EXITOSO!");
            System.out.println("=".repeat(60));
            System.out.println("Pedido #" + pedido.getIdPedido() + " pagado completamente");
            System.out.printf("Monto pagado: $%.2f%n", monto);
            System.out.println("Método: " + metodo.toUpperCase());
            System.out.println("ID Pago: #" + pagoExitoso.getIdPago());
            System.out.println("Dirección de envío: " + pedido.getDireccionEnvio());
            System.out.println("=".repeat(60));
            System.out.println("\nTu pedido será enviado a la dirección especificada.");
            System.out.println("¡Gracias por tu compra!");
            System.out.println("=".repeat(60));

            pedido.cambiarEstado("Confirmado");
        } else {
            System.out.println("\nEl pago no se pudo procesar.");
        }
    }

    private void dejarResena() {
        System.out.print("Nombre del producto que desea reseñar: ");
        String nombreProducto = scanner.nextLine().trim();

        Producto producto = gestionInventario.buscarPorNombre(nombreProducto);
        if (producto == null) {
            System.out.println("No se encontró un producto con ese nombre.");
            return;
        }

        System.out.print("Escribe tu reseña: ");
        String texto = scanner.nextLine().trim();

        int calificacion = 0;
        while (calificacion < 1 || calificacion > 5) {
            try {
                System.out.print("Calificación (1-5): ");
                calificacion = scanner.nextInt();
                scanner.nextLine();
                if (calificacion < 1 || calificacion > 5) {
                    System.out.println("Debe ser un número entre 1 y 5.");
                }
            } catch (Exception e) {
                System.out.println("Debe ser un número válido.");
                scanner.nextLine();
            }
        }

        producto.agregarResena(usuarioActual, texto, calificacion);
        System.out.println("¡Gracias! Tu reseña ha sido añadida.");
    }

    private void actualizarDatosUsuario() {
        System.out.println("\n=== Actualizar datos ===");
        System.out.print("Ingresa tu correo actual: ");
        String correoActual = scanner.nextLine().trim();

        System.out.print("Nombre nuevo (Enter para no cambiar): ");
        String nombreNuevo = scanner.nextLine().trim();
        nombreNuevo = nombreNuevo.isEmpty() ? null : nombreNuevo;

        System.out.print("Correo nuevo (Enter para no cambiar): ");
        String correoNuevo = scanner.nextLine().trim();
        correoNuevo = correoNuevo.isEmpty() ? null : correoNuevo;

        System.out.print("Contraseña nueva (Enter para no cambiar): ");
        String contrasenaNueva = scanner.nextLine().trim();
        contrasenaNueva = contrasenaNueva.isEmpty() ? null : contrasenaNueva;

        if (gestionUsuarios.actualizarUsuario(correoActual, nombreNuevo, correoNuevo, contrasenaNueva)) {
            System.out.println("Datos cambiados con éxito.");
        } else {
            System.out.println("No se pudieron actualizar los datos.");
        }
    }

    private void verResenasProducto() {
        System.out.print("Nombre del producto para ver reseñas: ");
        String nombreProducto = scanner.nextLine().trim();

        Producto producto = gestionInventario.buscarPorNombre(nombreProducto);
        if (producto != null) {
            producto.mostrarResenas();
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    // ==================== MODO INVITADO ====================

    private void modoInvitado() {
        System.out.println("\n=== Modo Invitado ===");
        System.out.println("Mostrando catálogo de productos:");
        catalogo.mostrarCatalogo();

        System.out.print("\n¿Desea buscar un producto? (si/no): ");
        String busqueda = scanner.nextLine().trim().toLowerCase();
        if (busqueda.equals("si")) {
            System.out.print("Ingresa el nombre del producto a buscar: ");
            String nombre = scanner.nextLine().trim();
            catalogo.buscarPorNombre(nombre);
        }

        System.out.print("\n¿Desea registrarse como usuario? (si/no): ");
        String registro = scanner.nextLine().trim().toLowerCase();
        if (registro.equals("si")) {
            registrarCliente();
        } else {
            System.out.println("Gracias por visitar nuestro catálogo.");
        }
    }

    // ==================== RECUPERAR CONTRASEÑA ====================

    private void recuperarContrasena() {
        System.out.println("\n=== Recuperación de contraseña ===");
        System.out.print("Ingresa tu correo para recuperar tu contraseña: ");
        String correo = scanner.nextLine().trim();

        if (gestionUsuarios.recuperarContrasena(correo)) {
            System.out.println("Puedes iniciar sesión exitosamente con tu nueva contraseña.");
        } else {
            System.out.println("Recuperación fallida. Inténtalo nuevamente.");
        }
    }
}