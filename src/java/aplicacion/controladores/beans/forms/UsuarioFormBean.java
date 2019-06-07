/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controladores.beans.forms;

import aplicacion.controladores.beans.UsuarioBean;
import aplicacion.modelo.dominio.Usuario;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * este managed bean permite trabajar con la pagina web Sesion y CrearNuevoUsuario
 * @author Falcao
 */
@ManagedBean
@SessionScoped
public class UsuarioFormBean implements Serializable{
@ManagedProperty (value="#{usuarioBean}")
private UsuarioBean usuarioBean;
private Usuario usuario;
private String nombreUsu;
private String contraseña;

    /**
     * Creates a new instance of UsuarioFormBean
     */
    public UsuarioFormBean() {
        usuario = new Usuario();
    }
    /**
     * con esto creamos un nuevo usuario y lo agregamos a la base de datos
     */
    public void crearUsuario(){
        usuario.setEstado(true);
        usuario.setTipoUsuario("normal");
        usuarioBean.crearUsuario(usuario);
        
    }
    /**
     * este metodo permite verificar las credenciales del usuario para poder ingresar (nombreUsuario
     * y contraseña)
     * @return una cadena que contiene la redireccion a otra pagina web o un mensaje de error en caso
     * de no ser validas las credenciales
     */
    public String verificarCredenciales(){
        String redireccion="";
        Usuario usu = usuarioBean.verificarUsuario(nombreUsu, contraseña);
        if(usu != null){
           FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usu);
           redireccion="index?faces-redirect=true";
        }
        else{
            FacesContext fc= FacesContext.getCurrentInstance();
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"No permitido","error credenciales"));
        }  
        return redireccion; 
    }
    
    //seccion de getters y setters

    public UsuarioBean getUsuarioBean() {
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombreUsu() {
        return nombreUsu;
    }

    public void setNombreUsu(String nombreUsu) {
        this.nombreUsu = nombreUsu;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
}
