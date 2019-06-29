/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.hibernate.dao;

import aplicacion.modelo.dominio.Carrito;
import aplicacion.modelo.dominio.HelCar;
import aplicacion.modelo.dominio.Helado;
import java.util.List;

/**
 *
 * @author gabri
 */
public interface IHelCarDAO extends IGenericDAO<HelCar, Integer>{
    void actualizarCantidadHelCar(Helado helado, Integer cantidad,List<HelCar> helCars);
    void completarCantidad(List<HelCar> listAntes, List<HelCar> listDespues);
    List<HelCar> obtenerHelCARSegunCarrito(Carrito carrito);
    HelCar obtenerHelCar(Carrito carrito, Helado helado);
    void eliminarCarrito(HelCar helCar);
}
