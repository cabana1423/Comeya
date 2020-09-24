package com.example.comeya;

public class Menuview {

    private String nombre_menu, precio, descripcion,fotomenu,id_rest,title_Rest,img_rest;

    public Menuview(String nombre_menu, String precio, String descripcion, String fotomenu, String id_rest, String title_Rest, String img_rest) {
        this.nombre_menu = nombre_menu;
        this.precio = precio;
        this.descripcion = descripcion;
        this.fotomenu = fotomenu;
        this.id_rest = id_rest;
        this.title_Rest = title_Rest;
        this.img_rest = img_rest;
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

    public String getId_rest() {
        return id_rest;
    }

    public void setId_rest(String id_rest) {
        this.id_rest = id_rest;
    }

    public String getTitle_Rest() {
        return title_Rest;
    }

    public void setTitle_Rest(String title_Rest) {
        this.title_Rest = title_Rest;
    }

    public String getImg_rest() {
        return img_rest;
    }

    public void setImg_rest(String img_rest) {
        this.img_rest = img_rest;
    }
}

