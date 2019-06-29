/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.modelo.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Lucas Choque
 */
@ManagedBean
@SessionScoped

/**
 * Clase que posee los sabores y tipos de manera estatica y constantes es decir, no se van a modificar
 */
public class SaboresYTipos implements Serializable{
    
    private final static String[] Sabores;
    private final static String[] Tipos;
    private final static String[] TiposOfertas;
    
    static {
        Sabores = new String[10];
        Sabores[0] = "Vainilla";
        Sabores[1] = "Frutilla";
        Sabores[2] = "Chocolate";
        Sabores[3] = "Granizado";
        Sabores[4] = "Dulce de Leche";
        Sabores[5] = "Dulce de Leche y Frutilla";
        Sabores[6] = "Granizado y Chocolate";
        Sabores[7] = "Vainilla y Granizado";
        Sabores[8] = "Vainilla y Frutilla";
        Sabores[9] = "Granizado y Dulce de Leche"; 
        
        Tipos = new String[5];
        
        Tipos[0] = "Pote";
        Tipos[1] = "Palitos";
        Tipos[2] = "Tortas";
        Tipos[3] = "Familiar";
        Tipos[4] = "Postres";
        
        TiposOfertas = new String[2];
        
        TiposOfertas[0] = "10% de Descuento";
        TiposOfertas[1] = "20% de Descuento";
    }
    
    /**
     * Metodos que convierten las matrices en listas 
     */
    public List<String> obtenerSabores(){
        return Arrays.asList(Sabores);
    }
    public List<String> obtenerTipos(){
        return Arrays.asList(Tipos);
    }
    public List<String> obtenerTiposOfertas(){
        return Arrays.asList(TiposOfertas);
    }
}