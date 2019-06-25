/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controladores.beans;

import aplicacion.hibernate.dao.ICarritoDAO;
import aplicacion.hibernate.dao.IHeladoDAO;
import aplicacion.hibernate.dao.imp.CarritoDAOImp;
import aplicacion.hibernate.dao.imp.HeladoDAOImp;
import aplicacion.modelo.dominio.Carrito;
import aplicacion.modelo.dominio.Helado;
import aplicacion.modelo.dominio.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Lucas Choque
 */
@ManagedBean
@SessionScoped
/**
 * Clase que funciona como beans administrado de HeladoFormBean
 */
public class HeladoBean implements Serializable{
    /**
     * Se crea una instancia de tipo heladoDAO
     */
    private IHeladoDAO heladoDAO;
    private ICarritoDAO carritoDAO;
    /**
     * Se inicializa el atributo heladoDAO
     */
    
    private Helado helado;
    private Integer cantidad;
    
    public HeladoBean() {
        heladoDAO = new HeladoDAOImp();
        carritoDAO = new CarritoDAOImp();
        cantidad = 1;
    }
    
    public void agregarAlCarrito(){
        System.out.println(this.cantidad);
        Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        Boolean bandera = true;
        Carrito carrito = new Carrito(1, usuario.getCodigoUsuario(), helado.getCodigoHelado(), cantidad, cantidad * helado.getPrecioOferta());
        if (helado.getPrecioOferta().equals(0.0)){
            carrito.setTotal(cantidad * helado.getPrecio());
        }
 
        for (Carrito c: carritoDAO.obtenerCarritoSegunIdUsuario(usuario.getCodigoUsuario())){
            if (c.getCodigoHelado().equals(carrito.getCodigoHelado())){
                bandera = false;
                c.setCantidad(carrito.getCantidad() + c.getCantidad());
                carritoDAO.update(c);
            }
        }
        if (bandera){
            carritoDAO.create(carrito);
        }
        FacesMessage msg = new FacesMessage("Mensaje", "Agregado al Carrito");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        PrimeFaces.current().executeScript("PF('multiProdDialog').hide();");
    }

    /**
     * Metodo que crea un nuevo helado
     * @param nuevoHelado variable de tipo helado la cual se mapear con la base de datos
     */
    public void crearHelado(Helado nuevoHelado){
        heladoDAO.create(nuevoHelado);
        helado = new Helado();
    }
    
    /**
     * Metodo que elimina un helado
     * @param Helado variable de tipo helado la cual se elimina de la base de datos
     */
    public void eliminarHelado(Helado Helado){
        heladoDAO.delete(Helado);
    }
    
    public void leer(Helado heladoSeleccion){
        System.out.println(heladoSeleccion.getCodigoHelado());
       helado = heladoSeleccion; // copia la referencia 
    }
            
    /**
     * Metodo que modifica algunos atributos un helado
     * @param helado variable de tipo helado la cual se modificara en la base de datos
     */
    public void modificarHelado(Helado helado){
        heladoDAO.update(helado);
    }
    
    
    /**
     * Metodo para obtener todos los helados de la base de datos
     * @return lista de helados
     */
    public List<Helado> obtenerHelados(){
        return heladoDAO.getAll(Helado.class);
    }
    
    /**
     * Metodo para obtener todos los helados de la base de datos que su cantidad de stock no sea igual a 0
     * @return lista de helados
     */
    public List<Helado> obtenerHeladosDisponibles(){
        return heladoDAO.obtenerHeladosDisponibles();
    }
    
    public List<Helado> obtenerHeladosTipo(String tipo){
        return heladoDAO.obtenerHeladosTipo(tipo);
    }
    
    public Helado obtenerHeladoUnico(Integer codigoHelado){
        return heladoDAO.obtenerUnicoHelado(codigoHelado);
    }
    public Helado obtenerHeladoUnicoDisponible(Integer codigoHelado){
        return heladoDAO.obtenerUnicoHeladoDisponible(codigoHelado);
    }
        
    public IHeladoDAO getHeladoDAO() {
        return heladoDAO;
    }

    public void setHeladoDAO(IHeladoDAO heladoDAO) {
        this.heladoDAO = heladoDAO;
    }

    public Helado getHelado() {
        return helado;
    }

    public void setHelado(Helado helado) {
        this.helado = helado;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

   
    
    
}
