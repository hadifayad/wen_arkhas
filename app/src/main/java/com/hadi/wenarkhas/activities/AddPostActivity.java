package com.hadi.wenarkhas.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
import com.hadi.wenarkhas.R;
import com.hadi.wenarkhas.utils.network.NetworkHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class AddPostActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int PICK_IMAGES = 1;

    RelativeLayout addImageLayout;
    TextInputEditText text;
    ImageView image1;
    Uri imageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        addImageLayout = findViewById(R.id.addImageLayout);
        text = findViewById(R.id.text);
        image1 = findViewById(R.id.image1);

        image1.setOnClickListener(this);
    }


    private void sendImage(final String image) {
        final String textString = text.getText().toString();
        if (!textString.equals("")) {
            final ProgressDialog dialog = ProgressDialog.show(AddPostActivity.this, "",
                    "Please wait...", true);

            String url = NetworkHelper.getUrl(NetworkHelper.ACTION_CREATE_POST);
            final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog.dismiss();
                            Log.d("respondCreatePost", response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dialog.dismiss();
//                        Log.d("respondCreatePost", error.getMessage());
                            Toast.makeText(AddPostActivity.this, "No internet connection", Toast.LENGTH_LONG).show();

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new Hashtable<String, String>();
                    params.put("text", textString);
                    if (image != null) {
                        params.put("image", image);
                    } else {
                        params.put("image", "");
                    }
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
        } else {
            Toast.makeText(this, getString(R.string.please_fill_all_fields), Toast.LENGTH_SHORT).show();
        }
    }

    public void addImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), PICK_IMAGES);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGES && resultCode == RESULT_OK && null != data) {

            imageUri = data.getData();
            updateImage();

        }
    }

    private void updateImage() {
        image1.setVisibility(View.GONE);
        addImageLayout.setVisibility(View.VISIBLE);

        if (imageUri != null) {
            image1.setVisibility(View.VISIBLE);
            addImageLayout.setVisibility(View.GONE);
            image1.setImageURI(null);
            image1.setImageURI(imageUri);
        }
    }


    private void imageViewDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.what_would_you_like_to_do));

        String[] options = {
                getString(R.string.remove_image),
                getString(R.string.cancel)
        };

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: // remove image
                        imageUri = null;
                        updateImage();
                        break;

                    case 4: // Cancel
                        break;
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void createAdNadUploadImages() {
        if (imageUri != null) {
            try {
                final InputStream imageStream = getContentResolver().
                        openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                //encoding image to string
                String image = getStringImage(selectedImage);
                if (!image.equals("")) {
                    sendImage(image);
                } else {
                    sendImage(null);

                }

            } catch (IOException e) {
                sendImage(null);
            }
        } else {
            sendImage(null);
        }
    }

    public String getStringImage(Bitmap bmp) {

        int currSize;
        int currQuality = 100;
        int maxSizeBytes = 800000;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, currQuality, baos);


        do {
            if (currQuality < 15) {
                return "";
            }
            baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, currQuality, baos);
            currSize = baos.toByteArray().length;
            currQuality = currQuality - 5;

        } while (currSize >= maxSizeBytes);

        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void onClick(View v) {
        if (v == image1) {
            imageViewDialog();
        }
    }

    public void savePost(View view) {
        createAdNadUploadImages();
    }
}