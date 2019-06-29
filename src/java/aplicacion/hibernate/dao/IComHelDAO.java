/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.hibernate.dao;

import aplicacion.modelo.dominio.ComHel;
import aplicacion.modelo.dominio.Compra;
import aplicacion.modelo.dominio.Helado;
import aplicacion.modelo.dominio.Usuario;

/**
 *
 * @author gabri
 */
public interface IComHelDAO extends IGenericDAO<ComHel, Integer>{
    ComHel obtenerComHel(Compra compra,Helado helado);
    void transferirCantidadHelCarAComHel(Compra compra, Usuario usuario);
    
    int obtenerCantidadComHel(Integer idCompra, Integer idHelado);
   
}
