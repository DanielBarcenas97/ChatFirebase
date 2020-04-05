package com.example.chatfirebase.mainModule.model.dataAccess;

import com.example.chatfirebase.common.pojo.User;

public interface userEventListener {

    void onUserAdded(User user);
    void onUserRemoved(User user);
    void onUserUpdated(User user);

    void onError(int resMsg);

}
