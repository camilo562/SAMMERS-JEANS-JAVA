package usuarios;


public class Usuario {
    private int idUsuario;
    private String nombre;
    private String correo;
    private String contrasena;
    private String rol;


    public Usuario(int idUsuario, String nombre, String correo, String contraseña) {
        this(idUsuario, nombre, correo, contraseña, "cliente");
    }


    public Usuario(int idUsuario, String nombre, String correo, String contrasena, String rol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
    }


    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getRol() {
        return rol;
    }



    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    // ==================== MÉTODOS ====================

    @Override
    public String toString() {
        return String.format("Usuario(%d, %s, %s)", idUsuario, nombre, correo);
    }

    /**
     * Verifica si el usuario es administrador
     */
    public boolean esAdmin() {
        return "admin".equalsIgnoreCase(rol);
    }

    /**
     * Verifica si el usuario es cliente
     */
    public boolean esCliente() {
        return "cliente".equalsIgnoreCase(rol);
    }
}