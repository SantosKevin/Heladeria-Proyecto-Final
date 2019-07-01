/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controladores.validaciones;

import aplicacion.modelo.dominio.Helado;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Lucas Choque
 * 
 * Clase utilizada para realizar validaciones para las Ofertas
 */
public class ValidacionesOferta implements Serializable{
    
    /**
     * Metodo estatico que valida 2 fechas pasadas por parametro
     * este metodo verifica que haya una fecha inicial y una final
     * @param fechaInicial fecha inicial de la oferta
     * @param fechaFinal fecha final de la oferta
     * @return Verdadero si las fecha estan correctas. Falso si las fechas estan incorrectas
     */
    public static int validarFechasOferta(Date fechaInicial, Date fechaFinal){
        int validar = 0;
        Date fechaActual = new Date();
        if(fechaInicial.compareTo(fechaFinal) < 0 ){
            if(fechaInicial.compareTo(fechaActual) >= 0)
                validar = 1;
            else
                validar = 2;
        }else
            validar = 3;
        return validar;
    }
    /**
     * Metodo que valida y evita la posibilidad de agregar helados repetidos a una oferta
     * @param listaHelado lista de helados que van a entrar en oferta
     * @param codigoHelado codigo del helado que se desea agregar a la oferta
     * Con esos 2 parametros se valida si el usuario esta intetando agregar 2 veces el mismo helado a la oferta
     * @return Verdadero si hay helados repetidos. Falso si no hay helados repetidos
     */
    public static boolean validarHeladoRepetido(List<Helado> listaHelado, Integer codigoHelado){
        boolean validar = false;
        for(Helado h : listaHelado){
            if(h.getCodigoHelado() == codigoHelado)
                validar = true;
        }
        return validar;
    }
    /**
     * Metodo que valida la existencia de un helado
     * este metodo se asegura de que el usuario agrege solo helados existentes de la base de datos de helados
     * @param listaHelado base de datos helados
     * @param codigoHelado codigo del helado que se desea verificar
     * @return Verdadero si el helado existe en la base de datos. Falso si el helado no existe
     */
    public static boolean validarCodigoExistente(List<Helado> listaHelado, Integer codigoHelado){
        boolean validar = false;
        for(Helado h : listaHelado){
            if(h.getCodigoHelado() == codigoHelado)
                validar = true;
        }
        return validar;
    }
    /**
     * Metodo que devuelve una lista de helados
     * este metodo se asegura de que no se agregen helados repetidos a una oferta cuando se utiliza la opcion Agregar por Tipo
     * @param HeladosOferta lista de helados que estan en oferta
     * @param HeladosTipo lista de helados de un tipo en especifico que se desea agregar a la oferta
     * @return una lista de helados de un tipo en especifico eliminando si es necesario los helados que ya se encuentra en oferta
     */
    public static List<Helado> eliminarHeladoRepetidoPorTipos(List<Helado> heladosOferta, List<Helado> heladosTipo){
        List<Helado> listaAuxiliar = heladosTipo;
        for(int i=0; i<heladosOferta.size();i++){
            for(int j=0; j<heladosTipo.size();j++){
                if(heladosOferta.get(i).getCodigoHelado() == heladosTipo.get(j).getCodigoHelado())
                    listaAuxiliar.remove(j);
            }
        }
        return listaAuxiliar;
    }
}