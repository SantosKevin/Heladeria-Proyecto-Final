/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.hibernate.dao.imp;

import aplicacion.hibernate.configuracion.HibernateUtil;
import aplicacion.hibernate.dao.IOfertaDAO;
import aplicacion.modelo.dominio.Helado;
import aplicacion.modelo.dominio.Oferta;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 *
 * @author Lucas Choque
 */
public class OfertaDAOImp extends GenericDAOImp<Oferta, Integer> implements IOfertaDAO, Serializable{
    /**
     * Metodo para obtener una solo oferta, la oferta se obtiene mediante un codigo que es pasado
     * por parametro. Se declara un objeto de tipo Oferta, y se recorre toda la base de datos de oferta
     * en busca de una coincidencia con el codigo pasado por parametro.
     * @param codigoOferta codigo de la oferta que deseamos obtener
     * @return ofertaActual la oferta que obtenemos
     */
    @Override
    public Oferta obtenerUnicaOferta(Integer codigoOferta) {
        Oferta ofertaActual = null;
        for(Oferta o : super.getAll(Oferta.class)){
            if(o.getCodigoOferta() == codigoOferta)
                ofertaActual = o;
        }
        return ofertaActual;
    }
    /**
     * Metodo que obtiene las oferta sin repeticiones
     * @return todas las ofertas sin repeticiones
     */
    @Override
    public List<Oferta> obtenerOfertaDistinct() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Oferta.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Oferta> listaOfertas = criteria.list();
        session.close();
        return listaOfertas;
    }
    /**
     * Metodo para consultar si la oferta se encuentra activa o no, mediante un codigo pasado por parametro.
     * Se declaran 3 objetos de tipo Date, 2 de ellas van a tener la fecha de inicio y final de la oferta consultada
     * mientras que la otra va a tener la fecha actual del sistema. Luego se comparan dichas fechas y
     * se determina si la oferta continua o no.
     * @param codigoOferta codigo de la oferta que se desea consultar
     * @return
     * -1 si la oferta todavia no empezo
     * 0 si la oferta esta activa
     * 1 si la oferta ya termino
     */
    @Override
    public int consultarOferta(Integer codigoOferta) {
        Date fechaInicial = this.obtenerUnicaOferta(codigoOferta).getFechaInicio();
        Date fechaFinal = this.obtenerUnicaOferta(codigoOferta).getFechaFinal();
        Date fechaActual = new Date();
        int consultar = 1;
        if(fechaActual.compareTo(fechaInicial) < 0)
            consultar = -1;
        else{
            if(fechaActual.compareTo(fechaInicial) >= 0 && fechaActual.compareTo(fechaFinal) <= 0)
                consultar = 0;
        }
        return consultar;
    }
    /**
     * Metodo que devuelve una lista de ofertas cuyo atributo Estado es igual a Verdadero
     * se declara una lista de oferta auxiliar, se recorre la base de datos ofertas consultado por
     * el estado de las ofertas, si el estado es verdadero, dicha oferta se almacena en la lista auxiliar
     * @return lista de ofertas activas
     */
    @Override
    public List<Oferta> obtenerOfertasActuales() {
       List<Oferta> listaOfertasActuales = new ArrayList<>();
       for(Oferta o : this.obtenerOfertaDistinct()){
           if(o.isEstado())
               listaOfertasActuales.add(o);
       }
       return listaOfertasActuales;
    }
    /**
     * Metodo que devuelve una lista de helados, estos helados son los que se encuentran en oferta
     * se declara una variable auxiliar, y se hace un doble recorrido, primero se recorren las ofertas
     * y luegos los helados
     * @return lista de helados en oferta
     */
    @Override
    public List<Helado> obtenerHeladosEnOferta() {
        List<Helado> heladosEnOferta = new ArrayList<>();
        for(Oferta o : this.obtenerOfertasActuales()){
            for(Helado h : o.getHeladosOferta()){
                heladosEnOferta.add(h);
            }
        }
        return heladosEnOferta;
    }
}
