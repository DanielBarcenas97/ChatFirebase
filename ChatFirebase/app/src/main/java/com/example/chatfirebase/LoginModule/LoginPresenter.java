package com.example.chatfirebase.LoginModule;

import android.content.Intent;

import com.example.chatfirebase.LoginModule.events.LoginEvent;

public interface LoginPresenter {

    void OnCreate();
    void OnResume();
    void OnPause();
    void OnDestroy();


    void result(int requestCode, int resultCode, Intent data);

    void getStatusAuth();

    void onEventListener(LoginEvent event);


}
