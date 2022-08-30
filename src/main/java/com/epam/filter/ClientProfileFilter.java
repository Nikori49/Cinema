package com.epam.filter;

import com.epam.dao.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
/**
 * Filter that restricts non client users from accessing "client.jsp".
 * @author Mykyta Ponomarenko
 * @version 1.0
 */
@WebFilter(filterName = "ClientProfileFilter")
public class ClientProfileFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest requestHttp = (HttpServletRequest) request;
        User user= (User) requestHttp.getSession().getAttribute("loggedUser");

        if (user==null){
            HttpServletResponse responseHttp = (HttpServletResponse) response;
            responseHttp.sendRedirect("index.jsp");
        }else {
            if (!Objects.equals(user.getRole(), "client")){
                HttpServletResponse responseHttp = (HttpServletResponse) response;
                responseHttp.sendRedirect("index.jsp");
            }
        }
        chain.doFilter(request, response);

    }
}
