/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controladores.beans;

import aplicacion.hibernate.dao.ICompraDAO;
import aplicacion.hibernate.dao.imp.CompraDAOImp;
import aplicacion.modelo.dominio.Compra;
import aplicacion.modelo.dominio.Helado;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author gabri
 */
@ManagedBean
@SessionScoped
public class CompraBean {
    private ICompraDAO compraDAO;

    /**
     * Creates a new instance of CompraBean
     */
    public CompraBean() {
        compraDAO = new CompraDAOImp();
    }

    public List<Compra> obtenerCompras(){
        return compraDAO.obtenerCompras();
    }
    
    public ICompraDAO getCompraDAO() {
        return compraDAO;
    }

    public void setCompraDAO(ICompraDAO compraDAO) {
        this.compraDAO = compraDAO;
    }
    
    
}
