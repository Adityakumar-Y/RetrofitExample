package com.example.retrofitexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    Context context;
    List<User> data;

    public UserAdapter(Context context, List<User> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_layout, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = data.get(position);
        holder.tvName.setText(user.getLogin());
        Glide.with(holder.imgUser.getContext()).load(user.getAvatarUrl()).into(holder.imgUser);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        ImageView imgUser;
        TextView tvName;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            imgUser = (ImageView) itemView.findViewById(R.id.imgUser);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
        }
    }
}
