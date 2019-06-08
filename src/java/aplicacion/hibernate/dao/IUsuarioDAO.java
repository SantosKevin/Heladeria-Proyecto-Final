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
public interface IUsuarioDAO extends IGenericDAO<Usuario, Integer> {

    /**
     * permite validar que la contraseña sea compleja
     * @param contraseña a validar
     * @return un valor boleano
     */
    public boolean validarContraseña(String contraseña);
/**
 * permite verificar que el email ingresado lleve un dominio correcto y el signo "@"
 * @param email a verificar
 * @return un valor boleano en true o false
 */
    public boolean validarEmail(String email);
/**
 *  permtie verificar que no se repitan los emails
 * @param email cadena de string a verificar
 * @return un usuario con el email encontrado
 */
    public Usuario validarEmailExistente(String email);
    
    /**
     * metodo abstracto que permitira verificar un login de usuario
     * @param nombreUsu el nombre del usuario
     * @param contraseña del usuario
     * @return un objeto de tipo USUARIO
     */
    public Usuario verificarUsuario(String nombreUsu, String contraseña);
}
