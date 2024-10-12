package org.example.dao;

import org.example.entity.administrator;

public interface administratorDAO extends GenericDAO<administrator, String> {
    Boolean CheckTheUser(String username, String password);
}