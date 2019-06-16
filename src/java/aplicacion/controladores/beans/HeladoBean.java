/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controladores.beans;

import aplicacion.hibernate.dao.IHeladoDAO;
import aplicacion.hibernate.dao.imp.HeladoDAOImp;
import aplicacion.modelo.dominio.Helado;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Lucas Choque
 */
@ManagedBean
@SessionScoped
/**
 * Clase que funciona como beans administrado de HeladoFormBean
 */
public class HeladoBean implements Serializable{
    /**
     * Se crea una instancia de tipo heladoDAO
     */
    private IHeladoDAO heladoDAO;
    /**
     * Se inicializa el atributo heladoDAO
     */
    public HeladoBean() {
        heladoDAO = new HeladoDAOImp();
    }
    /**
     * Metodo que crea un nuevo helado
     * @param nuevoHelado variable de tipo helado la cual se mapear con la base de datos
     */
    public void crearHelado(Helado nuevoHelado){
        heladoDAO.create(nuevoHelado);
    }
    /**
     * Metodo que elimina un helado
     * @param Helado variable de tipo helado la cual se elimina de la base de datos
     */
    public void eliminarHelado(Helado Helado){
        heladoDAO.delete(Helado);
    }
    /**
     * Metodo que modifica algunos atributos un helado
     * @param helado variable de tipo helado la cual se modificara en la base de datos
     */
    public void modificarHelado(Helado helado){
        heladoDAO.update(helado);
    }
    /**
     * Metodo para obtener todos los helados de la base de datos
     * @return lista de helados
     */
    public List<Helado> obtenerHelados(){
        return heladoDAO.getAll(Helado.class);
    }
    public IHeladoDAO getHeladoDAO() {
        return heladoDAO;
    }

    public void setHeladoDAO(IHeladoDAO heladoDAO) {
        this.heladoDAO = heladoDAO;
    }
}
