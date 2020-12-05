package com.eb.appdemo.entidades;

public class SinglentonUtil {

    public static SinglentonUtil singlentonUtil= new SinglentonUtil();

    private String idModulo;

    public SinglentonUtil(String idModulo) {
        this.idModulo = idModulo;
    }

    public SinglentonUtil() {}

    public String getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(String idModulo) {
        this.idModulo = idModulo;
    }
}
