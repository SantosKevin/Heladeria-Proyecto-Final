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
 * clase que maneja la gestion de usuario
 * @author Lucas Choque
 */
public class UsuarioDAOImp extends GenericDAOImp<Usuario, Integer> implements IUsuarioDAO, Serializable{
    /**
     * permite que la contraseña ingresada contenga caracteres como Mayusculas,minuscuas,numeros, signos
     * @param contraseña a validar
     * @return un valor booleano
     */
    @Override
    public boolean validarContraseña(String contraseña) {
       char clave; 
       boolean passCorrecta=false;
       int  contNumero=0, contLetraMay=0, contLetraMin=0, contCaracteres=0; //contadores
       for (int i = 0; i < contraseña.length(); i++) { //recorremos la cadena de contraseña
                clave = contraseña.charAt(i); //asignando cada caracter de "contraseña" a clave
               String passValue = String.valueOf(clave); // convertimos clave a string
                if (passValue.matches("[A-Z]")) { //y verificamos que el caracter en la posicion indicada
                    contLetraMay++;               // contenga al menos una mayuscula en este caso.
                }
                if (passValue.matches("[a-z]")) { //se verifica que contenga al menos una minuscula
                    contLetraMin++;
                }
                if (passValue.matches("[0-9]")) { //al menos un numero
                    contNumero++;
                }
                if(passValue.matches("[-_]")){ // y al menos uno de estos signos especiales
                    contCaracteres++;
                }
        }
       if(contNumero>0 && contCaracteres>0 && contLetraMay>0 && contLetraMin>0){
           passCorrecta=true;}
        return passCorrecta;
    }
    /**
     * permite verificar que el email ingresado tenga un dominio (gmail) correcto
     * @param email a validar
     * @return un booleano en true o false
     */
    @Override
    public boolean validarEmail(String email) {
        int contArroba=0; //contador de arrobas
        char emailAux; //un caracter que irá guardando posicion a posicion los caracteres de "email"
        boolean emailCorrecto=false;
        for(int i=0;i<email.length();i++){
            emailAux = email.charAt(i);
                if (emailAux == '@') { //se verifica que contenga el @
                    contArroba++;
                }
        }
        if(contArroba==1){ // si hay solo un @, procede a evaluar el dominio, en caso contrario termina la operacion
            int posicionArroba=email.indexOf("@"); //capturamos el lugar donde esta el @
            String dominio = email.substring(posicionArroba, email.length()); // y hacemos una subcadena con el dominio
            if(dominio.equals("@gmail.com") || dominio.equals("@hotmail.com")){
                emailCorrecto = true;}
        }
        return emailCorrecto;
    }
    /**
     * validar que el email ingresado no este repetido
     * @param email a evaluar
     * @return un objeto tipo usuario ya sea en null (si no esta repetido) o asignado
     */
    @Override
    public Usuario validarEmailExistente(String email) {
       Session session = HibernateUtil.getSessionFactory().openSession(); //abrimos una sesion
        Criteria criteria = session.createCriteria(Usuario.class); //criteria, es para hacer una consulta
        criteria.add(Restrictions.like("emailUsuario", email)); //filtramos la busqueda por emails  
        Usuario usu = null;
        if(!criteria.list().isEmpty()){ //verificamos que la lista no este vacia
            usu = (Usuario)criteria.list().get(0);
        }
        session.close();
        return usu;
    }
    
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
