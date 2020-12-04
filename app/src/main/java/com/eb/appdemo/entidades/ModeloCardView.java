package com.eb.appdemo.entidades;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ModeloCardView {

    private String id;
    private String nombre_modulo;
    private String nombre_campo;
    private String tipo_campo;

    private String temperatura;
    private String humedad;
    private String luminosidad;
    private String ph_suelo;
    private String crop;
    private String tiempo;

    private String fechaInicio;
    private List<String> semanaCosecha;
    private List<String> semanaFumigacion;
    private List<String> semanaLimpieza;
    private List<Responsables> responsables;
    private String latitud;
    private String longitud;
    private List<String> users;
    private boolean isActive;

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm:ss");

    public ModeloCardView() { }

    public void setFixedData(Modulo modulo) {
        this.id = modulo.getId();
        this.nombre_modulo = modulo.getNombre_modulo();
        this.nombre_campo = modulo.getNombre_campo();
        this.tipo_campo = modulo.getTipo_campo();
        this.fechaInicio = modulo.getFechaInicio();
        this.isActive = modulo.isActive();
        this.responsables = modulo.getResponsables();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setVariableData(DataModulo dataModulo) {
        this.temperatura = dataModulo.getTemperatura();
        this.humedad = dataModulo.getHumedad();
        this.luminosidad = dataModulo.getLuminosidad();
        this.ph_suelo = dataModulo.getPh_suelo();
        this.crop = dataModulo.getCrop();
        this.tiempo = LocalDateTime.ofInstant(Instant.parse(dataModulo.getTiempo()),
                ZoneId.of(ZoneOffset.UTC.getId())).toLocalTime().format(dtf);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre_modulo() {
        return nombre_modulo;
    }

    public void setNombre_modulo(String nombre_modulo) {
        this.nombre_modulo = nombre_modulo;
    }

    public String getNombre_campo() {
        return nombre_campo;
    }

    public void setNombre_campo(String nombre_campo) {
        this.nombre_campo = nombre_campo;
    }

    public String getTipo_campo() {
        return tipo_campo;
    }

    public void setTipo_campo(String tipo_campo) {
        this.tipo_campo = tipo_campo;
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

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public List<String> getSemanaCosecha() {
        return semanaCosecha;
    }

    public void setSemanaCosecha(List<String> semanaCosecha) {
        this.semanaCosecha = semanaCosecha;
    }

    public List<String> getSemanaFumigacion() {
        return semanaFumigacion;
    }

    public void setSemanaFumigacion(List<String> semanaFumigacion) {
        this.semanaFumigacion = semanaFumigacion;
    }

    public List<String> getSemanaLimpieza() {
        return semanaLimpieza;
    }

    public void setSemanaLimpieza(List<String> semanaLimpieza) {
        this.semanaLimpieza = semanaLimpieza;
    }

    public List<Responsables> getResponsables() {
        return responsables;
    }

    public void setResponsables(List<Responsables> responsables) {
        this.responsables = responsables;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "ModeloCardView{" +
                "nombre_modulo='" + nombre_modulo + '\'' +
                ", nombre_campo='" + nombre_campo + '\'' +
                ", tipo_campo='" + tipo_campo + '\'' +
                ", temperatura='" + temperatura + '\'' +
                ", humedad='" + humedad + '\'' +
                ", luminosidad='" + luminosidad + '\'' +
                ", ph_suelo='" + ph_suelo + '\'' +
                ", crop='" + crop + '\'' +
                ", tiempo='" + tiempo + '\'' +
                ", fechaInicio='" + fechaInicio + '\'' +
                ", semanaCosecha=" + semanaCosecha +
                ", semanaFumigacion=" + semanaFumigacion +
                ", semanaLimpieza=" + semanaLimpieza +
                ", responsables=" + responsables +
                ", latitud='" + latitud + '\'' +
                ", longitud='" + longitud + '\'' +
                ", users=" + users +
                ", isActive=" + isActive +
                '}';
    }
}
