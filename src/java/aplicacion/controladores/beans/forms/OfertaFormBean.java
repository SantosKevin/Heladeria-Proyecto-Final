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
        actualizarOfertas();
        actualizarPrecio();
        listaHelados = new ArrayList<>();
    }
    /**
     * Metodo que genera todas las ofertas 
     */
    public void generarOfertas(){
        listaOfertas = ofertaBean.obtenerOfertas();
    }
    /**
     * Metodo que genera solo las ofertas Activas, es decir las que tiene el estado en Verdadero
     */
    public void generarOfertasActuales(){
        listaOfertasActuales = ofertaBean.obtenerOfertasActuales();
    }
    /**
     * Metodo que actualiza las ofertas
     * Recorre la lista completa de ofertas determinando su estado en Verdadero o Falso
     */
    public void actualizarOfertas(){
        actualizarPrecio();
        for(Oferta o: listaOfertas){
            if(ofertaBean.consultarOferta(o.getCodigoOferta()) == 0){
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
     * Metodo que actualiza el precio de los productos en oferta
     * Se recorre todas las ofertas de la base de dato consultado que oferta estan activas, y que ofertas terminaron definitivamente.
     * En las ofertas que estan activa, se hace un recorrido consultado el tipo de oferta que tiene, para asignarle el precio correspondiente
     * En las ofertas que estan terminadas, tambien se hace un recorrido, solo que a esos productos se le asinga $0 a sus preciosOFerta
     * 
     */
    public void actualizarPrecio(){
        for(Oferta o: listaOfertas){
            if(ofertaBean.consultarOferta(o.getCodigoOferta()) == 0){
                for(Helado h : o.getHeladosOferta()){
                    if(o.getTipoOferta().equalsIgnoreCase("10% de Descuento"))
                        h.setPrecioOferta(h.getPrecio() - h.getPrecio()*0.1);
                    else{
                        if(o.getTipoOferta().equalsIgnoreCase("20% de Descuento"))
                            h.setPrecioOferta(h.getPrecio() - h.getPrecio()*0.2);
                        else{
                            if(o.getTipoOferta().equalsIgnoreCase("2x1")){
                                System.out.println("Cual es la cantidad aquiiuuii" + heladoBean.getCantidad());
                                if(heladoBean.getCantidad() == 2)
                                    h.setPrecioOferta(h.getPrecio()/2);
                                else
                                    h.setPrecioOferta(0.0);
                            }
                        }
                    }
                    heladoBean.modificarHelado(h);
                }
            }else{
                if(ofertaBean.consultarOferta(o.getCodigoOferta()) == 1){
                    for(Helado h : o.getHeladosOferta()){
                        h.setPrecioOferta(0.0);
                        heladoBean.modificarHelado(h);
                    }
                }
            }
        }
        generarOfertas();
        generarOfertasActuales();
    }
    /**
     * Este metodo lo que hace es crear una oferta en donde solo se incluyen helados de un solo tipo
     * 1ro: se crea una lista de helados auxiliar, llamada listaHeladoRubro en donde se almacenan solo helados de un solo tipo
     * 2do: se crear otra lista auxiliar, en esta se almacenaran los helados de la variable ListaHeladoRubro, solo que sin repeticiones
     * 3ro: se pregunta si el tamaña de la lista es >0, esto para determinar si hay helados o no para agregar a la oferta
     * si el tamañno es >0, quiere decir que si hay helados, por lo tanto se agrega a la base de datos de oferta
     * sino se muestra un mensaje de error.
     */
    public void crearOfertaTipo(){
        List<Helado> listaHeladoRubro = heladoBean.obtenerHeladosTipo(helado.getTipoHelado());
        List<Helado> listaHeladoAuxiliar = validacionesOferta.eliminarHeladoRepetidoPorTipos(ofertaBean.obtenerHeladosEnOferta(), listaHeladoRubro);
        if(listaHeladoAuxiliar.size() > 0){
            FacesContext.getCurrentInstance().
            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Atencion", "Existe la posiblidad de que algunos helados"
                    + " hallan sido omitidos por estar en oferta o por no estar disponibles"));
            oferta.setHeladosOferta(new HashSet<>(listaHeladoAuxiliar));
            ofertaBean.agregarOferta(oferta);
            oferta = new Oferta();
            generarOfertas();
            generarOfertasActuales();
            actualizarOfertas();
            actualizarPrecio();
        }else{
            FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No hay Helados de tipo : \""+helado.getTipoHelado()+"\" "
                        + "disponibles para una oferta"));
        }
    }
    /**
     * Este Metodo lo unico que hace es agregar helados 1 por 1 a una lista auxiliar, para luego ser agregados a la oferta
     * 1ro: se crea un objecto de tipo helado llamado  heladoAuxiliar, que sera el helado que se desea agregar a la oferta
     * 2do: se valida si el helado es repetido
     * 3ro: se valida si el helado ya se encuentra en una oferta activa
     * 4to: se valida si el helado existe y si tiene un codigo correcto
     * 5to: una vez realizadas las validaciones necesarias, se agrega el helado a la lista de helados y se muestra un mensaje
     *  En el caso de que alguna validacion sea incorrecta, se mostra un mensaje de error.
     */
    public void agregarHeladoOferta(){
        Helado heladoAuxiliar = heladoBean.obtenerHeladoUnicoDisponible(helado.getCodigoHelado());
        boolean validarHeladoRepetido = validacionesOferta.validarHeladoRepetido(listaHelados, helado.getCodigoHelado());
        boolean validarHeladoRepetidoEnOferta = validacionesOferta.validarHeladoRepetido(ofertaBean.obtenerHeladosEnOferta(), helado.getCodigoHelado());
        boolean validarCodigoExistente = validacionesOferta.validarCodigoExistente(heladoBean.obtenerHelados(),helado.getCodigoHelado());
        if(!validarHeladoRepetido){
            if(validarCodigoExistente){
                if(!validarHeladoRepetidoEnOferta){
                    if(heladoAuxiliar != null){
                        listaHelados.add(heladoAuxiliar);
                        FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "El Helado se ha agregado"));
                    }else{
                        FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El Helado no esta disponible"));
                    }
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
     * Metodo para detectar si un helado esta en oferta o no
     * Este metodo lo que recibe como parametro es el codigo del helado que se desea verificar
     * luego se recorre la lista de todos los helados en oferta y se pregunta si los codigos son iguales
     * @param codigoHelado codigo del helado que se desea consultar
     */
    public void detectarHeladoOferta(Integer codigoHelado){
        boolean validar = false;
        for(Helado h: ofertaBean.obtenerHeladosEnOferta()){
            if(h.getCodigoHelado() == codigoHelado)
                validar = true;
        }
        if(validar){
            PrimeFaces.current().executeScript("PF('dlgOfertaDisponible').show();");
        }else
            PrimeFaces.current().executeScript("PF('multiProdDialog').show();");
        
    }
    /**
     * Este metodo crea una oferta, Los helados que se almacenan en esta oferta 
     * son los helados de ListaHelados que previamente fueron cargados con el metodo agregarHeladoOferta()
     * Se pregunta si el atributo ListaHelados tiene un tamaña mayor a 0, si es correcto
     * quiere decir que posee helados para agregar a la oferta, entonces se los agrega a la oferta
     * y se crea dicha oferta, actualizando todas las ofertas y los precios de oferta
     * 
     * Si el atributo ListaHelados tiene un tamaño de 0, quiere decir que no hay helados para agregar
     * a la oferta, por lo tanto solo se muestra un mensaje de error.
     */
    public void crearOfertaHelado(){
        if(this.listaHelados.size() > 0){
             oferta.setHeladosOferta(new HashSet<>(this.listaHelados));
             ofertaBean.agregarOferta(oferta);
             oferta = new Oferta();
             generarOfertas();
            generarOfertasActuales();
            actualizarOfertas();
            actualizarPrecio();
            this.listaHelados.clear();
        }else
            FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se puede crear una oferta sin helados"));
    }
    /**
     * Metodo para validar las fechas de la oferta
     * si las fechas son correctas, se ejecuta un scrips que muestra un dialog en la vista.
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
    
    /**
     * Metodos Getters Y Setters.
     */
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