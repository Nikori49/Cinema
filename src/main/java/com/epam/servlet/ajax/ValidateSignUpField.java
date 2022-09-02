package com.epam.servlet.ajax;

import com.epam.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Objects;

/**
 * AJAX Servlet that validates signUp page fields.
 *
 * @author Mykyta Ponomarenko
 * @version 1.0
 */
@WebServlet(name = "ValidateSignUpField", value = "/ValidateSignUpField")
public class ValidateSignUpField extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String value = request.getParameter("value");
        String type = request.getParameter("type");
        UserService userService = (UserService) request.getServletContext().getAttribute("userService");
        if (Objects.equals(type, "email")) {
            if (value.isEmpty() || !value.matches("[a-z0-9._]+@[a-z0-9]+\\.[a-z]+") || !Objects.equals(userService.findUserByEmail(value), null)) {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<span class=\"glyphicon glyphicon-remove\"></span>");
            } else {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<span class=\"glyphicon glyphicon-ok\"></span>");
            }
        }
        if (Objects.equals(type, "phoneNumber")) {
            value = value.trim();
            if (value.isEmpty() || !value.matches("[0-9\\-]{8,17}") || !Objects.equals(userService.findUserByPhoneNumber(value), null)) {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<span class=\"glyphicon glyphicon-remove\"></span>");
            } else {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<span class=\"glyphicon glyphicon-ok\"></span>");
            }
        }
        if (Objects.equals(type, "name")) {
            if (value.isEmpty()) {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<span class=\"glyphicon glyphicon-remove\"></span>");
            } else {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<span class=\"glyphicon glyphicon-ok\"></span>");
            }
        }
        if (Objects.equals(type, "surname")) {
            if (value.isEmpty()) {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<span class=\"glyphicon glyphicon-remove\"></span>");
            } else {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<span class=\"glyphicon glyphicon-ok\"></span>");
            }
        }
        if (Objects.equals(type, "login")) {
            if (value.isEmpty() || !value.matches("[a-zA-Z0-9_]{5,16}") || !Objects.equals(userService.findUserByLogin(value), null)) {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<span class=\"glyphicon glyphicon-remove\"></span>");
            } else {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<span class=\"glyphicon glyphicon-ok\"></span>");
            }
        }
        if (Objects.equals(type, "password")) {
            if (value.length() < 5) {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<span class=\"glyphicon glyphicon-remove\"></span>");
            } else {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<span class=\"glyphicon glyphicon-ok\"></span>");
            }
        }
        if (Objects.equals(type, "repeatedPassword")) {
            String secondValue = request.getParameter("value2");
            if (value.isEmpty() || !value.equals(secondValue)) {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<span class=\"glyphicon glyphicon-remove\"></span>");
            } else {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<span class=\"glyphicon glyphicon-ok\"></span>");
            }
        }
    }


}
