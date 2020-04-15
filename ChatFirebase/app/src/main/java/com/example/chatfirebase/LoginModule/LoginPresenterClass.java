package com.example.chatfirebase.LoginModule;

import android.app.Activity;
import android.content.Intent;

import com.example.chatfirebase.LoginModule.events.LoginEvent;
import com.example.chatfirebase.LoginModule.model.LoginInteractor;
import com.example.chatfirebase.LoginModule.model.LoginInteractorClass;
import com.example.chatfirebase.LoginModule.view.LoginView;
import com.example.chatfirebase.LoginModule.view.LoginActivity;
import com.example.chatfirebase.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class LoginPresenterClass implements LoginPresenter {

    private LoginView mView;
    private LoginInteractor mInteractor;

    public LoginPresenterClass(LoginView mView) {
        this.mView = mView;
        this.mInteractor = new LoginInteractorClass();
    }



    @Override
    public void OnCreate() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void OnResume() {
        if(setProgress()){
            mInteractor.OnResume();
        }

    }

    private boolean setProgress() {
        if(mView != null){
            mView.showProgress();
            return  true;
        }
        return  false;
    }

    @Override
    public void OnPause() {
        if(setProgress()){
            mInteractor.OnPause();
        }
    }

    @Override
    public void OnDestroy() {
        mView = null;
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void result(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case LoginActivity.RC_SIGN_IN:
                    if(data != null){
                        mView.showLoginSucessfully(data);
                    }
                    break;
            }
        }else{
            mView.showError(R.string.login_message_error);
        }
    }

    @Override
    public void getStatusAuth() {
        if(setProgress()){
            mInteractor.getStatusAuth();
        }

    }

    @Subscribe
    @Override
    public void onEventListener(LoginEvent event) {
        if(mView != null){
            mView.hideProgress();

            switch (event.getTypeEvent()){
                case LoginEvent.STATUS_AUTH_SUCCESS:
                    if (setProgress()){
                        mView.showMessageStarting();
                        mView.openMainActivity();
                    }
                    break;
                case  LoginEvent.STATUS_AUTH_ERROR:
                    mView.openUILogin();
                    break;
                case LoginEvent.ERROR_SERVER:
                    mView.showError(event.getResMsg());
                    break;
            }
        }
    }
}
