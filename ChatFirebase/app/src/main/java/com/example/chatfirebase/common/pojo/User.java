package com.example.chatfirebase.common.pojo;

import android.net.Uri;

import com.google.firebase.database.Exclude;

public class User {

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
        return photoURL;
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
}
