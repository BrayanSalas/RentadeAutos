package markintoch.rentcar;

/**
 * Created by maste on 06/04/2018.
 */

public class Usuario {
    String nombre, correo, password, username;

    public Usuario() {
    }

    public Usuario(String nombre, String correo, String password, String username) {

        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.username = username;
    }

    public String getNombre() {

        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
