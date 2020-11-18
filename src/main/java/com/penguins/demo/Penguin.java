package com.penguins.demo;

import javax.persistence.Column;
import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Penguin extends PanacheEntity{

    @Column(name="penguin_name")
    public String name;
}
