/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.hibernate.dao;

import aplicacion.modelo.dominio.Compra;
import java.util.List;

/**
 *
 * @author Lucas Choque
 */
public interface ICompraDAO extends IGenericDAO<Compra, Integer>{
    List<Compra> obtenerCompras();
    List<Compra> obtenerComprasSegunIdUsuario(Integer idUsuario);
    Integer obtenerUltimoCodigodeCompra(Integer idUsuario);
    List<Compra> obtenerCompra(Integer codigo);
}
