package com.eb.appdemo.entidades;

public class ContactPhone {

    private String name;
    private String number;
    private Boolean selected = false;

    public ContactPhone(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "ContactPhone{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", selected=" + selected +
                '}';
    }
}
