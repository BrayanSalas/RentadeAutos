package markintoch.rentcar.Objetos;

import android.net.Uri;

public class Carro {
    String marca;
    String poster;
    String puertas, color, modelo, ac, transmision, capacidad, descripcionAdicional, precio;

    public Carro() {
    }

    public Carro(String marca, String poster, String puertas, String color, String modelo, String ac, String transmision, String capacidad, String descripcionAdicional, String precio) {
        this.marca = marca;
        this.poster = poster;
        this.puertas = puertas;
        this.color = color;
        this.modelo = modelo;
        this.ac = ac;
        this.transmision = transmision;
        this.capacidad = capacidad;
        this.descripcionAdicional = descripcionAdicional;
        this.precio = precio;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPuertas() {
        return puertas;
    }

    public void setPuertas(String puertas) {
        this.puertas = puertas;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAc() {
        return ac;
    }

    public void setAc(String ac) {
        this.ac = ac;
    }

    public String getTransmision() {
        return transmision;
    }

    public void setTransmision(String transmision) {
        this.transmision = transmision;
    }

    public String getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(String capacidad) {
        this.capacidad = capacidad;
    }

    public String getDescripcionAdicional() {
        return descripcionAdicional;
    }

    public void setDescripcionAdicional(String descripcionAdicional) {
        this.descripcionAdicional = descripcionAdicional;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

}
