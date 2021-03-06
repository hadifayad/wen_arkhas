package com.hadi.wenarkhas.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import com.google.gson.annotations.SerializedName;
import com.hadi.wenarkhas.Login;
import com.hadi.wenarkhas.MainActivity;
import com.hadi.wenarkhas.R;

public class User {
    static final String KEY_USERNAME = "username";
    static final String KEY_PASSWORD = "password";
    static final String KEY_FULLNAME = "fullname";

    @SerializedName("id")
    String id;
    @SerializedName("fullname")
    String fullname;
    @SerializedName("username")
    String username;
    @SerializedName("password")
    String password;
    @SerializedName("c_phone")
    String c_phone;
    @SerializedName("email")
    String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String c_text) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String r_user) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String image) {
        this.password = password;
    }

    public String getC_phone() {
        return c_phone;
    }

    public void setC_phone(String creation_date) {
        this.c_phone = c_phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String creation_date) {
        this.email = email;
    }

    public static boolean isLogged(Context context)
    {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String username = prefs.getString(KEY_USERNAME, "");

       if(username!=""&& username!=null)
       {
           return true;
       }
       else return false;
    }

    public static String getID(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String ID = prefs.getString("id", "");

        return ID;
    }

    public static String getFullname(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String fullname = prefs.getString("fullname", "");

        return fullname;
    }

    public static void clearPref(Context context){
        Button login;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putString(KEY_USERNAME,  "");
        ed.putString(KEY_PASSWORD,"");

        ed.putString(KEY_FULLNAME,"");
        ed.putString("id","");
        ed.putString("fullname","");
        ed.commit();
        MainActivity.login.setVisibility(View.VISIBLE);


    }


}
