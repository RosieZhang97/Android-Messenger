package com.example.client.adapter;

import android.view.View;
import android.widget.TextView;

import com.example.client.R;
import com.example.client.model.ChatModel;

public class ChatBViewHolder extends BaseViewHolder {
    private TextView tv;

    public ChatBViewHolder(View view) {
        super(view);
        tv = (TextView) itemView.findViewById(R.id.tv);
    }

    @Override
    public void setData(Object object) {
        super.setData(object);
        ChatModel model = (ChatModel) object;
        tv.setText(model.getContent());
    }
}