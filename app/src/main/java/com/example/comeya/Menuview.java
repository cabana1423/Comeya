package com.example.comeya;

public class Menuview {

    private String titleRest, precio, descripcion;
    private  int fotomenu;

    public Menuview(String titleRest, String precio, String descripcion, int fotomenu) {
        this.titleRest = titleRest;
        this.precio = precio;
        this.descripcion = descripcion;
        this.fotomenu = fotomenu;
    }

    public String getTitleRest() {
        return titleRest;
    }

    public void setTitleRest(String titleRest) {
        this.titleRest = titleRest;
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

    public int getFotomenu() {
        return fotomenu;
    }

    public void setFotomenu(int fotomenu) {
        this.fotomenu = fotomenu;
    }
}

