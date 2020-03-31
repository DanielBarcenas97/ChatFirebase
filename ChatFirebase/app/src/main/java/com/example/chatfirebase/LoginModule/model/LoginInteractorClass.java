package com.example.chatfirebase.LoginModule.model;

import com.example.chatfirebase.LoginModule.events.LoginEvent;
import com.example.chatfirebase.LoginModule.model.dataAccess.Authentication;
import com.example.chatfirebase.LoginModule.model.dataAccess.RealtimeDatabase;
import com.example.chatfirebase.LoginModule.model.dataAccess.StatusAuthCallback;
import com.example.chatfirebase.common.model.EventErrorTypeListener;
import com.example.chatfirebase.common.pojo.User;
import com.google.firebase.auth.FirebaseUser;

import org.greenrobot.eventbus.EventBus;

public class LoginInteractorClass implements LoginInteractor {


    private Authentication mAuthentication;
    private RealtimeDatabase mDatabase;

    public LoginInteractorClass() {
        mAuthentication = new Authentication();
        mDatabase = new RealtimeDatabase();
    }

    @Override
    public void OnResume() {
        mAuthentication.OnResume();

    }

    @Override
    public void OnPause() {
        mAuthentication.OnPause();
    }

    @Override
    public void getStatusAuth() {
        mAuthentication.getStatusAuth(new StatusAuthCallback() {
            @Override
            public void onGetUser(FirebaseUser user) {
                post(LoginEvent.STATUS_AUTH_SUCCESS, user);

                mDatabase.checkUserExist(mAuthentication.getCurrentUser().getUid(), new EventErrorTypeListener() {
                    @Override
                    public void onError(int typeEvent, int resMsg) {
                        if(typeEvent == LoginEvent.USER_NOT_EXIST){
                            registerUser();
                        }else{
                            post(typeEvent);
                        }
                    }
                });
            }

            @Override
            public void onLaunchUILogin() {
                post(LoginEvent.STATUS_AUTH_ERROR);
            }
        });
    }

    private void registerUser() {
        User currentUser = mAuthentication.getCurrentUser();
        mDatabase.registerUser(currentUser);
    }

    private void post(int typeEvent) {
        post(typeEvent, null);
    }

    private void post(int typeEvent, FirebaseUser user) {
        LoginEvent event = new LoginEvent();
        event.setTypeEvent(typeEvent);
        event.setUser(user);

        EventBus.getDefault().post(event);
    }
}
