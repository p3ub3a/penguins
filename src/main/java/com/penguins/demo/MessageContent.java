package com.penguins.demo;

import javax.json.bind.annotation.JsonbProperty;

public class MessageContent {
    @JsonbProperty
    private int option;

    @JsonbProperty
    private int x;

    @JsonbProperty
    private int y;

    public int getX(){
        return x;
    }

    public void setX(int x){
        this.x = x;
    }

    public int getY(){
        return y;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getOption(){
        return option;
    }

    public void setOption(int option){
        this.option = option;
    }
}
