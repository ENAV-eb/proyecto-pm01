package com.eb.appdemo.entidades;

public class DataModulo {

    private String temperatura;
    private String humedad;
    private String luminosidad;
    private String ph_suelo;
    private String crop;
    private String tiempo;

    public DataModulo() {
    }

    public DataModulo(String temperatura, String humedad, String luminosidad, String ph_suelo,
                      String crop, String tiempo) {
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.luminosidad = luminosidad;
        this.ph_suelo = ph_suelo;
        this.crop = crop;
        this.tiempo = tiempo;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getHumedad() {
        return humedad;
    }

    public void setHumedad(String humedad) {
        this.humedad = humedad;
    }

    public String getLuminosidad() {
        return luminosidad;
    }

    public void setLuminosidad(String luminosidad) {
        this.luminosidad = luminosidad;
    }

    public String getPh_suelo() {
        return ph_suelo;
    }

    public void setPh_suelo(String ph_suelo) {
        this.ph_suelo = ph_suelo;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    @Override
    public String toString() {
        return "DataModulo{" +
                "temperatura='" + temperatura + '\'' +
                ", humedad='" + humedad + '\'' +
                ", luminosidad='" + luminosidad + '\'' +
                ", ph_suelo='" + ph_suelo + '\'' +
                ", crop='" + crop + '\'' +
                ", tiempo='" + tiempo + '\'' +
                '}';
    }
}
