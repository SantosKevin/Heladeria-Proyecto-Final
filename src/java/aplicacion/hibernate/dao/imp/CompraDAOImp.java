/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.hibernate.dao.imp;

import aplicacion.hibernate.configuracion.HibernateUtil;
import aplicacion.hibernate.dao.ICompraDAO;
import aplicacion.modelo.dominio.Compra;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;

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
    
    /**
     * @param idUsuario id del Usuario
     * @return Returna una lista con todas las compras segund IdUsuario
     */
    @Override
    public List<Compra> obtenerComprasSegunIdUsuario(Integer idUsuario) {
        List<Compra> comprasAux = new ArrayList<>();
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Compra.class);
        List<Compra> compras = criteria.list();
        session.close();
        
        //quita valores repetidos segun el id de compra
        for (Compra c: compras){
            boolean bandera = true;
            if (c.getUsuarioCompra().getCodigoUsuario().equals(idUsuario)){
                for(Compra cAux: comprasAux){
                if ( cAux.getCodigoCompra().equals(c.getCodigoCompra())){
                    bandera = false;
                }
            }
            if ( bandera){
                comprasAux.add(c);
            }
            }
        }
        return comprasAux;
    }
    
    /**
     permite obtener el ultimo codigo compra de la tabla Compra en base al Id de
     * Usuario
     * @param idUsuario
     * @return un Integer con el ultimo codigo
     **/
    @Override
    public Integer obtenerUltimoCodigodeCompra(Integer idUsuario) {
        List<Compra> compras = obtenerComprasSegunIdUsuario(idUsuario);
        Integer codigo = 0;
        for (Compra c: compras){
            codigo = c.getCodigoCompra();
            System.out.println(c.getCodigoCompra());
        }
        return codigo;
    }
}
