package com.example.chatfirebase.mainModule;

import com.example.chatfirebase.common.pojo.User;
import com.example.chatfirebase.mainModule.events.Main2Event;

public interface Main2Presenter {

    void onCreate();
    void onDestroy();
    void onPause();
    void onResume();

    void signOff();
    User getCurrentUser();
    void removeFriend(String friendUid);

    void acceptRequest();

    void denyRequest();
    void onEventListener(Main2Event event);

}
