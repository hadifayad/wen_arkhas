package com.hadi.wenarkhas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;



        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.appcompat.app.AppCompatActivity;

        import com.android.volley.AuthFailureError;
        import com.android.volley.DefaultRetryPolicy;
        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.RetryPolicy;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;
        import com.google.android.material.textfield.TextInputEditText;
import com.hadi.wenarkhas.activities.AddPostActivity;
import com.hadi.wenarkhas.activities.AllPosts;

import java.util.HashMap;
        import java.util.Hashtable;
        import java.util.Map;

public class Login extends  AppCompatActivity {

    static final String KEY_USERNAME = "username";
    static final String KEY_PASSWORD = "password";
    private TextInputEditText username,password;
    private TextView signUpText;
    private Button signinButton , signupButton;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signinButton = findViewById(R.id.buttonLogin);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signUpText = findViewById(R.id.signUp);




        signUpText.setOnClickListener(new View.OnClickListener() {

                                            public void onClick(View v) {

                                                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
        );

        signinButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                String usernamestring = username.getText().toString();

//                    Toast.makeText(SignUp.this,usernamestring ,
//                            Toast.LENGTH_LONG).show();

                String serverURL = "http://5.189.150.68/wen-arkhas-web/web/index.php?r=";
                String url = serverURL + "api/mobile/login";

                final ProgressDialog dialog = ProgressDialog.show(Login.this, "",
                        "Please wait...", true);


                final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                dialog.dismiss();
                                finish();
//                                Toast.makeText(Login.this, response,
//                                        Toast.LENGTH_LONG).show();
                                if(response.equalsIgnoreCase("false")){
                                    Toast.makeText(Login.this, "wrong username or password",
                                            Toast.LENGTH_LONG).show();
                                    username.setText("");
                                    password.setText("");
//                                    Intent intent = new Intent(getApplicationContext(), Login.class);
//                                    startActivity(intent);




                                }
                                else
                                {
                                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Login.this);
                                    SharedPreferences.Editor ed = prefs.edit();
                                    ed.putString(KEY_USERNAME,  username.getText().toString());
                                    ed.putString(KEY_PASSWORD, password.getText().toString());
                                    ed.commit();

                                    Intent intent = new Intent(getApplicationContext(), AllPosts.class);
                                    startActivity(intent);
                                    finish();
                                }
                                Log.d("upload", response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                dialog.dismiss();
                                Toast.makeText(Login.this, "error", Toast.LENGTH_LONG).show();

                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> params = new HashMap<>();
                        //    Map<String, String> params = new Hashtable<>();
                        String usernamestring = username.getText().toString();

                        String passwordstring = password.getText().toString();



                        params.put("username", usernamestring);

                        params.put("password", passwordstring);

                        Log.d("tag", params.toString());
                        return params;

                    }
                };
                {
                    int socketTimeout = 30000;
                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    stringRequest.setRetryPolicy(policy);
                    RequestQueue requestQueue = Volley.newRequestQueue(Login.this);

                    requestQueue.add(stringRequest);
                }


            }});

    }

}





