package servlets.ajaxServlets;

import DB.DBManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Objects;

@WebServlet(name = "ValidateSignUpField", value = "/ValidateSignUpField")
public class ValidateSignUpField extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String value = request.getParameter("value");
        System.out.println(value);
        String type = request.getParameter("type");
        System.out.println(type);
        if(Objects.equals(type, "email")){
            if(value.isEmpty()||!value.matches("[a-z0-9._]+@[a-z0-9]+\\.[a-z]+")||!Objects.equals(DBManager.getInstance().findUserByEMail(value), null)){
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<span class=\"glyphicon glyphicon-remove\"></span>");

            }else {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<span class=\"glyphicon glyphicon-ok\"></span>");

            }
        }
        if(Objects.equals(type, "phoneNumber")){
            value=value.trim();
            if(value.isEmpty()||!value.matches("[0-9\\-]{8,17}")||!Objects.equals(DBManager.getInstance().findUserByPhoneNumber(value),null)){
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<span class=\"glyphicon glyphicon-remove\"></span>");
            }else {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<span class=\"glyphicon glyphicon-ok\"></span>");
            }
        }
        if(Objects.equals(type, "name")){
            if(value.isEmpty()){
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<span class=\"glyphicon glyphicon-remove\"></span>");
            }else {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<span class=\"glyphicon glyphicon-ok\"></span>");
            }
        }
        if(Objects.equals(type, "surname")){
            if(value.isEmpty()){
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<span class=\"glyphicon glyphicon-remove\"></span>");
            }else {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<span class=\"glyphicon glyphicon-ok\"></span>");
            }
        }
        if(Objects.equals(type, "login")){
            if(value.isEmpty()||!value.matches("[a-zA-Z0-9_]{5,16}")||!Objects.equals(DBManager.getInstance().findUserByLogin(value),null)){
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<span class=\"glyphicon glyphicon-remove\"></span>");
            }else {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<span class=\"glyphicon glyphicon-ok\"></span>");
            }
        }
        if(Objects.equals(type, "password")){
            if(value.length() < 5){
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<span class=\"glyphicon glyphicon-remove\"></span>");
            }else {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<span class=\"glyphicon glyphicon-ok\"></span>");
            }
        }
        if(Objects.equals(type, "repeatedPassword")){
            String secondValue = request.getParameter("value2");
            if(value.isEmpty()||!value.equals(secondValue)){
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<span class=\"glyphicon glyphicon-remove\"></span>");
            }else {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("<span class=\"glyphicon glyphicon-ok\"></span>");
            }
        }




    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
