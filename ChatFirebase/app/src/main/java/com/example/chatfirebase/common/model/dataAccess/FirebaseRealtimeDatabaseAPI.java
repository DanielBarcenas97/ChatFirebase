package com.example.chatfirebase.common.model.dataAccess;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseRealtimeDatabaseAPI {

    public static final String SEPARATOR = "___&___";

    public static final String PATH_USERS = "users";

    public static final String PATH_CONTACTS = "contacts";

    public static final String PATH_REQUEST = "requests";

    private DatabaseReference mDatabaseReference;

    private static class SinglentonHolder{
       private static final FirebaseRealtimeDatabaseAPI INSTANCE = new FirebaseRealtimeDatabaseAPI();
    }

    public static FirebaseRealtimeDatabaseAPI getInstance() {
        return SinglentonHolder.INSTANCE;
    }

    private FirebaseRealtimeDatabaseAPI(){

        this.mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }


    /*
    References
     */


    public DatabaseReference getRootReference(){
        return mDatabaseReference.getRoot();
    }

    public  DatabaseReference getUserReferenceByUid(String uid){
        return getRootReference().child(PATH_USERS).child(uid);

    }

}
