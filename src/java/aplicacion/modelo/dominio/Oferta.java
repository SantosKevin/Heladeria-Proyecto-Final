/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.modelo.dominio;

import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author Lucas Choque
 */
public class Oferta implements Serializable{
    /**
     * codigo unico que representa a cada oferta
     */
    private Integer codigoOferta;
    /**
     * Helados que pueden inclurse en la oferta
     */
    private Set<Helado> heladosOferta;
    /**
     * descuento que se le hara a los helados en oferta
     */
    private Double descuento;
    /**
     * Pendiente: agregar la fecha de inicio de oferta (no la agrego porque no se mapear objetos de tipo Calendar)
     * 
     * private Calendar duracionOferta;
     */
    /**
     * Constructor por defecto
     */
    public Oferta() {
    }
    /**
     * Constructor parametrizado
     * @param codigoOferta codigo unico que representa a cada oferta
     * @param heladosOferta Helados que pueden inclurse en la oferta
     * @param descuento descuento que se le hara a los helados en oferta
     */
    public Oferta(Integer codigoOferta, Set<Helado> heladosOferta, Double descuento) {
        this.codigoOferta = codigoOferta;
        this.heladosOferta = heladosOferta;
        this.descuento = descuento;
    }
    /**
     * @return the codigoOferta
     */
    public Integer getCodigoOferta() {
        return codigoOferta;
    }
    /**
     * @param codigoOferta the codigoOferta to set
     */
    public void setCodigoOferta(Integer codigoOferta) {
        this.codigoOferta = codigoOferta;
    }
    /**
     * @return the heladosOferta
     */
    public Set<Helado> getHeladosOferta() {
        return heladosOferta;
    }
    /**
     * @param heladosOferta the heladosOferta to set
     */
    public void setHeladosOferta(Set<Helado> heladosOferta) {
        this.heladosOferta = heladosOferta;
    }
    /**
     * @return the descuento
     */
    public Double getDescuento() {
        return descuento;
    }
    /**
     * @param descuento the descuento to set
     */
    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }
}


