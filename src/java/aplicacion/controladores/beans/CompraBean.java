/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controladores.beans;

import aplicacion.hibernate.dao.IComHelDAO;
import aplicacion.hibernate.dao.ICompraDAO;
import aplicacion.hibernate.dao.imp.ComHelDAOImp;
import aplicacion.hibernate.dao.imp.CompraDAOImp;
import aplicacion.modelo.dominio.Compra;
import aplicacion.modelo.dominio.Usuario;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author gabri
 */
@ManagedBean
@SessionScoped
public class CompraBean {
    private ICompraDAO compraDAO;
    private IComHelDAO comHelDAO;

    /**
     * Creates a new instance of CompraBean
     */
    public CompraBean() {
        compraDAO = new CompraDAOImp();
        comHelDAO = new ComHelDAOImp();
    }

    public List<Compra> obtenerCompras(){
        Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        if (usuario.getTipoUsuario().equals("normal")){
            return compraDAO.obtenerComprasSegunIdUsuario(usuario.getCodigoUsuario());
        }
        return compraDAO.obtenerCompras();
    }
    
    public String ObtenerEstadoCompra(Integer estado){
        if (estado.equals(0)){
            return "A retirar";
        }
        return "Cancelado";
    }
    
    public int obtenerCantidadComHel(Integer idCompra, Integer idHelado){
        System.out.println(idCompra);
        System.out.println(idHelado);
        System.out.println(comHelDAO.obtenerCantidadComHel(idCompra, idHelado));
        return comHelDAO.obtenerCantidadComHel(idCompra, idHelado);
    }
    
    public ICompraDAO getCompraDAO() {
        return compraDAO;
    }

    public void setCompraDAO(ICompraDAO compraDAO) {
        this.compraDAO = compraDAO;
    }
}
