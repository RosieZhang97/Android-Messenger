package client.example.com.server.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import client.example.com.server.R;
import client.example.com.server.Util.ToastUtil;
import client.example.com.server.adapter.ChatAdapter;
import client.example.com.server.model.ChatModel;
import client.example.com.server.model.ItemModel;

public class ChatOnlineActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ChatAdapter adapter;
    EditText et;
    TextView tvSend, tvSendImg;

    ArrayList<ItemModel> models;

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        models = new ArrayList<>();

        initWidget();

        init();
    }

    /**
     * Broadcast and wait for a reply
     */
    private void init() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("client_to_server_action");
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    /**
     * Receive the data returned from client
     */
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String dataFromServer = intent.getStringExtra("data");
            boolean isExsitImage = intent.getBooleanExtra("isExsitImage", false);

            ArrayList<ItemModel> data = new ArrayList<>();

            ChatModel model = new ChatModel();
            model.setContent(dataFromServer);

            ItemModel itemModel = new ItemModel(isExsitImage ? ItemModel.CHAT_C : ItemModel.CHAT_A, model);
            data.add(itemModel);

            adapter.addAll(data);
            adapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(adapter.getItemCount() - 1);
        }
    };

    private void initWidget() {
        et = (EditText) findViewById(R.id.et);

        recyclerView = (RecyclerView) findViewById(R.id.recylerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter = new ChatAdapter());

        ChatModel model = new ChatModel();
        model.setContent("Connected");
        ItemModel itemModel = new ItemModel(ItemModel.CHAT_B, model);
        models.add(itemModel);
        adapter.replaceAll(models);

        //Send text
        tvSend = (TextView) findViewById(R.id.tvSend);
        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = et.getText().toString().trim();
                if (content.isEmpty()) {
                    ToastUtil.showShort(ChatOnlineActivity.this, "Enter text here");
                    return;
                }

                content = "Server：" + content;

                et.setText("");
                hideKeyBorad(et);

                ArrayList<ItemModel> data = new ArrayList<>();

                ChatModel model = new ChatModel();
                model.setContent(content);

                ItemModel itemModel = new ItemModel(ItemModel.CHAT_B, model);
                data.add(itemModel);

                adapter.addAll(data);
                recyclerView.scrollToPosition(adapter.getItemCount() - 1);

                sendDataToClient(false, content);
            }
        });

        //Send picture
        tvSendImg = (TextView) findViewById(R.id.tvSendImg);
        tvSendImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showShort(ChatOnlineActivity.this, "Sent a picture!");
                sendDataToClient(true, "");
            }
        });
    }

    /**
     * Send data to client side
     */
    private void sendDataToClient(boolean isExsitImage, String data) {
        Intent intentBroadcast = new Intent();
        intentBroadcast.setAction("server_to_client_action");
        intentBroadcast.putExtra("data", data);
        intentBroadcast.putExtra("isExsitImage", isExsitImage);
        sendBroadcast(intentBroadcast);
    }

    private void hideKeyBorad(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }
}

