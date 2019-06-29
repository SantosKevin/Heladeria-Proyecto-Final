/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.hibernate.dao;

import aplicacion.modelo.dominio.Carrito;
import aplicacion.modelo.dominio.Helado;
import aplicacion.modelo.dominio.Usuario;
import java.util.List;

/**
 *
 * @author gabri
 */
public interface ICarritoDAO extends IGenericDAO<Carrito, Integer>{
    Carrito obtenerCarritoSegunUsuario(Usuario usuario);
    List<Helado> actualizarListaCarrito(Usuario usuario,Helado helado);
    void calcularTotalListaHeladoCarrito(Carrito carrito);
    
    List<Helado> obtenerHeladoSegunIdUsuario(Integer idUsuario);
    void eliminarCarrito(Carrito carrito);
    
    List<Helado> obtenerHeladoSegunIdUsuarioConCantidadCarrito(Integer idUsuario);
    double obtenerPrecioTotalCarrito(List<Carrito> carritos);
}
