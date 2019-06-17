
package aplicacion.controladores.beans;

import aplicacion.hibernate.dao.IUsuarioDAO;
import aplicacion.hibernate.dao.imp.UsuarioDAOImp;
import aplicacion.modelo.dominio.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
    private List<Usuario> listaAdministrativos;
    /**
     * Creates a new instance of UsuarioBean
     */
    public UsuarioBean() {
        iUsuario = new UsuarioDAOImp();
        listaAdministrativos = new ArrayList<Usuario>();
    }
    /**
     * sirve para borrar un usuario
     * @param administrativo usuari a borrar
     */
    public void borrarAdministrativo(Usuario administrativo){
        iUsuario.delete(administrativo);
    }
    /**
     * metodo que nos permite crear un usuario y agregarlo a la base de datos
     * @param usuario a agregar a la base de datos
     */
    public void crearUsuario(Usuario usuario){
        iUsuario.create(usuario);
    }
    /**
     * sirve para modificar un usuario
     * @param usuario  a modificar
     */
    public void modificarUsuario(Usuario usuario){
        Usuario usuarioAux= usuario;
        iUsuario.update(usuarioAux);
    }
    /**
     * srive para obtener una lista con todos los usuarios
     * @return una lista cargada
     */
    public List<Usuario> obtenerAdministrativos(){
        listaAdministrativos.clear();
        listaAdministrativos = iUsuario.getAll(Usuario.class);
        return listaAdministrativos;
    }
    
    /**
     * validamos que la contraseña sea segura
     * @param contraseña a verificar
     * @return un valor booleano
     */
    public boolean validarContraseña(String contraseña){
      return iUsuario.validarContraseña(contraseña);
    }
    /**
     * validamos que el email ingresado tenga un dominio correcto
     * @param email a validar
     * @return un booleando indicando true o false
     */
    public boolean validarEmail(String email){
        return iUsuario.validarEmail(email);
    }
    /**
     * se comprueba que el email no sea repetido
     * @param email a verificar
     * @return un objeto tipo usuario con su email, o null
     */
    public Usuario validarEmailExsitente(String email){
        return iUsuario.validarEmailExistente(email);
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

    public List<Usuario> getListaAdministrativos() {
        return listaAdministrativos;
    }

    public void setListaAdministrativos(List<Usuario> listaAdministrativos) {
        this.listaAdministrativos = listaAdministrativos;
    }
    
    
}
