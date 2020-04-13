package com.example.chatfirebase.mainModule.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.chatfirebase.R;
import com.example.chatfirebase.common.pojo.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {



    private List<User> mUsers;
    private Context mContext;

    private OnItemClickListener mListener;

    public UserAdapter(List<User> mUsers, OnItemClickListener mListener) {
        this.mUsers = mUsers;
        this.mListener = mListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user, viewGroup, false);
        mContext = viewGroup.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        User user = mUsers.get(position);
        holder.setOnClickListener(user,mListener);

        holder.tvName.setText(user.getUsernameValid());

        int messageUnread = user.getMessagesUnread();

        if(messageUnread > 0 ){
            String countStr = messageUnread > 99? mContext.getString(R.string.main_item_max_messageUnread): String.valueOf(messageUnread);
            holder.tvCountUnread.setText(countStr);
            holder.tvCountUnread.setVisibility(View.VISIBLE);
        }else{
            holder.tvCountUnread.setVisibility(View.GONE);
        }

        RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .error(R.drawable.ic_emoticon_sad)
                .placeholder(R.drawable.ic_emoticon_tongue);
        Glide.with(mContext)
                .load(user.getPhotoURL())
                .apply(options)
                .into(holder.imgPhoto);


    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgPhoto)
        CircleImageView imgPhoto;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvCountUnread)
        TextView tvCountUnread;

        private View view;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            this.view = itemView;
        }

        private void setOnClickListener(final  User user, final OnItemClickListener listener ){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(user);
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemLongClick(user);
                    return true;
                }
            });
        }

    }
}
