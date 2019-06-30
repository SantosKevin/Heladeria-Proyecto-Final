/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controladores.beans;

import aplicacion.hibernate.dao.ICarritoDAO;
import aplicacion.hibernate.dao.IComHelDAO;
import aplicacion.hibernate.dao.ICompraDAO;
import aplicacion.hibernate.dao.IHelCarDAO;
import aplicacion.hibernate.dao.imp.CarritoDAOImp;
import aplicacion.hibernate.dao.imp.ComHelDAOImp;
import aplicacion.hibernate.dao.imp.CompraDAOImp;
import aplicacion.hibernate.dao.imp.HelCarDAOImp;
import aplicacion.modelo.dominio.Compra;
import aplicacion.modelo.dominio.HelCar;
import aplicacion.modelo.dominio.Helado;
import aplicacion.modelo.dominio.Usuario;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
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
    private IHelCarDAO helCarDAO;
    private ICarritoDAO carritoDAO;
    /**
     * Creates a new instance of CompraBean
     */
    public CompraBean() {
        compraDAO = new CompraDAOImp();
        comHelDAO = new ComHelDAOImp();
        helCarDAO = new HelCarDAOImp();
        carritoDAO= new CarritoDAOImp();
    }

    
    public Integer obtenerCantidadHeladoDeHelCar(Helado helado) {    
        Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        HelCar helCar = helCarDAO.obtenerHelCar(carritoDAO.obtenerCarritoSegunUsuario(usuario), helado);
        return helCar.getCantHelado();
    }
    
    public double obtenerPrecioTotalPorHelado(Helado helado) {
        Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        HelCar helCar = helCarDAO.obtenerHelCar(carritoDAO.obtenerCarritoSegunUsuario(usuario), helado);
        if (helado.getPrecioOferta() > 0) {
            return helCar.getCantHelado() * helado.getPrecioOferta();
        } else {
            return helCar.getCantHelado() * helado.getPrecio();
        }
    }
    /**
     * 
     * @return 
     */
    public List<Compra> obtenerCompras(){
        Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        
        if (usuario.getTipoUsuario().equals("normal")){
            return compraDAO.obtenerCompras(usuario);
        }
        return compraDAO.obtenerCompras();
    }
    /**
     * permite cancelar un pedido pasado las 24 hrs
     * @param estado estado de la compra 
     * @param fecha fecha de la compra
     * @param comp objeto compra
     * @return 
     */
    public String ObtenerEstadoCompra(Integer estado, Date fecha, Compra comp){
        if(estado != 1){
        //Creamos una instancia de la fecha del momento en que se corre el codigo
        Date date = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        //Aqui se le da formato a la fecha
        String datefrom = dateFormat.format(fecha);  
        String dateto = dateFormat.format(date); 
        //Casteamos la Fecha del Formato Date a String
        LocalDate from = LocalDate.parse(datefrom);
        LocalDate to = LocalDate.parse(dateto);
        //Obtenemos en un numero Entero el rango de dias que pasaron entre ambas fechas(fecha de la base de datos y la fecha de hoy)        
        long days = ChronoUnit.DAYS.between(from, to);
        //Preguntamos la variable dias es mayor o igual a 1 es que pasaron 2 dias
        if (days>=1){
            estado=3;
            comp.setEstado(estado);
            compraDAO.update(comp);
            return "CANCELADO";
        }
        if (estado.equals(0)){
            return "A retirar";
        }
        if (estado.equals(3))
            return "CANCELADO";
        }
        return "Vendido";  //actualizamos la tabla
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

    public IHelCarDAO getHelCarDAO() {
        return helCarDAO;
    }

    public void setHelCarDAO(IHelCarDAO helCarDAO) {
        this.helCarDAO = helCarDAO;
    }

    public ICarritoDAO getCarritoDAO() {
        return carritoDAO;
    }

    public void setCarritoDAO(ICarritoDAO carritoDAO) {
        this.carritoDAO = carritoDAO;
    }
    
    
}
