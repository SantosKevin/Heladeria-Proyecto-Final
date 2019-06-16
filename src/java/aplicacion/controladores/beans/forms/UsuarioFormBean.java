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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

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
private Usuario usuarioConectado;
private String nombreUsu;
private String contraseña;

    /**
     * Creates a new instance of UsuarioFormBean
     */
    public UsuarioFormBean() {
        usuario = new Usuario();
    }
     public void crearUsuarioAdministrativo(){
        usuario.setEstado(true);
        usuario.setTipoUsuario("administrativo");
        usuarioBean.crearUsuario(usuario);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Se agregó un nuevo Usuario Administrativo","A trabajar!"));
        
    }
    /**
     * con esto creamos un nuevo usuario y lo agregamos a la base de datos
     */
    public void crearUsuario(){
        usuario.setEstado(true);
        usuario.setTipoUsuario("normal");
        usuarioBean.crearUsuario(usuario);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Se agregó un nuevo Usuario","Bienvenido"));
        
    }
    /**
     *  es un validador de contraseña
     * @param fc para añadir mensajes
     * @param uic clase base para todos los componentes de la interfaz de usuario en JavaServer Faces
     * @param o el objeto con que trabajamos (string)
     * @throws ValidatorException añade un mensaje en caso de error
     */
    public void validarContraseña(FacesContext fc,UIComponent uic, Object o) throws ValidatorException{
        boolean passCorrecta = usuarioBean.validarContraseña((String)o);
        if (passCorrecta == false){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN,"Error contraseña","La contraseña no esta conforme los requerimientos"));
        }
    }
    
    /**
     * es un validador para el email
     * @param fc para añadir mensajes
     * @param uic clase base para todos los componentes de la interfaz de usuario en JavaServer Faces
     * @param o es el objeto con que trabajamos (string)
     * @throws ValidatorException añane un mensaje en caso de error
     */
    public void validarEmail(FacesContext fc,UIComponent uic, Object o) throws ValidatorException{
        boolean emailValido=false;
        Usuario usu = usuarioBean.validarEmailExsitente((String)o);
        emailValido=usuarioBean.validarEmail((String)o);
        if (usu != null || emailValido==false){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error email","el correo ingresado ya existe o el dominio es incorrecto"));
        }
    
    }
    /**
     * este metodo permite verificar las credenciales del usuario para poder ingresar (nombreUsuario
     * y contraseña)
     * @return una cadena que contiene la redireccion a otra pagina web o un mensaje de error en caso
     * de no ser validas las credenciales
     */
    public String verificarCredenciales(){
        String redireccion="";
        Usuario usuarioAuxiliar = usuarioBean.verificarUsuario(nombreUsu, contraseña);
        if(usuarioAuxiliar != null){
            if(usuarioAuxiliar.getEstado()){
               FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuarioAuxiliar);
               if(usuarioAuxiliar.getTipoUsuario().equals("normal"))
                   redireccion="pagina_principal?faces-redirect=true";
               else{
                   if(usuarioAuxiliar.getTipoUsuario().equals("administrativo"))
                        redireccion="pagina_administrador?faces-redirect=true";
                    else
                        redireccion="pagina_root?faces-redirect=true";
               }
               this.usuarioConectado = usuarioAuxiliar;
            }else{
                FacesContext facesContext= FacesContext.getCurrentInstance();
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"No permitido","su cuenta fue dada de baja"));
            }
        }
        else{
            FacesContext facesContext= FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"No permitido","error credenciales"));
        }
        nombreUsu="";
        contraseña="";
        return redireccion; 
    }
    /**
     * Metodo para validar la contraseña del usuario logeado que desee entrar a la base de datos Helado
     * @return una cadena que contiene la redireccion a otra pagina web o un mensaje de error en caso
     * de no ser validas las credenciales
     */
    public String verificarCredencialesBD(){
        String redireccion="";
        if(contraseña.equals(this.usuarioConectado.getContraseña())){
            redireccion="baseDeDatosHelados?faces-redirect=true";
        }
        else{
            FacesContext facesContext= FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"No permitido","error credenciales"));
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

    public Usuario getUsuarioConectado() {
        return usuarioConectado;
    }

    public void setUsuarioConectado(Usuario usuarioConectado) {
        this.usuarioConectado = usuarioConectado;
    }
}
