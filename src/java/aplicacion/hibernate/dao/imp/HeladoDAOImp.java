/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.hibernate.dao.imp;

import aplicacion.hibernate.configuracion.HibernateUtil;
import aplicacion.hibernate.dao.IHeladoDAO;
import aplicacion.modelo.dominio.Helado;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 *
 * @author Lucas Choque
 */
public class HeladoDAOImp extends GenericDAOImp<Helado, Integer> implements IHeladoDAO, Serializable{
    
    /**
     * @return una lista de helado con stock mayor a 0
     **/
    @Override
    public List<Helado> obtenerHeladosDisponibles() {
        List<Helado> heladosAux =  new ArrayList<>();
        for (Helado h: super.getAll(Helado.class)){
            if (!h.getCantidad().equals(0) && h.getEstado()){
                heladosAux.add(h);
            }
        }
        return heladosAux;
    }
    /**
     * @param idHelado
     * @return un helado segun el id del helado
     **/
    @Override
    public Helado obtenerHeladoSegunIdHelado(Integer idHelado) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Helado.class);
        List<Helado> helados = criteria.list();
        session.close();
        
        Helado helado = new Helado();
        for (Helado h: helados){
            if (h.getCodigoHelado().equals(idHelado)){
                helado = h;
            }
        }
        return helado;
    }

    /**
     * Metodo que devuelve una lista de helados de un tipo en especifico, dicho tipo se pasa por parametro
     * Se crea una lista auxiliar y se recorre la base de datos helados, consultando el tipo de helado
     * @param tipo tipo de helado que se desea almacenar en la lista
     * @return una lista de helados de un tipo en especifico
     */
    @Override
    public List<Helado> obtenerHeladosTipo(String tipo) {
        List<Helado> listaHeladosTipo = new ArrayList<>();
        for(Helado h : this.obtenerHeladosDisponibles()){
            if(h.getTipoHelado().equalsIgnoreCase(tipo))
                listaHeladosTipo.add(h);
        }
        return listaHeladosTipo;
    }
    
    /**
     * Metodo que obtiene un helado en singular,
     * Metodo para obtener un solo helado, el helado se obtiene mediante un codigo que es pasado por parametro
     * Se declara un objeto de tipo helado y se recorre la base de datos de helado, consultando por el codigo
     * @param codigoHelado
     * @return 
     */
    @Override
    public Helado obtenerUnicoHelado(Integer codigoHelado) {
        Helado heladoUnico = null;
        for(Helado h: super.getAll(Helado.class)){
            if(h.getEstado()){
                if(h.getCodigoHelado() == codigoHelado)
                heladoUnico = h;
            }
        }
        return heladoUnico;
    }
    /**
     * Metodo que obtiene un helado en particular y ese helado esta disponible
     * recoore la lista de todos los helados disponibles, es decir que tiene
     * su stock mayor que 0 o que tiene su estado en True
     * @param codigoHelado codigo del helado que se desea obtener
     * @return null si el helado no esta disponible, Helado si el helado esta disponible
     */
    @Override
    public Helado obtenerUnicoHeladoDisponible(Integer codigoHelado){
        Helado heladoUnicoDisponible = null;
        for(Helado h: this.obtenerHeladosDisponibles()){
            if(h.getCodigoHelado() == codigoHelado)
                heladoUnicoDisponible = h;
        }
        return heladoUnicoDisponible;
    }
}