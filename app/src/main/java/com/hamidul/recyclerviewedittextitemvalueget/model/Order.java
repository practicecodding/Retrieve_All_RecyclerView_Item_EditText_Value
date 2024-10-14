package com.hamidul.recyclerviewedittextitemvalueget.model;

public class Order {

    int id;
    String name,editTextValue;
    int price;

    public Order(int id, String name, String editTextValue, int price) {
        this.id = id;
        this.name = name;
        this.editTextValue = editTextValue;
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

    public String getEditTextValue() {
        return editTextValue;
    }

    public void setEditTextValue(String editTextValue) {
        this.editTextValue = editTextValue;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
