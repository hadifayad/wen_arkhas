package com.hadi.wenarkhas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.hadi.wenarkhas.activities.AddPostActivity;
import com.hadi.wenarkhas.activities.AllPosts;
import com.hadi.wenarkhas.activities.PostDetailActivity;
import com.hadi.wenarkhas.models.User;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.hadi.wenarkhas.models.User.clearPref;
import static com.hadi.wenarkhas.models.User.isLogged;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public static Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);

        final boolean logged;
        login = (Button) header.findViewById(R.id.login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean logged = isLogged(MainActivity.this);
                if (logged) {
                    login.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(MainActivity.this, AddPostActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Please Login", Toast.LENGTH_LONG).show();
                }
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home
//                R.id.nav_gallery,
//                R.id.nav_slideshow
        )
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        NavigationUI.setupWithNavController(navigationView, navController);


        login.setOnClickListener(new View.OnClickListener() {

                                     public void onClick(View v) {

                                         Intent intent = new Intent(getApplicationContext(), Login.class);
                                         startActivity(intent);
                                         finish();
                                     }
                                 }
        );


//        Intent intent = new Intent(getApplicationContext(), PostDetailActivity.class);
//        startActivity(intent);



    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater findMenuItems = getMenuInflater();
        findMenuItems.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void getData() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = prefs.getString("KEY_USERNAME", "Default Value if not found");
        String password = prefs.getString("KEY_PASSWORD", "pass"); //return nothing if no pass saved

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                Toast.makeText(MainActivity.this, "Logout Successful",    Toast.LENGTH_LONG).show();
                clearPref(MainActivity.this);
                break;

        }
        return super.onOptionsItemSelected(item);
    }


}
