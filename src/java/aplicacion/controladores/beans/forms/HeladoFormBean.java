/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controladores.beans.forms;

import aplicacion.controladores.beans.HeladoBean;
import aplicacion.modelo.dominio.Helado;
import aplicacion.modelo.util.SaboresYTipos;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Lucas Choque
 */
@ManagedBean
@SessionScoped
/**
 * Clase que funciona como Managed Bean
 */
public class HeladoFormBean implements Serializable {
    /**
     * Se injecta el bean administrado de la clase heladoBean
     * Se instancia un objeto de tipo helado para almacenar los valores que se ingresen desde la vista
     * Se instancia una lista de helados para mostrar los helados en la mista
     */
    @ManagedProperty(value = "#{heladoBean}")
    private HeladoBean heladoBean;
    private Helado helado;
    private List<Helado> listaHelados;
    private SaboresYTipos saboresYTipos;
    
    private Integer codigoHelado;
    private Integer stock;
    /**
     * Creates a new instance of HeladoFormBean
     */
    public HeladoFormBean() {
    }
    /**
     * Metodo que inicializa la variable helado y llama a un metodo para cargar la lista de helados
     */
    @PostConstruct
    public void init() {
        helado = new Helado();
        saboresYTipos = new SaboresYTipos();
        generarHelados();
    }
    /**
     * Metodo que le asigna al atributo listaHelados, todos los helados almacenados en la base de datos
     */
    public void generarHelados() {
        listaHelados = heladoBean.obtenerHelados();
    }
    /**
     * Metodo que crea un helado
     */
    public void crearHelado() {
        helado.setPrecioOferta(0.0);
        helado.setEstado(Boolean.TRUE);
        heladoBean.crearHelado(helado);
        helado = new Helado();
        generarHelados();
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "El Helado se ha agregado"));
    }
    /**
     * Metodo que elimina un helado
     * @param heladoBorrar variable de tipo helado que se desea eliminar
     */
    public void eliminarHelado(Helado heladoBorrar) {
        if (heladoBorrar.getEstado()) {
            heladoBorrar.setEstado(Boolean.FALSE);
            heladoBean.eliminarHelado(heladoBorrar);
            generarHelados();
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Exito", "El Helado se ha eliminado"));
        }
        else
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El Helado ya se encuentra eliminado"));

    }
    /**
     * Metodo para modificar un helado
     * @param event variable de tipo RowEditEvent, que alamcena un objecto que se debe castear a Helado
     */
    public void onRowEdit(RowEditEvent event) {
        Helado heladoModificado = (Helado) event.getObject();
        heladoBean.modificarHelado(heladoModificado);
        generarHelados();
        FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "El Helado se ha modificado"));
    }
    /**
     * Metodo que muestra un mensaje al momento de cancelar la modificiacion
     * @param event 
     */
    public void onRowEditCancel (RowEditEvent event) {
        generarHelados();
        FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cancelado", "La modificacion se ha cancelado"));
    }
    /**
     * Este metodo modifica el stock actual
     * mediante un parametro se determina si el usuario desea agregar o quitar el stock
     * @param parametro 
     */
    public void modificarStock(int parametro){
        Helado helado = heladoBean.obtenerHeladoUnico(codigoHelado);
        if(parametro == 1){
            helado.setCantidad(helado.getCantidad() + this.stock);
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "El Stock se ha agregado"));
        }else{
            if(helado.getCantidad() >= this.stock){
                FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "El Stock se ha decrementado"));
                helado.setCantidad(helado.getCantidad() - this.stock);
            }
            else
               FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Stock que desea quitar es mayor que el stock actual")); 
        }
        heladoBean.modificarHelado(helado);
        this.stock=0;
        generarHelados();
    }
    public HeladoBean getHeladoBean() {
        return heladoBean;
    }

    public void setHeladoBean(HeladoBean heladoBean) {
        this.heladoBean = heladoBean;
    }

    public Helado getHelado() {
        return helado;
    }

    public void setHelado(Helado helado) {
        this.helado = helado;
    }

    public List<Helado> getListaHelados() {
        return listaHelados;
    }

    public void setListaHelados(List<Helado> listaHelados) {
        this.listaHelados = listaHelados;
    }

    public SaboresYTipos getSaboresYTipos() {
        return saboresYTipos;
    }

    public void setSaboresYTipos(SaboresYTipos saboresYTipos) {
        this.saboresYTipos = saboresYTipos;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getCodigoHelado() {
        return codigoHelado;
    }

    public void setCodigoHelado(Integer codigoHelado) {
        this.codigoHelado = codigoHelado;
    }
}