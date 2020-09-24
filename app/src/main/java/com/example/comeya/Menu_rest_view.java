package com.example.comeya;

public class Menu_rest_view {
    private String title_rest, img_rest;

    public Menu_rest_view(String title_rest, String img_rest) {
        this.title_rest = title_rest;
        this.img_rest = img_rest;
    }

    public String getTitle_rest() {
        return title_rest;
    }

    public void setTitle_rest(String title_rest) {
        this.title_rest = title_rest;
    }

    public String getImg_rest() {
        return img_rest;
    }

    public void setImg_rest(String img_rest) {
        this.img_rest = img_rest;
    }
}
