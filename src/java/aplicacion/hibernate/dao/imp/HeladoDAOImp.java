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
import aplicacion.modelo.dominio.HelCar;
import aplicacion.modelo.dominio.Helado;
import aplicacion.modelo.dominio.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Lucas Choque
 */
public class HeladoDAOImp extends GenericDAOImp<Helado, Integer> implements IHeladoDAO, Serializable {

    @Override
    public void actualizarLaCantidadDeHelados() {
        Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        IHelCarDAO helCarDAO = new HelCarDAOImp();
        ICarritoDAO carritoDAO = new CarritoDAOImp();
        IHeladoDAO heladoDAO = new HeladoDAOImp();
        
        for (HelCar helCar : helCarDAO.obtenerHelCARSegunCarrito(carritoDAO.obtenerCarritoSegunUsuario(usuario))) {
            Helado helado = heladoDAO.obtenerHeladoSegunIdHelado(helCar.getHelado().getCodigoHelado());
            helado.setCantidad(helado.getCantidad() - helCar.getCantHelado());
            update(helado);
        }
    }

    /**
     * @return una lista de helado con stock mayor a 0
     *
     */
    @Override
    public List<Helado> obtenerHeladosDisponibles() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Helado.class);
        criteria.add(Restrictions.gt("cantidad", 0));//gt es mayor a "0"
        criteria.add(Restrictions.eq("estado", true));//es igual a "true"
        List<Helado> helados = criteria.list();
        session.close();
        return helados;
    }

    /**
     * @param idHelado
     * @return un helado segun el id del helado
     *
     */
    @Override
    public Helado obtenerHeladoSegunIdHelado(Integer idHelado) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Helado.class);
        List<Helado> helados = criteria.list();
        session.close();

        Helado helado = new Helado();
        for (Helado h : helados) {
            if (h.getCodigoHelado().equals(idHelado)) {
                helado = h;
            }
        }
        return helado;
    }

    /**
     * Metodo que devuelve una lista de helados de un tipo en especifico, dicho
     * tipo se pasa por parametro Se crea una lista auxiliar y se recorre la
     * base de datos helados, consultando el tipo de helado
     *
     * @param tipo tipo de helado que se desea almacenar en la lista
     * @return una lista de helados de un tipo en especifico
     */
    @Override
    public List<Helado> obtenerHeladosTipo(String tipo) {
        List<Helado> listaHeladosTipo = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Helado.class);
        criteria.add(Restrictions.gt("cantidad", 0));//gt es mayor "0"
        criteria.add(Restrictions.eq("estado", true));//es igual a "true"
        criteria.add(Restrictions.like("tipoHelado", tipo));
        if(!criteria.list().isEmpty())
            listaHeladosTipo = criteria.list();
        session.close();
        return listaHeladosTipo;
    }

    /**
     * Metodo que obtiene un helado en singular, Metodo para obtener un solo
     * helado, el helado se obtiene mediante un codigo que es pasado por
     * parametro Se declara un objeto de tipo helado y se recorre la base de
     * datos de helado, consultando por el codigo sino no puedo modificar el stock, se usa para el stock
     *
     * @param codigoHelado
     * @return
     */
    @Override
    public Helado obtenerUnicoHelado(Integer codigoHelado) {
        Helado heladoUnico = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Helado.class);
        criteria.add(Restrictions.like("codigoHelado", codigoHelado));
        criteria.add(Restrictions.eq("estado", true));
        if(!criteria.list().isEmpty())
            heladoUnico = (Helado)criteria.list().get(0);
        session.close();
        return heladoUnico;
    }

    /**
     * Metodo que obtiene un helado en particular y ese helado esta disponible
     * recoore la lista de todos los helados disponibles, es decir que tiene su
     * stock mayor que 0 o que tiene su estado en True
     *
     * @param codigoHelado codigo del helado que se desea obtener
     * @return null si el helado no esta disponible, Helado si el helado esta
     * disponible
     */
    @Override
    public Helado obtenerUnicoHeladoDisponible(Integer codigoHelado) {
        Helado heladoUnicoDisponible = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Helado.class);
        criteria.add(Restrictions.gt("cantidad", 0));//gt es mayor a "0"
        criteria.add(Restrictions.eq("estado", true));//es igual a "true"
        criteria.add(Restrictions.like("codigoHelado", codigoHelado));
        if(!criteria.list().isEmpty())
            heladoUnicoDisponible = (Helado)criteria.list().get(0);
        session.close();
        return heladoUnicoDisponible;
    }
}