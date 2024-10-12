package org.example.service;

import java.util.List;

public interface GenericService<T> {
    T getById(String id);
    List<T> getByName(String name);
    void add(T entity);
    void delete(String id);
    void update(T entity);
}
