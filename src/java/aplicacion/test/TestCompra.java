/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.test;

import aplicacion.hibernate.dao.ICompraDAO;
import aplicacion.hibernate.dao.IHeladoDAO;
import aplicacion.hibernate.dao.IUsuarioDAO;
import aplicacion.hibernate.dao.imp.CompraDAOImp;
import aplicacion.hibernate.dao.imp.HeladoDAOImp;
import aplicacion.hibernate.dao.imp.UsuarioDAOImp;
import aplicacion.modelo.dominio.Compra;
import aplicacion.modelo.dominio.Helado;
import aplicacion.modelo.dominio.Usuario;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 *
 * @author Lucas Choque
 */
public class TestCompra {
    public static void main(String[] args) {
        ICompraDAO compraDAO = new CompraDAOImp();
        IUsuarioDAO usuarioDAO = new UsuarioDAOImp();
        IHeladoDAO heladoDAO = new HeladoDAOImp();
        
        List<Helado> listaHelados = heladoDAO.getAll(Helado.class);
        Usuario usuario = new Usuario();
        usuario = usuarioDAO.getAll(Usuario.class).get(0);
        
        Compra compra = new Compra();
        compra.setEstado(1);
        compra.setHeladosCompra(new HashSet<>(listaHelados));
        compra.setUsuarioCompra(usuario);
        
        //compraDAO.create(compra);
        //sirve para quitar valores duplicados
        List<Compra> compras = compraDAO.getAll(Compra.class);
        Set nums = compras.stream().collect(Collectors.toSet());
        compras = new ArrayList<>(nums);
        
        compra = compraDAO.obtenerUltimaCompra(usuario);
        System.out.println(compra.getCodigoCompra());
        compraDAO.eliminarCompra(compra);
    }
}