package com.hadi.wenarkhas.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hadi.wenarkhas.R;
import com.hadi.wenarkhas.activities.PostDetailActivity;
import com.hadi.wenarkhas.models.Post;
import com.hadi.wenarkhas.utils.network.NetworkHelper;

import java.util.List;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.MyViewHolder> {

    List<Post> posts;
    Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView textViewTitle;
        public TextView textViewCreationDate;

        public MyViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.image);
            textViewTitle = view.findViewById(R.id.title);
            textViewCreationDate = view.findViewById(R.id.creation_date);
        }
    }

    public HomeRecyclerViewAdapter(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Post post = this.posts.get(position);

        if(post.getImage() != null && !post.getImage().equals("")) {
            final String imagePath = NetworkHelper.IMAGES_PATH + post.getImage();
            Glide.with(context)
                    .load(imagePath)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.image);
        }else{
            holder.image.setVisibility(View.GONE);
        }
        String text = post.getC_text();
        String shortText = text.substring(0, Math.min(text.length(), 20));
        String finalText = shortText.replace("\n", " ").replace("\r", " ");

        holder.textViewTitle.setText(finalText+"...");
        holder.textViewCreationDate.setText(post.getCreation_date());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PostDetailActivity.class);
                intent.putExtra("id", post.getId());
                intent.putExtra("imageName", post.getImage());
                intent.putExtra("text", post.getC_text());
                intent.putExtra("userId", post.getR_user());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


}
