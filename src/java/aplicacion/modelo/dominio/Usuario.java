   
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.modelo.dominio;

import java.io.Serializable;

/**
 *Representa a los Usuarios en la pagina
 * @author Lucas Choque
 */
public class Usuario implements Serializable{
    /**
     * codigo unico que representa a cada usuario
     */
    private Integer codigoUsuario;
    /**
     * Nombre del usuario
     */
    private String nombreUsuario;
    /**
     * Apellido del usuario
     */
    private String apellidoUsuario;
    /**
     * Contraseña para que el usuario pueda ingresar
     */
    private String contraseña;
    /**
     * documento de indentidad nacional (DNI) del usuario
     */
    private Integer dniUsuario;
    /**
     * correo electronico del usuario
     */
    private String emailUsuario;
    /**
     * Especifica que tipo de usuario es
     */
    private String tipoUsuario;
    /**
     * Especifica si el usuario se encuentra activo o inactivo
     */
    private Boolean estado;
    /**
     * Constructor por defecto
     */
    public Usuario() {
    }
    /**
     * Construcor parametrizado
     * @param codigoUsuario codigo unico que representa a cada usuario
     * @param nombreUsuario Nombre del usuario
     * @param apellidoUsuario Apellido del usuario
     * @param contraseña Contraseña para que el usuario pueda ingresar
     * @param dniUsuario documento de indentidad nacional (DNI) del usuario
     * @param emailUsuario correo electronico del usuario
     * @param tipoUsuario Especifica que tipo de usuario es
     * @param estado Especifica si el usuario se encuentra activo o inactivo
     */
    public Usuario(Integer codigoUsuario, String nombreUsuario, String apellidoUsuario, String contraseña, Integer dniUsuario, String emailUsuario, String tipoUsuario, Boolean estado) {
        this.codigoUsuario = codigoUsuario;
        this.nombreUsuario = nombreUsuario;
        this.apellidoUsuario = apellidoUsuario;
        this.contraseña = contraseña;
        this.dniUsuario = dniUsuario;
        this.emailUsuario = emailUsuario;
        this.tipoUsuario = tipoUsuario;
        this.estado = estado;
    }
    /**
     * @return the codigoUsuario
     */
    public Integer getCodigoUsuario() {
        return codigoUsuario;
    }
    /**
     * @param codigoUsuario the codigoUsuario to set
     */
    public void setCodigoUsuario(Integer codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }
    /**
     * @return the nombreUsuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    /**
     * @param nombreUsuario the nombreUsuario to set
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    /**
     * @return the apellidoUsuario
     */
    public String getApellidoUsuario() {
        return apellidoUsuario;
    }
    /**
     * @param apellidoUsuario the apellidoUsuario to set
     */
    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }
    /**
     * @return the contraseña
     */
    public String getContraseña() {
        return contraseña;
    }
    /**
     * @param contraseña the contraseña to set
     */
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    /**
     * @return the dniUsuario
     */
    public Integer getDniUsuario() {
        return dniUsuario;
    }
    /**
     * @param dniUsuario the dniUsuario to set
     */
    public void setDniUsuario(Integer dniUsuario) {
        this.dniUsuario = dniUsuario;
    }
    /**
     * @return the emailUsuario
     */
    public String getEmailUsuario() {
        return emailUsuario;
    }
    /**
     * @param emailUsuario the emailUsuario to set
     */
    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }
    /**
     * @return the tipoUsuario
     */
    public String getTipoUsuario() {
        return tipoUsuario;
    }
    /**
     * @param tipoUsuario the tipoUsuario to set
     */
    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    /**
     * @return the estado
     */
    public Boolean getEstado() {
        return estado;
    }
    /**
     * @param estado the estado to set
     */
    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}