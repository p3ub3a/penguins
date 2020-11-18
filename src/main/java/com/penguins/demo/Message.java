package com.penguins.demo;

import javax.json.bind.annotation.JsonbProperty;

public class Message {
    @JsonbProperty
    private String from;

    @JsonbProperty
    private String to;

    @JsonbProperty
    private String info;

    @JsonbProperty
    private MessageContent messageContent;

    public String getFrom(){
        return from;
    }

    public void setFrom(String from){
        this.from = from;
    }

    public String getTo(){
        return to;
    }

    public void setTo(String to){
        this.to = to;
    }

    public String getInfo(){
        return to;
    }

    public void setInfo(String info){
        this.info = info;
    }

    public MessageContent getMessageContent(){
        return messageContent;
    }

    public void setMessageContent(MessageContent messageContent){
        this.messageContent = messageContent;
    }

    public String toString(){
        return "from: " + from + "; to: " + to + "; content: " + messageContent;
    }
}
