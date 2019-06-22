/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.hibernate.dao;

import aplicacion.modelo.dominio.Carrito;
import aplicacion.modelo.dominio.Helado;
import java.util.List;

/**
 *
 * @author gabri
 */
public interface ICarritoDAO extends IGenericDAO<Carrito, Integer>{
    List<Carrito> obtenerCarritoSegunIdUsuario(Integer idUsuario);
    List<Helado> obtenerHeladoSegunIdUsuario(Integer idUsuario);
    void eliminarCarrito(Carrito carrito) ;
    void eliminarCarrito(Integer idUsuario);
}
