package com.example.video_call_app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.video_call_app.R;
import com.example.video_call_app.listeners.UsersListener;
import com.example.video_call_app.models.User;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    private final List<User> users, selectedUsers;
    private final UsersListener usersListener;

    public UsersAdapter(List<User> users, UsersListener usersListener) {
        this.users = users;
        this.usersListener = usersListener;
        this.selectedUsers = new ArrayList<>();
    }

    public List<User> getSelectedUsers() {
        return selectedUsers;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_container_user, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.setUserData(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        TextView textFirstChar, textUsername, textEmail;
        ImageView imageAudioCall, imageVideoCall, imageSelected;
        ConstraintLayout userContainer;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textFirstChar = itemView.findViewById(R.id.textFirstChar);
            textUsername = itemView.findViewById(R.id.textUsername);
            textEmail = itemView.findViewById(R.id.textEmail);

            imageAudioCall = itemView.findViewById(R.id.imageAudioCall);
            imageVideoCall = itemView.findViewById(R.id.imageVideoCall);
            imageSelected = itemView.findViewById(R.id.imageSelected);

            userContainer = itemView.findViewById(R.id.userContainer);
        }

        void setUserData(User user) {
            textFirstChar.setText(user.firstName.substring(0, 1));
            textUsername.setText(String.format("%s %s", user.firstName, user.lastName));
            textEmail.setText(user.email);
            imageAudioCall.setOnClickListener(v -> usersListener.initiateAudioCall(user));
            imageVideoCall.setOnClickListener(v -> usersListener.initiateVideoCall(user));

            userContainer.setOnLongClickListener(v -> {
                if (imageSelected.getVisibility() == View.GONE) {
                    selectedUsers.add(user);
                    imageSelected.setVisibility(View.VISIBLE);
                    imageAudioCall.setVisibility(View.GONE);
                    imageVideoCall.setVisibility(View.GONE);
                    usersListener.onMultipleUsersAction(true);
                }
                return true;
            });

            userContainer.setOnClickListener(v -> {
                if (imageSelected.getVisibility() == View.VISIBLE) {
                    selectedUsers.remove(user);
                    imageSelected.setVisibility(View.GONE);
                    imageAudioCall.setVisibility(View.VISIBLE);
                    imageVideoCall.setVisibility(View.VISIBLE);
                    if (selectedUsers.size() == 0) {
                        usersListener.onMultipleUsersAction(false);
                    }
                } else {
                    if (selectedUsers.size() > 0) {
                        imageSelected.setVisibility(View.VISIBLE);
                        imageAudioCall.setVisibility(View.GONE);
                        imageVideoCall.setVisibility(View.GONE);
                    }
                }
            });
        }
    }
}
