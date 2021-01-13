package com.hadi.wenarkhas.models;

import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("id")
    String id;
    @SerializedName("c_text")
    String c_text;
    @SerializedName("r_user")
    String r_user;
    @SerializedName("image")
    String image;
    @SerializedName("creation_date")
    String creation_date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getC_text() {
        return c_text;
    }

    public void setC_text(String c_text) {
        this.c_text = c_text;
    }

    public String getR_user() {
        return r_user;
    }

    public void setR_user(String r_user) {
        this.r_user = r_user;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }
}
