package com.example.client.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.client.model.ChatModel;
import com.example.client.model.ItemModel;

import java.util.ArrayList;

public class ChatDB extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "Chat.db";
    private final static int DATABASE_VERSION = 1;
    private final static String TABLE_NAME = "chat_table";
    public final static String CHAT_ID = "chat_id";
    public final static String CHAT_TYPE = "chat_type";
    public final static String CHAT_CONTENT = "chat_content";

    public ChatDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //创建table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + CHAT_ID
                + " INTEGER primary key autoincrement, " + CHAT_TYPE + " INTEGER, " + CHAT_CONTENT + " text);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public ArrayList<ItemModel> getChats() {
        ArrayList<ItemModel> models = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + TABLE_NAME;

        //example :rawQuery("SELECT id, name FROM people WHERE name = ? AND id = ?", new String[] {"David", "2"});

        Cursor cursor = db.rawQuery(sql, null);

        //Check if empty cursor
        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                ChatModel model = new ChatModel();
                model.setContent(cursor.getString(cursor.getColumnIndex(CHAT_CONTENT)));

                models.add(new ItemModel(cursor.getInt(cursor.getColumnIndex(CHAT_TYPE)), model));

                cursor.moveToNext();
            }
        }

        return models;
    }

    //Insertion
    public long insert(ItemModel itemModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ChatModel model = (ChatModel) itemModel.object;

        ContentValues cv = new ContentValues();
        cv.put(CHAT_CONTENT, (model.getContent()));
        cv.put(CHAT_TYPE, itemModel.type);

        long row = db.insert(TABLE_NAME, null, cv);
        return row;
    }
}