package com.example.comeya;

public class PedidoView {
    private String titleproducto, descripcion, precio,fotoproducto;;

    public PedidoView(String titleproducto, String descripcion, String precio, String fotoproducto) {
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

    public String getFotoproducto() {
        return fotoproducto;
    }

    public void setFotoproducto(String fotoproducto) {
        this.fotoproducto = fotoproducto;
    }
}
