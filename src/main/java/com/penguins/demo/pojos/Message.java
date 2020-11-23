package com.penguins.demo.pojos;

import javax.json.bind.annotation.JsonbProperty;

public class Message {
    @JsonbProperty
    private String from;

    @JsonbProperty
    private String to;

    @JsonbProperty
    private String info;

    @JsonbProperty
    private Placement placement;

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
        return info;
    }

    public void setInfo(String info){
        this.info = info;
    }

    public Placement getPlacement(){
        return placement;
    }

    public void setPlacement(Placement placement){
        this.placement = placement;
    }

    public String toString(){
        return "from: " + from + "; to: " + to + "; info: " + info + "; content: " + placement;
    }
}
