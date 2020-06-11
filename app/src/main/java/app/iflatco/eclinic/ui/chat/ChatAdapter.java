package app.iflatco.eclinic.ui.chat;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import app.iflatco.eclinic.R;
import app.iflatco.eclinic.models.ChatModel;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private static final String TAG = "ChatAdapter";

    private List<ChatModel> modelList = new ArrayList<>();
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private boolean right = false;
    private int lastPosition = -1;
    private Context context;

    public ChatAdapter(Context context, List<ChatModel> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @Override
    public int getItemViewType(int position) {
        ChatModel message = modelList.get(position);

        if (message.getRoll().equals("user")) {
            right = true;
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            right = false;
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_message_right, parent, false);

        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_message_left, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        ChatModel message = modelList.get(position);

        if (position != 0) {
            if (modelList.get(position).getUserName().equals(modelList.get(position - 1).getUserName())) {
                holder.userName.setVisibility(View.GONE);

            } else {
                holder.userName.setVisibility(View.VISIBLE);
                holder.userName.setText(message.getUserName());
            }
        } else {
            holder.userName.setVisibility(View.VISIBLE);
            holder.userName.setText(message.getUserName());
        }
        if (message.getImage().equals("")) {
            holder.imageLayout.setVisibility(View.GONE);
            holder.message.setVisibility(View.VISIBLE);
            holder.message.setText(message.getMessage());
        } else {
            if (message.getImage().equals("default")) {
                holder.message.setVisibility(View.GONE);
                holder.image.setVisibility(View.INVISIBLE);
                holder.progressBar.setVisibility(View.VISIBLE);
                holder.imageLayout.setVisibility(View.VISIBLE);
                Log.d(TAG, "onBindViewHolder: hereeee");
            } else {
                Uri uri = Uri.parse("https://clinice.herokuapp.com/images/chats" + message.getImage());
                holder.message.setVisibility(View.GONE);
                holder.imageLayout.setVisibility(View.VISIBLE);
                holder.image.setVisibility(View.INVISIBLE);


                Glide.with(context)
                        .asBitmap()
                        .load(uri)
                        .override(250, 300)
                        .into(new CustomTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                holder.image.setImageBitmap(resource);
                                holder.progressBar.setVisibility(View.GONE);
                                holder.image.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {

                            }
                        });
            }
        }
        holder.image.setOnClickListener(v -> {
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation((Activity) context, holder.image, "imageMain");
            context.startActivity(new Intent(context, ImagePreview.class)
                    .putExtra("image", message.getImage()), options.toBundle());
        });


        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            if (right) {
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right);
                viewToAnimate.startAnimation(animation);
                lastPosition = position;
            } else {
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_left);
                viewToAnimate.startAnimation(animation);
                lastPosition = position;
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView message, userName;
        ImageView image;
        RelativeLayout imageLayout;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.msg);
            userName = itemView.findViewById(R.id.user_name);
            image = itemView.findViewById(R.id.msg_image);
            imageLayout = itemView.findViewById(R.id.image_layout);
            progressBar = itemView.findViewById(R.id.progress);
        }
    }
}
