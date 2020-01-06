package client.example.com.server.adapter;

import android.view.View;
import android.widget.TextView;

import client.example.com.server.R;
import client.example.com.server.model.ChatModel;


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