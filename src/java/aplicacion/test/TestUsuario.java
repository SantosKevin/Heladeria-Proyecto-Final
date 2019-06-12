/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.test;

import aplicacion.hibernate.dao.IUsuarioDAO;
import aplicacion.hibernate.dao.imp.UsuarioDAOImp;
import aplicacion.modelo.dominio.Usuario;

/**
 *
 * @author Lucas Choque
 */
public class TestUsuario {
    public static void main(String[] args) {
        IUsuarioDAO usuarioDAO = new UsuarioDAOImp();
        
        Usuario u1 = new Usuario(1, "root", "root", "root", 12345, "root@gmail", "administrador", true);
        
        
        usuarioDAO.create(u1);
    }
}