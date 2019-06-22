/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.hibernate.dao;

import aplicacion.modelo.dominio.Helado;
import aplicacion.modelo.dominio.Oferta;
import java.util.List;

/**
 *
 * @author Lucas Choque
 */
public interface IOfertaDAO extends IGenericDAO<Oferta, Integer>{
    Oferta obtenerUnicaOferta(Integer codigoOferta);
    List<Oferta> obtenerOfertaDistinct();
    List<Oferta> obtenerOfertasActuales();
    List<Helado> obtenerHeladosEnOferta();
    boolean consultarOferta(Integer codigoOferta);
}
