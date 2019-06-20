/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.modelo.dominio;

import java.io.Serializable;

/**
 *Representa el producto Helado
 * @author Lucas Choque
 */
public class Helado implements Serializable {
    /**
     * codigo unico que representa a cada helado
     */
    private Integer codigoHelado;
    /**
     * Nombre del helado
     */
    private String nombreHelado;
    /**
     * Especifica el tipo de helado
     */
    private String tipoHelado;
    /**
     * precio unitario del helado
     */
    private Double precio;
    /**
     * sabor del helado
     */
    private String saborHelado;
    /**
     * stock del helado
     */
    private Integer cantidad;
    /**
     * imagen del helado
     */
    private String imagen;
    /**
     * Especifica si el helado se encuentra disponible, independientemente del stock
     */
    private Boolean estado;
    /**
     * Constructor por defecto
     */
    public Helado() {
    }
    /**
     * Constructor parametrizado 
     * @param codigoHelado codigo unico que representa a cada helado
     * @param nombreHelado Nombre del helado
     * @param tipoHelado Especifica el tipo de helado
     * @param precio precio unitario del helado
     * @param saborHelado sabor del helado
     * @param cantidad stock del helado
     * @param imagen imagen del helado
     * @param estado Especifica si el helado se encuentra disponible, independientemente del stock
     */
    public Helado(Integer codigoHelado, String nombreHelado, String tipoHelado, Double precio, String saborHelado, Integer cantidad,String imagen,Boolean estado) {
        this.codigoHelado = codigoHelado;
        this.nombreHelado = nombreHelado;
        this.tipoHelado = tipoHelado;
        this.precio = precio;
        this.saborHelado = saborHelado;
        this.cantidad = cantidad;
        this.imagen = imagen;
        this.estado = estado;
    }
    /**
     * @return the codigoHelado
     */
    public Integer getCodigoHelado() {
        return codigoHelado;
    }
    /**
     * @param codigoHelado the codigoHelado to set
     */
    public void setCodigoHelado(Integer codigoHelado) {
        this.codigoHelado = codigoHelado;
    }
    /**
     * @return the nombreHelado
     */
    public String getNombreHelado() {
        return nombreHelado;
    }
    /**
     * @param nombreHelado the nombreHelado to set
     */
    public void setNombreHelado(String nombreHelado) {
        this.nombreHelado = nombreHelado;
    }
    /**
     * @return the tipoHelado
     */
    public String getTipoHelado() {
        return tipoHelado;
    }
    /**
     * @param tipoHelado the tipoHelado to set
     */
    public void setTipoHelado(String tipoHelado) {
        this.tipoHelado = tipoHelado;
    }
    /**
     * @return the precio
     */
    public Double getPrecio() {
        return precio;
    }
    /**
     * @param precio the precio to set
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    /**
     * @return the saborHelado
     */
    public String getSaborHelado() {
        return saborHelado;
    }
    /**
     * @param saborHelado the saborHelado to set
     */
    public void setSaborHelado(String saborHelado) {
        this.saborHelado = saborHelado;
    }
    /**
     * @return the cantidad
     */
    public Integer getCantidad() {
        return cantidad;
    }
    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    /**
     * @return the estado
     */
    public Boolean getEstado() {
        return estado;
    }
    /**
     * @param estado the estado to set
     */
    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}