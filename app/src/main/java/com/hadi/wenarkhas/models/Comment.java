package com.hadi.wenarkhas.models;

import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("id")
    String id;
    @SerializedName("c_text")
    String c_text;
    @SerializedName("r_post")
    String r_post;
    @SerializedName("r_user")
    String r_user;
    @SerializedName("creation_date")
    String creation_date;
    @SerializedName("fullname")
    String fullname;

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

    public String getR_post() {
        return r_post;
    }

    public void setR_post(String r_post) {
        this.r_post = r_post;
    }

    public String getR_user() {
        return r_user;
    }

    public void setR_user(String r_user) {
        this.r_user = r_user;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
