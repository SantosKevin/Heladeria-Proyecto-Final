/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.test;

import aplicacion.hibernate.dao.IHeladoDAO;
import aplicacion.hibernate.dao.IOfertaDAO;
import aplicacion.hibernate.dao.imp.HeladoDAOImp;
import aplicacion.hibernate.dao.imp.OfertaDAOImp;
import aplicacion.modelo.dominio.Helado;
import aplicacion.modelo.dominio.Oferta;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Lucas Choque
 */
public class TestOferta {
    public static void main(String[] args) {
        IOfertaDAO ofertaDAO = new OfertaDAOImp();
        IHeladoDAO heladoDAO = new HeladoDAOImp();
        List<Helado> listaHelados = heladoDAO.getAll(Helado.class);
        
        Oferta oferta = new Oferta();
        
        oferta.setHeladosOferta(new HashSet<>(listaHelados));
        oferta.setDescuento(100.0);
        
        ofertaDAO.create(oferta);
        
        
    }
    
}
