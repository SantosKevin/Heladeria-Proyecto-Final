/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controladores.beans.forms;

import aplicacion.controladores.beans.HeladoBean;
import aplicacion.controladores.beans.OfertaBean;
import aplicacion.controladores.validaciones.validacionesOferta;
import aplicacion.modelo.dominio.Helado;
import aplicacion.modelo.dominio.Oferta;
import aplicacion.modelo.util.SaboresYTipos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Lucas Choque
 * 
 * Clase managed bean de tipo Oferta
 */
@ManagedBean
@SessionScoped
public class OfertaFormBean implements Serializable{
    /**
     * Se injectan 2 beans administrado. 1 de tipo ofertaBean y otro de tipo heladoBean
     * Se instancia atributos de tipo oferta, helado y saboresYtipos
     * Se instancia listas de tipo oferta y helado
     */
    
    @ManagedProperty(value = "#{ofertaBean}")
    private OfertaBean ofertaBean;
    
    @ManagedProperty(value = "#{heladoBean}")
    private HeladoBean heladoBean;
    
    private Oferta oferta;
    private List<Oferta> listaOfertas;
    private List<Oferta> listaOfertasActuales;
    private Helado helado;
    private List<Helado> listaHelados;
    private SaboresYTipos saboresYTipos;
    

    /**
     * Creates a new instance of OfertaFormBean
     */
    public OfertaFormBean() {
    }
    /**
     * Se inicializan los atributos
     */
    @PostConstruct
    public void init(){
        oferta = new Oferta();
        helado = new Helado();
        saboresYTipos = new SaboresYTipos();
        generarOfertas();
        generarOfertasActuales();
        listaHelados = new ArrayList<>();
    }
    /**
     * Metodo que genera todas las ofertas 
     */
    public void generarOfertas(){
        listaOfertas = ofertaBean.obtenerOfertas();
    }
    /**
     * Metodo que genera solo las ofertas Activas, es decir las que tiene le estado en Verdadero
     */
    public void generarOfertasActuales(){
        listaOfertasActuales = ofertaBean.obtenerOfertasActuales();
    }
    /**
     * Metodo que actualiza las ofertas
     * Recorre la lista completa de ofertas determinando su estado en Verdadero o Falso
     */
    public void actualizarOfertas(){
        for(Oferta o: listaOfertas){
            if(ofertaBean.consultarOferta(o.getCodigoOferta())){
                o.setEstado(true);
            }else{
                o.setEstado(false);
            }
            ofertaBean.modificarOferta(o);
        }
        generarOfertas();
        generarOfertasActuales();
    }
    /**
     * Metodo que crear una oferta de helados de un solo tipo
     * 1ro: se crea una lista de helados auxiliar donde se almacenan helados de un solo tipo
     * 2do: se crear otra lista auxilair donde se almacenan helados de un solo tipo, eliminando las repeticiones si es necesario
     * 3ro: se pregunta si el tamaña de la lista es >0, esto para determinar si hay helados o no para agregar a la oferta
     *      si el tamañno es >0, quiere decir que si hay helados, por lo tanto se agrega a la base de datos de oferta
     *      sino se muestra un mensaje de error
     */
    public void crearOfertaTipo(){
        List<Helado> listaHeladoRubro = heladoBean.obtenerHeladosTipo(helado.getTipoHelado());
        List<Helado> listaHeladoAuxiliar = validacionesOferta.eliminarHeladoRepetidoPorTipos(ofertaBean.obtenerHeladosEnOferta(), listaHeladoRubro);
        if(listaHeladoAuxiliar.size() > 0){
            if(listaHeladoRubro.size() != listaHeladoAuxiliar.size()){
                FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atencion", "Algunos helados del tipo: \""+helado.getTipoHelado()+"\" \n"
                        + "ya se encuentran en oferta, por lo tanto han sido omitidos"));
            }
            oferta.setHeladosOferta(new HashSet<>(listaHeladoAuxiliar));
            ofertaBean.agregarOferta(oferta);
            oferta = new Oferta();
            generarOfertas();
            generarOfertasActuales();
        }else{
            FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No hay Helados de tipo : \""+helado.getTipoHelado()+"\" "
                        + "disponibles para una oferta"));
        }
    }
    /**
     * Metodo para agregar los helados a una lista, para luego ser agregados a una oferta
     * 1ro: se crea un objecto de tipo helado auxiliar, que sera el helado que se desea agregar a la oferta
     * 2do: se valida si el helado es repetido
     * 3ro: se valida si el helado ya se encuentra en una oferta
     * 4to: se valida si el helado existe y tiene un codigo correcto
     * 5to: una vez realizadas las validaciones necesarios, se agrega el helado a la lista de helados y se muestra un mensaje
     *      caso contrario, se muestran mensajes respectivos
     */
    public void agregarHeladoOferta(){
        Helado heladoAuxiliar = heladoBean.obtenerHeladoUnico(helado.getCodigoHelado());
        boolean validarHeladoRepetido = validacionesOferta.validarHeladoRepetido(listaHelados, helado.getCodigoHelado());
        boolean validarHeladoRepetidoEnOferta = validacionesOferta.validarHeladoRepetido(ofertaBean.obtenerHeladosEnOferta(), helado.getCodigoHelado());
        boolean validarCodigoExistente = validacionesOferta.validarCodigoExistente(heladoBean.obtenerHelados(),helado.getCodigoHelado());
        if(!validarHeladoRepetido){
            if(validarCodigoExistente){
                if(!validarHeladoRepetidoEnOferta){
                    listaHelados.add(heladoBean.obtenerHeladoUnico(helado.getCodigoHelado()));
                    FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "El Helado se ha agregado"));
                }else{
                    FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El Helado que intenta agregar, ya posee una oferta activa"));
                }
            }else{
                FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El Helado no existe"));
            }
        }else{
            FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El Helado ya se encuentra agregado"));
        }
    }
    
    /**
     * Metodos para crear una oferta con helados seleccionados por codigo
     * 1ro: se Setea la lista de helados previamente validada
     * 2do: se la persiste con la base de datos y se limpia la lista de helados
     * 
     */
    public void crearOfertaHelado(){
        oferta.setHeladosOferta(new HashSet<>(this.listaHelados));
        ofertaBean.agregarOferta(oferta);
        oferta = new Oferta();
        generarOfertas();
        generarOfertasActuales();
        this.listaHelados.clear();
    }
    /**
     * Metodo para validar las fechas de la oferta
     * si las fechas son correctas, se ejecuta un scrips que muestra un dialog en la vista
     */
    public void validarFechas(){
        boolean validarFechas = validacionesOferta.validarFechasOferta(oferta.getFechaInicio(), oferta.getFechaFinal());
        if(validarFechas){
            PrimeFaces.current().executeScript("PF('dlgAgregarHeladoOferta').show();PF('dlgAgregarOferta').hide()");
        }
        else
            FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La fecha inicial es mayor que la fecha final"));
    }
    public OfertaBean getOfertaBean() {
        return ofertaBean;
    }
    public void setOfertaBean(OfertaBean ofertaBean) {
        this.ofertaBean = ofertaBean;
    }
    public Oferta getOferta() {
        return oferta;
    }
    public void setOferta(Oferta oferta) {
        this.oferta = oferta;
    }
    public List<Oferta> getListaOfertas() {
        return listaOfertas;
    }
    public void setListaOfertas(List<Oferta> listaOfertas) {
        this.listaOfertas = listaOfertas;
    }
    public List<Oferta> getListaOfertasActuales() {
        return listaOfertasActuales;
    }
    public void setListaOfertasActuales(List<Oferta> listaOfertasActuales) {
        this.listaOfertasActuales = listaOfertasActuales;
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
}