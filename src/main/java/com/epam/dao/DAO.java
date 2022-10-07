package com.epam.dao;


import com.epam.dao.entity.Entity;
import com.epam.dao.exception.DBException;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DAO {
    public Entity findById(long id) throws DBException;
    public Entity extract(ResultSet resultSet) throws SQLException;
    public void insert(Entity entity) throws DBException;
}
