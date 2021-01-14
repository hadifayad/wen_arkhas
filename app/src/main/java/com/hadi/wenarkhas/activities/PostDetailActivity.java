package com.hadi.wenarkhas.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hadi.wenarkhas.R;
import com.hadi.wenarkhas.utils.network.NetworkHelper;

public class PostDetailActivity extends AppCompatActivity {

    ImageView imageview;
    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        imageview = findViewById(R.id.image);
        textview = findViewById(R.id.text);

        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            String id = intent.getExtras().getString("id");
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


        }


    }
}