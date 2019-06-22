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
    
    @Override
    public List<Helado> obtenerHeladosDisponibles() {
        List<Helado> heladosAux =  new ArrayList<>();
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Helado.class);
        List<Helado> helados = criteria.list();
        session.close();
        
        Helado helado = new Helado();
        for (Helado h: helados){
            if (!h.getCantidad().equals(0)){
                heladosAux.add(h);
            }
        }
        return heladosAux;
    }
}
