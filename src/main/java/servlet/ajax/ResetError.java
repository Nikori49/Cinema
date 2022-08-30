package servlet.ajax;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * AJAX Servlet that removes error attributes from Session.
 *
 * @author Mykyta Ponomarenko
 * @version 1.0
 */
@WebServlet(name = "ResetError", value = "/ResetError")
public class ResetError extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute("showtimeError");
        request.getSession().removeAttribute("filmError");
        request.getSession().removeAttribute("loginError");
        request.getSession().removeAttribute("registerError");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
