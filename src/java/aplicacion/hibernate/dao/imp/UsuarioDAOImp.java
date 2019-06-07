/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.hibernate.dao.imp;

import aplicacion.hibernate.configuracion.HibernateUtil;
import aplicacion.hibernate.dao.IUsuarioDAO;
import aplicacion.modelo.dominio.Usuario;
import java.io.Serializable;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Lucas Choque
 */
public class UsuarioDAOImp extends GenericDAOImp<Usuario, Integer> implements IUsuarioDAO, Serializable{
    /**
     * le asignamos codigo al metodo abstracto que permitira verificar un login de usuario
     * @param nombreUsu el nombre del usuario 
     * @param contraseña del usuario
     * @return un objeto de tipo USUARIO
     */
    @Override
    public Usuario verificarUsuario(String nombreUsu,String contraseña) {
        Session session = HibernateUtil.getSessionFactory().openSession(); //abrimos una sesion
        Criteria criteria = session.createCriteria(Usuario.class); //criteria, es para hacer una consulta
        criteria.add(Restrictions.like("nombreUsuario", nombreUsu)); //filtramos la busqueda por nombreUusuario
        criteria.add(Restrictions.like("contraseña", contraseña)); //tambien la filtramos por contraeña
        Usuario usu = null;
        if(!criteria.list().isEmpty()){ //verificamos que la lista no este vacia
            usu = (Usuario)criteria.list().get(0);
        }
        session.close();
        return usu;
    }
    
}
