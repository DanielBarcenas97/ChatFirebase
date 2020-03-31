package com.example.chatfirebase.LoginModule.model.dataAccess;

import androidx.annotation.NonNull;

import com.example.chatfirebase.common.model.dataAccess.FirebaseAuthenticationAPI;
import com.example.chatfirebase.common.pojo.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Authentication {
    private FirebaseAuthenticationAPI mAuthenticationAPI;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    public Authentication(){
        mAuthenticationAPI = FirebaseAuthenticationAPI.getInstance();
    }

    public void OnResume(){
        mAuthenticationAPI.getmFirebaseAuth().addAuthStateListener(mAuthStateListener);
    }

    public void OnPause(){
        if (mAuthStateListener != null){
            mAuthenticationAPI.getmFirebaseAuth().removeAuthStateListener(mAuthStateListener);
        }
    }

    public void getStatusAuth(StatusAuthCallback callback){
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null){
                    callback.onGetUser(user);
                }else{
                    callback.onLaunchUILogin();
                }
            }
        };
    }

    public User getCurrentUser(){

        return mAuthenticationAPI.getAuthUser();
    }

}
