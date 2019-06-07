/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.hibernate.dao;

import aplicacion.modelo.dominio.Usuario;

/**
 * 
 * @author Lucas Choque
 */
public interface IUsuarioDAO extends IGenericDAO<Usuario, Integer>{
    /**
     * metodo abstracto que permitira verificar un login de usuario
     * @param nombreUsu el nombre del usuario 
     * @param contraseña del usuario
     * @return un objeto de tipo USUARIO
     */
    public Usuario verificarUsuario(String nombreUsu,String contraseña);
}