/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.hibernate.dao;

import aplicacion.modelo.dominio.Compra;
import aplicacion.modelo.dominio.Usuario;
import java.util.List;

/**
 *
 * @author Lucas Choque
 */
public interface ICompraDAO extends IGenericDAO<Compra, Integer>{
    List<Compra> obtenerCompras(Usuario usuario);
    Integer obtenerUltimoCodigodeCompra(Usuario usuario);
    Compra obtenerUltimaCompra(Usuario usuario);
    void eliminarCompra(Compra compra);

    
    List<Compra> obtenerCompras();
    List<Compra> obtenerCompra(Integer codigo);
}
