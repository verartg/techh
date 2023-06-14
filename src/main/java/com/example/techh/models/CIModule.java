package com.example.techh.models;
import jakarta.persistence.*;

@Entity
public class CIModule {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String type;
    private double price;

    @ManyToOne
    @JoinColumn(name = "television_id")
    private Television television;

    public Television getTelevision() {
        return television;
    }

    public void setTelevision(Television Television) {
        this.television = Television;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
