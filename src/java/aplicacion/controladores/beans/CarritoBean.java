/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controladores.beans;

import aplicacion.hibernate.dao.ICarritoDAO;
import aplicacion.hibernate.dao.imp.CarritoDAOImp;
import aplicacion.modelo.dominio.Carrito;
import aplicacion.modelo.dominio.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author samanta
 */
@ManagedBean
@SessionScoped
public class CarritoBean implements Serializable{

    private ICarritoDAO carritoDAO;
    /**
     * Creates a new instance of CarritoBean
     */
    public CarritoBean() {
        carritoDAO = new CarritoDAOImp();
    }
    
    public List<Carrito> obtenerCarrito(){
        return carritoDAO.getAll(Carrito.class);
    }
    
    public List<Carrito> obtenerCarritoSegunIdUsuario(){
        Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        System.out.println(usuario.getCodigoUsuario());
        return carritoDAO.obtenerCarritoSegunIdUsuario(usuario.getCodigoUsuario());
    }
}
