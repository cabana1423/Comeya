package com.example.comeya;

public class restView {
    private String titleRest, direccion, telefono, fotorest ,lat,lon, id_rest;

    public restView(String titleRest, String direccion, String telefono, String fotorest, String lat, String lon, String id_rest) {
        this.titleRest = titleRest;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fotorest = fotorest;
        this.lat = lat;
        this.lon = lon;
        this.id_rest = id_rest;
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

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getId_rest() {
        return id_rest;
    }

    public void setId_rest(String id_rest) {
        this.id_rest = id_rest;
    }
}
