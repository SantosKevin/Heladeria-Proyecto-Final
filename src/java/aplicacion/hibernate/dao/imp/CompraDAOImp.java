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
import aplicacion.modelo.dominio.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Lucas Choque
 */
public class CompraDAOImp extends GenericDAOImp<Compra, Integer> implements ICompraDAO, Serializable{

    public CompraDAOImp() {
    }
    /**
     * @return Returna una lista con todas las compras
     */
    @Override
    public List<Compra> obtenerCompras() {
        List<Compra> comprasAux = new ArrayList<>();
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Compra.class);
        List<Compra> compras = criteria.list();
        session.close();
        
        //quita valores repetidos segun el id de compra
        for (Compra c: compras){
            boolean bandera = true;
            for(Compra cAux: comprasAux){
                if ( cAux.getCodigoCompra().equals(c.getCodigoCompra())){
                    bandera = false;
                }
            }
            if ( bandera){
                comprasAux.add(c);
            }
        }
        return comprasAux;
    }
    @Override
    public List<Compra> obtenerCompra(Integer codigo) {
        List<Compra> comprasAux = new ArrayList<>();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Compra.class);
        List<Compra> compras = criteria.list();
        session.close();

        
        //quita valores repetidos segun el id de compra
        for (Compra c : compras) {
            boolean bandera = true;
            if (c.getCodigoCompra().equals(codigo)) {
                for (Compra cAux : comprasAux) {
                    if (cAux.getCodigoCompra().equals(c.getCodigoCompra())) {
                        bandera = false;
                    }
                }
                if (bandera) {
                    comprasAux.add(c);
                }
            }
        }
        return comprasAux;
    }
    /**
     * @param idUsuario id del Usuario
     * @return Returna una lista con todas las compras segund IdUsuario
     */
    @Override
    public Compra obtenerUltimaCompra(Usuario usuario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Compra.class);
        criteria.add(Restrictions.eq("codigoCompra", obtenerUltimoCodigodeCompra(usuario)));
        Compra compra = (Compra) criteria.uniqueResult();
        session.close();
        return compra;
    }
    
    @Override
    public List<Compra> obtenerCompras(Usuario usuario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Compra.class);
        criteria.add(Restrictions.eq("usuarioCompra", usuario));
        List<Compra> compras = criteria.list();
        session.close();
        return compras;
    }
    /**
     permite obtener el ultimo codigo compra de la tabla Compra en base al Id de
     * Usuario
     * @param idUsuario
     * @return un Integer con el ultimo codigo
     **/
    @Override
    public Integer obtenerUltimoCodigodeCompra(Usuario usuario) {
        List<Compra> compras = obtenerCompras(usuario);
        Integer codigo = 0;
        for (Compra c: compras){
            codigo = c.getCodigoCompra();
        }
        return codigo;
    }
    
    @Override
    public void eliminarCompra(Compra compra) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();
        sesion.delete(compra);
        sesion.getTransaction().commit();
        sesion.close();
    }
}
