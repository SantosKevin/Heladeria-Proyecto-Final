/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.modelo.dominio;

import java.io.Serializable;
import java.util.Set;

/**
 *Representa una compra realizado por un usuario
 * @author Lucas Choque
 */
public class Compra implements Serializable{
    /**
     * codigo unico que representa a cada compra
     */
    private Integer codigoCompra;
    /**
     * Usuario que realiza la compra
     */
    private Usuario usuarioCompra;
    /**
     * Helados que pueden inclurse en la compra
     */
    private Set<Helado> heladosCompra;
    /**
     * Pendiente: agregar la fecha de compra (no la agrego porque no se mapear objetos de tipo Calendar)
     * 
     * private Calendar fechaCompra;
     */
    /**
     * Estado de la compra
     * 1) Pendiente: En espera a que el usuario confirme la compra
     * 2) Pagado: Cuando el usuario confirma la compra, se cambia el estado de la compra a Pagado
     * 3) Cancelado: Cuando pasan 24 horas y el usuario no confirma la compra se cambia el estado a Cancelado
     */
    private Integer estado;
    
    /**
     * Constructor por defecto
     */
    public Compra() {
    }
    /**
     * Constructor parametrizado
     * @param codigoCompra codigo unico que representa a cada compra
     * @param usuarioCompra Usuario que realiza la compra
     * @param heladosCompra Helados que pueden inclurse en la compra
     * @param estado Estado de la compra
     */
    public Compra(Integer codigoCompra, Usuario usuarioCompra, Set<Helado> heladosCompra, Integer estado) {
        this.codigoCompra = codigoCompra;
        this.usuarioCompra = usuarioCompra;
        this.heladosCompra = heladosCompra;
        this.estado = estado;
    }
    /**
     * @return the codigoCompra
     */
    public Integer getCodigoCompra() {
        return codigoCompra;
    }
    /**
     * @param codigoCompra the codigoCompra to set
     */
    public void setCodigoCompra(Integer codigoCompra) {
        this.codigoCompra = codigoCompra;
    }
    /**
     * @return the usuarioCompra
     */
    public Usuario getUsuarioCompra() {
        return usuarioCompra;
    }
    /**
     * @param usuarioCompra the usuarioCompra to set
     */
    public void setUsuarioCompra(Usuario usuarioCompra) {
        this.usuarioCompra = usuarioCompra;
    }
    /**
     * @return the heladosCompra
     */
    public Set<Helado> getHeladosCompra() {
        return heladosCompra;
    }
    /**
     * @param heladosCompra the heladosCompra to set
     */
    public void setHeladosCompra(Set<Helado> heladosCompra) {
        this.heladosCompra = heladosCompra;
    }
    /**
     * @return the estado
     */
    public Integer getEstado() {
        return estado;
    }
    /**
     * @param estado the estado to set
     */
    public void setEstado(Integer estado) {
        this.estado = estado;
    }
}