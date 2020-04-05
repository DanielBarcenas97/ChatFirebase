package com.example.chatfirebase.mainModule.view;

import com.example.chatfirebase.common.pojo.User;

public interface Main2View {
    void friendAdded(User user);
    void friendUpdated(User user);
    void friendRemove(User user);

    void requestAdded(User user);
    void requestUpdated(User user);
    void requestRemove(User user);


    void showRequestAccepted(String username);
    void showRequestDenied();

    void showFriendRemoved(User user);
    void showError(int resMsg);
}
