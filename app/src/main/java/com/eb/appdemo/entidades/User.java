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
    private String providerType;
    private String photoUrl;

    public User(String id,String firstName, String lastName, String email, String mobilePhone,
                String countryCode, String providerType, String photoUrl) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobilePhone = mobilePhone;
        this.countryCode = countryCode;
        this.providerType = providerType;
        this.photoUrl = photoUrl;
    }

    public User(String firstName, String lastName, String email, String mobilePhone, String countryCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobilePhone = mobilePhone;
        this.countryCode = countryCode;
    }


    public User() { }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

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

    public String getProviderType() {
        return providerType;
    }

    public void setProviderType(String providerType) {
        this.providerType = providerType;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", providerType='" + providerType + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }

    public String getUserCompleteName () {
        return firstName + " " + lastName;
    }


}
