/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.hibernate.dao.imp;

import aplicacion.hibernate.configuracion.HibernateUtil;
import aplicacion.hibernate.dao.ICompraDAO;
import aplicacion.modelo.dominio.Carrito;
import aplicacion.modelo.dominio.Compra;
import aplicacion.modelo.dominio.Helado;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 *
 * @author Lucas Choque
 */
public class CompraDAOImp extends GenericDAOImp<Compra, Integer> implements ICompraDAO, Serializable{

    public CompraDAOImp() {
    }
    
    @Override
    public List<Compra> obtenerCompras() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Compra.class);
        List<Compra> compras = criteria.list();
        session.close();
        
        //sirve para quitar valores duplicados
        Set listAux = compras.stream().collect(Collectors.toSet());
        compras = new ArrayList<>(listAux);
        Collections.reverse(compras);
        
        return compras;
    }
}
