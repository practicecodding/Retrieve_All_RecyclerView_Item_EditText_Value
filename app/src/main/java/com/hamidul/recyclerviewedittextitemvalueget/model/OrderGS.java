package com.hamidul.recyclerviewedittextitemvalueget.model;

public class OrderGS {

    int id;
    String name,editTextValue;

    public OrderGS(int id, String name, String editTextValue) {
        this.id = id;
        this.name = name;
        this.editTextValue = editTextValue;
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

}
