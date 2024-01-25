package com.cifpceuta.pruebasqllite;

public class Practica {

    public String tarea;
    public String fechaInicio;
    public String fechaFinal;
    public String descrtiption;
    public String modulo;

    public String grupo;


    public Practica(String tarea, String fechaInicio, String fechaFinal, String descrtiption, String modulo, String grupo) {
        this.tarea = tarea;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.descrtiption = descrtiption;
        this.modulo = modulo;
        this.grupo = grupo;
    }


    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getDescrtiption() {
        return descrtiption;
    }

    public void setDescrtiption(String descrtiption) {
        this.descrtiption = descrtiption;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }
}
