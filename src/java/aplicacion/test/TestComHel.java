/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.test;

import aplicacion.hibernate.dao.IComHelDAO;
import aplicacion.hibernate.dao.imp.ComHelDAOImp;
import aplicacion.modelo.dominio.ComHel;
import java.util.List;

/**
 *
 * @author gabri
 */
public class TestComHel {
    public static void main(String[] args) {
        IComHelDAO comHelDAO = new ComHelDAOImp();
        
        List<ComHel> listaComHel = comHelDAO.getAll(ComHel.class);
        
        for(ComHel l: listaComHel){
            System.out.println(l.getCantHelado());
        }
    }
}
