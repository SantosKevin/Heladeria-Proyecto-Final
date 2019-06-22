/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.hibernate.dao.imp;

import aplicacion.hibernate.configuracion.HibernateUtil;
import aplicacion.hibernate.dao.ICarritoDAO;
import aplicacion.modelo.dominio.Carrito;
import aplicacion.modelo.dominio.Helado;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 *
 * @author samanta
 */
public class CarritoDAOImp extends GenericDAOImp<Carrito, Integer> implements ICarritoDAO, Serializable{

    public CarritoDAOImp() {
    }
    
    
    @Override
    public List<Carrito> obtenerCarritoSegunIdUsuario(Integer idUsuario) {
        List<Carrito> carritosAux = new ArrayList<>();
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Carrito.class);
        List<Carrito> carritos = criteria.list();
        session.close();
        
        for (Carrito c: carritos){
            if (c.getCodigoUsuario().equals(idUsuario)){
                carritosAux.add(c);
            }
        }
        return carritosAux;
    }
    
    @Override
    public List<Helado> obtenerHeladoSegunIdUsuario(Integer idUsuario) {
        List<Helado> HeladoAux = new ArrayList<>();
        HeladoDAOImp heladoDAOImp = new HeladoDAOImp();
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Carrito.class);
        List<Carrito> carritos = criteria.list();
        session.close();
        
        for (Carrito c: carritos){
            if (c.getCodigoUsuario().equals(idUsuario)){
                Helado helado = heladoDAOImp.obtenerHeladoSegunIdHelado(c.getCodigoHelado());
                helado.setCantidad(helado.getCantidad() - c.getCantidad());
                System.out.println(helado.toString());
                HeladoAux.add(helado);
            }
        }
        return HeladoAux;
    }
    
    @Override
    public void eliminarCarrito(Carrito carrito) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        sesion.delete(carrito);
        sesion.getTransaction().commit();
        sesion.close();
    }
    
    @Override
    public void eliminarCarrito(Integer idUsuario) {
        for (Carrito c: obtenerCarritoSegunIdUsuario(idUsuario)){
            eliminarCarrito(c);
        }
    }
}
