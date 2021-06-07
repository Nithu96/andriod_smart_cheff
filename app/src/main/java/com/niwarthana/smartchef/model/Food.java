package com.niwarthana.smartchef.model;

public class Food {
    private int id;
    private String name;
    private double weight;
    private double price;
    private String description;
    private int availability;

    public Food() {
    }

    public Food(String name, double weight, double price, String description) {
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.description = description;
    }

    public Food(int id, String name, double weight, double price, String description, int availability) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.description = description;
        this.availability = availability;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
