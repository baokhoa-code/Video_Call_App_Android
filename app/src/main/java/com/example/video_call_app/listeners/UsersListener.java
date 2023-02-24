package com.example.video_call_app.listeners;

import com.example.video_call_app.models.User;

public interface UsersListener {

    void initiateVideoCall(User user);

    void initiateAudioCall(User user);

    void onMultipleUsersAction(Boolean isMultipleUsersSelected);
}
