package com.eb.appdemo.entidades;



import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    public static User singletonUser = new User();


    //TODO provider.type, photourl
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobilePhone;
    private String countryCode;
    private String providerType;
    private String photoUrl;
    private List<String> modulos;

    public User(String id,String firstName, String lastName, String email, String mobilePhone,
                String countryCode, String providerType, String photoUrl, List<String> modulos) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobilePhone = mobilePhone;
        this.countryCode = countryCode;
        this.providerType = providerType;
        this.photoUrl = photoUrl;
        this.modulos = modulos;
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

    public List<String> getModulos() {
        return modulos;
    }

    public void setModulos(List<String> modulos) {
        this.modulos = modulos;
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
                ", modulos=" + modulos +
                '}';
    }

    public String getUserCompleteName () {
        return firstName + " " + lastName;
    }


}
