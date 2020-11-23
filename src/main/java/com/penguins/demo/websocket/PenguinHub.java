package com.penguins.demo.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.inject.Inject;
import javax.transaction.TransactionManager;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import com.penguins.demo.pojos.Message;
import com.penguins.demo.pojos.Penguin;
import com.penguins.demo.pojos.Placement;

import org.eclipse.microprofile.context.ManagedExecutor;

@ServerEndpoint(value = "/waddle/{user}", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class PenguinHub {
    @Inject
    ManagedExecutor managedExecutor;

    @Inject
    TransactionManager transactionManager;

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
        message.setFrom(users.get(session.getId()));
        managedExecutor.submit(() -> {
            try{
                transactionManager.begin();
                parseMessage(message);
                transactionManager.commit();
            }catch(Exception e){
                e.printStackTrace();
            } 
        });
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

    private void parseMessage(Message message) {
        try {
            if(message.getInfo().equals("spawn-pengu")){
                Placement placement = message.getPlacement();
                placement.setPenguin(Penguin.findById((long) placement.getPenguin().id));
                placement.persist();
            }else{
                Placement.deleteAll();
            }
            
            broadcast(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void broadcast(Message message) {
        sessions.forEach(s -> {
            try {
                System.out.println(
                        "*****Sending msg, session: " + s.getId() + "; isopen: " + s.isOpen() + "\n*message: " + message.toString());
                if (s.isOpen()) {
                    s.getAsyncRemote().sendObject(message);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        });
    }
}
