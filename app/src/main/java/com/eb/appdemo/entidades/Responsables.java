package com.eb.appdemo.entidades;

public class Responsables {

    private String name;
    private String number;

    public Responsables(ContactPhone contactPhone) {
        this.name = contactPhone.getName();
        this.number = contactPhone.getNumber();
    }

    @Override
    public String toString() {
        return "Responsables{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}
