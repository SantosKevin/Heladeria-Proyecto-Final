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
import java.util.Date;
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
        
        Date f1 = new Date(2019, 6, 21);
        Date f2 = new Date(2019, 6, 28);
        
        oferta.setFechaInicio(f1);
        oferta.setFechaFinal(f2);
        oferta.setTipoOferta("2x1");
        oferta.setEstado(true);
        oferta.setHeladosOferta(new HashSet<>(listaHelados));
        
        
        ofertaDAO.create(oferta);
        
    }
    
}
