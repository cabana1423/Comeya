package com.example.comeya.notificaciones;

public class Noti_solicitarpedido_View {
    String img,text, id_fac;

    public Noti_solicitarpedido_View(String img, String text, String id_fac) {
        this.img = img;
        this.text = text;
        this.id_fac = id_fac;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId_fac() {
        return id_fac;
    }

    public void setId_fac(String id_fac) {
        this.id_fac = id_fac;
    }
}
