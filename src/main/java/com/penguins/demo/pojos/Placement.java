package com.penguins.demo.pojos;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Placement extends PanacheEntity{

    @JsonbProperty
    private double x;

    @JsonbProperty
    private double y;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonbProperty
    private Penguin penguin;

    public double getX(){
        return x;
    }
    

    public void setX(double x){
        this.x = x;
    }

    public double getY(){
        return y;
    }

    public void setY(double y){
        this.y = y;
    }

    public void setPenguin(Penguin penguin){
        this.penguin = penguin;
    }

    public Penguin getPenguin(){
        return penguin;
    }

    public String toString(){
        return "x: " + x + "y: " + y + "; penguin name: "+ penguin.name + "; penguin id: " + penguin.id;
    }
}
