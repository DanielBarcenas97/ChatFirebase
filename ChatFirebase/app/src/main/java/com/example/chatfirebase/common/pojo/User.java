package com.example.chatfirebase.common.pojo;

import android.net.Uri;

import com.google.firebase.database.Exclude;

public class User {

    public static final String USERNAME = "username";
    public static final String PHOTO_URL = "photoUrl";
    public static final String EMAIL = "email";
    public static final String LAST_CONNECTION_UNREAD = "lasConnectionWith";
    public static final String MESSAGE_UNREAD = "messagesUnread";
    public static final String UID = "uid";

    private String lastConnectionWith;
    private String username;
    private String email;
    private String photoURL;
    private String messagesUnread;

    @Exclude
    private String uid;

    @Exclude
    private Uri uri;

    public User() {
    }

    public void setLastConnectionWith(String lastConnectionWith) {
        this.lastConnectionWith = lastConnectionWith;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public void setMessagesUnread(String messagesUnread) {
        this.messagesUnread = messagesUnread;
    }

    @Exclude
    public void setUid(String uid) {
        this.uid = uid;
    }

    @Exclude
    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getLastConnectionWith() {
        return lastConnectionWith;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhotoURL() {
        return photoURL != null ? photoURL : uri != null?  uri.toString() : "";
    }

    public String getMessagesUnread() {
        return messagesUnread;
    }

    @Exclude
    public String getUid() {
        return uid;
    }
    @Exclude
    public Uri getUri() {
        return uri;
    }


    @Exclude
    public  String getUsernameValid(){
       return username == null?
       getEmail() : username.isEmpty()?
       getEmail() : username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return uid != null ? uid.equals(user.uid) : user.uid == null;
    }

    @Override
    public int hashCode() {
        return uid != null ? uid.hashCode() : 0;
    }
}
