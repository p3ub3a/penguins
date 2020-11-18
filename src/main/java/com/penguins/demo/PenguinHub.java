package com.penguins.demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/waddle/{user}", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class PenguinHub {
    private static Set<Session> sessions = new CopyOnWriteArraySet<>();
    private static Map<String, String> users = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("user") String user) throws IOException {
        // Get session and WebSocket connection
        sessions.add(session);
        users.put(session.getId(), user);

        Message message = new Message();
        message.setFrom(user);
        message.setInfo("connected");
        broadcast(message);
    }

    @OnMessage
    public void onMessage(Session session, Message message) throws IOException {
        // Handle new messages
        message.setFrom(users.get(session.getId()));
        // System.out.println(message.getContent());
        broadcast(message);
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        // WebSocket connection closes
        sessions.remove(session);
        Message message = new Message();
        message.setFrom(users.get(session.getId()));
        message.setInfo("disconnected");
        broadcast(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }

    private static void broadcast(Message message) {
        System.out.println(sessions.size() + sessions.toString());
        sessions.forEach(s -> {
            try {
                System.out.println("*sending " + s.getId() + "; isopen: " + s.isOpen() + "\n*message: " + message.toString());
                if(s.isOpen()){
                    System.out.println(s.getAsyncRemote() + "ceva");
                    s.getAsyncRemote().sendObject(message);
                    System.out.println("message sent");
                }
                    
                }catch(IllegalArgumentException e){
                    e.printStackTrace();
                }
        });
    }
}
