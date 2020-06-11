package app.iflatco.eclinic.ui.old_chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.iflatco.eclinic.R;
import app.iflatco.eclinic.models.OldMessageData;

public class OldChatAdapter extends RecyclerView.Adapter<OldChatAdapter.ViewHolder> {
    private static final String TAG = "ChatAdapter";

    private List<OldMessageData> modelList = new ArrayList<>();
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private boolean right = false;
    private int lastPosition = -1;
    private Context context;

    public OldChatAdapter(Context context, List<OldMessageData> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @Override
    public int getItemViewType(int position) {
        OldMessageData message = modelList.get(position);

        if (message.getSender().equals("user")) {
            right = true;
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            right = false;
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @NonNull
    @Override
    public OldChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
    public void onBindViewHolder(@NonNull OldChatAdapter.ViewHolder holder, int position) {
        OldMessageData message = modelList.get(position);

        if (position != 0) {
            if (modelList.get(position).getSender().equals(modelList.get(position - 1).getSender())) {
                holder.userName.setVisibility(View.GONE);
            } else {
                holder.userName.setVisibility(View.VISIBLE);
                holder.userName.setText(message.getSenderName());
            }
        } else {
            holder.userName.setVisibility(View.VISIBLE);
            holder.userName.setText(message.getSenderName());
        }
        holder.message.setText(message.getMessage());

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.msg);
            userName = itemView.findViewById(R.id.user_name);

        }
    }
}
