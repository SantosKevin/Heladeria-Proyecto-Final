
package aplicacion.controladores.beans;

import aplicacion.hibernate.dao.IUsuarioDAO;
import aplicacion.hibernate.dao.imp.UsuarioDAOImp;
import aplicacion.modelo.dominio.Usuario;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Falcao
 */
@ManagedBean
@SessionScoped
public class UsuarioBean implements Serializable{
    private IUsuarioDAO iUsuario;
    /**
     * Creates a new instance of UsuarioBean
     */
    public UsuarioBean() {
        iUsuario = new UsuarioDAOImp();
    }
    /**
     * metodo que nos permite crear un usuario y agregarlo a la base de datos
     * @param usuario a agregar a la base de datos
     */
    public void crearUsuario(Usuario usuario){
        iUsuario.create(usuario);
    }
    /**
     * verificar si es un usuario valido 
     * @param nombreUsu (nombre del usuario)
     * @param contraseña del usuario
     * @return un objeto de tipo Usuario
     */
    public Usuario verificarUsuario(String nombreUsu,String contraseña){
        return iUsuario.verificarUsuario(nombreUsu, contraseña);
    }
    //seccion de getters y setters

    public IUsuarioDAO getiUsuario() {
        return iUsuario;
    }

    public void setiUsuario(IUsuarioDAO iUsuario) {
        this.iUsuario = iUsuario;
    }
    
}
