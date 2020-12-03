package com.eb.appdemo.entidades;

import android.content.Context;

import com.eb.appdemo.db.KeeperDB;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Modulo implements Serializable {

    public static Modulo singletonModule;

    public static Modulo getInstance() {
        if (singletonModule == null){
            singletonModule = new Modulo();
            singletonModule.setId(UUID.randomUUID().toString());
            singletonModule.setActive(true);
        }
        return singletonModule;
    }

    private String id;
    private String nombre_modulo;
    private String nombre_campo;
    private String tipo_campo;
    /*
    private String temperatura;
    private String humedad;
    private String luminosidad;
    private String ph_suelo;
    private String crop;
    private String tiempo;
     */
    private String fechaInicio;
    private List<String> semanaCosecha;
    private List<String> semanaFumigacion;
    private List<String> semanaLimpieza;
    private List<Responsables> responsables;
    private String latitud;
    private String longitud;
    private List<String> users;
    private boolean isActive;

    public Modulo(String id, String nombre_modulo, String nombre_campo, String tipo_campo,
                  String fechaInicio, List<String> semanaCosecha, List<String> semanaFumigacion,
                  List<String> semanaLimpieza,List<Responsables> responsables,
                  List<String> users, boolean isActive) {
        this.id = id;
        this.nombre_modulo = nombre_modulo;
        this.nombre_campo = nombre_campo;
        this.tipo_campo = tipo_campo;
        this.fechaInicio = fechaInicio;
        this.semanaCosecha = semanaCosecha;
        this.semanaFumigacion = semanaFumigacion;
        this.semanaLimpieza = semanaLimpieza;
        this.responsables = responsables;
        this.users = users;
        this.isActive = isActive;
    }

    public Modulo(String id) {
        this.id = id;
    }

    public Modulo() {
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

    public String getTipo_campo() {
        return tipo_campo;
    }

    public void setTipo_campo(String tipo_campo) {
        this.tipo_campo = tipo_campo;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
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

    @Override
    public String toString() {
        return "Modulo{" +
                "id='" + id + '\'' +
                ", nombre_modulo='" + nombre_modulo + '\'' +
                ", nombre_campo='" + nombre_campo + '\'' +
                ", tipo_campo='" + tipo_campo + '\'' +
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
