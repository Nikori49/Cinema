package com.epam.servlet.ajax;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * AJAX Servlet that sets Session "schedulePage" attribute to value from request.
 *
 * @author Mykyta Ponomarenko
 * @version 1.0
 */
@WebServlet(name = "ChangePage", value = "/ChangePage")
public class ChangePage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("schedulePage", request.getParameter("value"));
    }

}
