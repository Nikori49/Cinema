package filters;

import DB.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebFilter(filterName = "LoginAndSignUpFilter")
public class LoginAndSignUpFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest requestHttp = (HttpServletRequest) request;
        User user= (User) requestHttp.getSession().getAttribute("loggedUser");

        if (user!=null){
            HttpServletResponse responseHttp = (HttpServletResponse) response;
            responseHttp.sendRedirect("index.jsp");
        }
        chain.doFilter(request, response);
    }
}
