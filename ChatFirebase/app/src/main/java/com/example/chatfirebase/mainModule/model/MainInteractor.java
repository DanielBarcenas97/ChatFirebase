package com.example.chatfirebase.mainModule.model;

import com.example.chatfirebase.common.pojo.User;

public interface MainInteractor {

    void subscribeToUserList();
    void unsubscribeToUserList();

    void signOff();

    User getCurrentUser();

    void removeFriend(String friendUid);

    void acceptRequest(User user);
    void denyRequest(User user);
}
