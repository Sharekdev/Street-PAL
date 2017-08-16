package com.sharekeg.streetpal.chatcomponents;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.sharekeg.streetpal.R;

import java.util.List;

/**
 * Created by MMenem on 8/2/2017.
 */


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.chatHolder> {
    List<ChatMessage> ChatMessage;
    Context context;
    private View v;


    public ChatAdapter(Context context, List<ChatMessage> ChatMessage) {
        this.ChatMessage = ChatMessage;
        this.context = context;
    }


    @Override
    public chatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_message_rec, parent, false);
        return new chatHolder(v);
    }

    @Override
    public void onBindViewHolder(chatHolder holder, final int position) {
        Log.i("Chat_Message", ChatMessage.toString());
        holder.tvMessageRec.setText(ChatMessage.get(position).getMessageText());
//        Log.i("Message_adapter",ChatMessage.get(position).toString());
        if (ChatMessage.get(position).isUserMessage()) {
            holder.streetPalLogo.setVisibility(View.GONE);
            holder.tvMessageRec.setVisibility(View.GONE);
//            holder.userChatLogo.setVisibility(View.VISIBLE);
//            holder.tvMessageRec.setGravity(RelativeLayout.ALIGN_PARENT_RIGHT);
            holder.userMessage.setText(ChatMessage.get(position).getMessageText());
            holder.userMessage.setVisibility(View.VISIBLE);
        } else {
            holder.userMessage.setVisibility(View.GONE);
            holder.tvMessageRec.setText(ChatMessage.get(position).getMessageText());
            holder.streetPalLogo.setVisibility(View.VISIBLE);
            holder.tvMessageRec.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public int getItemCount() {
        return ChatMessage.size();
    }

    public class chatHolder extends RecyclerView.ViewHolder {

        TextView tvMessageRec, userMessage;
        ImageView streetPalLogo, userChatLogo;

        public chatHolder(View itemView) {
            super(itemView);
            tvMessageRec = (TextView) itemView.findViewById(R.id.tvmessage_send);
            streetPalLogo = (ImageView) itemView.findViewById(R.id.logo_street_pal);
            userMessage = (TextView) itemView.findViewById(R.id.tvmessage_user_text);
            //  userChatLogo = (ImageView) itemView.findViewById(R.id.logo_chat_user);

        }
    }

}
