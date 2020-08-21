package com.example.comeya;

public class PedidoView {
    private String titleproducto, descripcion, precio;
    private  int fotoproducto;

    public PedidoView(String titleproducto, String descripcion, String precio, int fotoproducto) {
        this.titleproducto = titleproducto;
        this.descripcion = descripcion;
        this.precio = precio;
        this.fotoproducto = fotoproducto;
    }

    public String getTitleproducto() {
        return titleproducto;
    }

    public void setTitleproducto(String titleproducto) {
        this.titleproducto = titleproducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public int getFotoproducto() {
        return fotoproducto;
    }

    public void setFotoproducto(int fotoproducto) {
        this.fotoproducto = fotoproducto;
    }
}
