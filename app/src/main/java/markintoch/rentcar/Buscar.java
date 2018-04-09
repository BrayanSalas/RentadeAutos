package markintoch.rentcar;

public class Buscar {
    public String fechaInicio;
    public String fechaDevolucion;
    public String horaInicio;
    public String horaFin;
    public String establecimiento;

    public Buscar() {
    }

    public Buscar(String fechaInicio, String fechaDevolucion, String horaInicio, String horaFin, String establecimiento) {
        this.fechaInicio = fechaInicio;
        this.fechaDevolucion = fechaDevolucion;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.establecimiento = establecimiento;
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
}
