/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Estructuras.ArrayList;

/**
 *
 * @author EvilFourier
 */
public class Player {
    private int puntaje;
    private int errores;
    private final ArrayList<String> palabras_encontradas;
    private int cantidad_cambios;
    
    //Constructor 
    public Player(){
        palabras_encontradas = new ArrayList();
        errores = 3;
        cantidad_cambios = 2;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void modificarPuntaje(int puntaje) {
        this.puntaje += puntaje;
        if(this.puntaje < 0) this.puntaje = 0;
    }

    public int getErrores() {
        return errores;
    }

    
    public int getCantidad_cambios() {
        return cantidad_cambios;
    }

    public void reducir_cantidadCambios(){
        cantidad_cambios--;
    }
    
    // Metodo que reduce la cantidad de intentos dado que cometio un error
    public void reducirIntentos(){
        errores--;
    }
    
   
    
}
