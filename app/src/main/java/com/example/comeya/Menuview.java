package com.example.comeya;

public class Menuview {

    private String nombre_menu, precio, descripcion,fotomenu;

    public Menuview(String nombre_menu, String precio, String descripcion, String fotomenu) {
        this.nombre_menu = nombre_menu;
        this.precio = precio;
        this.descripcion = descripcion;
        this.fotomenu = fotomenu;
    }

    public String getNombre_menu() {
        return nombre_menu;
    }

    public void setNombre_menu(String nombre_menu) {
        this.nombre_menu = nombre_menu;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFotomenu() {
        return fotomenu;
    }

    public void setFotomenu(String fotomenu) {
        this.fotomenu = fotomenu;
    }
}

