package com.epam.servlet.command;

import com.epam.annotation.MyInject;
import com.epam.dao.entity.User;
import com.epam.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * Command which execute method validates and inserts User object into DB.
 *
 * @author Mykyta Ponomarenko
 * @version 1.0
 */
public class RegisterCommand implements Command {
    private final UserService userService;

    @MyInject
    public RegisterCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        if (email.isEmpty() || !email.matches("[a-z0-9._]+@[a-z0-9]+\\.[a-z]+")) {
            request.getSession().setAttribute("registerError", 1);
            return "register.jsp";
        }
        if (!Objects.equals(userService.findUserByEmail(email), null)) {
            request.getSession().setAttribute("registerError", 2);
            return "register.jsp";
        }
        String login = request.getParameter("login");
        if (login.isEmpty() || !login.matches("[a-zA-Z0-9_]{5,16}")) {
            request.getSession().setAttribute("registerError", 3);
            return "register.jsp";
        }
        if (!Objects.equals(userService.findUserByLogin(login), null)) {
            request.getSession().setAttribute("registerError", 4);
            return "register.jsp";
        }
        String phoneNumber = request.getParameter("phoneNumber");
        if (phoneNumber.isEmpty() || !phoneNumber.matches("\\+[0-9\\-]{8,17}")) {
            request.getSession().setAttribute("registerError", 5);
            return "register.jsp";
        }
        if (!Objects.equals(userService.findUserByPhoneNumber(phoneNumber), null)) {
            request.getSession().setAttribute("registerError", 6);
            return "register.jsp";
        }
        String name = request.getParameter("name");
        if (name.isEmpty()) {
            request.getSession().setAttribute("registerError", 8);
            return "register.jsp";
        }
        String surname = request.getParameter("surname");
        if (surname.isEmpty()) {
            request.getSession().setAttribute("registerError", 9);
            return "register.jsp";
        }
        String password = request.getParameter("password");
        if (password.length() < 5) {
            request.getSession().setAttribute("registerError", 10);
            return "register.jsp";
        }
        String repeatPassword = request.getParameter("repeatPassword");
        if (!password.equals(repeatPassword)) {
            request.getSession().setAttribute("registerError", 7);
            return "register.jsp";
        }
        User user = new User();
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setName(name);
        user.setSurname(surname);
        user.setLogin(login);
        user.setPassword(password);
        user.setRole("client");
        user = userService.createUser(user);
        request.getSession().setAttribute("loggedUser", user);
        return "index.jsp";
    }
}
