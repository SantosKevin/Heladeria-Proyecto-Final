/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controladores.beans.forms;

import aplicacion.controladores.beans.UsuarioBean;
import aplicacion.modelo.dominio.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

/**
 * este managed bean permite trabajar con la pagina web Sesion y
 * CrearNuevoUsuario
 *
 * @author Falcao
 */
@ManagedBean
@SessionScoped
public class UsuarioFormBean implements Serializable {

    @ManagedProperty(value = "#{usuarioBean}")
    private UsuarioBean usuarioBean;
    private Usuario usuario;
    private Usuario usuarioConectado;
    private String EmailUsu;
    private String contraseña;
    private Integer paramBaseDeDatos;

    /**
     * Creates a new instance of UsuarioFormBean
     */
    public UsuarioFormBean() {

    }

    /**
     * me permite inicializar las variables a usar
     */
    @PostConstruct
    public void iniciar() {
        usuario = new Usuario();
        usuarioConectado = new Usuario();
    }

    /**
     * permite borrar un usuario tipo administrativo
     *
     * @param admin
     */
    public void borrarAdministrativo(Usuario admin) {
        if (admin.getEstado()) {
            admin.setEstado(Boolean.FALSE);
            usuarioBean.borrarAdministrativo(admin);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se eliminó un Usuario", ""));
        } else {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El Usuario ya se encuentra eliminado"));

        }
    }

    /**
     * me permite crear usuarios tipo administrativos, agregando los
     * correspondientes mensajes
     */
    public void crearUsuarioAdministrativo(){
        usuario.setEstado(true);
        usuarioBean.crearUsuario(usuario);
        usuario = new Usuario(); //limpiar los campos
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se agregó un nuevo Usuario Administrativo", "A trabajar!"));

    }

    /**
     * con esto creamos un nuevo usuario normal y lo agregamos a la base de
     * datos
     */
    public void crearUsuario() {
        usuario.setEstado(true);
        usuario.setTipoUsuario("normal");
        usuarioBean.crearUsuario(usuario);
        usuario = new Usuario();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se agregó un nuevo Usuario", "Bienvenido"));
    }

    /**
     * permite controlar que haya un usuario logueado, de lo contrario nos
     * mandara a otra pagina
     */
    public void controlarSesion() {
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            Usuario us = (Usuario) fc.getExternalContext().getSessionMap().get("usuario");
            if (us == null) {
                fc.getExternalContext().redirect("permisos.xhtml");
            }
        } catch (Exception e) {
        }
    }

    /**
     * permite desconectarse de la sesion
     *
     * @return la redirecion de la pagina
     */
    public String desconectar() {
        String redireccion = "";
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        redireccion = "sesion?faces-redirect=true";
        return redireccion;
    }
    public String redireccionar(){
        String redireccion = "";
        if(this.usuarioConectado.getTipoUsuario().equalsIgnoreCase("normal"))
            redireccion = "pagina_principal?faces-redirect=true";
        else{
            if(this.usuarioConectado.getTipoUsuario().equalsIgnoreCase("administrativo"))
                redireccion = "pagina_administrador?faces-redirect=true";
            else{
                if (this.usuarioConectado.getTipoUsuario().equals("administrador")){
                    redireccion = "pagina_root?faces-redirect=true";
                }else{
                    redireccion = "pagina_vendedor?faces-redirect=true";
                }
            }
        }
        return redireccion;
    }

    /**
     * sirve para obtener el nombre del usuario logueado
     *
     * @return el apellido y nombre del usuario
     */
    public String devolverNombreUsuario() {
        Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return usuario.getApellidoUsuario() + ", " + usuario.getNombreUsuario();
    }

    public Integer devolverIDUsuario() {
        Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return usuario.getCodigoUsuario();
    }

    public void generarUsuario() {
        usuarioBean.obtenerAdministrativos();
    }

    /**
     * me permite obtener la lista de todos los usuarios de mi base de datos
     *
     * @return la lista cargada
     */
    public List<Usuario> obtenerAdministrativos() {
        return usuarioBean.obtenerAdministrativos();
    }

    /**
     * permite validar un DNI
     * @param fc para añadir mensajes
     * @param uic clase base para todos los componentes de la interfaz de usuario en JavaServer Faces
     * @param o dni a validar
     * @throws ValidatorException añade un mensaje en caso de error
     */
    public void validarDNI (FacesContext fc, UIComponent uic, Object o) throws ValidatorException{
       int dni = (int) o, digitos =0;
       while(dni >0){          
           dni = dni / 10;
           digitos ++;
       }
        System.out.println("es "+ digitos);
       if(digitos != 7 && digitos != 8){
           throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "el DNI debe tener 7 u 8 digitos"));         
       }
    }  
    /**
     * es un validador de contraseña
     *
     * @param fc para añadir mensajes
     * @param uic clase base para todos los componentes de la interfaz de
     * usuario en JavaServer Faces
     * @param o el objeto con que trabajamos (string)
     * @throws ValidatorException añade un mensaje en caso de error
     */
    public void validarContraseña(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        boolean passCorrecta = usuarioBean.validarContraseña((String) o);
        if (passCorrecta == false) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN, "Error contraseña", "La contraseña no esta conforme los requerimientos"));
        }
    }

    /**
     * es un validador para el email
     *
     * @param fc para añadir mensajes
     * @param uic clase base para todos los componentes de la interfaz de
     * usuario en JavaServer Faces
     * @param o es el objeto con que trabajamos (string)
     * @throws ValidatorException añane un mensaje en caso de error
     */
    public void validarEmail(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        boolean emailValido = false;
        Usuario usu = usuarioBean.validarEmailExsitente((String) o);
        emailValido = usuarioBean.validarEmail((String) o);
        if (usu != null) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error email", "el correo ingresado ya existe"));
        }else{
           if(emailValido == false){
               throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error email", "el dominio es incorrecto"));
           }
        }
    }
    /**
     * validador para que al modificar un email no se repita ninguno
     * @param fc contexto
     * @param uic componente
     * @param o email a cambiar
     * @throws ValidatorException 
     */
    public void cambiarEmail(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
       String emailAnterior = this.usuarioConectado.getEmailUsuario();
        System.out.println("este es el anterios "+ emailAnterior);
       if(!emailAnterior.equals((String)o)){
            boolean emailValido = false;
        Usuario usu = usuarioBean.validarEmailExsitente((String) o);
        emailValido = usuarioBean.validarEmail((String) o);
        if (usu != null) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error email", "el correo ingresado ya existe"));
        }else{
           if(emailValido == false){
               throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error email", "el dominio es incorrecto"));
           }
        }
       }
    }

    /**
     * este metodo permite verificar las credenciales del usuario para poder
     * ingresar (nombreUsuario y contraseña)
     *
     * @return una cadena que contiene la redireccion a otra pagina web o un
     * mensaje de error en caso de no ser validas las credenciales
     */
    public String verificarCredenciales() {
        String redireccion = "";
        Usuario usuarioAuxiliar = usuarioBean.verificarUsuario(EmailUsu, contraseña);
        if (usuarioAuxiliar != null) {
            if (usuarioAuxiliar.getEstado()) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuarioAuxiliar);
                if (usuarioAuxiliar.getTipoUsuario().equals("normal")) {
                    redireccion = "pagina_principal?faces-redirect=true";
                } else if (usuarioAuxiliar.getTipoUsuario().equals("administrativo")) {
                    redireccion = "pagina_administrador?faces-redirect=true";
                } else {
                    redireccion = "pagina_root?faces-redirect=true";
                    if(usuarioAuxiliar.getTipoUsuario().equals("vendedor")){
                        redireccion="pagina_vendedor?faces-redirect = true";
                    }
                }
                this.usuarioConectado = usuarioAuxiliar;
            } else {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No permitido", "su cuenta fue dada de baja"));
            }
        } else {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No permitido", "error credenciales"));
        }
        EmailUsu = "";
        contraseña = "";
        return redireccion;
    }

    /**
     * Metodo para validar la contraseña del usuario logeado que desee entrar a
     * la base de datos Helado
     *
     * @return una cadena que contiene la redireccion a otra pagina web o un
     * mensaje de error en caso de no ser validas las credenciales
     */
    public String verificarCredencialesBD() {
        String redireccion = "";
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (contraseña.equals(this.usuarioConectado.getContraseña())) {
            if(this.paramBaseDeDatos == 1)
                redireccion = "base_de_datos_helados?faces-redirect=true";
            else
                redireccion = "base_de_datos_ofertas?faces-redirect=true";
        } else {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No permitido", "contraseña incorrecta"));
        }
        
        contraseña = "";
        return redireccion;
    }

    //editar la dataTable
    /**
     * me permite modificar un usuario
     *
     * @param usuarioModi a modificar
     */
    public void modificarUsuario(Usuario usuarioModi) {
        usuarioBean.modificarUsuario(usuarioModi);
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "El Usuario se ha modificado"));
        generarUsuario();
    }

    /**
     * en caso de cancelar la edicion genero mensajes
     *
     * @param event
     */
    public void onRowCancel(RowEditEvent event) {
        generarUsuario();
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cancelado", "La modificacion se ha cancelado"));

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

    public String getEmailUsu() {
        return EmailUsu;
    }

    public void setEmailUsu(String EmailUsu) {
        this.EmailUsu = EmailUsu;
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

    public Integer getParamBaseDeDatos() {
        return paramBaseDeDatos;
    }

    public void setParamBaseDeDatos(Integer paramBaseDeDatos) {
        this.paramBaseDeDatos = paramBaseDeDatos;
    }
    
    
}
