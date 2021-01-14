package com.hadi.wenarkhas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hadi.wenarkhas.R;
import com.hadi.wenarkhas.models.Comment;

import java.util.List;


public class CommentsRecyclerViewAdapter extends RecyclerView.Adapter<CommentsRecyclerViewAdapter.MyViewHolder> {

    List<Comment> comments;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewOutGoing, client_name, textviewIngoing,client_nameInGOING;

        public MyViewHolder(View view) {
            super(view);
            textviewIngoing = view.findViewById(R.id.textview);
            client_name = view.findViewById(R.id.client_name);
            textViewOutGoing = view.findViewById(R.id.textViewOutGoing);
            client_nameInGOING = view.findViewById(R.id.client_nameInGOING);
        }
    }


    public CommentsRecyclerViewAdapter(List<Comment> comments) {
        this.comments = comments;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comments_item_list, parent, false);
        context = parent.getContext();
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Comment comment = comments.get(position);
        holder.textviewIngoing.setVisibility(View.GONE);
        holder.textViewOutGoing.setVisibility(View.GONE);
        holder.client_name.setVisibility(View.GONE);
        holder.client_nameInGOING.setVisibility(View.GONE);

        holder.textviewIngoing.setText(comment.getC_text());
        holder.textViewOutGoing.setText(comment.getC_text());

        holder.client_name.setText(comment.getFullname());
        holder.client_nameInGOING.setText(comment.getFullname());

//        if (comment.getUsername().equals(UserPreferences.getUsername(context))) {
//            holder.textViewOutGoing.setVisibility(View.VISIBLE);
//            holder.client_name.setVisibility(View.VISIBLE);
//        } else {
            holder.textviewIngoing.setVisibility(View.VISIBLE);
            holder.client_nameInGOING.setVisibility(View.VISIBLE);
//        }


    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

}
