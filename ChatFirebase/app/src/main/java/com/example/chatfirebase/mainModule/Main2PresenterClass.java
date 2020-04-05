package com.example.chatfirebase.mainModule;

import com.example.chatfirebase.common.pojo.User;
import com.example.chatfirebase.mainModule.events.Main2Event;
import com.example.chatfirebase.mainModule.model.InteractorMain;
import com.example.chatfirebase.mainModule.model.MainInteractorClass;
import com.example.chatfirebase.mainModule.view.Main2View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class Main2PresenterClass implements Main2Presenter {
    private Main2View mView;
    private InteractorMain mInteractor;


    public Main2PresenterClass(Main2View mView) {
        this.mView = mView;
        this.mInteractor = new MainInteractorClass();
    }

    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        mView = null;
    }

    @Override
    public void onPause() {
        if (mView != null){
            mInteractor.unsubscribeToUserList();
        }
    }

    @Override
    public void onResume() {
        if (mView != null){
            mInteractor.subscribeToUserList();
        }
    }

    @Override
    public void signOff() {
        mInteractor.unsubscribeToUserList();
        mInteractor.signOff();
        onDestroy();

    }

    @Override
    public User getCurrentUser() {
        return mInteractor.getCurrentUser();
    }

    @Override
    public void removeFriend(String friendUid) {
        if (mView != null){
            mInteractor.removeFriend(friendUid);
        }
    }

    @Override
    public void acceptRequest(User user) {
        if (mView != null){
            mInteractor.acceptRequest(user);
        }
    }

    @Override
    public void denyRequest(User user) {
        if (mView != null){
            mInteractor.denyRequest(user);
        }

    }

    @Subscribe
    @Override
    public void onEventListener(Main2Event event) {
        if (mView != null){
            User user = event.getUser();

            switch (event.getTypeEvent()){
                case Main2Event.USER_ADDED:
                    mView.friendAdded(user);
                    break;
                case  Main2Event.USER_UPDATED:
                    mView.friendUpdated(user);
                    break;
                case Main2Event.USER_REMOVED:
                    if (user != null){
                        mView.friendRemoved(user);
                    }else{
                        mView.showFriendRemoved();
                    }
                    break;
                case Main2Event.REQUEST_ADDED:
                    mView.requestAdded(user);
                    break;
                case Main2Event.REQUEST_UPDATED:
                    mView.requestUpdated(user);
                    break;
                case Main2Event.REQUEST_REMOVED:
                    mView.requestRemove(user);
                    break;
                case Main2Event.REQUEST_ACCEPTED:
                    mView.showRequestAccepted(user.getUsername());
                    break;
                case Main2Event.REQUEST_DENIED:
                    mView.showRequestDenied();
                    break;
                case Main2Event.ERROR_SERVER:
                    mView.showError(event.getResMsg());
                    break;
            }
        }
    }
}
