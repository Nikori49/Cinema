package com.epam.service;

import com.epam.dao.DBManager;
import com.epam.dao.entity.User;
import com.epam.dao.exception.DBException;

public class UserService {

    private final DBManager dbManager;

    public UserService(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public User findUserByEmail(String email){
        try {
            return dbManager.findUserByEMail(email);
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public User findUserByPhoneNumber(String phoneNumber){
        try {
            return dbManager.findUserByPhoneNumber(phoneNumber);
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public User findUserByLogin(String login){
        try {
            return dbManager.findUserByLogin(login);
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public User createUser(User user){
        try {
            return dbManager.insertUser(user);
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


}
