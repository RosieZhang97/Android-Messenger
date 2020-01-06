package com.example.client.model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class ChatModel implements Serializable {
    private String icon = "";
    private String content = "";
    private String type = "";
    private Bitmap bitmap;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
