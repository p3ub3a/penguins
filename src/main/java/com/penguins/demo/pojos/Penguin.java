package com.penguins.demo.pojos;

import java.util.ArrayList;
import java.util.List;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Penguin extends PanacheEntity{

    @Column(name="penguin_name")
    @JsonbProperty
    public String name;

    @JsonbProperty
    public int option;

    @OneToMany(mappedBy = "penguin")
    @JsonbTransient
    public List<Placement> placements = new ArrayList<>();

    public String toString(){
        return "name: " + name + "; option: " + option;
    }
}
