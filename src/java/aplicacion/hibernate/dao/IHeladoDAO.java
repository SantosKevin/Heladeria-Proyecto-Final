/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.hibernate.dao;

import aplicacion.modelo.dominio.Helado;
import java.util.List;

/**
 *
 * @author Lucas Choque
 */
public interface IHeladoDAO extends IGenericDAO<Helado, Integer>{
    Helado obtenerHeladoSegunIdHelado(Integer idHelado);
    List<Helado> obtenerHeladosDisponibles();
    Helado obtenerUnicoHelado(Integer codigoHelado);
    List<Helado> obtenerHeladosTipo(String tipo);
}
