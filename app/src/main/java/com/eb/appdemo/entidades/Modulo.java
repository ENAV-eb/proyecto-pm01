package com.eb.appdemo.entidades;

import java.util.Objects;

public class Modulo {

    private Long id;
    private String nombre_modulo;
    private String nombre_campo;
    private String temperatura;
    private String humedad;
    private String luminosidad;
    private String ph_suelo;
    private String crop;
    private String tiempo;
    private boolean isActive;


    public Modulo(Long id) {
        this.id = id;
    }

    public Modulo() {
    }

    public Modulo(Long id, String nombre_modulo, String nombre_campo, String temperatura,
                  String humedad, String luminosidad, String ph_suelo, String crop,
                  String tiempo, boolean isActive) {
        this.id = id;
        this.nombre_modulo = nombre_modulo;
        this.nombre_campo = nombre_campo;
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.luminosidad = luminosidad;
        this.ph_suelo = ph_suelo;
        this.crop = crop;
        this.tiempo = tiempo;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public String getNombre_modulo() {
        return nombre_modulo;
    }

    public String getNombre_campo() {
        return nombre_campo;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public String getHumedad() {
        return humedad;
    }

    public String getLuminosidad() {
        return luminosidad;
    }

    public String getPh_suelo() {
        return ph_suelo;
    }

    public String getCrop() {
        return crop;
    }

    public String getTiempo() {
        return tiempo;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Modulo)) return false;
        Modulo modulo = (Modulo) o;
        return isActive() == modulo.isActive() &&
                Objects.equals(getId(), modulo.getId()) &&
                Objects.equals(getNombre_modulo(), modulo.getNombre_modulo()) &&
                Objects.equals(getNombre_campo(), modulo.getNombre_campo()) &&
                Objects.equals(getTemperatura(), modulo.getTemperatura()) &&
                Objects.equals(getHumedad(), modulo.getHumedad()) &&
                Objects.equals(getLuminosidad(), modulo.getLuminosidad()) &&
                Objects.equals(getPh_suelo(), modulo.getPh_suelo()) &&
                Objects.equals(getCrop(), modulo.getCrop()) &&
                Objects.equals(getTiempo(), modulo.getTiempo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNombre_modulo(), getNombre_campo(), getTemperatura(), getHumedad(), getLuminosidad(), getPh_suelo(), getCrop(), getTiempo(), isActive());
    }

    @Override
    public String toString() {
        return "Modulo{" +
                "id=" + id +
                ", nombre_modulo='" + nombre_modulo + '\'' +
                ", nombre_campo='" + nombre_campo + '\'' +
                ", temperatura='" + temperatura + '\'' +
                ", humedad='" + humedad + '\'' +
                ", luminosidad='" + luminosidad + '\'' +
                ", ph_suelo='" + ph_suelo + '\'' +
                ", crop='" + crop + '\'' +
                ", tiempo='" + tiempo + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
