package com.epam.dao.entity;

import com.epam.dao.DBManager;

import java.util.Objects;

/**
 * Represents entry from DB table <code>users</code> .
 * <p>
 * Has the following fields:
 * <ul>
 *     <li><code>Long id</code></li>
 *     <li><code>String email</code></li>
 *     <li><code>String phoneNumber</code></li>
 *     <li><code>String name</code></li>
 *     <li><code>String surname</code></li>
 *     <li><code>String login</code></li>
 *     <li><code>String password</code></li>
 *     <li><code>String role</code></li>
 * </ul>
 * Setter and getter methods are provided.
 *
 * @author Mykyta Ponomarenko
 * @version 1.0
 * @see DBManager
 */
public class User {
    Long id;
    String email;
    String phoneNumber;
    String name;
    String surname;
    String login;
    String password;
    String role;

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, phoneNumber, name, surname, login, password, role);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
