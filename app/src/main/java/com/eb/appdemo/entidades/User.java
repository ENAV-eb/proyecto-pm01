package com.eb.appdemo.entidades;

import androidx.annotation.NonNull;

public class User {

    //TODO provider.type, photourl
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobilePhone;
    private String countryCode;

    public User(String id, String firstName, String lastName, String email, String mobilePhone, String countryCode) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobilePhone = mobilePhone;
        this.countryCode = countryCode;
    }

    public User() {
    }

    public User(String first_name, String last_name, String email, String phone, String country) {
        this.firstName = first_name;
        this.lastName = last_name;
        this.email = email;
        this.mobilePhone = phone;
        this.countryCode = country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }

    public String getUserCompleteName () {
        return firstName + " " + lastName;
    }


}
