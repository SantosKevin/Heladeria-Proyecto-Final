/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.hibernate.dao.imp;

import aplicacion.hibernate.configuracion.HibernateUtil;
import aplicacion.modelo.dominio.Carrito;
import aplicacion.modelo.dominio.HelCar;
import aplicacion.modelo.dominio.Helado;
import aplicacion.modelo.dominio.Usuario;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import aplicacion.hibernate.dao.IHelCarDAO;


/**
 *
 * @author gabri
 */
public class HelCarDAOImp extends GenericDAOImp<HelCar, Integer> implements IHelCarDAO, Serializable {

    public HelCarDAOImp() {
    }

    @Override
    public void actualizarCantidadHelCar(Helado helado, Integer cantidad, List<HelCar> helCars) {
        for (HelCar helCar : helCars) {
            if (helCar.getHelado().getCodigoHelado().equals(helado.getCodigoHelado())) {
                helCar.setCantHelado(cantidad + helCar.getCantHelado());
            }
            update(helCar);
        }
    }

    //explicar
    @Override
    public void completarCantidad(List<HelCar> listAntes, List<HelCar> listDespues) {
        System.out.println(listDespues.contains(null));
        for (int i = 0; i < listDespues.size(); i++) {
            if (listDespues.get(i).getCantHelado() == null) {
                for (int j = 0; j < listAntes.size(); j++) {
                    if (listDespues.get(i).getHelado().getCodigoHelado().equals(listAntes.get(j).getHelado().getCodigoHelado())){
                        listDespues.set(i, listAntes.get(j));
                    }
                }
            } else if (listAntes.size() == listDespues.size()) {
                listDespues.get(i).setCantHelado(listDespues.get(i).getCantHelado() + listAntes.get(i).getCantHelado());
            }
            update(listDespues.get(i));
        }
    }
    
    @Override
    public HelCar obtenerHelCar(Carrito carrito, Helado helado) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(HelCar.class);
        criteria.add(Restrictions.eq("carrito", carrito));
        criteria.add(Restrictions.eq("helado", helado));
        HelCar helCar = (HelCar) criteria.uniqueResult();
        session.close();
        return helCar;
    }
    
    @Override
    public List<HelCar> obtenerHelCARSegunCarrito(Carrito carrito) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(HelCar.class);
        criteria.add(Restrictions.eq("id.carritoCarCodigo", carrito.getCarCodigo()));
        List<HelCar> helCars = criteria.list();
        session.close();
        return  helCars;
    }
    
    @Override
    public void eliminarCarrito(HelCar helCar) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        sesion.delete(helCar);
        sesion.getTransaction().commit();
        sesion.close();
    }
}
