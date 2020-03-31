package com.example.chatfirebase.LoginModule.model.dataAccess;

import com.google.firebase.auth.FirebaseUser;

interface StatusAuthCallback {

    void onGetUser(FirebaseUser user);

    void onLaunchUILogin();

}
