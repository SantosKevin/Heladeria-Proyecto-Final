/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.modelo.dominio;

/**
 *
 * @author samanta
 */
public class Carrito {
    private Integer codigo;
    private Integer codigoUsuario;
    private Integer codigoHelado;
    private Integer cantidad;

    public Carrito() {
    }
    
    public Carrito(Integer codigo, Integer codigoUsuario, Integer codigoHelado, Integer cantidad) {
        this.codigo = codigo;
        this.codigoUsuario = codigoUsuario;
        this.codigoHelado = codigoHelado;
        this.cantidad = cantidad;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(Integer codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public Integer getCodigoHelado() {
        return codigoHelado;
    }

    public void setCodigoHelado(Integer codigoHelado) {
        this.codigoHelado = codigoHelado;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
    
}