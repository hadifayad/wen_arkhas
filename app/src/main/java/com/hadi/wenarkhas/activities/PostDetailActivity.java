package com.hadi.wenarkhas.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hadi.wenarkhas.Login;
import com.hadi.wenarkhas.MainActivity;
import com.hadi.wenarkhas.R;
import com.hadi.wenarkhas.adapters.CommentsRecyclerViewAdapter;
import com.hadi.wenarkhas.models.Comment;
import com.hadi.wenarkhas.models.User;
import com.hadi.wenarkhas.utils.network.GsonRequest;
import com.hadi.wenarkhas.utils.network.NetworkHelper;
import com.hadi.wenarkhas.utils.network.VolleySingleton;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static com.hadi.wenarkhas.models.User.isLogged;

public class PostDetailActivity extends AppCompatActivity {

    ImageView imageview;
    TextView textview;
    RecyclerView comments_recyclerview;
    EditText textInput;
    Button btnSend;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        imageview = findViewById(R.id.image);
        textview = findViewById(R.id.text);
        textInput = findViewById(R.id.textInput);
        btnSend = findViewById(R.id.btnSend);
        comments_recyclerview = findViewById(R.id.comments_recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        comments_recyclerview.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            id = intent.getExtras().getString("id");
            String imageName = intent.getExtras().getString("imageName");
            String text = intent.getExtras().getString("text");
            String userId = intent.getExtras().getString("userId");

            if (imageName != null && !imageName.equals("")) {
                final String imagePath = NetworkHelper.IMAGES_PATH + imageName;
                Glide.with(getApplicationContext())
                        .load(imagePath)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageview);
            } else {
                imageview.setVisibility(View.GONE);
            }

            textview.setText(text);

            setupComments();

        }


    }

    public void sendComment(View view) {


        final String text = textInput.getText().toString();
        if (!text.equals("")) {
            boolean logged = isLogged(PostDetailActivity.this);
            if (logged) {




            btnSend.setEnabled(false);
            String url = NetworkHelper.getUrl(NetworkHelper.ACTION_ADD_COMMENT);
            final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("true")) {
                                textInput.setText("");
                                setupComments();
                            } else {
                                Toast.makeText(PostDetailActivity.this, "حدث خطأ", Toast.LENGTH_LONG).show();
                            }
                            btnSend.setEnabled(true);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(PostDetailActivity.this, "حدث خطأ", Toast.LENGTH_LONG).show();
                            btnSend.setEnabled(true);

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new Hashtable<String, String>();
                    params.put("text", text);
                    params.put("postId", id);
                    params.put("userId", User.getID(getApplicationContext()));
                    return params;
                }
            };
            {
                int socketTimeout = 30000;
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(policy);
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
            }
        }
               else {    Toast.makeText(PostDetailActivity.this, "Please Login",    Toast.LENGTH_LONG).show();
                Intent intent = new Intent(PostDetailActivity.this, Login.class);
                startActivity(intent);
               }
        }
    }

    private void setupComments() {
        String url = NetworkHelper.getUrl(NetworkHelper.ACTION_GET_COMMENTS_BY_POST);
        Map<String, String> params = new HashMap();
        params.put("postId", id);
        GsonRequest<Comment[]> myGsonRequest = new GsonRequest<Comment[]>(Request.Method.POST, url, Comment[].class, null, params,
                new Response.Listener<Comment[]>() {
                    @Override
                    public void onResponse(Comment[] response) {
                        comments_recyclerview.setAdapter(new CommentsRecyclerViewAdapter(Arrays.asList(response)));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        NetworkHelper.handleError(error);
                    }
                });

        VolleySingleton.getInstance(PostDetailActivity.this).addToRequestQueue(myGsonRequest);
    }
}