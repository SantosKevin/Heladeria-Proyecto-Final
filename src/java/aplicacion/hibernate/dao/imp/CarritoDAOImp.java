/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.hibernate.dao.imp;

import aplicacion.hibernate.configuracion.HibernateUtil;
import aplicacion.hibernate.dao.ICarritoDAO;
import aplicacion.hibernate.dao.IHelCarDAO;
import aplicacion.hibernate.dao.IHeladoDAO;
import aplicacion.modelo.dominio.Carrito;
import aplicacion.modelo.dominio.HelCar;
import aplicacion.modelo.dominio.Helado;
import aplicacion.modelo.dominio.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.mapping.Collection;

/**
 *
 * @author samanta
 */
public class CarritoDAOImp extends GenericDAOImp<Carrito, Integer> implements ICarritoDAO, Serializable {

    public CarritoDAOImp() {
    }

    @Override
    public List<Helado> actualizarListaCarrito(Usuario usuario, Helado helado) {
        Boolean bandera = true;
        List<Helado> helados = new ArrayList<>(obtenerCarritoSegunUsuario(usuario).getHelCars());
        for (Helado h : helados) {
            if (h.getCodigoHelado().equals(helado.getCodigoHelado())) {
                bandera = false;
            }
        }
        if (bandera) {
            helados.add(helado);
        }
        return helados;
    }

    @Override
    public void calcularTotalListaHeladoCarrito(Carrito carrito) {
        double total = 0;
        IHelCarDAO iHelCarDAO = new HelCarDAOImp();
        List<HelCar> helCars = new ArrayList<>(iHelCarDAO.obtenerHelCARSegunCarrito(carrito));
        List<Helado> helados = new ArrayList<>(carrito.getHelCars());
        for (Helado h : helados) {
            for (HelCar helCar : helCars) {
                if (helCar.getHelado().getCodigoHelado().equals(h.getCodigoHelado())) {
                    if (h.getPrecioOferta() > 0) {
                        total = total + (helCar.getCantHelado() * h.getPrecioOferta());
                    }else{
                        total = total + (helCar.getCantHelado() * h.getPrecio());
                    }
                }
            }
        }
        carrito.setCarTotal(total);
        update(carrito);
    }

    /**
     * permite elimnar valor de la tabla carrito
     *
     * @param carrito objeto carrito
     *
     */
    
    @Override
    public void eliminarCarrito(Carrito carrito) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        sesion.delete(carrito);
        sesion.getTransaction().commit();
        sesion.close();
    }
    
    @Override
    public Carrito obtenerCarritoSegunUsuario(Usuario usuario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Carrito.class);
        criteria.add(Restrictions.eq("usuario", usuario));
        Carrito carrito = (Carrito) criteria.uniqueResult();
        session.close();
        return carrito;
    }

    /**
     * permite obtener una lista de helados con la cantidad de stock modificada
     * ( segun la cantidad los helados que se compraron) y lista para guardarse
     * en la tabla helado,
     *
     * @param idUsuario
     * @return lista de helados segun el id de Usuario
     *
     */
    @Override
    public List<Helado> obtenerHeladoSegunIdUsuario(Integer idUsuario) {
        List<Helado> HeladoAux = new ArrayList<>();
        HeladoDAOImp heladoDAOImp = new HeladoDAOImp();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Carrito.class);
        List<Carrito> carritos = criteria.list();
        session.close();

        /*for (Carrito c: carritos){
            if (c.getCodigoUsuario().equals(idUsuario)){
                Helado helado = heladoDAOImp.obtenerHeladoSegunIdHelado(c.getCodigoHelado());
                helado.setCantidad(helado.getCantidad() - c.getCantidad());
                System.out.println(helado.toString());
                HeladoAux.add(helado);
            }
        }*/
        return HeladoAux;
    }

    /**
     * calcula el total del carrito
     *
     * @param carritos
     * @return el total     
     *
     */
    @Override
    public double obtenerPrecioTotalCarrito(List<Carrito> carritos) {
        IHeladoDAO heladoDAO = new HeladoDAOImp();

        double total = 0;
        /*for (Carrito c: carritos){
            Helado helado = heladoDAO.obtenerHeladoSegunIdHelado(c.getCodigoHelado());
            if (helado.getPrecioOferta().equals(0.0)){
                total = total + c.getTotal();
            }else{
                total = total + helado.getPrecioOferta() * c.getCantidad();
            }
        }*/
        return total;
    }

    @Override
    public List<Helado> obtenerHeladoSegunIdUsuarioConCantidadCarrito(Integer idUsuario) {
        List<Helado> HeladoAux = new ArrayList<>();
        HeladoDAOImp heladoDAOImp = new HeladoDAOImp();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Carrito.class);
        List<Carrito> carritos = criteria.list();
        session.close();

        /*for (Carrito c: carritos){
            if (c.getCodigoUsuario().equals(idUsuario)){
                Helado helado = heladoDAOImp.obtenerHeladoSegunIdHelado(c.getCodigoHelado());
                helado.setCantidad(c.getCantidad());
                HeladoAux.add(helado);
            }
        }*/
        return HeladoAux;
    }

}
