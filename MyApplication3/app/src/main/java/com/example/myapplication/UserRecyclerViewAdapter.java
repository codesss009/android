package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.UserViewHolder> {
    ArrayList<MainActivity.User> Users;

    public UserRecyclerViewAdapter(ArrayList<MainActivity.User> data){
        this.Users = data;
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        UserViewHolder userViewHolder = new UserViewHolder(view);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        MainActivity.User user = Users.get(position);
        holder.textViewAppName.setText(user.appName);
        holder.textViewArtistName.setText(user.name);
        holder.textViewReleaseDate.setText(user.date);

    }

    @Override
    public int getItemCount() {
        return this.Users.size();
    }


    public class UserViewHolder extends RecyclerView.ViewHolder{
        TextView textViewArtistName;
        TextView textViewReleaseDate;
        TextView textViewAppName;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAppName = itemView.findViewById(R.id.textViewAppName);
            textViewReleaseDate = itemView.findViewById(R.id.textViewReleaseDate);
            textViewArtistName = itemView.findViewById(R.id.textViewArtistName);
        }
    }
}
