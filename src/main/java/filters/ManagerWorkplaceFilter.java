package filters;

import DB.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
/**
 * Filter that restricts non manager users from accessing "manager.jsp" and related pages.
 * @author Mykyta Ponomarenko
 * @version 1.0
 */
@WebFilter(filterName = "ManagerWorkplaceFilter")
public class ManagerWorkplaceFilter implements Filter {
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
            if (!Objects.equals(user.getRole(), "manager")){
                HttpServletResponse responseHttp = (HttpServletResponse) response;
                responseHttp.sendRedirect("index.jsp");
            }
        }
        chain.doFilter(request, response);
    }
}
