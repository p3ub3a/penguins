package com.penguins.demo;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

public class MessageDecoder implements Decoder.Text<Message> {

    private static Gson gson = new Gson();

    @Override
    public void init(EndpointConfig config) {
        // TODO Auto-generated method stub

    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public Message decode(String s) throws DecodeException {
        // TODO Auto-generated method stub
        Message message = new Message();
        MessageContent messageContent = gson.fromJson(s, MessageContent.class);
        message.setInfo("spawnPengu");
        message.setMessageContent(messageContent);
        return message;
    }

    @Override
    public boolean willDecode(String s) {
        // TODO Auto-generated method stub
        return s!=null;
    }

    
}
