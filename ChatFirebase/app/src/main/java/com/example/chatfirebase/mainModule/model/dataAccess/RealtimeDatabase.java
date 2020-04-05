package com.example.chatfirebase.mainModule.model.dataAccess;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.chatfirebase.R;
import com.example.chatfirebase.common.model.BasicEventsCallBack;
import com.example.chatfirebase.common.model.dataAccess.FirebaseRealtimeDatabaseAPI;
import com.example.chatfirebase.common.pojo.User;
import com.example.chatfirebase.common.utils.UtilsCommon;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RealtimeDatabase {

    private FirebaseRealtimeDatabaseAPI mDatabaseAPI;

    private ChildEventListener mUserEventListener;
    private ChildEventListener mRequestEventListener;


    public RealtimeDatabase(){
        mDatabaseAPI = FirebaseRealtimeDatabaseAPI.getInstance();
    }

    /*References*/

    public FirebaseRealtimeDatabaseAPI getmDatabaseAPI() {
        return mDatabaseAPI;
    }

    private DatabaseReference getUsersReference(){
        return mDatabaseAPI.getRootReference().child(FirebaseRealtimeDatabaseAPI.PATH_USERS);
    }

    /*Public Methods*/
    public void suscribeToUserList(String myUid, final userEventListener listener){

        if(mUserEventListener == null){
            mRequestEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    listener.onUserAdded(getUser(dataSnapshot));
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    listener.onUserUpdated(getUser(dataSnapshot));
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    listener.onUserRemoved(getUser(dataSnapshot));
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    switch ( databaseError.getCode()){
                        case DatabaseError.PERMISSION_DENIED:
                            listener.onError(R.string.main_error_permission_denied);
                            break;
                        default:
                            listener.onError(R.string.common_error_server);
                            break;
                    }

                }
            };
        }

        mDatabaseAPI.getContactReference(myUid).addChildEventListener(mUserEventListener);
    }

    private User getUser(DataSnapshot dataSnapshot) {
        User user = dataSnapshot.getValue(User.class);
        if (user != null){
            user.setUid(dataSnapshot.getKey());
        }
        return user;
    }

    public void subscribeToRequest(String email, final userEventListener listener){
        if (mRequestEventListener == null){
            mUserEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    listener.onUserAdded(getUser(dataSnapshot));

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    listener.onUserUpdated(getUser(dataSnapshot));

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    listener.onUserRemoved(getUser(dataSnapshot));

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    listener.onError(R.string.common_error_server);

                }
            };
        }
        final String emailEncoded = UtilsCommon.getEmailEncoded(email);
        mDatabaseAPI.getRequestReference(emailEncoded).addChildEventListener(mRequestEventListener);
    }


    public void onsubscribeToUsers(String uid){
        if (mUserEventListener != null){
            mDatabaseAPI.getContactReference(uid).removeEventListener(mUserEventListener);
        }
    }

    public void onsubscribeToRequest(String email){
        if (mRequestEventListener != null){
            final String emailEncoded = UtilsCommon.getEmailEncoded(email);
            mDatabaseAPI.getRequestReference(emailEncoded).removeEventListener(mRequestEventListener);
        }
    }

    public void removeUser(String friendUid, String myUid, final BasicEventsCallBack callback){

        Map<String, Object> removeUserMap = new HashMap<>();

        removeUserMap.put(myUid+"/"+FirebaseRealtimeDatabaseAPI.PATH_CONTACTS+"/"+friendUid,null);
        removeUserMap.put(friendUid+"/"+FirebaseRealtimeDatabaseAPI.PATH_CONTACTS+"/"+myUid,null);

        getUsersReference().updateChildren(removeUserMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError == null){
                    callback.onSuccess();
                }else
                    callback.onError();
            }
        });

    }


    public void acceptRequest(User user, User myUser, final BasicEventsCallBack callback){
        Map<String, Object> userRequestMap = new HashMap<>();
        userRequestMap.put(User.USERNAME, user.getEmail());
        userRequestMap.put(User.EMAIL, user.getEmail());
        userRequestMap.put(User.PHOTO_URL, user.getPhotoURL());

        Map<String, Object> myUserMap = new HashMap<>();
        myUserMap.put(User.USERNAME, myUser.getEmail());
        myUserMap.put(User.EMAIL, myUser.getEmail());
        myUserMap.put(User.PHOTO_URL, myUser.getPhotoURL());

        final String emailEncoded = UtilsCommon.getEmailEncoded(myUser.getEmail());

        Map<String, Object> acceptRequest = new HashMap<>();

        acceptRequest.put(FirebaseRealtimeDatabaseAPI.PATH_USERS+"/"+user.getUid()+"/"+
                FirebaseRealtimeDatabaseAPI.PATH_CONTACTS+"/"+myUser.getUid(),myUserMap);

        acceptRequest.put(FirebaseRealtimeDatabaseAPI.PATH_USERS+"/"+myUser.getUid()+"/"+
                FirebaseRealtimeDatabaseAPI.PATH_CONTACTS+"/"+user.getUid(),userRequestMap);

        acceptRequest.put(FirebaseRealtimeDatabaseAPI.PATH_USERS+"/"+emailEncoded+"/"+
                user.getUid(),null);

        mDatabaseAPI.getRootReference().updateChildren(acceptRequest, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError == null){
                    callback.onSuccess();
                }else{
                    callback.onError();
                }
            }
        });
    }

    public void denyRequest(User user, String myEmail, final BasicEventsCallBack callback){
        final String emailEncoded = UtilsCommon.getEmailEncoded(myEmail);
        mDatabaseAPI.getRequestReference(emailEncoded).child(user.getUid())
                .removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if (databaseError == null){
                            callback.onSuccess();
                        }else{
                            callback.onError();
                        }
                    }
                });
    }


}
