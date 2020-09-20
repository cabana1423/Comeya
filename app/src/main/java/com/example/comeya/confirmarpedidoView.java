package com.example.comeya;

public class confirmarpedidoView {
    private String titulo, cantidad, total,id_order;

    public confirmarpedidoView(String titulo, String cantidad, String total, String id_order) {
        this.titulo = titulo;
        this.cantidad = cantidad;
        this.total = total;
        this.id_order = id_order;
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

    public String getId_order() {
        return id_order;
    }

    public void setId_order(String id_order) {
        this.id_order = id_order;
    }
}
