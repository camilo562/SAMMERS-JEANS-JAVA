package usuarios;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Gestion_usuarios {
    private Map<String, Usuario> usuarios;
    private Map<String, Usuario> admins;
    private Scanner scanner;


    public Gestion_usuarios() {
        this.usuarios = new HashMap<>();
        this.admins = new HashMap<>();
        this.scanner = new Scanner(System.in);

        // Usuarios creados
        usuarios.put("camilo@gmail.com",
                new Usuario(1, "camilo", "camilo@gmail.com", "CAMILO123"));
        usuarios.put("ricardo@gmail.com",
                new Usuario(2, "ricardo", "ricardo@gmail.com", "RICARDO123"));

        // Administradores predefinidos
        admins.put("administrador@gmail.com",
                new Usuario(100, "admin", "administrador@gmail.com", "ADMIN123", "admin"));
    }




    public boolean registrarUsuario(Usuario usuario) {
        if (usuarios.containsKey(usuario.getCorreo())) {
            System.out.println("El usuario ya existe. No se puede registrar.");
            return false;
        }

        usuarios.put(usuario.getCorreo(), usuario);
        System.out.println("Usuario " + usuario.getNombre() + " registrado con éxito.");
        return true;
    }




    public boolean iniciarSesionUsuario(String correo, String contrasena) {
        if (!usuarios.containsKey(correo)) {
            System.out.println("El usuario no existe.");
            return false;
        }

        Usuario usuario = usuarios.get(correo);

        if (!usuario.getContrasena().equals(contrasena)) {
            System.out.println("Contraseña incorrecta.");
            return false;
        }

        System.out.println("Inicio de sesión exitoso. Bienvenido " + usuario.getNombre() + ".");
        return true;
    }




    public boolean actualizarUsuario(String correoActual, String nombre,
                                     String nuevoCorreo, String nuevaContrasena) {
        if (!usuarios.containsKey(correoActual)) {
            System.out.println("El usuario no existe.");
            return false;
        }

        Usuario usuario = usuarios.get(correoActual);


        if (nombre != null && !nombre.trim().isEmpty()) {
            usuario.setNombre(nombre);
        }
        if (nuevaContrasena != null && !nuevaContrasena.trim().isEmpty()) {
            usuario.setContrasena(nuevaContrasena);
        }


        if (nuevoCorreo != null && !nuevoCorreo.trim().isEmpty()) {
            usuario.setCorreo(nuevoCorreo);
            usuarios.put(nuevoCorreo, usuario);
            usuarios.remove(correoActual);
        }

        System.out.println("Usuario " + usuario.getNombre() + " actualizó sus datos correctamente.");
        return true;
    }




    public boolean recuperarContrasena(String correo) {
        if (!usuarios.containsKey(correo)) {
            System.out.println("El usuario no existe.");
            return false;
        }

        Usuario usuario = usuarios.get(correo);

        System.out.println("\n" + "=".repeat(60));
        System.out.println("RECUPERACIÓN DE CONTRASEÑA");
        System.out.println("=".repeat(60));
        System.out.println("Usuario encontrado: " + usuario.getNombre());
        System.out.println("=".repeat(60));

        System.out.println("Verifica tu identidad");
        System.out.print("Ingresa tu nombre de usuario: ");
        String verificar = scanner.nextLine().trim();

        if (!verificar.equalsIgnoreCase(usuario.getNombre())) {
            System.out.println("Acceso denegado");
            return false;
        }

        System.out.print("Ingresa la nueva contraseña -> ");
        String nuevaContrasena = scanner.nextLine().trim();

        System.out.print("Confirma tu contraseña -> ");
        String confirmacion = scanner.nextLine().trim();

        if (!nuevaContrasena.equals(confirmacion)) {
            System.out.println("La contraseña es distinta a la confirmación");
            return false;
        }

        System.out.println("Su contraseña se cambió con éxito.");
        usuario.setContrasena(nuevaContrasena);

        System.out.println("\n" + "=".repeat(60));
        System.out.println("CONTRASEÑA RESTABLECIDA EXITOSAMENTE");
        System.out.println("=".repeat(60));
        System.out.println("Ahora puedes iniciar sesión con tu nueva contraseña.");
        System.out.println("=".repeat(60));

        return true;
    }




    public boolean iniciarSesionAdmin(String correo, String contrasena) {
        if (!admins.containsKey(correo)) {
            System.out.println("El administrador no existe.");
            return false;
        }

        Usuario admin = admins.get(correo);

        if (!admin.getContrasena().equals(contrasena)) {
            System.out.println("Contraseña incorrecta.");
            return false;
        }

        System.out.println("Inicio de sesión exitoso. Bienvenido administrador " + admin.getNombre() + ".");
        return true;
    }



    public void listarUsuarios() {
        System.out.println("\n" + "=".repeat(70));
        System.out.printf("%-20s %-30s %-15s%n", "NOMBRE", "CORREO", "ROL");
        System.out.println("=".repeat(70));

        for (Usuario usuario : usuarios.values()) {
            System.out.printf("%-20s %-30s %-15s%n",
                    usuario.getNombre(),
                    usuario.getCorreo(),
                    usuario.getRol());
        }

        System.out.println("=".repeat(70) + "\n");
    }

    // ==================== GETTERS ====================

    /**
     * Obtiene el Map de usuarios
     */
    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * Obtiene el Map de administradores
     */
    public Map<String, Usuario> getAdmins() {
        return admins;
    }

    /**
     * Busca un usuario por correo (en usuarios o admins)
     * @param correo El correo a buscar
     * @return El usuario encontrado o null
     */
    public Usuario buscarUsuario(String correo) {
        if (usuarios.containsKey(correo)) {
            return usuarios.get(correo);
        } else if (admins.containsKey(correo)) {
            return admins.get(correo);
        }
        return null;
    }


    public void cerrarScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}