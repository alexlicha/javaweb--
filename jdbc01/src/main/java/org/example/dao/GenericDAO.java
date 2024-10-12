package org.example.dao;

import java.util.List;

public interface GenericDAO<T, ID> {
    T findById(ID id);
    List<T> findAll();
    void add(T entity);
    void update(T entity);
    void delete(ID id);
}