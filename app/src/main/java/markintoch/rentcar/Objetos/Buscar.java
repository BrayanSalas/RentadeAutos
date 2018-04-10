package markintoch.rentcar.Objetos;

public class Buscar {
    public String fechaInicio;
    public String fechaDevolucion;
    public String horaInicio;
    public String horaFin;
    public String establecimiento;
    public String marca;
    public String precio;
    public String poster;

    public Buscar() {
    }

    public Buscar(String fechaInicio, String fechaDevolucion, String horaInicio, String horaFin, String establecimiento, String marca, String precio, String poster) {
        this.fechaInicio = fechaInicio;
        this.fechaDevolucion = fechaDevolucion;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.establecimiento = establecimiento;
        this.marca = marca;
        this.precio = precio;
        this.poster = poster;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(String fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(String establecimiento) {
        this.establecimiento = establecimiento;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
