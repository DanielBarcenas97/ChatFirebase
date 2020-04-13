package com.example.chatfirebase.mainModule.view;

import android.app.NotificationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatfirebase.R;
import com.example.chatfirebase.common.pojo.User;
import com.example.chatfirebase.common.utils.UtilsCommon;
import com.example.chatfirebase.mainModule.Main2Presenter;
import com.example.chatfirebase.mainModule.Main2PresenterClass;
import com.example.chatfirebase.mainModule.view.adapters.OnItemClickListener;
import com.example.chatfirebase.mainModule.view.adapters.RequestAdapter;
import com.example.chatfirebase.mainModule.view.adapters.UserAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity2 extends AppCompatActivity implements OnItemClickListener, Main2View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imgProfile)
    CircleImageView imgProfile;
    @BindView(R.id.rvRequests)
    RecyclerView rvRequests;
    @BindView(R.id.rvUsers)
    RecyclerView rvUsers;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.contentMain)
    CoordinatorLayout contentMain;

    private UserAdapter mUserAdapter;
    private RequestAdapter mRequestAdapter;

    private Main2Presenter mPresenter;

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        mPresenter = new Main2PresenterClass(this);
        mPresenter.onCreate();
        mUser = mPresenter.getCurrentUser();

        configToolbar();
        configAdapter();
        configRecyclerView();
    }



    private void configAdapter() {
        mUserAdapter = new UserAdapter(new ArrayList<User>(),this);
        mRequestAdapter = new RequestAdapter(new ArrayList<User>(),this);
        
    }

    private void configToolbar() {
        toolbar.setTitle(mUser.getUsernameValid());
        UtilsCommon.loadImage(this,mUser.getPhotoURL(),imgProfile);
        setSupportActionBar(toolbar);
    }

    private void configRecyclerView() {
        rvUsers.setLayoutManager(new LinearLayoutManager(this));
        rvUsers.setAdapter(mUserAdapter);

        rvRequests.setLayoutManager(new LinearLayoutManager(this));
        rvRequests.setAdapter(mRequestAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_logout:
                break;

            case R.id.action_profile:
                break;

            case R.id.action_about:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mPresenter.onResume();
        clearNotifications();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    private void clearNotifications(){

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if(notificationManager != null){
            notificationManager.cancelAll();
        }
    }

    /*
     * Main View
     * */

    @Override
    public void friendAdded(User user) {
        mUserAdapter.add(user);
    }

    @Override
    public void friendUpdated(User user) {
        mUserAdapter.update(user);
    }

    @Override
    public void friendRemoved(User user) {
        mUserAdapter.remove(user);
    }

    @Override
    public void requestAdded(User user) {
        mRequestAdapter.add(user);
    }

    @Override
    public void requestUpdated(User user) {
        mRequestAdapter.update(user);
    }

    @Override
    public void requestRemove(User user) {
        mRequestAdapter.remove(user);
    }

    @Override
    public void showRequestAccepted(String username) {
        Snackbar.make(contentMain,getString(R.string.main_message_request_accepted, username),Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showRequestDenied() {
        Snackbar.make(contentMain,getString(R.string.main_message_request_denied),Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showFriendRemoved() {
        Snackbar.make(contentMain,getString(R.string.main_message_user_removed),Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void showError(int resMsg) {
        Snackbar.make(contentMain,resMsg,Snackbar.LENGTH_SHORT).show();
    }


    /*On ItemClick Listener*/
    @Override
    public void onItemClick(User user) {

    }

    @Override
    public void onItemLongClick(User user) {
        mPresenter.removeFriend(user.getUid());
    }

    @Override
    public void onAcceptRequest(User user) {
        mPresenter.acceptRequest(user);
    }

    @Override
    public void onDenyRequest(User user) {
        mPresenter.denyRequest(user);
    }
}
