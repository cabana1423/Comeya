package com.example.comeya;

public class confirmarpedidoView {
    private String titulo, cantidad, total;

    public confirmarpedidoView(String titulo, String cantidad, String total) {
        this.titulo = titulo;
        this.cantidad = cantidad;
        this.total = total;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
