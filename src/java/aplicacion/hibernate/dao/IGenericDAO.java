/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.hibernate.dao;

import java.util.List;

/**
 *
 * @author Lucas Choque
 */
public interface IGenericDAO<T,ID> {
    void create(T object);
    void update(T object);
    void delete(T object);
    List<T> getAll(Class<T> object);
}