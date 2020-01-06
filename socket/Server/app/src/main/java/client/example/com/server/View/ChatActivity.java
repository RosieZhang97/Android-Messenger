package client.example.com.server.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import client.example.com.server.R;
import client.example.com.server.Util.FileUtil;
import client.example.com.server.Util.ToastUtil;
import client.example.com.server.adapter.ChatAdapter;
import client.example.com.server.dao.ChatDB;
import client.example.com.server.model.ChatModel;
import client.example.com.server.model.ItemModel;

public class ChatActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ChatAdapter adapter;
    EditText et;
    TextView tvSend;

    ChatDB chatDB;
    ArrayList<ItemModel> models;

    int lastChatType = ItemModel.CHAT_A;

    String[] contents = {"", "", "", ""};

    Bitmap bitmap;
    String chatDirectory = FileUtil.getSDCardRoot() + File.separator + "chat";
    String chatPath = chatDirectory + File.separator + "chat.JPEG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatDB = new ChatDB(ChatActivity.this);
        models = new ArrayList<>();

        FileUtil.isSDCardMounted();

        initChatImage();

        initData();
        initWidget();
    }

    //region Initialize ChatImage
    private void initChatImage() {
        File chatFile = new File(chatDirectory);
        if (!chatFile.exists()) {
            chatFile.mkdirs();
        }

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test);

        // Save pictures to file
        File file = new File(chatPath);
        if (file.exists()) {
            file.delete();
        }
        FileUtil.saveBitmap(chatPath, bitmap);

        // Load the pic from file
        bitmap = FileUtil.getBitmap(chatPath);
    }
    //endregion

    // region Initialize variables
    private void initData() {
        models = chatDB.getChats();
        if (models.isEmpty()) {
            for (int i = 0; i < contents.length; i++) {
                ChatModel chatModel = new ChatModel();
                chatModel.setContent(contents[i]);
                ItemModel model = new ItemModel(i % 2 == 0 ? ItemModel.CHAT_A : ItemModel.CHAT_B, chatModel);
                models.add(model);
                chatDB.insert(model);
            }
        }

        ChatModel model = new ChatModel();
        model.setBitmap(bitmap);

        ItemModel itemModel = new ItemModel(ItemModel.CHAT_C, model);
        models.add(itemModel);
    }

    //endregion

    //region Initialize the chat layout

    private void initWidget() {
        et = (EditText) findViewById(R.id.et);

        recyclerView = (RecyclerView) findViewById(R.id.recylerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter = new ChatAdapter());
        adapter.replaceAll(models);

        tvSend = (TextView) findViewById(R.id.tvSend);
        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = et.getText().toString().trim();
                if (content.isEmpty()) {
                    ToastUtil.showShort(ChatActivity.this, "Enter text here");
                }

                saveData(content);
            }
        });
    }
    //endregion

    //region Save chat history
    private void saveData(String content) {
        lastChatType = lastChatType == ItemModel.CHAT_A ? ItemModel.CHAT_B : ItemModel.CHAT_A;
        ArrayList<ItemModel> data = new ArrayList<>();

        ChatModel model = new ChatModel();
        model.setContent(content);

        ItemModel itemModel = new ItemModel(lastChatType, model);
        data.add(itemModel);

        adapter.addAll(data);
        chatDB.insert(itemModel);

        et.setText("");
        hideKeyBorad(et);
    }
    //endregion

    //region  Hide keyboard
    private void hideKeyBorad(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }
    //endregion

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }
}

