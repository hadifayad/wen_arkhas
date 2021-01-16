package com.hadi.wenarkhas.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.hadi.wenarkhas.Login;
import com.hadi.wenarkhas.R;



public class AllPosts extends AppCompatActivity {
    static final String KEY_USERNAME = "username";
    static final String KEY_PASSWORD = "password";
    static final String KEY_FULLNAME = "fullname";
    Context context;
    TextView user,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_posts);
        setTitle("All Posts");
        user = findViewById(R.id.user);
        pass = findViewById(R.id.pass);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(AllPosts.this);
        String username = prefs.getString(KEY_USERNAME, "Default Value if not found");
        String password = prefs.getString(KEY_PASSWORD, "pass"); //return nothing if no pass saved
        user.setText(username);
        pass.setText(password);

    }
}
