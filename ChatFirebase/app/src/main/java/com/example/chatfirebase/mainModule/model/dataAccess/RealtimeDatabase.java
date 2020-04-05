package com.example.chatfirebase.mainModule.model.dataAccess;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.chatfirebase.R;
import com.example.chatfirebase.common.model.dataAccess.FirebaseRealtimeDatabaseAPI;
import com.example.chatfirebase.common.pojo.User;
import com.example.chatfirebase.common.utils.UtilsCommon;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

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

    


}
