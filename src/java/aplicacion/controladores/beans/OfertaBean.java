/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controladores.beans;

import aplicacion.hibernate.dao.IOfertaDAO;
import aplicacion.hibernate.dao.imp.OfertaDAOImp;
import aplicacion.modelo.dominio.Helado;
import aplicacion.modelo.dominio.Oferta;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Lucas Choque
 * 
 * Clase que fonciona como beans administrado de OfertaFormBean
 */
@ManagedBean
@SessionScoped
public class OfertaBean implements Serializable{
    /**
     * Se declara una instancia de IOfertaDAO
     */
    private IOfertaDAO ofertaDAO;

    /**
     * Creates a new instance of OfertaBean
     */
    public OfertaBean() {
        ofertaDAO = new OfertaDAOImp();
    }
    
    public void agregarOferta(Oferta oferta){
        ofertaDAO.create(oferta);
    }
    
    public void eliminarOferta(Oferta oferta){
        ofertaDAO.delete(oferta);
    }
    
    public void modificarOferta(Oferta oferta){
        ofertaDAO.update(oferta);
    }
    public int consultarOferta(Integer codigoOferta){
        return ofertaDAO.consultarOferta(codigoOferta);
    }
    public List<Oferta> obtenerOfertas(){
        return ofertaDAO.obtenerOfertaDistinct();
    }
    public List<Oferta> obtenerOfertasActuales(){
        return ofertaDAO.obtenerOfertasActuales();
    }
    public List<Helado> obtenerHeladosEnOferta(){
        return ofertaDAO.obtenerHeladosEnOferta();
    }
    public List<Helado> obtenerHeladosEnOfertaActiva(){
        return ofertaDAO.obtenerHeladosEnOfertaActiva();
    }
    public IOfertaDAO getOfertaDAO() {
        return ofertaDAO;
    }
    public void setOfertaDAO(IOfertaDAO ofertaDAO) {
        this.ofertaDAO = ofertaDAO;
    }
}