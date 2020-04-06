package com.example.chatfirebase.mainModule.view.adapters;

import com.example.chatfirebase.common.pojo.User;

public interface OnItemClickListener {

    void onItemClick(User user);
    void onItemLongClick(User user);

    void onAcceptRequest(User user );

    void onDenyRequest(User user);

}
