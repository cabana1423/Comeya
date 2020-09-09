package com.example.comeya;

public class restView {
    private String titleRest, direccion, telefono, fotorest;

    public restView(String titleRest, String direccion, String telefono, String fotorest) {
        this.titleRest = titleRest;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fotorest = fotorest;
    }

    public String getTitleRest() {
        return titleRest;
    }

    public void setTitleRest(String titleRest) {
        this.titleRest = titleRest;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFotorest() {
        return fotorest;
    }

    public void setFotorest(String fotorest) {
        this.fotorest = fotorest;
    }
}
