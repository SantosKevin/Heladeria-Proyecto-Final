/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.hibernate.dao.imp;

import aplicacion.hibernate.configuracion.HibernateUtil;
import aplicacion.hibernate.dao.ICarritoDAO;
import aplicacion.hibernate.dao.IComHelDAO;
import aplicacion.modelo.dominio.Carrito;
import aplicacion.modelo.dominio.ComHel;
import aplicacion.modelo.dominio.ComHelId;
import aplicacion.modelo.dominio.Compra;
import aplicacion.modelo.dominio.Helado;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 *
 * @author Samanta
 */
public class ComHelDAOImp extends GenericDAOImp<ComHel, Integer> implements IComHelDAO, Serializable {

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
    
    @Override
    public void actualizarCantidadHeladoComHel(Compra compra, Integer idUsuario) {
        ICarritoDAO carritoDAO = new CarritoDAOImp();
        IComHelDAO comHelDAO = new ComHelDAOImp();
        for (Helado h : compra.getHeladosCompra()) {
            for (Carrito c : carritoDAO.obtenerCarritoSegunIdUsuario(idUsuario)) {
                if (h.getCodigoHelado().equals(c.getCodigoHelado())) {
                    ComHel comHel = new ComHel();
                    comHel.setId(new ComHelId(compra.getCodigoCompra(), h.getCodigoHelado()));
                    comHel.setCompras(compra);
                    comHel.setHelados(h);
                    comHel.setCantHelado(c.getCantidad());
                    comHelDAO.update(comHel);
                }
            }
        }
        
    }
}
