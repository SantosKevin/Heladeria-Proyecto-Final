/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.test;

import aplicacion.hibernate.dao.IHeladoDAO;
import aplicacion.hibernate.dao.imp.HeladoDAOImp;
import aplicacion.modelo.dominio.Helado;

/**
 *
 * @author Lucas Choque
 */
public class TestHelado {
    public static void main(String[] args) {
        IHeladoDAO heladoDAO = new HeladoDAOImp();
        
        Helado h1 = new Helado(1, "Tentacion", "POTE", 200.0, "Frutilla", 5, true);
        Helado h2 = new Helado(2, "Tentacion", "POTE", 200.0, "Chocolate", 5, true);
        
        heladoDAO.create(h1);
        heladoDAO.create(h2);
    }
}
