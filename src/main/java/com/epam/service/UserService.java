package com.epam.service;

import com.epam.annotation.Service;
import com.epam.dao.UserDAO;
import com.epam.dao.entity.User;
import com.epam.dao.exception.DBException;

@Service
public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User findUserByEmail(String email){
        try {
            return userDAO.findUserByEMail(email);
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public User findUserByPhoneNumber(String phoneNumber){
        try {
            return userDAO.findUserByPhoneNumber(phoneNumber);
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public User findUserByLogin(String login){
        try {
            return userDAO.findUserByLogin(login);
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void createUser(User user){
        try {
            userDAO.insert(user);
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public User findUserById(Long id){
        try {
            return userDAO.findById(id);
        }catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void updateBalance(Long id,Long balance){
        try {
            userDAO.updateUserBalance(id,balance);
        } catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public Long getUserBalance(Long userId){
        try {
         return userDAO.getUserBalance(userId);
        }catch (DBException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


}
