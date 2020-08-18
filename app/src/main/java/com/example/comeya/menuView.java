package com.example.comeya;

public class menuView {
    private String restaurant, precio, descripcion;
    private  int fotomenu;

    public menuView() {
    }

    public menuView(String restaurant, String precio, String descripcion, int fotomenu) {
        this.restaurant = restaurant;
        this.precio = precio;
        this.descripcion = descripcion;
        this.fotomenu = fotomenu;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
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
