package com.cifpceuta.pruebasqllite;

public class Estudiante {

    String nombre;
    String correo;
    String grupo;
    String turno;

    public Estudiante(String nombre, String correo, String grupo, String turno) {
        this.nombre = nombre;
        this.correo = correo;
        this.grupo = grupo;
        this.turno = turno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
}
