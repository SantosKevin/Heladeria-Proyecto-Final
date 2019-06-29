/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.hibernate.dao.imp;

import aplicacion.hibernate.configuracion.HibernateUtil;
import aplicacion.hibernate.dao.ICarritoDAO;
import aplicacion.hibernate.dao.IComHelDAO;
import aplicacion.hibernate.dao.IHelCarDAO;
import aplicacion.modelo.dominio.Carrito;
import aplicacion.modelo.dominio.ComHel;
import aplicacion.modelo.dominio.ComHelId;
import aplicacion.modelo.dominio.Compra;
import aplicacion.modelo.dominio.HelCar;
import aplicacion.modelo.dominio.Helado;
import aplicacion.modelo.dominio.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Samanta
 */
public class ComHelDAOImp extends GenericDAOImp<ComHel, Integer> implements IComHelDAO, Serializable {

    /**
     * actualiza la cantidad de helados que se compraron de la tabla ComHel
     *
     * @param compra tipo Compra
     *
     *
     */
    @Override
    public void transferirCantidadHelCarAComHel(Compra compra, Usuario usuario) {
        IHelCarDAO helCarDAO = new HelCarDAOImp();
        ICarritoDAO carritoDAO = new CarritoDAOImp();
        for (HelCar helCar : helCarDAO.obtenerHelCARSegunCarrito(carritoDAO.obtenerCarritoSegunUsuario(usuario))) {
            ComHel comHel = obtenerComHel(compra, helCar.getHelado());
            comHel.setCantHelado(helCar.getCantHelado());
            update(comHel);
        }
    }

    @Override
    public ComHel obtenerComHel(Compra compra, Helado helado) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(ComHel.class);
        criteria.add(Restrictions.eq("compras", compra));
        criteria.add(Restrictions.eq("helados", helado));
        ComHel comHel = (ComHel) criteria.uniqueResult();
        session.close();
        return comHel;
    }

    /**
     * permite obtener la cantidad de helado que se compro de la tabla ComHel
     * segun el id de Compra y el id de Helado
     *
     * @param idCompra
     * @param idHelado
     * @return la cantidad de helado
     *
     */
    @Override
    public int obtenerCantidadComHel(Integer idCompra, Integer idHelado) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(ComHel.class);
        List<ComHel> comHels = criteria.list();
        session.close();

        for (ComHel ch : comHels) {
            if (ch.getCompras().getCodigoCompra().equals(idCompra) && ch.getHelados().getCodigoHelado().equals(idHelado)) {
                if (ch.getCantHelado() == null) {
                    return 0;
                }
                return ch.getCantHelado();
            }
        }
        return 0;
    }

}
