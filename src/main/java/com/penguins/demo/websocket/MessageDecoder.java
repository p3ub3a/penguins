package com.penguins.demo.websocket;

import javax.inject.Inject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;
import com.penguins.demo.pojos.Message;

import org.eclipse.microprofile.context.ManagedExecutor;

public class MessageDecoder implements Decoder.Text<Message> {

    private static Gson gson = new Gson();

    @Inject
    ManagedExecutor managedExecutor;

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
        try{
            Message message = gson.fromJson(s, Message.class);
            return message;
        }catch( Exception e){
            e.printStackTrace();
        }
        return new Message();
    }

    @Override
    public boolean willDecode(String s) {
        // TODO Auto-generated method stub
        return s != null;
    }

}
