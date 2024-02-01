package com.cifpceuta.pruebasqllite;

import java.util.Map;

public class Viaje  {

    public String nombreViaje;

    public String grupo;

    public String fecha;


    public Viaje(String nombreViaje, String grupo, String fecha) {
        this.nombreViaje = nombreViaje;
        this.grupo = grupo;
        this.fecha = fecha;
    }

    public Viaje(Map<String, Object> mapa) {

        this.nombreViaje = mapa.get("mapa").toString();
        this.grupo = mapa.get("grupo").toString();
        this.fecha = mapa.get("fecha").toString();

    }

    public String getNombreViaje() {
        return nombreViaje;
    }

    public void setNombreViaje(String nombreViaje) {
        this.nombreViaje = nombreViaje;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
