/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controladores.beans;

import aplicacion.hibernate.dao.ICarritoDAO;
import aplicacion.hibernate.dao.IHeladoDAO;
import aplicacion.hibernate.dao.imp.CarritoDAOImp;
import aplicacion.hibernate.dao.imp.HelCarDAOImp;
import aplicacion.hibernate.dao.imp.HeladoDAOImp;
import aplicacion.modelo.dominio.Carrito;
import aplicacion.modelo.dominio.HelCar;
import aplicacion.modelo.dominio.HelCarId;
import aplicacion.modelo.dominio.Helado;
import aplicacion.modelo.dominio.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import aplicacion.hibernate.dao.IHelCarDAO;
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
public class HeladoBean implements Serializable {

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

    /**
     permite agregar un carrito
     */
    
    public void agregarAlCarrito() {
        Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        List<Helado> helados = new ArrayList<>();
        IHelCarDAO helCarDAO = new HelCarDAOImp();
        
        double total = helado.getPrecio() * cantidad;
        if (helado.getPrecioOferta() > 0) {
            total = helado.getPrecioOferta() * cantidad;
        }
        
        if (carritoDAO.obtenerCarritoSegunUsuario(usuario) == null) {
            //creacion de un carrito con la agregacion de su cantidad
            helados.add(helado);
            Carrito carrito = new Carrito(1, usuario, total, new HashSet(helados)); //HashSet transforma un Set a arraylist
            carritoDAO.create(carrito);
            //al crear un carrito se guarda la cantidad con el valor null
            HelCar helCar = new HelCar(new HelCarId(carrito.getCarCodigo(), helado.getCodigoHelado()), carrito, helado, cantidad);
            helCarDAO.update(helCar);
        } else {
            //actualizar el contenido de un carrito con su cantidad
            Carrito carrito = carritoDAO.obtenerCarritoSegunUsuario(usuario);
            List<HelCar> helCars = helCarDAO.obtenerHelCARSegunCarrito(carrito);//trae una lista de HelCars
            
            HelCar helCar = helCarDAO.obtenerHelCar(carrito, helado); //trae un objeto especifico de helcar
            if (helCar != null) {
                //actualiza el elemento de tu helcar
                helCar.setCantHelado(helCar.getCantHelado() + cantidad);
                helCarDAO.update(helCar);
            } else {
                //crea o agrega un elemento a la tabla helcar
                helCar = helCars.get(0); //hace una copia de la primera lista de helcars
                // actualizamos los valores del objeto helcar 
                helCar.setId(new HelCarId(helCar.getId().getCarritoCarCodigo(), helado.getCodigoHelado()));
                helCar.setHelado(helado);
                helCar.setCantHelado(cantidad);
                helCarDAO.create(helCar);
            }
            carritoDAO.calcularTotalListaHeladoCarrito(carritoDAO.obtenerCarritoSegunUsuario(usuario));
        }
        FacesMessage msg = new FacesMessage("Mensaje", "Agregado al Carrito");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
        PrimeFaces.current().executeScript("PF('multiProdDialog').hide()");
        
    }

    /**
     * Metodo que crea un nuevo helado
     *
     * @param nuevoHelado variable de tipo helado la cual se mapear con la base
     * de datos
     */
    public void crearHelado(Helado nuevoHelado) {
        heladoDAO.create(nuevoHelado);
        helado = new Helado();
    }

    /**
     * Metodo que elimina un helado
     *
     * @param Helado variable de tipo helado la cual se elimina de la base de
     * datos
     */
    public void eliminarHelado(Helado Helado) {
        heladoDAO.delete(Helado);
    }

    public void leer(Helado heladoSeleccion) {
        helado = heladoSeleccion; // copia la referencia 
    }

    /**
     * Metodo que modifica algunos atributos un helado
     *
     * @param helado variable de tipo helado la cual se modificara en la base de
     * datos
     */
    public void modificarHelado(Helado helado) {
        heladoDAO.update(helado);
    }

    /**
     * Metodo para obtener todos los helados de la base de datos
     *
     * @return lista de helados
     */
    public List<Helado> obtenerHelados() {
        return heladoDAO.getAll(Helado.class);
    }

    /**
     * Metodo para obtener todos los helados de la base de datos que su cantidad
     * de stock no sea igual a 0
     *
     * @return lista de helados
     */
    public List<Helado> obtenerHeladosDisponibles() {
        return heladoDAO.obtenerHeladosDisponibles();
    }
    public List<Helado> obtenerHeladosTipo(String tipo) {
        return heladoDAO.obtenerHeladosTipo(tipo);
    }
    public Helado obtenerHeladoUnico(Integer codigoHelado) {
        return heladoDAO.obtenerUnicoHelado(codigoHelado);
    }
    public Helado obtenerHeladoUnicoDisponible(Integer codigoHelado) {
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