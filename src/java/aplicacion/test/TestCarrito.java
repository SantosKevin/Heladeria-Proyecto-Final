/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.test;

import aplicacion.hibernate.dao.ICarritoDAO;
import aplicacion.hibernate.dao.IHelCarDAO;
import aplicacion.hibernate.dao.IHeladoDAO;
import aplicacion.hibernate.dao.IUsuarioDAO;
import aplicacion.hibernate.dao.imp.CarritoDAOImp;
import aplicacion.hibernate.dao.imp.HelCarDAOImp;
import aplicacion.hibernate.dao.imp.HeladoDAOImp;
import aplicacion.hibernate.dao.imp.UsuarioDAOImp;
import aplicacion.modelo.dominio.Carrito;
import aplicacion.modelo.dominio.HelCar;
import aplicacion.modelo.dominio.HelCarId;
import aplicacion.modelo.dominio.Helado;
import aplicacion.modelo.dominio.Usuario;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author gabri
 */
public class TestCarrito {
    public static void main(String[] args) {
        ICarritoDAO carritoDAO = new CarritoDAOImp();
        IUsuarioDAO usuarioDAO = new UsuarioDAOImp();
        IHeladoDAO heladoDAO = new HeladoDAOImp();
        IHelCarDAO helCarDAO = new HelCarDAOImp();
        
        List<Usuario> usuarios = usuarioDAO.getAll(Usuario.class);
        List<Helado> helados = heladoDAO.getAll(Helado.class);
        List<Helado> heladosAux = new ArrayList<>();
        
        heladosAux.add(helados.get(1));
        //Carrito carrito = new Carrito(1, usuarios.get(0), 200,new HashSet<>(heladosAux));
        //carritoDAO.create(carrito);
        Carrito carrito1 = carritoDAO.obtenerCarritoSegunUsuario(usuarios.get(0));
        //carrito1.setHelCars(new HashSet( heladosAux));
        //carritoDAO.update(carrito1);
        HelCar helCar = helCarDAO.obtenerHelCar(carrito1, helados.get(1));
        
        helCar.setId(new HelCarId(helCar.getId().getCarritoCarCodigo(), helados.get(0).getCodigoHelado()));
        helCar.setHelado(helados.get(0));
        helCar.setCantHelado(20);
        
        helCarDAO.create(helCar);
        
        
        //carrito = new Carrito();
        //carrito.setCarCodigo(carrito1.getCarCodigo());
        //carrito.setUsuario(carrito1.getUsuario());
        /**
         for (HelCar helCar: helCarDAO.obtenerHelCARSegunCarrito(carrito1)){
            helCarDAO.eliminarCarrito(helCar);
        }*/
        //carritoDAO.eliminarCarrito(carrito);
        
        
    }
}
