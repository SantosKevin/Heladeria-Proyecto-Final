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

    /**
     * Creates a new instance of CompraBean
     */
    public CompraBean() {
        compraDAO = new CompraDAOImp();
        comHelDAO = new ComHelDAOImp();
    }

    
    public int obtenerCantidadComHel(Integer idCompra, Integer idHelado){
        System.out.println(idCompra);
        System.out.println(idHelado);
        System.out.println(comHelDAO.obtenerCantidadComHel(idCompra, idHelado));
        return comHelDAO.obtenerCantidadComHel(idCompra, idHelado);
    }
    
    public List<Compra> obtenerCompras(){
        Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        if (usuario.getTipoUsuario().equals("normal")){
            return compraDAO.obtenerComprasSegunIdUsuario(usuario.getCodigoUsuario());
        }
        return compraDAO.obtenerCompras();
    }
    
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
        }
        return "Vendido";  //actualizamos la tabla
    }
    
    
    public ICompraDAO getCompraDAO() {
        return compraDAO;
    }

    public void setCompraDAO(ICompraDAO compraDAO) {
        this.compraDAO = compraDAO;
    }
}
