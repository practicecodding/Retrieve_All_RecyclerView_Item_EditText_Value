package com.hamidul.recyclerviewedittextitemvalueget.model;

public class Order {

    int id;
    String name,quantity;
    double tp,discount;
    int price;

    public Order(int id, String name, String quantity, double tp, double discount, int price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.tp = tp;
        this.discount = discount;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public double getTp() {
        return tp;
    }

    public void setTp(double tp) {
        this.tp = tp;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
