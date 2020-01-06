package com.example.client.socketService;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.channels.NotYetConnectedException;

public class PushClientHelper extends WebSocketClient {

    SocketMessageCallBack callBack;

    public SocketMessageCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(SocketMessageCallBack callBack) {
        this.callBack = callBack;
    }

    public PushClientHelper(URI serverURI) {
        super(serverURI);
    }

    public PushClientHelper(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
    }

    @Override
    public void onMessage(String message) {
        if (callBack != null) {
            callBack.handleMessage(message);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        if (callBack != null) {
            callBack.handleMessage(reason);
        }
    }

    @Override
    public void onError(Exception ex) {
        if (callBack != null) {
            callBack.handleMessage(ex.getMessage());
        }
    }

    @Override
    public void send(String text) throws NotYetConnectedException {
        super.send(text);
    }
}