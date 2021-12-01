/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author david
 */
public class Persona {
    
    private int cambiosDisponibles;
    private int cambiosRealizados;
    private String direccion;

    public Persona() {
        cambiosDisponibles=2;
        cambiosRealizados=2;
        direccion="sinDireccion";
    }

    public int getCambiosDisponibles() {
        return cambiosDisponibles;
    }

    public void setCambiosDisponibles() {
        this.cambiosDisponibles--;
    }

    public int getCambiosRealizados() {
        return cambiosRealizados;
    }

    public void setCambiosRealizados() {
        this.cambiosRealizados--;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    
    
    
}
