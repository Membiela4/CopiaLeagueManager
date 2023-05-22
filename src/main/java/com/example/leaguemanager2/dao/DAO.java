package com.example.leaguemanager2.dao;

import com.example.leaguemanager2.modelDomain.Player;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> extends AutoCloseable {

    List<T> findAll() throws SQLException;
    T findByid(int id) throws SQLException;

    T save(T entity) throws SQLException;

    void delete(T o) throws SQLException;

}
