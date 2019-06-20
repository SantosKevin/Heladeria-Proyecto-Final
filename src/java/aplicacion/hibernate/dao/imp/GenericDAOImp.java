/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.hibernate.dao.imp;

import aplicacion.hibernate.configuracion.HibernateUtil;
import aplicacion.hibernate.dao.IGenericDAO;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 *
 * @author Lucas Choque
 */
public class GenericDAOImp<T, ID extends Serializable> implements IGenericDAO<T, ID>{
    
    /**
     * El metodo create permite agregar datos a nuestra base de datos
     * creamos un objeto de tipo Session, y le asignamos la sesion actual
     * con el objecto session, empezamos una transaccion hacia la base de datos
     * con el metodo save, le pasamos por parametro el objecto que deseamos persistir
     * finalmente hacemos un commit, y finalizamos la transaccion
     * @param object objecto que deseamos persistir
     */
    
    public GenericDAOImp() {
    }

    @Override
    public void create(T object) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(object);
        session.getTransaction().commit();
        session.close();
    }
    /**
     * El metodo update permite modificar los datos de un objecto en nuestra base de datos
     * al igual que en el metodo create, instanciamos un objecto de tipo Session
     * a diferencia de que en lugar de usar el metodo save, usamos el metodo update, solo para actualizar los datos
     * @param object objecto que deseamos modificar
     */
    @Override
    public void update(T object) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(object);
        session.getTransaction().commit();
        session.close();
    }
    /**
     * El metodo delete permite eliminar un objecto en nuestra base de datos
     * solo se hace un borrado logico, lo unico que hacemos es cambiar el estado 
     * del objecto en false, por eso se utiliza el metodo update, y no delete
     * @param object 
     */
    @Override
    public void delete(T object) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(object);
        session.getTransaction().commit();
        session.close();
    }
    /**
     * el metodo getALL lo que hace es devolvernos todos los datos de una tabla
     * instanciamos un objecto de tipo Criteria, y mediante ese objecto obtenemos la lista
     * Esa lista la almacenamos en un lista para luego retornarla
     * @param object
     * @return 
     */
    @Override
    public List<T> getAll(Class<T> object) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(object);
        List<T> objectos = criteria.list();
        session.close();
        return objectos;
    }
}