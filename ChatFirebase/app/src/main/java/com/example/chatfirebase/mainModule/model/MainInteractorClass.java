package com.example.chatfirebase.mainModule.model;

import com.example.chatfirebase.common.Constants;
import com.example.chatfirebase.common.model.BasicEventsCallBack;
import com.example.chatfirebase.common.pojo.User;
import com.example.chatfirebase.mainModule.events.Main2Event;
import com.example.chatfirebase.mainModule.model.dataAccess.Authentication;
import com.example.chatfirebase.mainModule.model.dataAccess.RealtimeDatabase;
import com.example.chatfirebase.mainModule.model.dataAccess.userEventListener;

import org.greenrobot.eventbus.EventBus;

public class MainInteractorClass implements InteractorMain {

    private RealtimeDatabase mDatabase;
    private Authentication mAuthentication;

    private User myUser = null;

    public MainInteractorClass(){
        mDatabase  = new RealtimeDatabase();
        mAuthentication = new Authentication();
    }

    @Override
    public void subscribeToUserList() {
        mDatabase.subscribeUserList(getCurrentUser().getUid(), new userEventListener() {
            @Override
            public void onUserAdded(User user) {
                post(Main2Event.USER_ADDED, user);
            }

            @Override
            public void onUserRemoved(User user) {
                post(Main2Event.USER_REMOVED,user);
            }

            @Override
            public void onUserUpdated(User user) {
                post(Main2Event.USER_UPDATED,user);
            }

            @Override
            public void onError(int resMsg) {
                postError(resMsg);
            }
        });

        mDatabase.subscribeToRequest(getCurrentUser().getEmail(), new userEventListener() {
            @Override
            public void onUserAdded(User user) {
                post(Main2Event.REQUEST_ACCEPTED,user);
            }

            @Override
            public void onUserRemoved(User user) {
                post(Main2Event.REQUEST_REMOVED,user);

            }

            @Override
            public void onUserUpdated(User user) {
                post(Main2Event.REQUEST_UPDATED,user);

            }

            @Override
            public void onError(int resMsg) {
                post(Main2Event.ERROR_SERVER);

            }
        });

        changeConnectionStatus(Constants.ONLINE);

    }

    private void changeConnectionStatus(boolean online) {
        mDatabase.getmDatabaseAPI().updateMyLastConnection(online,getCurrentUser().getUid());
    }


    @Override
    public void unsubscribeToUserList() {
        mDatabase.unsubscribeToUser(getCurrentUser().getUid());
        mDatabase.unsubscribeToRequest(getCurrentUser().getEmail());

        changeConnectionStatus(Constants.OFFLINE);
    }

    @Override
    public void signOff() {
        mAuthentication.signOff();
    }

    @Override
    public User getCurrentUser() {
        return myUser == null? mAuthentication.getmAuthenticationAPI().getAuthUser() : myUser;
    }

    @Override
    public void removeFriend(String friendUid) {
        mDatabase.removeUser(friendUid, getCurrentUser().getUid(), new BasicEventsCallBack() {
            @Override
            public void onSuccess() {
                post(Main2Event.USER_REMOVED);
            }

            @Override
            public void onError() {
                post(Main2Event.ERROR_SERVER);
            }
        });
    }

    @Override
    public void acceptRequest(User user) {
        mDatabase.acceptRequest(user, getCurrentUser(), new BasicEventsCallBack() {
            @Override
            public void onSuccess() {
                post(Main2Event.REQUEST_ACCEPTED,user);
            }

            @Override
            public void onError() {
                post(Main2Event.ERROR_SERVER);

            }
        });

    }

    @Override
    public void denyRequest(User user) {
        mDatabase.denyRequest(user, getCurrentUser().getEmail(), new BasicEventsCallBack() {
            @Override
            public void onSuccess() {
                post(Main2Event.REQUEST_DENIED);
            }

            @Override
            public void onError() {
                post(Main2Event.ERROR_SERVER);
            }
        });

    }

    private void post(int typeEvent, User user){
        post(typeEvent, user, 0);
    }


    private void post(int typeEvent, User user, int resMsg) {
        Main2Event event =  new Main2Event();
        event.setTypeEvent(typeEvent);
        event.setUser(user);
        event.setResMsg(resMsg);

        EventBus.getDefault().post(event);

    }

    private void postError(int resMsg) {
        post(Main2Event.ERROR_SERVER,null ,resMsg);
    }

    private void post(int typeEvent) {
        post(typeEvent, null , 0);
    }

}
