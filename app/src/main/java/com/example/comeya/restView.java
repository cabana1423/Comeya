package com.example.comeya;

public class restView {
    private String titleRest, direccion, telefono;
    private  int fotorest;

    public restView() {
    }

    public restView(String titleRest, String direccion, String telefono, int fotorest) {
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

    public int getFotorest() {
        return fotorest;
    }

    public void setFotorest(int fotorest) {
        this.fotorest = fotorest;
    }


}
