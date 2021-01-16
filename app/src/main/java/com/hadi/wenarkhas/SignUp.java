package com.hadi.wenarkhas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    private TextInputEditText username,fullname,email,password,c_phone;
    private TextView signinText;
    private Button signupButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signupButton = findViewById(R.id.buttonSignUp);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        fullname = findViewById(R.id.fullname);
        c_phone = findViewById(R.id.c_phone);
        signinText = findViewById(R.id.loginText);

        signinText.setOnClickListener(new View.OnClickListener() {

                                          public void onClick(View v) {

                                              Intent intent = new Intent(getApplicationContext(), Login.class);
                                              startActivity(intent);
                                              finish();
                                          }
                                      }
        );



        signupButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                String usernamestring = username.getText().toString();

//                    Toast.makeText(SignUp.this,usernamestring ,
//                            Toast.LENGTH_LONG).show();

                String serverURL = "http://5.189.150.68/wen-arkhas-web/web/index.php?r=";
                String url = serverURL + "api/mobile/signup";

                final ProgressDialog dialog = ProgressDialog.show(SignUp.this, "",
                        "Please wait...", true);


                final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
//                                dialog.dismiss();
//                                finish();
                                Toast.makeText(SignUp.this, "Signup Successful",
                            Toast.LENGTH_LONG).show();
                                Log.d("upload", response);
                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                dialog.dismiss();
                                Toast.makeText(SignUp.this, "error", Toast.LENGTH_LONG).show();

                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> params = new HashMap<>();
                    //    Map<String, String> params = new Hashtable<>();
                        String usernamestring = username.getText().toString();
                        String emailstring = email.getText().toString();
                        String passwordstring = password.getText().toString();
                        String fullnamestring = fullname.getText().toString();
                        String cphone = c_phone.getText().toString();


                            params.put("username", usernamestring);
                            params.put("email", emailstring);
                            params.put("password", passwordstring);
                            params.put("fullname", fullnamestring);
                            params.put("c_phone", cphone);
                        Log.d("tag", params.toString());
                            return params;

                    }
                };
                {
                    int socketTimeout = 30000;
                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    stringRequest.setRetryPolicy(policy);
                    RequestQueue requestQueue = Volley.newRequestQueue(SignUp.this);

                    requestQueue.add(stringRequest);
                }


            }});

            }

    }




