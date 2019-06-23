/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controladores.beans;

import aplicacion.hibernate.dao.ICarritoDAO;
import aplicacion.hibernate.dao.IComHelDAO;
import aplicacion.hibernate.dao.ICompraDAO;
import aplicacion.hibernate.dao.IHeladoDAO;
import aplicacion.hibernate.dao.imp.CarritoDAOImp;
import aplicacion.hibernate.dao.imp.ComHelDAOImp;
import aplicacion.hibernate.dao.imp.CompraDAOImp;
import aplicacion.hibernate.dao.imp.HeladoDAOImp;
import aplicacion.modelo.dominio.Carrito;
import aplicacion.modelo.dominio.ComHel;
import aplicacion.modelo.dominio.ComHelId;
import aplicacion.modelo.dominio.Compra;
import aplicacion.modelo.dominio.Helado;
import aplicacion.modelo.dominio.Usuario;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author samanta
 */
@ManagedBean
@SessionScoped
public class CarritoBean implements Serializable {

    private ICarritoDAO carritoDAO;
    private IHeladoDAO heladoDAO;
    private ICompraDAO compraDAO;
    private IComHelDAO comHelDAO;
    private Integer cantidad;
    private Carrito carrito;

    /**
     * Creates a new instance of CarritoBean
     */
    public CarritoBean() {
        carritoDAO = new CarritoDAOImp();
        heladoDAO = new HeladoDAOImp();
        compraDAO = new CompraDAOImp();
        comHelDAO = new ComHelDAOImp();
        carrito = new Carrito();
        cantidad = 1;
    }

    public List<Carrito> obtenerCarrito() {
        return carritoDAO.getAll(Carrito.class);
    }

    public List<Carrito> obtenerCarritoSegunIdUsuario() {
        Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return carritoDAO.obtenerCarritoSegunIdUsuario(usuario.getCodigoUsuario());
    }

    public Helado obtenerHelado(Integer idHelado) {
        return heladoDAO.obtenerHeladoSegunIdHelado(idHelado);
    }

    public void leer(Carrito carritoSeleccion) {
        carrito = carritoSeleccion; // copia la referencia 
    }

    public void modificarCarrito() {
        carritoDAO.update(carrito);
        FacesMessage msg = new FacesMessage("Exito", "Modificacion del carrito exitosa");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        PrimeFaces.current().executeScript("PF('dlgModificar').hide();");
    }

    public void eliminarCarrito() {
        carritoDAO.eliminarCarrito(carrito);
        FacesMessage msg = new FacesMessage("Exito", "Eliminacion del carrito exitosa");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        PrimeFaces.current().executeScript("PF('dlgEliminar').hide();");
    }

    public void agregarCompra() {
        Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        List<Helado> listaHelado = carritoDAO.obtenerHeladoSegunIdUsuario(usuario.getCodigoUsuario());
        System.out.println(listaHelado.size());
        if (listaHelado.isEmpty()) {
            FacesMessage msg = new FacesMessage("Mensaje", "No puede confirmar la compra si el carrito esta vacio");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            Compra compra = new Compra();

            compra.setUsuarioCompra(usuario);
            compra.setHeladosCompra(new HashSet<>(listaHelado));
            compra.setFecha_compra(new Date());
            compra.setEstado(0);

            compraDAO.create(compra);
            comHelDAO.actualizarCantidadHeladoComHel(compra, usuario.getCodigoUsuario());
            carritoDAO.eliminarCarrito(usuario.getCodigoUsuario());
            FacesMessage msg = new FacesMessage("Mensaje", "Compra Finalizada exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        PrimeFaces.current().executeScript("PF('dlgPregunta').hide();");
    }

    public ICarritoDAO getCarritoDAO() {
        return carritoDAO;
    }

    public void setCarritoDAO(ICarritoDAO carritoDAO) {
        this.carritoDAO = carritoDAO;
    }

    public IHeladoDAO getHeladoDAO() {
        return heladoDAO;
    }

    public void setHeladoDAO(IHeladoDAO heladoDAO) {
        this.heladoDAO = heladoDAO;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public ICompraDAO getCompraDAO() {
        return compraDAO;
    }

    public void setCompraDAO(ICompraDAO compraDAO) {
        this.compraDAO = compraDAO;
    }

    public IComHelDAO getComHelDAO() {
        return comHelDAO;
    }

    public void setComHelDAO(IComHelDAO comHelDAO) {
        this.comHelDAO = comHelDAO;
    }

}
