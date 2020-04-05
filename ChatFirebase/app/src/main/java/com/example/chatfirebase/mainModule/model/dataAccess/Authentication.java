package com.example.chatfirebase.mainModule.model.dataAccess;

import com.example.chatfirebase.common.model.dataAccess.FirebaseAuthenticationAPI;

public class Authentication {

    private FirebaseAuthenticationAPI mAuthenticationAPI;

    public Authentication(){
        mAuthenticationAPI = FirebaseAuthenticationAPI.getInstance();
    }

    public FirebaseAuthenticationAPI getmAuthenticationAPI(){
        return mAuthenticationAPI;
    }

    public void signOff(){
        mAuthenticationAPI.getmFirebaseAuth().signOut();
    }

}
