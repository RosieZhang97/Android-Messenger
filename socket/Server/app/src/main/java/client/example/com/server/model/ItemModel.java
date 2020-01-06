package client.example.com.server.model;

import java.io.Serializable;

public class ItemModel implements Serializable {

    public static final int CHAT_A = 1001;
    public static final int CHAT_B = 1002;
    public static final int CHAT_C = 1003;
    public static final int CHAT_D = 1004;

    public int type;
    public Object object;

    public ItemModel(int type, Object object) {
        this.type = type;
        this.object = object;
    }
}
