package servlet.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Command which execute method invalidates Session.
 * @author Mykyta Ponomarenko
 * @version 1.0
 */
public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        return "index.jsp";
    }
}
